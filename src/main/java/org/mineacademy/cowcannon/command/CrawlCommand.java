package org.mineacademy.cowcannon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.mineacademy.cowcannon.model.Crawl;

public final class CrawlCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		Crawl.start((Player) sender);

		return true;
	}
}
