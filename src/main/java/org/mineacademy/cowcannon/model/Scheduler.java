package org.mineacademy.cowcannon.model;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.cowcannon.CowCannon;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

public final class Scheduler {

	private static boolean isFolia;

	static {
		try {
			Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
			isFolia = true;

		} catch (final ClassNotFoundException e) {
			isFolia = false;
		}
	}

	public static void run(Runnable runnable) {
		if (isFolia)
			Bukkit.getGlobalRegionScheduler()
					.execute(CowCannon.getInstance(), runnable);

		else
			Bukkit.getScheduler().runTask(CowCannon.getInstance(), runnable);
	}

	public static Task runLater(Runnable runnable, long delayTicks) {
		if (isFolia)
			return new Task(Bukkit.getGlobalRegionScheduler()
					.runDelayed(CowCannon.getInstance(), t -> runnable.run(), delayTicks));

		else
			return new Task(Bukkit.getScheduler().runTaskLater(CowCannon.getInstance(), runnable, delayTicks));
	}

	public static Task runTimer(Runnable runnable, long delayTicks, long periodTicks) {
		if (isFolia)
			return new Task(Bukkit.getGlobalRegionScheduler()
					.runAtFixedRate(CowCannon.getInstance(), t -> runnable.run(), delayTicks < 1 ? 1 : delayTicks, periodTicks));

		else
			return new Task(Bukkit.getScheduler().runTaskTimer(CowCannon.getInstance(), runnable, delayTicks, periodTicks));
	}

	public static boolean isFolia() {
		return isFolia;
	}

	public static class Task {

		private Object foliaTask;
		private BukkitTask bukkitTask;

		Task(Object foliaTask) {
			this.foliaTask = foliaTask;
		}

		Task(BukkitTask bukkitTask) {
			this.bukkitTask = bukkitTask;
		}

		public void cancel() {
			if (foliaTask != null)
				((ScheduledTask) foliaTask).cancel();
			else
				bukkitTask.cancel();
		}
	}
}