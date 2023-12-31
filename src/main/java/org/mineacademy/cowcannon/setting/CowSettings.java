package org.mineacademy.cowcannon.setting;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.mineacademy.cowcannon.CowCannon;

import java.io.File;
import java.util.List;

public final class CowSettings {

	private final static CowSettings instance = new CowSettings();

	private File file;
	private YamlConfiguration config;

	private EntityType explodingType;
	private List<String> headerLines;
	private List<String> footerLines;

	private CowSettings() {
	}

	public void load() {
		file = new File(CowCannon.getInstance().getDataFolder(), "settings.yml");

		if (!file.exists())
			CowCannon.getInstance().saveResource("settings.yml", false);

		config = new YamlConfiguration();

		try {
			config.options().parseComments(true);
		} catch (Throwable t) {
			// Unsupported
		}

		try {
			config.load(file);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		explodingType = EntityType.valueOf(config.getString("Explosion.Entity_Type"));
		headerLines = config.getStringList("Tablist.Header");
		footerLines = config.getStringList("Tablist.Footer");
	}

	public void save() {
		try {
			config.save(file);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void set(String path, Object value) {
		config.set(path, value);

		save();
	}

	public EntityType getExplodingType() {
		return explodingType;
	}

	public List<String> getHeaderLines() {
		return headerLines;
	}

	public List<String> getFooterLines() {
		return footerLines;
	}

	public void setExplodingType(EntityType explodingType) {
		this.explodingType = explodingType;

		set("Explosion.Entity_Type", explodingType.name());
	}

	public static CowSettings getInstance() {
		return instance;
	}
}
