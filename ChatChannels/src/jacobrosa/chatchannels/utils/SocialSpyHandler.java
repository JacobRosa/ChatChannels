package jacobrosa.chatchannels.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import jacobrosa.chatchannels.listeners.PlayerData;
import net.md_5.bungee.api.ChatColor;

public class SocialSpyHandler {

	private static final String path = "settings.socialspy";
	
	private static List<UUID> socialSpy;

	//Handles reloads
	public static void setup() {
		socialSpy = new ArrayList<UUID>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			UUID uuid = player.getUniqueId();
			if(hasSocialSpyEnabled(player))
				if(!socialSpy.contains(uuid))
					socialSpy.add(uuid);
		}
	}

	//Handles player join
	public static void onJoin(Player player) {
		UUID uuid = player.getUniqueId();
		if(hasSocialSpyEnabled(player))
			if(!socialSpy.contains(uuid))
				socialSpy.add(uuid);
	}

	//Handles player quit
	public static void onQuit(Player player) {
		UUID uuid = player.getUniqueId();
		if(socialSpy.contains(uuid))
			socialSpy.remove(uuid);
	}
	
	public static List<UUID> getSocialSpyList(){
		return socialSpy;
	}

	public static void setSocialSpy(Player player, boolean bln) {
		PlayerData.setPlayerDataFileValue(player, path, bln);
		UUID uuid = player.getUniqueId();
		if(bln)
			if(!socialSpy.contains(uuid))
				socialSpy.add(uuid);
		if(!bln)
			if(socialSpy.contains(uuid))
				socialSpy.remove(uuid);
		player.sendMessage(Prefix.command + ChatColor.GRAY + "Social spy set to " + (bln ? ChatColor.GREEN : ChatColor.RED) + bln);
	}

	public static boolean hasSocialSpyEnabled(Player player) {
		//If player doesn't have data file don't take up space making them one they don't need.
		if(!PlayerData.hasPlayerDataFile(player))
			return false;
		if(!PlayerData.getPlayerDataFile(player).contains(path))
			return false;
		return PlayerData.getPlayerDataFile(player).getBoolean(path);
	}

}
