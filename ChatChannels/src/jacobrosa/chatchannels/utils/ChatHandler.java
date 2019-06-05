package jacobrosa.chatchannels.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import jacobrosa.chatchannels.listeners.ChatChannel;

public class ChatHandler {

	private static int localRadius = 100;
	private static ChatChannel defaultChannel;
	
	private static HashMap<UUID, ChatChannel> playerChannel;
	
	public static void setup() {
		playerChannel = new HashMap<UUID, ChatChannel>();
		
		if(!ConfigManager.getConfig().contains("config.defaultchannel")) {
			ConfigManager.getConfig().set("config.defaultchannel", "global");
			return;
		}else {
			String defaultChannelInput = ConfigManager.getConfig().getString("config.defaultchannel");
			if(defaultChannelInput.equalsIgnoreCase("global")) {
				defaultChannel = ChatChannel.Global;
				return;
			}else if(defaultChannelInput.equalsIgnoreCase("local")) {
				defaultChannel = ChatChannel.Local;
				return;
			}else if(defaultChannelInput.equalsIgnoreCase("world")) {
				defaultChannel = ChatChannel.World;
				return;
			}else {
				defaultChannel = ChatChannel.Global;
				return;
			}
		}
	}

	public static void handlePlayerJoin(UUID uuid) {
		playerChannel.put(uuid, defaultChannel);
	}

	public static void handlePlayerQuit(UUID uuid) {
		playerChannel.remove(uuid);
	}

	public static void setChatChannel(Player player, ChatChannel channel) {
		playerChannel.put(player.getUniqueId(), channel);
		player.sendMessage(Prefix.chat + "§7Chat channel set to " + getChatPrefix(player));
	}

	public static ChatChannel getChatChannel(Player player) {
		UUID uuid = player.getUniqueId();
		if(playerChannel.containsKey(uuid))
			return playerChannel.get(uuid);
		else {
			playerChannel.put(uuid, defaultChannel);
			return ChatChannel.Global;
		}
	}

	public static String getChatPrefix(Player player) {
		ChatChannel channel = getChatChannel(player);
		String prefix = "";
		switch(channel) {
		case Global:
			prefix = "§bGlobal";
			break;
		case Local:
			prefix = "§eLocal";
			break;
		case World:
			prefix = "§6World";
			break;
		case Staff:
			prefix = "§cStaff";
			break;
		default:
			prefix = "";
			break;
		}
		return prefix;
	}
	
	public static int getLocalRadius() {
		return localRadius;
	}

}
