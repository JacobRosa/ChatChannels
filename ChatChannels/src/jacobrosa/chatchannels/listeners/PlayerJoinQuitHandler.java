package jacobrosa.chatchannels.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import jacobrosa.chatchannels.ChatChannels;
import jacobrosa.chatchannels.utils.SocialSpyHandler;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerJoinQuitHandler implements Listener{

	@EventHandler
	public void onPlayerJoinCC(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SocialSpyHandler.onJoin(player);
		if(ChatChannels.hasUpdate())
			if(player.isOp()) {
				TextComponent msg = new TextComponent("[ChatChannels] Update available! Click for download link!");
				msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/chatchannels.67743"));
				player.spigot().sendMessage(msg);
			}
	}

	@EventHandler
	public void onPlayerQuitCC(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		SocialSpyHandler.onQuit(player);
	}

}
