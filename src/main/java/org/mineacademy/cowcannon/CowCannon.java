package org.mineacademy.cowcannon;

import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class CowCannon extends SimplePlugin {

	private BukkitTask task;

	@Override
	public void onPluginStart() {

		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);

		getCommand("cow").setExecutor(new CowCommand());
		getCommand("butterfly").setExecutor(new ButterflyCommand());
		getCommand("displayentity").setExecutor(new DisplayEntityCommand());
		getCommand("customitem").setExecutor(new CustomItemCommand());
		getCommand("gui").setExecutor(new GuiCommand());

		CowSettings.getInstance().load();

		task = getServer().getScheduler().runTaskTimer(this, ButterflyTask.getInstance(), 0, 1);
	}

	@Override
	public void onPluginStop() {
		if (task != null && !task.isCancelled())
			task.cancel();
	}

	public static CowCannon getInstance() {
		return getPlugin(CowCannon.class);
	}
}
