package org.mineacademy.cowcannon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class PsychoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		final Player player = (Player) sender;

		player.sendMessage("Uncomment code in this class and pom.xml remapped sections to make /psycho command work");

		// /psycho goto <player>
		/*if (args.length == 2 && "goto".equals(args[0])) {
			Player targetPlayer = player.getServer().getPlayer(args[1]);
		
			for (Entity nearby : player.getNearbyEntities(20, 20, 20)) {
				if (nearby.getPersistentDataContainer().has(AggressiveChicken1_20.KEY)) {
					Chicken chicken = (Chicken) nearby;
		
					chicken.getPathfinder().moveTo(targetPlayer);
					chicken.setTarget(targetPlayer);
		
					player.sendMessage(ChatColor.GOLD + "Chicken is now following " + targetPlayer.getName());
					break;
				}
			}
		} else
			new AggressiveChicken1_20(player.getLocation());*/

		return true;
	}
}
