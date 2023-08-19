package org.mineacademy.cowcannon.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mineacademy.cowcannon.CowCannon;

import java.util.UUID;

public final class TagCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command!");

			return true;
		}

		if (args.length != 1)
			return false;

		// /tag <X>
		String tag = args[0];
		Player player = (Player) sender;
		UUID uniqueId = player.getUniqueId();

		if ("reset".equals(tag)) {
			CowCannon.getPlayerTags().remove(uniqueId);

			player.sendMessage(ChatColor.GREEN + "Your tag has been reset.");

		} else {
			tag = ChatColor.translateAlternateColorCodes('&', tag);

			if (tag.length() > 16)
				tag = tag.substring(0, 16);

			CowCannon.getPlayerTags().put(uniqueId, tag);
			player.sendMessage(ChatColor.GREEN + "Your tag has been set to " + tag + ChatColor.GREEN + ".");
		}

		sender.sendMessage("Please uncomment TagCommand, code in ProtocolLib hook and pom.xml imports to make this command work.");
		/*EntityPlayer handle = ((CraftPlayer) player).getHandle();

		for (Player online : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer) online).getHandle().playerConnection;

			connection.sendPacket(new PacketPlayOutPlayerInfo(
					PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, handle));

			connection.sendPacket(new PacketPlayOutPlayerInfo(
					PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, handle));

			if (!online.equals(player)) {
				connection.sendPacket(new PacketPlayOutEntityDestroy(handle.getId()));
				connection.sendPacket(new PacketPlayOutNamedEntitySpawn(handle));
			}
		}*/

		return true;
	}
}
