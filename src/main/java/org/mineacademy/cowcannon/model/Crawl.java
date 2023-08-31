package org.mineacademy.cowcannon.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.mineacademy.cowcannon.api.CrawlEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Crawl {

	private static final Map<UUID, Crawl> crawlingPlayers = new HashMap<>();
	private static final BlockData BARRIER_DATA = Material.BARRIER.createBlockData();

	private final Player player;
	private Location barrierLocation = null;

	private Crawl(Player player) {
		this.player = player;

		this.player.setSwimming(true);
		this.update(player.getLocation());

		crawlingPlayers.put(player.getUniqueId(), this);
	}

	public void update(Location location) {
		Block blockAbove = location.getBlock().getRelative(BlockFace.UP);

		if (this.barrierLocation == null || !blockAbove.equals(this.barrierLocation.getBlock())) {
			this.destroyBarrier();

			if (!blockAbove.getType().isSolid())
				this.updateBarrierLocation(location.add(0, 1.5, 0));
		}
	}

	public void stop() {
		this.player.setSwimming(false);
		this.destroyBarrier();

		crawlingPlayers.remove(this.player.getUniqueId());
	}

	public void updateBarrierLocation(Location location) {
		this.barrierLocation = location;

		this.player.sendBlockChange(location, BARRIER_DATA);
	}

	private void destroyBarrier() {
		if (this.barrierLocation != null) {
			this.player.sendBlockChange(this.barrierLocation, this.barrierLocation.getBlock().getBlockData());

			this.barrierLocation = null;
		}
	}

	public Location getBarrierLocation() {
		return barrierLocation;
	}

	public static void start(Player player) {
		CrawlEvent event = new CrawlEvent(player);
		Bukkit.getPluginManager().callEvent(event);

		if (!event.isCancelled())
			new Crawl(event.getPlayer());
	}

	public static Crawl findPlayer(Player player) {
		return crawlingPlayers.get(player.getUniqueId());
	}

	public static Crawl findPlayer(UUID uuid) {
		return crawlingPlayers.get(uuid);
	}
}
