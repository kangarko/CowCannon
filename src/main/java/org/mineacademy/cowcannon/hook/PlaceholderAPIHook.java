package org.mineacademy.cowcannon.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPIHook extends PlaceholderExpansion implements Relational {

	@Override
	public @NotNull String getIdentifier() {
		return "cowcannon";
	}

	@Override
	public @NotNull String getAuthor() {
		return "MineAcademy";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	@Override
	public @Nullable String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {

		if (offlinePlayer != null && offlinePlayer.isOnline()) {
			Player player = offlinePlayer.getPlayer();

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
