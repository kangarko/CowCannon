package org.mineacademy.cowcannon.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HealthTagListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		/*Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Player player = event.getPlayer();

		Objective objective = board.registerNewObjective(
				CowCannon.getInstance().getName() + "_health", Criteria.HEALTH, ChatColor.of("#b1904c") + "❤");

		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		player.setScoreboard(board);*/
	}
}
