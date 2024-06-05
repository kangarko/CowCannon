package org.mineacademy.cowcannon;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mineacademy.cowcannon.command.AiCommand;
import org.mineacademy.cowcannon.command.BungeeCommand;
import org.mineacademy.cowcannon.command.ButterflyCommand;
import org.mineacademy.cowcannon.command.CowCommand;
import org.mineacademy.cowcannon.command.CrawlCommand;
import org.mineacademy.cowcannon.command.CustomItemCommand;
import org.mineacademy.cowcannon.command.DisplayEntityCommand;
import org.mineacademy.cowcannon.command.EconomyCommand;
import org.mineacademy.cowcannon.command.EmailCommand;
import org.mineacademy.cowcannon.command.FlyCommand;
import org.mineacademy.cowcannon.command.GiantCommand;
import org.mineacademy.cowcannon.command.GuiCommand;
import org.mineacademy.cowcannon.command.HologramCommand;
import org.mineacademy.cowcannon.command.LocaleCommand;
import org.mineacademy.cowcannon.command.PsychoCommand;
import org.mineacademy.cowcannon.command.ReadCommand;
import org.mineacademy.cowcannon.command.RegionCommand;
import org.mineacademy.cowcannon.command.TagCommand;
import org.mineacademy.cowcannon.command.ToastCommand;
import org.mineacademy.cowcannon.command.TradeCommand;
import org.mineacademy.cowcannon.command.VanishCommand;
import org.mineacademy.cowcannon.gui.MenuListener;
import org.mineacademy.cowcannon.hook.CowEconomy;
import org.mineacademy.cowcannon.hook.DiscordSRVHook;
import org.mineacademy.cowcannon.hook.PlaceholderAPIHook;
import org.mineacademy.cowcannon.hook.ProtocolLibHook;
import org.mineacademy.cowcannon.listener.AiListener;
import org.mineacademy.cowcannon.listener.ChatListener;
import org.mineacademy.cowcannon.listener.CrawlListener;
import org.mineacademy.cowcannon.listener.EntityListener;
import org.mineacademy.cowcannon.listener.GuiListener;
import org.mineacademy.cowcannon.listener.HealthTagListener;
import org.mineacademy.cowcannon.listener.InventoryListener;
import org.mineacademy.cowcannon.listener.LaserPointerListener;
import org.mineacademy.cowcannon.listener.PlayerListener;
import org.mineacademy.cowcannon.listener.RegionListener;
import org.mineacademy.cowcannon.model.Board;
import org.mineacademy.cowcannon.model.Bungee;
import org.mineacademy.cowcannon.model.CustomRecipe;
import org.mineacademy.cowcannon.model.Regions;
import org.mineacademy.cowcannon.model.Scheduler;
import org.mineacademy.cowcannon.setting.CowSettings;
import org.mineacademy.cowcannon.task.ButterflyTask;
import org.mineacademy.cowcannon.task.ItemPickupTask;
import org.mineacademy.cowcannon.task.LaserPointerTask;
import org.mineacademy.cowcannon.task.MessageBroadcasterTask;
import org.mineacademy.cowcannon.task.TablistTask;

public final class CowCannon extends JavaPlugin {

	private static final Map<UUID, String> playerTags = new HashMap<>();

	private Scheduler.Task task;
	private Scheduler.Task task2;
	private Scheduler.Task task3;
	private Scheduler.Task task4;
	private Scheduler.Task task5;
	private Scheduler.Task task6;

