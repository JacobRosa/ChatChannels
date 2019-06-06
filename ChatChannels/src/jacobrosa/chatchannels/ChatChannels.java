package jacobrosa.chatchannels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import jacobrosa.chatchannels.commands.CommandChatChannel;
import jacobrosa.chatchannels.listeners.ChatListener;
import jacobrosa.chatchannels.listeners.PlayerJoinQuitHandler;
import jacobrosa.chatchannels.utils.ChatHandler;
import jacobrosa.chatchannels.utils.ConfigManager;
import jacobrosa.chatchannels.utils.SocialSpyHandler;

public class ChatChannels extends JavaPlugin{

	private static String name, version, updateCheckerPath = "config.updateChecker";

	private static Plugin plugin = null;

	private static boolean hasUpdate;

	@Override
	public void onEnable() {
		plugin = this;
		
		Metrics metrics = new Metrics(this);

		name = getDescription().getName();
		version = getDescription().getVersion();

		hasUpdate = false;

		if(ConfigManager.getConfig().contains(updateCheckerPath)) {
			boolean checkForUpdates = ConfigManager.getConfig().getBoolean(updateCheckerPath);
			if(checkForUpdates)
				checkupdates.start();
		}else {
			ConfigManager.getConfig().set(updateCheckerPath, true);
			checkupdates.start();
		}

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

	public static boolean hasUpdate() {
		return hasUpdate;
	}

	Thread checkupdates = new Thread() {

		public void run() {
			URL url = null;
			try {
				url = new URL("https://api.spigotmc.org/legacy/update.php?resource=67743");
			}catch(MalformedURLException e) {}

			URLConnection connection = null;
			try {
				connection = url.openConnection();
			}catch(IOException e) {}

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				if(br.readLine().equals(getVersion())) {
					hasUpdate = false;
					Bukkit.getLogger().info("No updates available.");
				}else {
					hasUpdate = true;
					Bukkit.getLogger().info("Plugin update available at https://www.spigotmc.org/resources/chatchannels.67743");
				}
			}catch(IOException e) {}
		}

	};

}
