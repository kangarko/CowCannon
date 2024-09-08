package org.mineacademy.cowcannon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.persistence.PersistentDataType;

import org.mineacademy.cowcannon.util.Common;
import org.mineacademy.cowcannon.util.Keys;

public final class AiCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		final Player player = (Player) sender;

		// /ai <name> <role>
		if (args.length < 2) {
			player.sendMessage("Please specify the NPC name and role.");

			return true;
		}

		final String name = args[0];
		final String role = args[1];

		final Skeleton skeleton = player.getWorld().spawn(player.getLocation(), Skeleton.class);

		skeleton.setGravity(false);
		skeleton.setAI(false);
		skeleton.setInvulnerable(true);
		skeleton.setCustomName(Common.colorize(name));
		skeleton.setCustomNameVisible(true);

		skeleton.getEquipment().setHelmet(player.getItemInHand());

		skeleton.getPersistentDataContainer().set(Keys.NPC_NAME, PersistentDataType.STRING, name);
		skeleton.getPersistentDataContainer().set(Keys.NPC_ROLE, PersistentDataType.STRING, role);

		Common.tell(player, "&8[&a✓&8] &7Created NPC '" + name + "' with role '" + role + "'.");

		return true;
	}
}
