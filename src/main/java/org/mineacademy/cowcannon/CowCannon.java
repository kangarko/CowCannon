package org.mineacademy.cowcannon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.cowcannon.command.*;
import org.mineacademy.cowcannon.hook.CowEconomy;
import org.mineacademy.cowcannon.hook.DiscordSRVHook;
import org.mineacademy.cowcannon.hook.PlaceholderAPIHook;
import org.mineacademy.cowcannon.hook.ProtocolLibHook;
import org.mineacademy.cowcannon.listener.*;
import org.mineacademy.cowcannon.model.Board;
import org.mineacademy.cowcannon.model.Bungee;
import org.mineacademy.cowcannon.model.CustomRecipe;
import org.mineacademy.cowcannon.setting.CowSettings;
import org.mineacademy.cowcannon.task.ButterflyTask;
import org.mineacademy.cowcannon.task.LaserPointerTask;

public final class CowCannon extends JavaPlugin {

	private BukkitTask task;
	private BukkitTask task2;
	private BukkitTask task3;

	@Override
	public void onEnable() {

		// This gives a string like 1_16_R3
		String minecraftVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

		// We want to extract the 16, which equals the MC version, such as Minecraft 1.16
		int minorVersion = Integer.parseInt(minecraftVersion.split("_")[1]);

		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);
		getServer().getPluginManager().registerEvents(new LaserPointerListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new HealthTagListener(), this);

		if (minorVersion >= 14)
			getServer().getPluginManager().registerEvents(new CrawlListener(), this);

		getCommand("cow").setExecutor(new CowCommand());
		getCommand("butterfly").setExecutor(new ButterflyCommand());

		if (minorVersion >= 19)
			getCommand("displayentity").setExecutor(new DisplayEntityCommand());

		getCommand("customitem").setExecutor(new CustomItemCommand());
		getCommand("gui").setExecutor(new GuiCommand());
		getCommand("giant").setExecutor(new GiantCommand());
		getCommand("economy").setExecutor(new EconomyCommand());
		getCommand("read").setExecutor(new ReadCommand());

		if (minorVersion == 8/* || minorVersion == 20*/) {
			//EntityRegister_1_8_8.registerEntity("DeadlyChicken", 93, EntityChicken.class, AggressiveChicken1_8_8.class);

			getCommand("psycho").setExecutor(new PsychoCommand());
		}

		if (minorVersion >= 14)
			getCommand("crawl").setExecutor(new CrawlCommand());

		if (minorVersion >= 12)
			getCommand("toast").setExecutor(new ToastCommand());

		getCommand("locale").setExecutor(new LocaleCommand());
		getCommand("bc").setExecutor(new BungeeCommand());

		CowSettings.getInstance().load();

		if (minorVersion >= 13)
			CustomRecipe.register();

		if (getServer().getPluginManager().getPlugin("ProtocolLib") != null)
			ProtocolLibHook.register();

		if (getServer().getPluginManager().getPlugin("Vault") != null)
			CowEconomy.register();

		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
			PlaceholderAPIHook.registerHook();

		if (getServer().getPluginManager().getPlugin("DiscordSRV") != null)
			DiscordSRVHook.register();

		task = getServer().getScheduler().runTaskTimer(this, ButterflyTask.getInstance(), 0, 1);
		task2 = getServer().getScheduler().runTaskTimer(this, Board.getInstance(), 0, 20 /* updates 1 per second */);
		task3 = getServer().getScheduler().runTaskTimer(this, LaserPointerTask.getInstance(), 0, 1);

		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new Bungee());
	}

	@Override
	public void onDisable() {

		if (task != null && Bukkit.getScheduler().isCurrentlyRunning(task.getTaskId()))
			task.cancel();

		if (task2 != null && Bukkit.getScheduler().isCurrentlyRunning(task2.getTaskId()))
			task2.cancel();

		if (task3 != null && Bukkit.getScheduler().isCurrentlyRunning(task3.getTaskId()))
			task3.cancel();

		if (getServer().getPluginManager().getPlugin("DiscordSRV") != null)
			DiscordSRVHook.unregister();

		this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
		this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
	}

	public static CowCannon getInstance() {
		return getPlugin(CowCannon.class);
	}
}
