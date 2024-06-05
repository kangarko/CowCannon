package org.mineacademy.cowcannon.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public final class LaserPointerTask implements Runnable {

	private static final LaserPointerTask instance = new LaserPointerTask();

	private LaserPointerTask() {
	}

	@Override
	public void run() {
		final int length = 5;
		final double particleDistance = 0.5;

		for (final Player online : Bukkit.getOnlinePlayers()) {
			final ItemStack hand = online.getItemInHand();

			if (hand.hasItemMeta() && hand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Laser Pointer")) {
				final Location location = online.getLocation().add(0, 1, 0);

				for (double waypoint = 1; waypoint < length; waypoint += particleDistance) {
					final Vector vector = location.getDirection().multiply(waypoint);
					location.add(vector);

					if (location.getBlock().getType() != Material.AIR)
						break;

					try {
						Particle particle;

						try {
							particle = Particle.DUST;

						} catch (final Throwable t) { // Spigot 1.20.5+ changed names
							particle = Particle.valueOf("REDSTONE");
						}

						location.getWorld().spawnParticle(particle, location, 1, new Particle.DustOptions(Color.YELLOW, 0.75F));

					} catch (final Throwable t) {
						// Unsupported
					}
				}
			}
		}
	}

	public static LaserPointerTask getInstance() {
		return instance;
	}
}
