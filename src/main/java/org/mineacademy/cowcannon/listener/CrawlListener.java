package org.mineacademy.cowcannon.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.mineacademy.cowcannon.CowCannon;
import org.mineacademy.cowcannon.model.Crawl;

public final class CrawlListener implements Listener {

	public CrawlListener() {
		Bukkit.getPluginManager().registerEvents(new ModernListener(), CowCannon.getInstance());
	}

	@EventHandler(ignoreCancelled = true)
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Crawl crawl = Crawl.findPlayer(player);

		if (crawl != null) {
			Location from = event.getFrom();
			Location to = event.getTo();

			if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ())
				crawl.update(from.clone());
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Crawl crawl = Crawl.findPlayer(player);
		Block clickedBlock = event.getClickedBlock();

		if (crawl != null && clickedBlock != null && clickedBlock.getLocation().equals(crawl.getBarrierLocation())) {
			crawl.update(clickedBlock.getLocation());

			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		Crawl crawl = Crawl.findPlayer(player);

		if (crawl != null && event.isSneaking())
			crawl.stop();
	}
}

class ModernListener implements Listener {
	@EventHandler(ignoreCancelled = true)
	public void onToggleSwim(EntityToggleSwimEvent event) {
		if (Crawl.findPlayer(event.getEntity().getUniqueId()) != null)
			event.setCancelled(true);
	}
}
