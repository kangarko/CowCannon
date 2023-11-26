package org.mineacademy.cowcannon.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.mineacademy.cowcannon.util.Keys;

public final class ItemPickupTask implements Runnable {

	private static final ItemPickupTask instance = new ItemPickupTask();

	private ItemPickupTask() {
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			ArmorStand armorstand = null;

			for (Entity nearby : player.getWorld().getNearbyEntities(player.getLocation(), 3, 3, 3, ArmorStand.class::isInstance))
				if (nearby.getLocation().subtract(0.5, -0.8, -0.5).distance(player.getLocation()) < 1.5) {
					armorstand = (ArmorStand) nearby;

					break;
				}

			if (armorstand != null) {
				final Long itemDropTime = armorstand.getPersistentDataContainer().get(Keys.ITEM_DROP_TIME, PersistentDataType.LONG);

				if (itemDropTime != null && System.currentTimeMillis() - itemDropTime > 800) { // collect after 0.8 seconds
					final ItemStack item = armorstand.getEquipment().getItemInMainHand();

					if (item.getType() != Material.AIR && player.getInventory().firstEmpty() != -1) {
						player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 1F);
						player.getInventory().addItem(item);

						armorstand.remove();
					}
				}
			}
		}
	}

	public static ItemPickupTask getInstance() {
		return instance;
	}
}
