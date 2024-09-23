package org.mineacademy.cowcannon.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mineacademy.cowcannon.CowCannon;

public class MenuListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		final Player dude = (Player) event.getWhoClicked();
		final int slot = event.getSlot();

		System.out.println("Clicking in menu! Has metadata ? " + dude.hasMetadata("CoolMenuPlugin"));

		if (dude.hasMetadata("CoolMenuPlugin")) {
			final Menu menu = (Menu) dude.getMetadata("CoolMenuPlugin").get(0).value();

			//menu.onClick(event.getraw);

			System.out.println("Clicked " + slot + " in menu " + menu);

			for (final Button button : menu.getButtons())
				if (button.getSlot() == slot) {
					System.out.println("Found clickable slot " + button.getSlot() + " with item " + button.getItem());

					button.onClick(dude);
					event.setCancelled(true);
				}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		final Player dude = (Player) event.getPlayer();

		if (dude.hasMetadata("CoolMenuPlugin")) {
			//final Menu menu = (Menu) dude.getMetadata("CoolMenuPlugin").get(0).value();
			//menu.onClose();

			System.out.println("Removing menu metadata.");
			dude.removeMetadata("CoolMenuPlugin", CowCannon.getInstance());
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		// Implement as homework
	}
}
