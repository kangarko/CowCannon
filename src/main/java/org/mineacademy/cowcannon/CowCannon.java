package org.mineacademy.cowcannon;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.cowcannon.command.*;
import org.mineacademy.cowcannon.hook.ProtocolLibHook;
import org.mineacademy.cowcannon.listener.ChatListener;
import org.mineacademy.cowcannon.listener.EntityListener;
import org.mineacademy.cowcannon.listener.GuiListener;
import org.mineacademy.cowcannon.listener.LaserPointerListener;
import org.mineacademy.cowcannon.model.Board;
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

		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);
		getServer().getPluginManager().registerEvents(new LaserPointerListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);

		getCommand("cow").setExecutor(new CowCommand());
		getCommand("butterfly").setExecutor(new ButterflyCommand());
		getCommand("displayentity").setExecutor(new DisplayEntityCommand());
		getCommand("customitem").setExecutor(new CustomItemCommand());
		getCommand("gui").setExecutor(new GuiCommand());
		getCommand("giant").setExecutor(new GiantCommand());
		getCommand("donate").setExecutor(new DonateCommand());
		getCommand("read").setExecutor(new ReadCommand());

		CowSettings.getInstance().load();
		CustomRecipe.register();

		if (getServer().getPluginManager().getPlugin("ProtocolLib") != null)
			ProtocolLibHook.register();

		task = getServer().getScheduler().runTaskTimer(this, ButterflyTask.getInstance(), 0, 1);
		task2 = getServer().getScheduler().runTaskTimer(this, Board.getInstance(), 0, 20 /* updates 1 per second */);
		task3 = getServer().getScheduler().runTaskTimer(this, LaserPointerTask.getInstance(), 0, 1);
	}

	@Override
	public void onDisable() {
		if (task != null && !task.isCancelled())
			task.cancel();

		if (task2 != null && !task2.isCancelled())
			task2.cancel();

		if (task3 != null && !task3.isCancelled())
			task3.cancel();
	}

	public BukkitTask getTask() {
		return task;
	}

	public void setTask(BukkitTask task) {
		this.task = task;
	}

	public static CowCannon getInstance() {
		return getPlugin(CowCannon.class);
	}
}
