package org.mineacademy.cowcannon.model;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public final class Region {

	private final String name;
	private final Location primaryLocation;
	private final Location secondaryLocation;

	public Region(String name, Location primaryLocation, Location secondaryLocation) {
		this.name = name;
		this.primaryLocation = primaryLocation;
		this.secondaryLocation = secondaryLocation;
	}

	/**
	 * Return true if the given point is within this region
	 *
	 * @param location
	 * @return
	 */
	public boolean isWithin(final Location location) {
		final Location[] centered = this.getCorrectedPoints();
		final Location primary = centered[0];
		final Location secondary = centered[1];

		final int x = (int) location.getX();
		final int y = (int) location.getY();
		final int z = (int) location.getZ();

		return x >= primary.getX() && x <= secondary.getX()
				&& y >= primary.getY() && y <= secondary.getY()
				&& z >= primary.getZ() && z <= secondary.getZ();
	}

	/*
	 * Change primary/secondary around to make secondary always the lowest point
	 */
	private Location[] getCorrectedPoints() {
		if (this.primaryLocation == null || this.secondaryLocation == null)
			return null;

		final int x1 = this.primaryLocation.getBlockX(), x2 = this.secondaryLocation.getBlockX(),
				y1 = this.primaryLocation.getBlockY(), y2 = this.secondaryLocation.getBlockY(),
				z1 = this.primaryLocation.getBlockZ(), z2 = this.secondaryLocation.getBlockZ();

		final Location primary = this.primaryLocation.clone();
		final Location secondary = this.secondaryLocation.clone();

		primary.setX(Math.min(x1, x2));
		primary.setY(Math.min(y1, y2));
		primary.setZ(Math.min(z1, z2));

		secondary.setX(Math.max(x1, x2));
		secondary.setY(Math.max(y1, y2));
		secondary.setZ(Math.max(z1, z2));

		return new Location[]{primary, secondary};
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();

		map.put("Name", name);
		map.put("Primary", primaryLocation);
		map.put("Secondary", secondaryLocation);

		return map;
	}

	public String getName() {
		return name;
	}

	public Location getPrimaryLocation() {
		return primaryLocation;
	}

	public Location getSecondaryLocation() {
		return secondaryLocation;
	}

	public static Region deserialize(Map<String, Object> map) {
		String name = (String) map.get("Name");
		Location primary = (Location) map.get("Primary");
		Location secondary = (Location) map.get("Secondary");

		return new Region(name, primary, secondary);
	}
}
