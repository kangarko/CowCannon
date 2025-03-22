package org.mineacademy.cowcannon.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.mineacademy.cowcannon.CowCannon;
import org.mineacademy.cowcannon.setting.CowSettings;

import com.google.gson.Gson;

public final class Database {

	private static final Database instance = new Database();
	private static final Gson GSON = new Gson();

	private final CowSettings settings;
	private Connection connection;

	private Database() {
		this.settings = CowSettings.getInstance();
	}

	/**
	 * Load database configuration from file
	 */
	public void load() {
		this.connect();
		this.createTables();
	}

	/**
	 * Connect to the database
	 */
	private void connect() {
		try {
			if (this.connection != null && !this.connection.isClosed())
				return;

			if (this.settings.getDatabaseType() == DatabaseType.MYSQL) {
				Class.forName("com.mysql.cj.jdbc.Driver");

				final String jdbcUrl = "jdbc:mysql://" + this.settings.getMysqlHost() + ":" + this.settings.getMysqlPort() + "/" + this.settings.getMysqlDatabase() + "?useSSL=false&autoReconnect=true";

				Bukkit.getLogger().info("[CowCannon] Connecting to MySQL database: " + jdbcUrl + " with user " + this.settings.getMysqlUsername());

				this.connection = DriverManager.getConnection(jdbcUrl, this.settings.getMysqlUsername(), this.settings.getMysqlPassword());

				Bukkit.getLogger().info("[CowCannon] Connected to MySQL database");

			} else {
				Class.forName("org.sqlite.JDBC");

				final File file = new File(CowCannon.getInstance().getDataFolder(), this.settings.getSqliteFile());

				if (!file.exists())
					file.createNewFile();

				final String jdbcUrl = "jdbc:sqlite:" + file.getAbsolutePath();
				this.connection = DriverManager.getConnection(jdbcUrl);

				Bukkit.getLogger().info("[CowCannon] Connected to SQLite database");
			}

		} catch (final Exception ex) {
			Bukkit.getLogger().severe("[CowCannon] Failed to connect to database: " + ex.getMessage());

			ex.printStackTrace();
		}
	}

	/**
	 * Create database tables if they don't exist
	 */
	private void createTables() {
		try {
			final Statement statement = this.connection.createStatement();

			statement.execute("CREATE TABLE IF NOT EXISTS chat_messages (" +
					"id INTEGER PRIMARY KEY " + (this.settings.getDatabaseType() == DatabaseType.MYSQL ? "AUTO_INCREMENT" : "AUTOINCREMENT") + ", " +
					"player_uuid VARCHAR(36) NOT NULL, " +
					"player_name TEXT NOT NULL, " +
					"world TEXT NOT NULL, " +
					"x INT NOT NULL, " +
					"y INT NOT NULL, " +
					"z INT NOT NULL, " +
					"message TEXT NOT NULL, " +
					"recipients TEXT NOT NULL, " +
					"timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
					")");

			// Create index on player_name for faster lookups
			statement.execute("CREATE INDEX IF NOT EXISTS idx_chat_player_name ON chat_messages (player_name)");

			statement.close();

		} catch (final SQLException ex) {
			Bukkit.getLogger().severe("[CowCannon] Failed to create tables: " + ex.getMessage());

			ex.printStackTrace();
		}
	}

	/**
	 * Close the database connection
	 */
	public void close() {
		try {
			if (this.connection != null && !this.connection.isClosed()) {
				this.connection.close();

				Bukkit.getLogger().info("[CowCannon] Database connection closed.");
			}
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Save a chat message to the database
	 *
	 * @param player The player who sent the message
	 * @param message The chat message content
	 * @param recipients The recipients
	 *
	 * @return true if saved successfully, false otherwise
	 */
	public boolean saveChatMessage(Player player, String message, Collection<Player> recipients) {
		synchronized (this.connection) {
			try {
				final PreparedStatement statement = this.connection.prepareStatement(
						"INSERT INTO chat_messages (player_uuid, player_name, world, x, y, z, message, recipients) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

				final Location loc = player.getLocation();

				statement.setString(1, player.getUniqueId().toString());
				statement.setString(2, player.getName());
				statement.setString(3, loc.getWorld().getName());
				statement.setInt(4, loc.getBlockX());
				statement.setInt(5, loc.getBlockY());
				statement.setInt(6, loc.getBlockZ());
				statement.setString(7, message);

				List<String> recipientNames = recipients.stream().map(Player::getName).collect(Collectors.toList());
				statement.setString(8, this.GSON.toJson(recipientNames));

				final int rows = statement.executeUpdate();
				statement.close();

				return rows > 0;

			} catch (final SQLException ex) {
				ex.printStackTrace();

				return false;
			}
		}
	}

	/**
	 * Get chat messages from a specific player
	 *
	 * @param playerName The player's name
	 * @param limit Maximum number of messages to return (0 for all)
	 * @return List of chat messages
	 */
	public List<DatabaseMessage> getPlayerChatMessages(String playerName, int limit) {
		final List<DatabaseMessage> messages = new ArrayList<>();

		try {
			String sql = "SELECT * FROM chat_messages WHERE player_name = ? ORDER BY timestamp DESC";

			if (limit > 0)
				sql += " LIMIT " + limit;

			final PreparedStatement statement = this.connection.prepareStatement(sql);

			statement.setString(1, playerName);

			final ResultSet resultSet = statement.executeQuery();

			while (resultSet.next())
				messages.add(DatabaseMessage.fromResultSet(resultSet));

			resultSet.close();
			statement.close();

		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return messages;
	}

	/**
	 * Database types
	 */
	public enum DatabaseType {
		MYSQL,
		SQLITE
	}

	/**
	 * A chat message from the database
	 */
	public final static class DatabaseMessage {

		private final int id;
		private final UUID playerUuid;
		private final String playerName;
		private final String world;
		private final int x;
		private final int y;
		private final int z;
		private final String message;
		private final Timestamp timestamp;
		private final List<String> recipients;

		private DatabaseMessage(int id, UUID playerUuid, String playerName, String world, int x, int y, int z, String message, Timestamp timestamp, List<String> recipients) {
			this.id = id;
			this.playerUuid = playerUuid;
			this.playerName = playerName;
			this.world = world;
			this.x = x;
			this.y = y;
			this.z = z;
			this.message = message;
			this.timestamp = timestamp;
			this.recipients = recipients;
		}

		public int getId() {
			return id;
		}

		public UUID getPlayerUuid() {
			return playerUuid;
		}

		public String getPlayerName() {
			return playerName;
		}

		public String getWorld() {
			return world;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZ() {
			return z;
		}

		public String getMessage() {
			return message;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public List<String> getRecipients() {
			return recipients;
		}

		public static DatabaseMessage fromResultSet(ResultSet row) throws SQLException {
			return new DatabaseMessage(
					row.getInt("id"),
					UUID.fromString(row.getString("player_uuid")),
					row.getString("player_name"),
					row.getString("world"),
					row.getInt("x"),
					row.getInt("y"),
					row.getInt("z"),
					row.getString("message"),
					row.getTimestamp("timestamp"),
					GSON.fromJson(row.getString("recipients"), List.class));
		}
	}

	/**
	 * Get database instance
	 *
	 * @return Database instance
	 */
	public static Database getInstance() {
		return instance;
	}
}