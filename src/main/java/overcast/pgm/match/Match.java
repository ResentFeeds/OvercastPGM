package overcast.pgm.match;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.w3c.dom.Document;

import static org.bukkit.ChatColor.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import overcast.pgm.OvercastPGM;
import overcast.pgm.event.MatchLoadEvent;
import overcast.pgm.generator.NullChunkGenerator;
import overcast.pgm.map.Map;
import overcast.pgm.module.MatchModuleContext;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamModule;
import overcast.pgm.module.modules.timelimit.TimeModule;
import overcast.pgm.module.modules.tutorial.TutorialManager;
import overcast.pgm.timer.MatchTimer;
import overcast.pgm.timer.StartTimer;
import overcast.pgm.util.FileUtils;
import overcast.pgm.util.Log;

public class Match {

	public OvercastPGM pgm;
	/** main class */

	public int id;
	/** id of the match */

	private Map map;
	/** the current map */

	private Map next = null;

	private ModuleFactory factory;

	private MatchModuleContext context;

	private PluginManager pm;
	/** plugin manager */

	private MatchState state;

	private MatchHandler handler;

	private World world = null;

	private ModuleCollection<Module> modules;
	private TutorialManager tutManager;
	private MatchTimer mTimer;

	/** state of the match */

	public Match(OvercastPGM pgm, int id, Map map) {
		this.pgm = pgm;
		this.id = id;
		this.map = map;
		loadMap(map, id++);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.teleport(getWorld().getSpawnLocation());
		}

		this.state = MatchState.LOAD;
		this.modules = new ModuleCollection<>();
		this.next = pgm.getRotation().getRotationMaps().get(pgm.getRotation().getPostion());
		this.handler = new MatchHandler(this);
		this.factory = new ModuleFactory(getDocument());

		StartTimer timer = new StartTimer(30, this);
		timer.run();

		loadModules();
		this.move();
		this.pm = this.pgm.getPluginManager();
		this.context = new MatchModuleContext();
		this.context.addMatchModules(this);
		load();
		this.context.enable();
		this.tutManager = new TutorialManager(this);

	}

	public void loadModules() {
		for (ModuleStage stage : ModuleStage.time()) {
			for (Module module : getFactory().build(getDocument(), stage)) {
				this.modules.add(module);
			}
		}
	}

	public void load() {
		this.context.load();
		this.getPluginManager().callEvent(new MatchLoadEvent(this, this.map));
	}

	public void loadMap(Map map, int id) {
		try {
			File mapFolder = OvercastPGM.getInstance().getRotationFolder();
			File src = new File(mapFolder.getName(), map.getInfo().getName());
			File dest = new File(src.getParentFile().getParent(), "match-" + src.getName());
			FileUtils.copyFolder(src, dest);
			String matchName = "match-" + src.getName();
			new File(matchName + File.separator + "level.dat").delete();
			WorldCreator creator = WorldCreator.name(dest.getName());
			creator.generator(new NullChunkGenerator());
			World world = creator.createWorld();
			world.setSpawnFlags(false, false);
			world.setMonsterSpawnLimit(0);
			world.setAutoSave(false);
			setWorld(world);
		} catch (IOException e) {
			Log.info("[OvercastPGM] Error while loading map: " + map.getInfo().getName());
			e.printStackTrace();
		}

	}

	public Document getDocument() {
		return this.getMap().getDocument();
	}

	public MatchModuleContext getContext() {
		return this.context;
	}

	public ModuleFactory getFactory() {
		return this.factory;
	}

	public PluginManager getPluginManager() {
		return this.pm;
	}

	/** register match events */
	public void registerEvents(Listener listener) {
		getPluginManager().registerEvents(listener, this.pgm);
	}

	/** set the state of the match */
	public void setState(MatchState state) {
		this.state = state;
	}

	/** start the match */
	public void start() {
		setState(MatchState.RUNNING);
		Bukkit.broadcastMessage(GREEN + "The match has started");

		boolean loaded = this.getModules().isModuleLoaded(TimeModule.class);

		if (loaded || !this.map.isObjectives()) {
			TimeModule timeModule = this.getModules().getModule(TimeModule.class);
			timeModule.create();
			timeModule.run();
		} else {
			this.mTimer = new MatchTimer(-1, this);
		}

		TeamModule teamModule = this.getModules().getModule(TeamModule.class); 
		if (this.mTimer != null && teamModule.hasEnoughPlayers()) {
			this.mTimer.run();
		}
	}

	/** get the next map */
	public Map getNext() {
		return this.next;
	}

	/** get the current state of the match */
	public MatchState getState() {
		return this.state;
	}

	/** get the current map */
	public Map getMap() {
		return this.map;
	}

	/** class handling the match when it unloads a new map */
	public MatchHandler getMatchHandler() {
		return this.handler;
	}

	/** setting the next map */
	public void setNext(Map next) {
		this.next = next;
	}

	/** setting the current map */
	public void setCurrent(Map map) {
		this.map = map;
	}

	// TODO change this
	public void move() {
		this.pgm.getRotation().move();
		int position = this.pgm.getRotation().getPostion();
		Map map = this.pgm.getRotation().getRotationMaps().get(position);
		setNext(map);
	}

	/** if the current state of the match is currently running */

	public boolean isRunning() {
		return this.state == MatchState.RUNNING;
	}

	/** if the current state of the match is currently loading */
	public boolean isLoading() {
		return this.state == MatchState.LOAD;
	}

	public TutorialManager getTutorialManager() {
		return this.tutManager;
	}

	public void disable() {
		this.context.disable();
	}

	public void setWorld(World w) {
		this.world = w;
	}

	public World getWorld() {
		return this.world;
	}

	public ModuleCollection<Module> getModules() {
		return this.modules;
	}
}