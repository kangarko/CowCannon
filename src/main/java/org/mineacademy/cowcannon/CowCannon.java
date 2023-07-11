package org.mineacademy.cowcannon;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class CowCannon extends JavaPlugin {

	private BukkitTask task;
	private BukkitTask task2;

	@Override
	public void onEnable() {

		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);

		getCommand("cow").setExecutor(new CowCommand());
		getCommand("butterfly").setExecutor(new ButterflyCommand());
		getCommand("displayentity").setExecutor(new DisplayEntityCommand());
		getCommand("customitem").setExecutor(new CustomItemCommand());
		getCommand("gui").setExecutor(new GuiCommand());

		CowSettings.getInstance().load();

		task = getServer().getScheduler().runTaskTimer(this, ButterflyTask.getInstance(), 0, 1);
		task2 = getServer().getScheduler().runTaskTimer(this, Board.getInstance(), 0, 20 /* updates 1 per second */);
	}

	@Override
	public void onDisable() {
		if (task != null && !task.isCancelled())
			task.cancel();

		if (task2 != null && !task2.isCancelled())
			task2.cancel();
	}

	public static CowCannon getInstance() {
		return getPlugin(CowCannon.class);
	}
}
