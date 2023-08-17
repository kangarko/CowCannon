package org.mineacademy.cowcannon.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class LocaleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

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

			if (player.getLocale() == "en_us") {
				
			}

		} else if ("translate".equals(param)) {
			player.sendMessage(Component.translatable("lanServer.port.invalid.new")
					.args(Component.text("Banana"), Component.text("Cherry"))
					.color(TextColor.fromCSSHexString("#cc11ff")));

		} else if ("key".equals(param)) {
			player.sendMessage(Component.translatable("key.back"));
			player.sendMessage(Component.keybind("key.back"));

		} else
			return false;

		return true;
	}
}
