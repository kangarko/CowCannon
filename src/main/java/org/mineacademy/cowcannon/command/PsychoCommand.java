package org.mineacademy.cowcannon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineacademy.cowcannon.nms.AggressiveChicken1_8_8;

public final class PsychoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		Player player = (Player) sender;

		// TODO
		player.sendMessage("Uncomment code in this class and pom.xml remapped sections to make /psycho command work");
		//new PsychoMob1_20(player.getLocation());

		// /psycho goto <player>
		if (args.length == 2 && "goto".equals(args[0])) {
			/*Player targetPlayer = player.getServer().getPlayer(args[1]);

			for (Entity nearby : player.getNearbyEntities(20, 20, 20)) {
				if (nearby.getPersistentDataContainer().has(AggressiveChicken1_20.KEY)) {
					Chicken chicken = (Chicken) nearby;

					chicken.getPathfinder().moveTo(targetPlayer);
					chicken.setTarget(targetPlayer);

					player.sendMessage(ChatColor.GOLD + "Chicken is now following " + targetPlayer.getName());
					break;
				}
			}*/
		} else
			new AggressiveChicken1_8_8(player.getLocation()); // /psycho

		return true;
	}
}
