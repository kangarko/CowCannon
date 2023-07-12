package org.mineacademy.cowcannon.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public final class ReadCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		if (args.length != 1)
			return false;

		// /read google.com
		String url = args[0];
		Document document;

		try {
			document = Jsoup.connect("https://" + url).get();

		} catch (IOException e) {
			sender.sendMessage("Failed to read '" + url + "': " + e.getMessage());

			return true;
		}

		sender.sendMessage(ChatColor.AQUA + "Title: " + ChatColor.WHITE + document.title());

		for (Element headline : document.select("h1"))
			sender.sendMessage(ChatColor.GOLD + "<h1> " + ChatColor.WHITE + headline.text());

		for (Element headline : document.select("h2"))
			sender.sendMessage(ChatColor.GRAY + "<h2> " + ChatColor.WHITE + headline.text());

		return true;
	}
}
