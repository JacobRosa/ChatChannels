package jacobrosa.chatchannels.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public abstract class ChatChannelCommand implements CommandExecutor, TabCompleter{

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
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return new ArrayList<String>();
	}

	public abstract void run(Player player, Command paramCommand, String[] paramArrayOfString);

	public abstract void run(CommandSender commandSender, Command paramCommand, String[] paramArrayOfString);
	
}
