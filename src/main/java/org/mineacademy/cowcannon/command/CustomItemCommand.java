package org.mineacademy.cowcannon.command;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import org.mineacademy.cowcannon.util.Keys;

public final class CustomItemCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String s,  String[] strings) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		final Player player = (Player) sender;
		final ItemStack customBucket = new ItemStack(Material.BUCKET);
		final ItemMeta meta = customBucket.getItemMeta();

		meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Custom Bucket");
		meta.setLore(Arrays.asList(
				"",
				ChatColor.GRAY + "Right click me on a cow!"));

		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		Enchantment ench = Enchantment.getByName("ARROW_DAMAGE");

		// Spigot 1.20.5+ changed names
		if (ench == null)
			ench = Enchantment.getByName("SHARPNESS");

		meta.addEnchant(ench, 1, true);

		try {
			meta.getPersistentDataContainer().set(Keys.CUSTOM_BUCKET, PersistentDataType.BOOLEAN, true);
		} catch (final LinkageError t) {
			// have an alternative code for old MC version
		}

		// NEW: You can set custom model data this way:
		meta.setCustomModelData(1);

		customBucket.setItemMeta(meta);

		player.getInventory().addItem(customBucket);

		return true;
	}
}
