package jacobrosa.chatchannels.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jacobrosa.chatchannels.ChatChannels;
import jacobrosa.chatchannels.listeners.ChatChannel;
import jacobrosa.chatchannels.listeners.ChatListener;
import jacobrosa.chatchannels.utils.ChatHandler;
import jacobrosa.chatchannels.utils.Permissions;
import jacobrosa.chatchannels.utils.Prefix;
import jacobrosa.chatchannels.utils.SocialSpyHandler;
import net.md_5.bungee.api.ChatColor;

public class CommandChatChannel extends ChatChannelCommand{

	@Override
	public void run(Player player, Command cmd, String[] args) {
		if(args.length == 0) {
			player.sendMessage(Prefix.chat + ChatColor.GRAY + "You are currently in chat channel " + ChatHandler.getChatPrefix(player));
			player.sendMessage(ChatColor.GRAY + "/chatchannel <global|local|world|staff>");
			if(player.hasPermission(Permissions.commandSocialSpy))
				player.sendMessage(ChatColor.GRAY + "/chatchannel socialspy");
			if(player.hasPermission(Permissions.commandMutechat))
				player.sendMessage(ChatColor.GRAY + "/chatchannel mutechat");
			return;
		}
		if(args[0].equalsIgnoreCase("global") || args[0].equalsIgnoreCase("g")) {
			ChatHandler.setChatChannel(player, ChatChannel.Global);
			return;
		}else if(args[0].equalsIgnoreCase("local") || args[0].equalsIgnoreCase("l")) {
			ChatHandler.setChatChannel(player, ChatChannel.Local);
			return;
		}else if(args[0].equalsIgnoreCase("world") || args[0].equalsIgnoreCase("w")) {
			ChatHandler.setChatChannel(player, ChatChannel.World);
			return;
		}else if(args[0].equalsIgnoreCase("staff") || args[0].equalsIgnoreCase("s")) {
			if(player.hasPermission(Permissions.commandStaffChat)) {
				ChatHandler.setChatChannel(player, ChatChannel.Staff);
				return;
			}else {
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return;
			}
		}else if(args[0].equalsIgnoreCase("socialspy") || args[0].equalsIgnoreCase("ss")) {
			if(player.hasPermission(Permissions.commandSocialSpy)) {
				if(SocialSpyHandler.hasSocialSpyEnabled(player))
					SocialSpyHandler.setSocialSpy(player, false);
				else
					SocialSpyHandler.setSocialSpy(player, true);
				return;
			}else {
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return;
			}
		}else if(args[0].equalsIgnoreCase("mutechat")) {
			if(player.hasPermission(Permissions.commandMutechat)) {
				boolean chatMuted = !ChatListener.isChatMuted();
				Bukkit.broadcastMessage(Prefix.chat + ChatColor.GRAY + "Chat has been " + (chatMuted ? "muted" : "unmuted") + " by " + player.getDisplayName() + ".");
				ChatListener.setChatMuted(chatMuted);
				return;
			}else {
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return;
			}
		}else if(args[0].equalsIgnoreCase("version")) {
			player.sendMessage(ChatColor.GRAY + ChatChannels.getPluginName() + " v" + ChatChannels.getVersion() + " by kingbluesapphire");
		}else {
			player.sendMessage(Prefix.command + "�7Invalid chat channel.");
			return;
		}
	}

	@Override
	public void run(CommandSender sender, Command cmd, String[] args) {
		sender.sendMessage(ChatColor.RED + "Invalid sender!");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) {
			List<String> arg1 = new ArrayList<>();
			if("global".startsWith(args[0]))
				arg1.add("global");
			if("local".startsWith(args[0]))
				arg1.add("local");
			if("world".startsWith(args[0]))
				arg1.add("world");
			if("version".startsWith(args[0]))
				arg1.add("version");
			if(sender.hasPermission(Permissions.commandStaffChat))
				if("staff".startsWith(args[0]))
					arg1.add("staff");
			if(sender.hasPermission(Permissions.commandSocialSpy))
				if("socialspy".startsWith(args[0]))
					arg1.add("socialspy");
			if(sender.hasPermission(Permissions.commandMutechat))
				if("mutechat".startsWith(args[0]))
					arg1.add("mutechat");
			return arg1;
		}
		return new ArrayList<String>();
	}

}
