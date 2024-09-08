package org.mineacademy.cowcannon.hook;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;

public class PlaceholderAPIHook extends PlaceholderExpansion implements Relational {

	@Override
	public String getIdentifier() {
		return "cowcannon";
	}

	@Override
	public String getAuthor() {
		return "MineAcademy";
	}

	@Override
	public String getVersion() {
		return "1.0.0";
	}

	@Override
	public String onRequest(OfflinePlayer offlinePlayer, String params) {

		if (offlinePlayer != null && offlinePlayer.isOnline()) {
			final Player player = offlinePlayer.getPlayer();

			if (params.equalsIgnoreCase("demo")) {
				return "Hello World";
			} else if (params.equalsIgnoreCase("balance")) {
				return VaultHook.formatCurrencySymbol(VaultHook.getBalance(player));
			}
		}

		return null;
	}

	public static void registerHook() {
		new PlaceholderAPIHook().register();
	}

	@Override
	public String onPlaceholderRequest(Player first, Player second, String params) {

		if (first != null && second != null) {
			if (params.equalsIgnoreCase("status")) {

				if (first.getName().equals(second.getName()))
					return ChatColor.GRAY + "Neutral";

				if (first.getName().equals("kangarko") && second.getName().equals("secondaccount"))
					return ChatColor.RED + "Enemy";

				return ChatColor.GREEN + "Friend";
			}
		}

		return null;
	}
}
