package overcast.pgm;

import java.io.File;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

import bukkit.util.CommandsManagerRegistration;
import overcast.pgm.channels.Channel;
import overcast.pgm.channels.ChannelFactory;
import overcast.pgm.command.CubeCommand;
import overcast.pgm.commands.ChatCommands;
import overcast.pgm.commands.JoinCommand;
import overcast.pgm.commands.KitsCommand;
import overcast.pgm.commands.LoadedMapsCommand;
import overcast.pgm.commands.MapCommands;
import overcast.pgm.commands.MapInfoCommand;
import overcast.pgm.commands.MyTeamCommand;
import overcast.pgm.commands.NickCommand;
import overcast.pgm.commands.RotationCommand;
import overcast.pgm.config.types.Config;
import overcast.pgm.listener.ChatListener;
import overcast.pgm.listener.ConnectionListener;
import overcast.pgm.listener.ListenerList;
import overcast.pgm.listener.MatchListener;
import overcast.pgm.listener.PlayerListener;
import overcast.pgm.map.MapLoader;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModuleContext;
import overcast.pgm.module.modules.info.Version;
import overcast.pgm.module.modules.team.TeamManager;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.rotation.Rotation;
import overcast.pgm.util.Log;
import overcast.pgm.util.TeamUtil;

/** make class for team handling */

//TODO finish up all current Modules 

//** TODO fix authors in loaded maps and rotation pages being "null"

//TODO timers 

//TODO match ending, cycling

//TODO Scoreboards, Objectives 

//TODO may add more to the list */

//TODO find a way to make the modules load diffferent times */

//TODO work on RegionModule, KitModule, FilterModule :)

//TODO make all commands using the Command Framework

/**
 * make finish all the currrent Modules;
 * 
 * @author tylern
 *
 */

public class OvercastPGM extends JavaPlugin {

	public static OvercastPGM instance;

	private Config config;

	private MapLoader loader;

	private PluginManager pm;

	private File rotationFolder;

	/** latest protocol version */

	private Version XML_PROTO = new Version(1, 4, 0);

	public Rotation rotation;

	private Match match;

	private CommandsManager<CommandSender> commands;

	public void onEnable() {
		instance = this;
		this.pm = this.getServer().getPluginManager();
		loadChannels();

		this.config = new Config("config.yml");
		if (this.getConfiguration().isPlayable()) {
			this.rotationFolder = new File("rotation");

			/**
			 * create rotation folder if it doesn't exist or its not a director
			 */
			if (!this.rotationFolder.exists() || !this.rotationFolder.isDirectory()) {
				this.rotationFolder.mkdir();
			}

			try {
				this.loader = new MapLoader(this.getRotationFolder());
			} catch (Exception e) {
				e.printStackTrace();
			}

			addOvercastPlayers();

			File rotationYML = new File(this.getDataFolder(), "rotation.yml");
			this.rotation = new Rotation(rotationYML, this.loader);

			this.match = new Match(this, 1, this.rotation.getRotationMaps().get(0));
			setupCommands(); 
			refreshTeams();
			loadListeners();
		}

		if (this.getConfiguration().isDevelopment()) {
			Log.info("development");
		}
	}

	private void setupCommands() {
		this.commands = new CommandsManager<CommandSender>() {
			@Override
			public boolean hasPermission(CommandSender sender, String perm) {
				return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
			}
		};

		CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
		cmdRegister.register(MapCommands.class);
		cmdRegister.register(JoinCommand.class);
		cmdRegister.register(LoadedMapsCommand.class);
		cmdRegister.register(KitsCommand.class);
		cmdRegister.register(NickCommand.class);
		cmdRegister.register(ChatCommands.class);
		cmdRegister.register(RotationCommand.class);
		cmdRegister.register(CubeCommand.class);
		cmdRegister.register(MyTeamCommand.class);
		cmdRegister.register(MapInfoCommand.class);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED + "You don't have permission.");
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		} catch (com.sk89q.minecraft.util.commands.CommandException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void addOvercastPlayers() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != null) {
				OvercastPlayer newOCNPlayer = new OvercastPlayer(p);
				newOCNPlayer.add();
			}
		}
	}

	public void onDisable() {
		instance = null;

		if (getMatch() != null) {
			if (getMatch().isRunning() || getMatch().isLoading()) { 
				MatchModuleContext context = getMatch().getContext();
				context.disable();
			}
		}

		removeOvercastPlayers();

	}

	public Match getMatch() {
		return this.match;
	}

	private void removeOvercastPlayers() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			OvercastPlayer ocnPlayer = OvercastPlayer.getPlayers(player);

			if (ocnPlayer != null) {
				ocnPlayer.remove();
			}
		}
	}

	public void addCommand(String name, CommandExecutor command) {
		this.getCommand(name).setExecutor(command);
	}

	public void addListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	public static OvercastPGM getInstance() {
		return instance;
	}

	public Config getConfiguration() {
		return this.config;
	}

	public MapLoader getLoader() {
		return this.loader;
	}

	/** load listeners */

	public void loadListeners() {
		ListenerList<Listener> listeners = new ListenerList<>();

		listeners.add(new PlayerListener());
		listeners.add(new ConnectionListener());
		listeners.add(new ChatListener());
		listeners.add(new MatchListener());

		for (Listener listener : listeners) {
			registerListener(listener);
		}
	}

	public void registerListener(Listener listener) {
		this.getServer().getPluginManager().registerEvents(listener, this);
	}

	public PluginManager getPluginManager() {
		return this.pm;
	}

	public void refreshTeams() {
		for (OvercastPlayer players : OvercastPlayer.getPlayers()) {
			if (players != null) {
				if (!players.hasTeam()
						|| players.hasTeam() && !players.getTeam().equals(TeamUtil.getTeamModule().getObservers())) {
					TeamManager.addPlayer(TeamUtil.getTeamModule().getObservers(), players);
				}
			}
		}
	}

	public void loadChannels() {
		new ChannelFactory();
	}

	public void addALL() {
		for (OvercastPlayer players : OvercastPlayer.getPlayers()) {
			if (players != null && getConfiguration().getDefaultChannel() != null) {
				Channel channel = getConfiguration().getDefaultChannel();
				if (players.hasChannel()) {
					Channel old = players.getChannel();
					old.removeMember(players);
					channel.addMember(players);
				} else {
					channel.addMember(players);
				}
			}
		}
	}

	/** disable crafting for a certain itemstack */
	public void removeDefaultCraft(ItemStack item) {
		Iterator<Recipe> it = Bukkit.getServer().recipeIterator();

		while (it.hasNext()) {
			ItemStack result = it.next().getResult();
			if (result.isSimilar(item)) {
				it.remove();
			}
		}
	}

	public File getRotationFolder() {
		return this.rotationFolder;
	}

	public Version getXMLProto() {
		return this.XML_PROTO;
	}

	public Rotation getRotation() {
		return this.rotation;
	}
}
