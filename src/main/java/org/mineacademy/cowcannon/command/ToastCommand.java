package org.mineacademy.cowcannon.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.mineacademy.cowcannon.model.Toast;

public final class ToastCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command!");

			return true;
		}

		if (args.length < 3)
			return false;

		final Toast.Style style;

		try {
			style = Toast.Style.valueOf(args[0].toUpperCase());

		} catch (final Throwable t) {
			sender.sendMessage(ChatColor.RED + "Invalid style: " + args[0]);

			return true;
		}

		final String materialName = args[1];

		try {
			Material.valueOf(materialName.toUpperCase());

		} catch (final Throwable t) {
			sender.sendMessage(ChatColor.RED + "Invalid material: " + materialName);

			return true;
		}

		String message = "";

		for (int i = 2; i < args.length; i++)
			message += args[i] + " ";

		message = ChatColor.translateAlternateColorCodes('&', message.trim());

		Toast.displayTo((Player) sender, materialName, message, style);

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		final List<String> tab = new ArrayList<>();

		switch (args.length) {
			case 1:
				for (final Toast.Style style : Toast.Style.values())
					tab.add(style.toString().toLowerCase());
				break;

			case 2:
				for (final Material material : Material.values())
					tab.add(material.toString().toLowerCase());
				break;

			case 3:
				tab.add("Hello");
				break;
		}

		return tab.stream().filter(completion -> completion.startsWith(args[args.length - 1])).collect(Collectors.toList());
	}
}
