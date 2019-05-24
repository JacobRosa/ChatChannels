package jacobrosa.chatchannels.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import jacobrosa.chatchannels.ChatChannels;

public class PlayerData {

	public static boolean hasPlayerDataFile(Player player) {
		File userdata = new File(ChatChannels.getPlugin().getDataFolder(), File.separator + "PlayerData");
		File f = new File(userdata, File.separator + player.getUniqueId() + ".yml");
		return f.exists();
	}

	public static void setPlayerDataFileValue(Player player, String path, Object value){
		File userdata = new File(ChatChannels.getPlugin().getDataFolder(), File.separator + "PlayerData");
		File f = new File(userdata, File.separator + player.getUniqueId() + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		playerData.set(path, value);
		try { 
			playerData.save(f); 
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	public static FileConfiguration getPlayerDataFile(Player player) { 
		File userdata = new File(ChatChannels.getPlugin().getDataFolder(), File.separator + "PlayerData");
		File f = new File(userdata, File.separator + player.getUniqueId() + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) 
			try { 
				f.createNewFile(); 
			} catch (IOException e) { }
		return playerData;
	}

}
