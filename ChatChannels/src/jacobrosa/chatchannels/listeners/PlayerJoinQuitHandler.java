package jacobrosa.chatchannels.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import jacobrosa.chatchannels.utils.SocialSpyHandler;

public class PlayerJoinQuitHandler implements Listener{
	
	@EventHandler
	public void onPlayerJoinCC(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SocialSpyHandler.onJoin(player);
	}
	
	@EventHandler
	public void onPlayerQuitCC(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		SocialSpyHandler.onQuit(player);
	}

}
