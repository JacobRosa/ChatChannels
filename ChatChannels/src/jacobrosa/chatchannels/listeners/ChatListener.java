package jacobrosa.chatchannels.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import jacobrosa.chatchannels.utils.ChatHandler;
import jacobrosa.chatchannels.utils.Permissions;
import jacobrosa.chatchannels.utils.Prefix;
import jacobrosa.chatchannels.utils.SocialSpyHandler;

public class ChatListener implements Listener{

	private static boolean isChatMuted = false;

	public static boolean isChatMuted() {
		return isChatMuted;
	}

	public static void setChatMuted(boolean muted) {
		isChatMuted = muted;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatEventCC(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		String message = event.getMessage();
		ChatChannel channel = ChatHandler.getChatChannel(player);
		if(ChatListener.isChatMuted()) {
			if(channel != ChatChannel.Staff) {
				if(!player.hasPermission(Permissions.bypassMutedChat)) {
					event.setCancelled(true);
					player.sendMessage(Prefix.chat + "§7Chat is currently muted!");
					return;
				}
			}
		}
		if(channel == ChatChannel.Global) {
			event.setFormat(ChatHandler.getChatPrefix(player) + " §7" + player.getDisplayName() + " §8» §f" + message);
			return;
		}else if(channel == ChatChannel.Local) {
			List<UUID> near = new ArrayList<UUID>();
			double radius = ChatHandler.getLocalRadius();
			for(Entity entity : player.getNearbyEntities(radius, radius, radius))
				if((entity instanceof Player))
					near.add(entity.getUniqueId());
			Iterator<Player> iter = event.getRecipients().iterator();
			while(iter.hasNext()) {
				Player tempPlayer = iter.next();
				UUID tempPlayerUUID = tempPlayer.getUniqueId();
				if(!near.contains(tempPlayerUUID) && !uuid.equals(tempPlayerUUID))
					iter.remove();
			}
			for(UUID tempUUID : SocialSpyHandler.getSocialSpyList()) {
				Player tempPlayer = Bukkit.getPlayer(tempUUID);
				if(!event.getRecipients().contains(tempPlayer))
					if(tempPlayer.hasPermission(Permissions.commandSocialSpy))
						tempPlayer.sendMessage("§aSocialSpy §7" + player.getName() + " §8» §f" + message);
			}
			event.setFormat(ChatHandler.getChatPrefix(player) + " §7" + player.getDisplayName() + " §8» §f" + message);
			return;
		}else if(channel == ChatChannel.World) {
			World world = player.getWorld();
			Iterator<Player> iter = event.getRecipients().iterator();
			while(iter.hasNext()) {
				Player tempPlayer = iter.next();
				World tempPlayerWorld = tempPlayer.getWorld();
				if(!world.getName().equals(tempPlayerWorld.getName()))
					iter.remove();
			}
			for(UUID tempUUID : SocialSpyHandler.getSocialSpyList()) {
				Player tempPlayer = Bukkit.getPlayer(tempUUID);
				if(!event.getRecipients().contains(tempPlayer))
					if(tempPlayer.hasPermission(Permissions.commandSocialSpy))
						tempPlayer.sendMessage("§aSocialSpy " + player.getName() + " §8» §f" + message);
			}
			event.setFormat(ChatHandler.getChatPrefix(player) + " §7" + player.getDisplayName() + " §8» §f" + message);
			return;
		}else if(channel == ChatChannel.Staff) {
			Iterator<Player> iter = event.getRecipients().iterator();
			while(iter.hasNext()) {
				Player tempPlayer = iter.next();
				if(!tempPlayer.hasPermission(Permissions.commandStaffChat))
					iter.remove();
			}
			event.setFormat(ChatHandler.getChatPrefix(player) + " §7" + player.getDisplayName() + " §8» §f" + message);
			return;
		}
	}

}
