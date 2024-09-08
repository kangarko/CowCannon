package org.mineacademy.cowcannon.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public final class GiantCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		if (Bukkit.getPluginManager().getPlugin("Boss") == null) {
			sender.sendMessage(ChatColor.RED + "Boss plugin not installed on this server.");

			return true;
		}

		/*Boss boss = Boss.findBoss("Giant");

		if (boss == null)
			boss = Boss.createBoss("Giant", EntityType.GIANT);

		boss.spawn(((Player) sender).getLocation(), BossSpawnReason.CUSTOM);
		sender.sendMessage(ChatColor.GREEN + "Boss summoned at your location");*/
		sender.sendMessage("Please uncomment the code and import Boss.jar for this feature...");

		return true;
	}
}
