package org.mineacademy.cowcannon;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CowCommand implements CommandExecutor, TabExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		// /cow set er1gert41erter
		if (args.length > 1) {

			if (args[0].equalsIgnoreCase("set")) {
				EntityType type;

				try {
					type = EntityType.valueOf(args[1].toUpperCase());

				} catch (IllegalArgumentException ex) {
					sender.sendMessage("Invalid entity type: " + args[1]);

					return true;
				}

				if (!type.isSpawnable() || !type.isAlive()) {
					sender.sendMessage("You can only use living entities!");

					return true;
				}

				CowSettings.getInstance().setExplodingType(type);
				sender.sendMessage(ChatColor.GREEN + "Set exploding type to " + type);

				return true;
			}

			return false;
		}

		//      	args[0]  args[1] 	args[2] 	args[3] 	args[4]
		// /cow 	baby	 world	 	this	 	is	 		pretty

		Player player = (Player) sender;
		LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), CowSettings.getInstance().getExplodingType());

		if (args.length == 1 && args[0].equalsIgnoreCase("baby")) {
			if (entity instanceof Ageable)
				((Ageable) entity).setBaby();

			else {
				sender.sendMessage("This entity cannot be a baby!");

				return true;
			}
		}
		
		entity.getPersistentDataContainer().set(Keys.CUSTOM_COW, PersistentDataType.BOOLEAN, true);
		entity.setCustomName(ChatColor.RED + "Milk Me");
		entity.setCustomNameVisible(true);

		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		System.out.println("Args size: " + args.length);

		if (args.length == 1)
			return Arrays.asList("baby", "set");

		if (args.length == 2) {
			String name = args[1].toUpperCase();

			return Arrays.stream(EntityType.values())
					.filter(type -> type.isSpawnable() && type.isAlive() && type.name().startsWith(name))
					.map(Enum::name)
					.collect(Collectors.toList());
		}

		return new ArrayList<>(); // null = all player names
	}
}
