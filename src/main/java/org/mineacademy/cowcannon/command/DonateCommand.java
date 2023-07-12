package org.mineacademy.cowcannon.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.mineacademy.cowcannon.CowCannon;
import org.mineacademy.cowcannon.hook.VaultHook;

public final class DonateCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		if (args.length != 2)
			return false;

		new BukkitRunnable() {

			@Override
			public void run() {
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

				if (!target.hasPlayedBefore()) {
					sender.sendMessage(ChatColor.RED + "Player '" + args[0] + "' has never played on the server.");

					return;
				}

				new BukkitRunnable() {

					@Override
					public void run() {
						double amount;

						try {
							amount = Double.parseDouble(args[1]);

						} catch (NumberFormatException e) {
							sender.sendMessage("The amount must be a valid decimal number. Got: '" + args[1] + "'.");

							return;
						}

						if (!VaultHook.hasEconomy()) {
							sender.sendMessage(ChatColor.RED + "Vault plugin not found or it did not find any compatible Economy plugin.");

							return;
						}

						VaultHook.deposit(target, amount);
						sender.sendMessage(ChatColor.GOLD + "Deposited " + VaultHook.formatCurrencySymbol(amount) + " to " + target.getName() + "' account.");
					}
				}.runTask(CowCannon.getInstance());
			}
		}.runTaskAsynchronously(CowCannon.getInstance());

		return true;
	}
}
