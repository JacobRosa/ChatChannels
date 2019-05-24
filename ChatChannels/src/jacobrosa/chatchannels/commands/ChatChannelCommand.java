package jacobrosa.chatchannels.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class ChatChannelCommand implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (sender instanceof Player) {
			run((Player) sender, cmd, args);
			return true;
		}

		if (!(sender instanceof Player)) {
			run(sender, cmd, args);
			return true;
		}
		
		return true;
	}

	public abstract void run(Player player, Command paramCommand, String[] paramArrayOfString);

	public abstract void run(CommandSender commandSender, Command paramCommand, String[] paramArrayOfString);
	
}
