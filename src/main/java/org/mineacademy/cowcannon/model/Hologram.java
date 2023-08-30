package org.mineacademy.cowcannon.model;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.mineacademy.cowcannon.util.Common;

public final class Hologram {

	private final String[] lines;

	public Hologram(String... lines) {
		this.lines = lines;
	}

	public void spawn(Location originLocation) {
		for (String line : lines) {
			ArmorStand stand = originLocation.getWorld().spawn(originLocation, ArmorStand.class);

			stand.setVisible(false);
			stand.setGravity(false);
			stand.setInvulnerable(true);

			stand.setCustomNameVisible(true);
			stand.setCustomName(Common.colorize(line));

			originLocation.subtract(0, 0.25, 0);
		}
	}
}