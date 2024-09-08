package org.mineacademy.cowcannon.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import org.mineacademy.cowcannon.CowCannon;
import org.mineacademy.cowcannon.hook.DiscordSRVHook;
import org.mineacademy.cowcannon.hook.VaultHook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class EconomyCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label,  String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		if (args.length < 2)
			return false;

		String param = args[0];
		String playerName = args[1];
		String amountRaw = args.length == 3 ? args[2] : "";

		new BukkitRunnable() {

			@Override
			public void run() {
				OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);

				if (!target.hasPlayedBefore()) {
					sender.sendMessage(ChatColor.RED + "Player '" + playerName + "' has never played on the server.");

					return;
				}

				new BukkitRunnable() {

					@Override
					public void run() {

						if (!VaultHook.hasEconomy()) {
							sender.sendMessage(ChatColor.RED + "Vault plugin not found or it did not find any compatible Economy plugin.");

							return;
						}

						if ("view".equals(param)) {
							double balance = VaultHook.getBalance(target);

							sender.sendMessage(ChatColor.GOLD + target.getName() + "'s balance: " + VaultHook.formatCurrencySymbol(balance));

						} else if ("take".equals(param) || "give".equals(param)) {
							double amount;

							try {
								amount = Double.parseDouble(amountRaw);

							} catch (NumberFormatException e) {
								sender.sendMessage("The amount must be a valid decimal number. Got: '" + args[1] + "'.");

								return;
							}

							if ("take".equals(param)) {
								String errorMessage = VaultHook.withdraw(target, amount);

								if (errorMessage != null && !errorMessage.isEmpty())
									sender.sendMessage(ChatColor.RED + "Error: " + errorMessage);
								else
									sender.sendMessage(ChatColor.RED + "Took " + VaultHook.formatCurrencySymbol(amount) + " from " + target.getName() + "' account.");

							} else {
								String errorMessage = VaultHook.deposit(target, amount);

								if (errorMessage != null && !errorMessage.isEmpty())
									sender.sendMessage(ChatColor.RED + "Error: " + errorMessage);
								else {
									sender.sendMessage(ChatColor.GREEN + "Gave " + VaultHook.formatCurrencySymbol(amount) + " to " + target.getName() + "' account.");

									DiscordSRVHook.sendMessage("standard", "Gave " + VaultHook.formatCurrencySymbol(amount) + " to " + target.getName() + "' account.");
								}
							}

						} else
							sender.sendMessage(ChatColor.RED + "Unknown parameter '" + param + "'. Usage: " + command.getUsage());
					}
				}.runTask(CowCannon.getInstance());
			}
		}.runTaskAsynchronously(CowCannon.getInstance());

		return true;
	}

	@Override
	public java.util.List<String> onTabComplete( CommandSender sender,  Command command,  String alias,  String[] args) {

		if (args.length == 1)
			return Arrays.asList("view", "take", "give").stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());

		else if (args.length == 2)
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());

		else if (args.length == 3 && ("take".equals(args[0]) || "give".equals(args[0])))
			return Arrays.asList("1", "100", "1000").stream().filter(s -> s.startsWith(args[2])).collect(Collectors.toList());

		else
			return new ArrayList<>();
	}
}