	@Override
	public void onEnable() {

		// Updated for the disappearance of safeguard in 1.20.5+ on Paper. Supports all versions including legacy and Spigot.
		final String bukkitVersion = Bukkit.getServer().getBukkitVersion(); // 1.20.6-R0.1-SNAPSHOT
		final String versionString = bukkitVersion.split("\\-")[0]; // 1.20.6
		final String[] versions = versionString.split("\\.");

		final int version = Integer.parseInt(versions[1]); // 20 in 1.20.6
		//final int subversion = versions.length == 3 ? Integer.parseInt(versions[2]) : 0; // 6 in 1.20.6

		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);
		getServer().getPluginManager().registerEvents(new LaserPointerListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new HealthTagListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new AiListener(), this);
		getServer().getPluginManager().registerEvents(new RegionListener(), this);
		//getServer().getPluginManager().registerEvents(new MenuListener(), this); // TODO enable for custom menu system if NOT using Foundation

		if (version >= 14)
			getServer().getPluginManager().registerEvents(new CrawlListener(), this);

		getCommand("cow").setExecutor(new CowCommand());
		getCommand("butterfly").setExecutor(new ButterflyCommand());

		if (version >= 19)
			getCommand("displayentity").setExecutor(new DisplayEntityCommand());

		getCommand("customitem").setExecutor(new CustomItemCommand());
		getCommand("gui").setExecutor(new GuiCommand());
		getCommand("giant").setExecutor(new GiantCommand());
		getCommand("economy").setExecutor(new EconomyCommand());
		getCommand("read").setExecutor(new ReadCommand());
		getCommand("tag").setExecutor(new TagCommand());
		getCommand("hologram").setExecutor(new HologramCommand());
		getCommand("trade").setExecutor(new TradeCommand());
		getCommand("region").setExecutor(new RegionCommand());
		getCommand("email").setExecutor(new EmailCommand());

		if (version == 8/* || minorVersion == 20*/) {
			//EntityRegister_1_8_8.registerEntity("DeadlyChicken", 93, EntityChicken.class, AggressiveChicken1_8_8.class);

			getCommand("psycho").setExecutor(new PsychoCommand());
		}

		if (version >= 14)
			getCommand("crawl").setExecutor(new CrawlCommand());

		if (version >= 12)
			getCommand("toast").setExecutor(new ToastCommand());

		getCommand("locale").setExecutor(new LocaleCommand());
		getCommand("bc").setExecutor(new BungeeCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("ai").setExecutor(new AiCommand());

		CowSettings.getInstance().load();

		if (version >= 13)
			CustomRecipe.register();

		if (getServer().getPluginManager().getPlugin("ProtocolLib") != null)
			ProtocolLibHook.register();

		if (getServer().getPluginManager().getPlugin("Vault") != null)
			CowEconomy.register();

		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
			PlaceholderAPIHook.registerHook();

		if (getServer().getPluginManager().getPlugin("DiscordSRV") != null)
			DiscordSRVHook.register();

		task = Scheduler.runTimer(ButterflyTask.getInstance(), 0, 1);
		if (!Scheduler.isFolia())
			task2 = Scheduler.runTimer(Board.getInstance(), 0, 20 /* updates 1 per second */);
		task3 = Scheduler.runTimer(LaserPointerTask.getInstance(), 0, 1);
		task4 = Scheduler.runTimer(TablistTask.getInstance(), 0, 20);
		task5 = Scheduler.runTimer(ItemPickupTask.getInstance(), 0, 2);
		//task6 = Scheduler.runTimer(MessageBroadcasterTask.getInstance(), 0, 20 * 30); // TODO enable for timed messages broadcaster

		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new Bungee());

		Regions.getInstance().load();
	}

	@Override
	public void onDisable() {

		if (task != null)
			task.cancel();

		if (task2 != null)
			task2.cancel();

		if (task3 != null)
			task3.cancel();

		if (task4 != null)
			task4.cancel();

		if (task5 != null)
			task5.cancel();

		if (task6 != null)
			task6.cancel();

		if (getServer().getPluginManager().getPlugin("DiscordSRV") != null)
			DiscordSRVHook.unregister();

		this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
		this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
	}

	public static Map<UUID, String> getPlayerTags() {
		return playerTags;
	}

	public static CowCannon getInstance() {
		return getPlugin(CowCannon.class);
	}
}
