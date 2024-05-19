package org.mineacademy.cowcannon.listener;

import org.bukkit.event.Listener;

public final class GuiListener implements Listener {

	// Replaced by gui package menu listener
	/*@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
	
		if (player.hasMetadata("OpenedMenu")) {
			event.setCancelled(true);
	
			if (event.getSlot() == 11) {
				player.getInventory().addItem(new ItemStack(Material.DIAMOND));
				player.closeInventory();
	
			} else if (event.getSlot() == 13) {
				player.getInventory().clear();
				player.closeInventory();
	
			} else if (event.getSlot() == 15) {
				player.getWorld().setStorm(false);
				player.getWorld().setThundering(false);
	
				player.closeInventory();
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
	
		if (player.hasMetadata("OpenedMenu"))
			player.removeMetadata("OpenedMenu", CowCannon.getInstance());
	}*/
}
