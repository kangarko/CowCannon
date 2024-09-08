package org.mineacademy.cowcannon.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public final class LocaleCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command!");

			return true;
		}

		if (args.length != 1)
			return false;

		Player player = (Player) sender;
		String param = args[0].toLowerCase();

		if ("get".equals(param)) {
			player.sendMessage("Your localization is: " + player.getLocale());

		} else if ("translate".equals(param)) {
			LocaleExecutor.executeLocale(player);

		} else if ("key".equals(param)) {
			LocaleExecutor.executeKey(player);

		} else
			return false;

		return true;
	}
}

class LocaleExecutor {

	static void executeLocale(Player player) {
		try {
			player.sendMessage(Component.translatable("lanServer.port.invalid.new")
					.args(Component.text("Banana"), Component.text("Cherry"))
					.color(TextColor.fromCSSHexString("#cc11ff")));

		} catch (LinkageError err) {
			player.sendMessage("You need to be on 1.16+ to use this command!");
		}
	}

	static void executeKey(Player player) {
		try {
			player.sendMessage(Component.translatable("key.back"));
			player.sendMessage(Component.keybind("key.back"));
		} catch (LinkageError err) {
			player.sendMessage("You need to be on 1.16+ to use this command!");
		}
	}
}