package jacobrosa.chatchannels;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import jacobrosa.chatchannels.commands.CommandChatChannel;
import jacobrosa.chatchannels.listeners.ChatListener;
import jacobrosa.chatchannels.listeners.PlayerJoinQuitHandler;
import jacobrosa.chatchannels.utils.ChatHandler;
import jacobrosa.chatchannels.utils.SocialSpyHandler;

public class ChatChannels extends JavaPlugin{
	
	private static String name, version;

	private static Plugin plugin = null;

	@Override
	public void onEnable() {
		plugin = this;
		
		name = getDescription().getName();
		version = getDescription().getVersion();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new PlayerJoinQuitHandler(), this);

		getCommand("chatchannel").setExecutor(new CommandChatChannel());

		ChatHandler.setup();
		SocialSpyHandler.setup();
	}

	@Override
	public void onDisable() {
		plugin = null;
	}

	public static String getPluginName() {
		return name;
	}
	
	public static String getVersion() {
		return version;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}

}
