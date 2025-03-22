package org.mineacademy.cowcannon.setting;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.mineacademy.cowcannon.CowCannon;
import org.mineacademy.cowcannon.model.Database.DatabaseType;

public final class CowSettings {

	private final static CowSettings instance = new CowSettings();

	private File file;
	private YamlConfiguration config;

	private EntityType explodingType;

	private List<String> headerLines;
	private List<String> footerLines;

	private DatabaseType databaseType;
	private String mysqlHost;
	private int mysqlPort;
	private String mysqlDatabase;
	private String mysqlUsername;
	private String mysqlPassword;
	private String sqliteFile;

	private CowSettings() {
	}

	public void load() {
		file = new File(CowCannon.getInstance().getDataFolder(), "settings.yml");

		if (!file.exists())
			CowCannon.getInstance().saveResource("settings.yml", false);

		config = new YamlConfiguration();

		try {
			config.options().parseComments(true);
		} catch (final Throwable t) {
			// Unsupported
		}

		try {
			config.load(file);

		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		explodingType = EntityType.valueOf(config.getString("Explosion.Entity_Type"));
		headerLines = config.getStringList("Tablist.Header");
		footerLines = config.getStringList("Tablist.Footer");
		databaseType = DatabaseType.valueOf(config.getString("Database.Type").toUpperCase());
		mysqlHost = config.getString("Database.MySQL.Host");
		mysqlPort = config.getInt("Database.MySQL.Port", 0);
		mysqlDatabase = config.getString("Database.MySQL.Database");
		mysqlUsername = config.getString("Database.MySQL.User");
		mysqlPassword = config.getString("Database.MySQL.Password");
		sqliteFile = config.getString("Database.SQLite.File");

		if (databaseType == DatabaseType.MYSQL && (mysqlHost == null || mysqlPort == 0 || mysqlDatabase == null || mysqlUsername == null || mysqlPassword == null))
			throw new RuntimeException("Missing MySQL database keys from settings.yml");
	}

	public void save() {
		try {
			config.save(file);

		} catch (final Exception ex) {
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

	public DatabaseType getDatabaseType() {
		return databaseType;
	}

	public String getMysqlHost() {
		return mysqlHost;
	}

	public int getMysqlPort() {
		return mysqlPort;
	}

	public String getMysqlDatabase() {
		return mysqlDatabase;
	}

	public String getMysqlUsername() {
		return mysqlUsername;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public String getSqliteFile() {
		return sqliteFile;
	}

	public static CowSettings getInstance() {
		return instance;
	}
}
