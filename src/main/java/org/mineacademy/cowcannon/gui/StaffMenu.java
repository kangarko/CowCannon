package org.mineacademy.cowcannon.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class StaffMenu extends Menu {

	public StaffMenu() {

		//this.setSize(9*3);
		this.setTitle("Staff Menu");

		this.addButton(new Button(9 + 4) {

			@Override
			public ItemStack getItem() {
				final ItemStack item = new ItemStack(Material.DIAMOND);
				final ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.WHITE + "List Online Players");
				item.setItemMeta(meta);

				return item;
				//return ItemCreator.of(CompMaterial.ARROW,
				//        "List Online Players",
				//        "",
				//        "Click me to list",
				//        "online players").make();
			}

			@Override
			public void onClick(Player player) {
				new ListPlayersMenu().displayTo(player);
			}
		});
	}

	private class ListPlayersMenu extends Menu {

		public ListPlayersMenu() {
			super(StaffMenu.this);

			this.setSize(9 * 6);
			this.setTitle("Listing Players");

			int startingSlot = 0;

			for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				this.addButton(new Button(startingSlot++) {
					@Override
					public ItemStack getItem() {
						final ItemStack item = new ItemStack(Material.PLAYER_HEAD);
						final SkullMeta meta = (SkullMeta) item.getItemMeta();
						meta.setDisplayName(ChatColor.WHITE + "Head Of " + onlinePlayer.getName());
						meta.setOwningPlayer(onlinePlayer);
						item.setItemMeta(meta);

						return item;
						//return ItemCreator.of(CompMaterial.PLAYER_HEAD).skullOwner(onlinePlayer.getName()).make();
					}

					@Override
					public void onClick(Player player) {
						player.sendMessage("You clicked player " + onlinePlayer.getName());
					}
				});
			}
		}
	}
}
