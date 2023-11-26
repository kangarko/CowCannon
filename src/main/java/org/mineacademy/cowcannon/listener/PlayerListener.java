package org.mineacademy.cowcannon.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.cowcannon.CowCannon;
import org.mineacademy.cowcannon.api.CrawlEvent;
import org.mineacademy.cowcannon.util.Keys;

public class PlayerListener implements Listener {

	@EventHandler
	public void onCrawl(CrawlEvent event) {
		event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&8[&cCrawl&8] &7You are crawling!"));
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		Item item = event.getItemDrop();

		new BukkitRunnable() {
			@Override
			public void run() {

				if (item.isDead() || !item.isValid()) {
					this.cancel();

					return;
				}

				if (item.isOnGround()) {
					final Location location = item.getLocation().add(0.5, -0.8, -0.5);

					location.setPitch(0);
					location.setYaw(0);

					final ArmorStand armorstand = location.getWorld().spawn(location, ArmorStand.class);

					armorstand.setVisible(false);
					armorstand.setGravity(false);
					armorstand.setArms(true);
					armorstand.setItemInHand(item.getItemStack());

					armorstand.getPersistentDataContainer()
							.set(Keys.ITEM_DROP_TIME, PersistentDataType.LONG, System.currentTimeMillis());

					item.remove();
					this.cancel();
				}
			}
		}.runTaskTimer(CowCannon.getInstance(), 0, 2); // 20 ticks = 1 second
	}
}
