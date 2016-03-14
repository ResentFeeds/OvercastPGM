package overcast.pgm.match;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.w3c.dom.Document;

import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import overcast.pgm.OvercastPGM;
import overcast.pgm.WinManager;
import overcast.pgm.event.MatchBeginEvent;
import overcast.pgm.event.MatchLoadEvent;
import overcast.pgm.generator.NullChunkGenerator;
import overcast.pgm.map.Map;
import overcast.pgm.module.MatchModuleContext;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.module.modules.objective.wool.WoolObjective;
import overcast.pgm.module.modules.observers.ObserverModule;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamModule;
import overcast.pgm.module.modules.timelimit.TimeModule;
import overcast.pgm.module.modules.tutorial.TutorialManager;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.scoreboard.ScoreboardHandler;
import overcast.pgm.timer.MatchTimer;
import overcast.pgm.timer.StartTimer;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.FileUtils;
import overcast.pgm.util.Log;
import overcast.pgm.util.StringUtils;
import overcast.pgm.util.TeamUtil;
import overcast.pgm.util.TimeUtil;

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
	private ScoreboardHandler sbhandler;
	private TimeModule timerModule;

	// class for handling the winner of the match
	private WinManager winM;

	/** state of the match */

	public Match(OvercastPGM pgm, int id, Map map) {
		this.pgm = pgm;
		this.id = id;
		this.map = map;
		this.handler = new MatchHandler(this);

		this.state = MatchState.LOAD;
		this.winM = new WinManager(this);
		this.modules = new ModuleCollection<>();
		this.next = pgm.getRotation().getRotationMaps().get(pgm.getRotation().getPostion());
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
		this.sbhandler = new ScoreboardHandler(this);

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
		this.getPluginManager().callEvent(new MatchLoadEvent(this));
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

	public ScoreboardHandler getScoreboardHandler() {
		return this.sbhandler;
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
		MatchBeginEvent event = new MatchBeginEvent(this);
		Bukkit.getPluginManager().callEvent(event);

		boolean loaded = this.getModules().isModuleLoaded(TimeModule.class);

		if (loaded) {
			this.timerModule = this.getModules().getModule(TimeModule.class);
			this.timerModule.create();
			this.timerModule.run();
		}

		this.mTimer = new MatchTimer(-1, this);
		this.mTimer.run();
	}

	public void end(Team winner) {
		if (this.state == MatchState.RUNNING) {

			this.setState(MatchState.ENDED);
			this.context.unload();


			this.pgm.refreshTeams();
			
			for (Module module : this.modules) {
				if (module instanceof ObserverModule) {
					continue;
				}
				this.factory.removeModule(module);
			}

			if (winner != null) {
				Bukkit.broadcastMessage(winner.getColoredName() + ChatColor.WHITE + "Wins");
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + "The match has ended");
			}
		}
	}

	public boolean isStarting() {
		return this.state == MatchState.START;
	}

	/** the match info */

	public void matchInfo(OvercastPlayer viewer) {
		viewer.sendMessage(BukkitUtils.dashedChatMessage(ChatColor.RESET + " Match Info", "-",
				ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH));

		boolean haveGameInfo = isRunning() || isEnding();

		boolean wools = getModules().isModuleLoaded(WoolObjective.class);

		String time = ChatColor.DARK_PURPLE + "Time: " + ChatColor.GOLD;
		
		if (haveGameInfo) {
			time += TimeUtil.formatIntoHHMMSS(this.mTimer.getSeconds());
		} else {
			time += TimeUtil.formatIntoHHMMSS(0);
		}

		viewer.sendMessage(time);

		boolean teams = getModules().isModuleLoaded(TeamModule.class);

		HashMap<Team, Integer> teamCounts = new HashMap<>();
		TeamModule teamMod = TeamUtil.getTeamModule();
		if (teams) {
			for (Team team : TeamUtil.getTeamModule().getTeams()) {
				if (team != null) {
					teamCounts.put(team, team.getMembers().size());
				}
			}
		}

		if (!teamCounts.isEmpty()) {
			String message = "";
			for (Entry<Team, Integer> entry : teamCounts.entrySet()) {
				message += entry.getKey().getColoredName() + ChatColor.WHITE + " " + entry.getValue() + ChatColor.GRAY
						+ "/" + entry.getKey().getMax() + ChatColor.DARK_GRAY + " | ";
			}

			Team obs = teamMod.getObservers();
			String format = message + obs.getColoredName() + ChatColor.GRAY + ": " + ChatColor.WHITE + ""
					+ obs.getMembers().size();
			viewer.sendMessage(format);
		}

		if (haveGameInfo) {
			HashMap<Team, Collection<WoolObjective>> objectives = new HashMap<>();
			for (Team team : TeamUtil.getTeamModule().getTeams()) {
				if (team != null) {
					// FIXME make this use all types of objectves :)
					if (wools) {
						objectives.put(team, WoolObjective.getObjectives(team));
					}  
				}
			}

			if (!objectives.isEmpty()) {
				viewer.sendMessage(ChatColor.DARK_PURPLE + "Goals: "); 
				for (Entry<Team, Collection<WoolObjective>> entry : objectives.entrySet()) {
					viewer.sendMessage(entry.getKey().getColoredName() + ChatColor.GRAY + ": "
							+ Joiner.on(" ").join(StringUtils.to(entry.getValue())));
				}
			}
		}
	}

	/** if the current state of the match is currently ending */
	public boolean isEnding() {
		return this.state == MatchState.ENDED;
	}

	/** get the next map */
	public Map getNext() {
		return this.next;
	}

	public TimeModule getTimeModule() {
		return this.timerModule;
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

	// return the WinManager class

	public WinManager getWinManager() {
		return this.winM;
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

	public void broadcast(String string) {
		Bukkit.broadcastMessage(string);
	}

	public MatchTimer getMatchTimer() {
		return this.mTimer;
	}

}