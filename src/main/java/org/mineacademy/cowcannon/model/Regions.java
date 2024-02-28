package org.mineacademy.cowcannon.model;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.mineacademy.cowcannon.CowCannon;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Regions {

	private static final Regions instance = new Regions();

	private final File file;
	private final YamlConfiguration config;

	private final Set<Region> regions = new HashSet<>();

	private Regions() {
		file = new File(CowCannon.getInstance().getDataFolder(), "regions.yml");
		config = new YamlConfiguration();
	}

	public void load() {

		try {
			if (!file.exists())
				file.createNewFile();

			config.load(file);

		} catch (Throwable t) {
			t.printStackTrace();
		}

		regions.clear();

		if (config.isSet("Regions")) {
			for (Map<?, ?> rawRegionMap : config.getMapList("Regions"))
				regions.add(Region.deserialize((Map<String, Object>) rawRegionMap));

			System.out.println("Loaded regions: " + getRegionsNames());
		}
	}

	public void save() {
		List<Map<String, Object>> serializedRegions = new ArrayList<>();

		for (Region region : regions)
			serializedRegions.add(region.serialize());

		config.set("Regions", serializedRegions);

		try {
			config.save(file);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param location
	 * @return
	 */
	public Region findRegion(Location location) {
		for (Region region : regions)
			if (region.isWithin(location))
				return region;

		return null;
	}

	public Region findRegion(String name) {
		for (Region region : regions)
			if (region.getName().equalsIgnoreCase(name))
				return region;

		return null;
	}

	public Set<Region> getRegions() {
		return Collections.unmodifiableSet(regions);
	}

	public Set<String> getRegionsNames() {
		Set<String> names = new HashSet<>();

		for (Region region : regions)
			names.add(region.getName());

		return Collections.unmodifiableSet(names);
	}

	public void saveRegion(String name, Location primary, Location secondary) {
		regions.add(new Region(name, primary, secondary));

		save();
	}

	public static Regions getInstance() {
		return instance;
	}
}
