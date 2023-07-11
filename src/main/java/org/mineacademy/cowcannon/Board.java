package org.mineacademy.cowcannon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Board implements Runnable {

	private final static Board instance = new Board();

	private Board() {
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getScoreboard() != null && player.getScoreboard().getObjective(CowCannon.getInstance().getName()) != null)
				updateScoreboard(player);
			else
				createNewScoreboard(player);
		}
	}

	private void createNewScoreboard(Player player) {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective(CowCannon.getInstance().getName(), "yummy");

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "COW PROTOTYPE");

		objective.getScore(ChatColor.WHITE + " ").setScore(8);
		objective.getScore(ChatColor.WHITE + "Games in this lobby are").setScore(7);
		objective.getScore(ChatColor.WHITE + "under heavy development").setScore(6);
		objective.getScore(ChatColor.RED + " ").setScore(5);
		objective.getScore(ChatColor.WHITE + "Report bugs and leave").setScore(4);
		objective.getScore(ChatColor.WHITE + "feedback at").setScore(3);
		objective.getScore(ChatColor.YELLOW + "mineacademy.org/project-orion").setScore(2);
		objective.getScore(ChatColor.GREEN + " ").setScore(1);
		//objective.getScore(ChatColor.WHITE + "Walked: 0cm").setScore(0);

		Team team1 = scoreboard.registerNewTeam("team1");
		String teamKey = ChatColor.GOLD.toString();

		team1.addEntry(teamKey);
		team1.setPrefix("Walked: ");
		team1.setSuffix("0cm");

		objective.getScore(teamKey).setScore(0);
		player.setScoreboard(scoreboard);
	}

	private void updateScoreboard(Player player) {
		Scoreboard scoreboard = player.getScoreboard();
		Team team1 = scoreboard.getTeam("team1");

		team1.setSuffix(ChatColor.YELLOW + "" + (player.getStatistic(Statistic.WALK_ONE_CM) + player.getStatistic(Statistic.SPRINT_ONE_CM)) + "cm");
	}

	public static Board getInstance() {
		return instance;
	}
}
