package org.mineacademy.cowcannon.command;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.mineacademy.cowcannon.model.Database;
import org.mineacademy.cowcannon.model.Database.DatabaseMessage;
import org.mineacademy.cowcannon.util.Common;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public final class DatabaseCommand implements CommandExecutor, TabCompleter {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			Common.tell(sender, "&e=== Database Command ===");
			Common.tell(sender, "&e/db view <player> [page] &f- View player's chat history");

			return true;
		}

		final String param = args[0].toLowerCase();

		if ("view".equals(param)) {
			if (args.length != 2) {
				Common.tell(sender, "&cUsage: /db view <player>");

				return true;
			}

			final String targetPlayerName = args[1];

			final List<DatabaseMessage> messages = Database.getInstance().getPlayerChatMessages(targetPlayerName, 5);

			Common.tell(sender, "&e=== Chat History for " + targetPlayerName + " (Total messages: " + messages.size() + ") ===");

			for (final DatabaseMessage message : messages) {
				final Timestamp timestamp = message.getTimestamp();
				final String messageText = message.getMessage();
				final String world = message.getWorld();
				final int x = message.getX();
				final int y = message.getY();
				final int z = message.getZ();

				sender.sendMessage(Component
						.text("[" + DATE_FORMAT.format(timestamp) + "] ", NamedTextColor.GRAY).append(Component.text(messageText, NamedTextColor.WHITE))

						.hoverEvent(HoverEvent.showText(
								Component.text("Time: " + DATE_FORMAT.format(timestamp), NamedTextColor.GOLD).appendNewline()
										.append(Component.text("World: " + world, NamedTextColor.GOLD)).appendNewline()
										.append(Component.text("Location: " + x + " " + y + " " + z, NamedTextColor.GOLD)).appendNewline()
										.append(Component.text("Recipients: " + String.join(", ", message.getRecipients()), NamedTextColor.GOLD)).appendNewline()
										.append(Component.text("Click to teleport", NamedTextColor.GREEN))))

						.clickEvent(ClickEvent.runCommand("/tp " + x + " " + y + " " + z)));
			}

			return true;

		} else
			Common.tell(sender, "&cInvalid subcommand! Usage: /db view/delete");

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			return Arrays.asList("view").stream()
					.filter(s -> s.startsWith(args[0].toLowerCase()))
					.collect(Collectors.toList());

		} else if (args.length == 2 && "view".equals(args[0].toLowerCase())) {
			return Bukkit.getOnlinePlayers().stream()
					.map(Player::getName)
					.filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
					.collect(Collectors.toList());
		}

		return new ArrayList<>();
	}
}