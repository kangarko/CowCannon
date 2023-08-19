package org.mineacademy.cowcannon.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public final class LaserPointerTask implements Runnable {

	private static final LaserPointerTask instance = new LaserPointerTask();

	private LaserPointerTask() {
	}

	@Override
	public void run() {
		int length = 5;
		double particleDistance = 0.5;

		for (Player online : Bukkit.getOnlinePlayers()) {
			ItemStack hand = online.getItemInHand();

			if (hand.hasItemMeta() && hand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Laser Pointer")) {
				Location location = online.getLocation().add(0, 1, 0);

				for (double waypoint = 1; waypoint < length; waypoint += particleDistance) {
					Vector vector = location.getDirection().multiply(waypoint);
					location.add(vector);

					if (location.getBlock().getType() != Material.AIR)
						break;

					//location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.YELLOW, 0.75F));
				}
			}
		}
	}

	public static LaserPointerTask getInstance() {
		return instance;
	}
}
