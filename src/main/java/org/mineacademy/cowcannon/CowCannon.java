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
import org.mineacademy.cowcannon.setting.CowSettings;
import org.mineacademy.cowcannon.task.ButterflyTask;
import org.mineacademy.cowcannon.task.LaserPointerTask;

public final class CowCannon extends JavaPlugin {

	private BukkitTask task;
	private BukkitTask task2;
	private BukkitTask task3;

	@Override
	public void onEnable() {

		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);
		getServer().getPluginManager().registerEvents(new LaserPointerListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new HealthTagListener(), this);

		String minecraftVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

		if (minecraftVersion.contains("1_8_R3")) {
			//
		} else
			getServer().getPluginManager().registerEvents(new CrawlListener(), this);

		getCommand("cow").setExecutor(new CowCommand());
		getCommand("butterfly").setExecutor(new ButterflyCommand());
		getCommand("displayentity").setExecutor(new DisplayEntityCommand());
		getCommand("customitem").setExecutor(new CustomItemCommand());
		getCommand("gui").setExecutor(new GuiCommand());
		getCommand("giant").setExecutor(new GiantCommand());
		getCommand("economy").setExecutor(new EconomyCommand());
		getCommand("read").setExecutor(new ReadCommand());
		getCommand("psycho").setExecutor(new PsychoCommand());

		try {
			getCommand("crawl").setExecutor(new CrawlCommand());
		} catch (LinkageError t) {
			// have an alternative code for old MC version
		}

		try {
			getCommand("toast").setExecutor(new ToastCommand());
		} catch (LinkageError t) {
			// have an alternative code for old MC version
		}

		getCommand("locale").setExecutor(new LocaleCommand());
		getCommand("bc").setExecutor(new BungeeCommand());

		CowSettings.getInstance().load();
		//CustomRecipe.register();

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
