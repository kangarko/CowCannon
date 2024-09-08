package org.mineacademy.cowcannon.command;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.mineacademy.cowcannon.CowCannon;

public final class BungeeCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command!");

			return true;
		}

		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		//out.writeUTF("ListServerAmounts");

		//out.writeUTF("Connect");
		//out.writeUTF("flat");
		
		out.writeUTF("IP");

		Player player = (Player) sender;
		player.sendPluginMessage(CowCannon.getInstance(), "BungeeCord", out.toByteArray());

		return true;
	}
}
