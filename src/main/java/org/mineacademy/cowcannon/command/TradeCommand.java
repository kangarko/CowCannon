package org.mineacademy.cowcannon.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class TradeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		Player player = (Player) sender;
		Merchant merchant = Bukkit.createMerchant(ChatColor.GREEN + "Minion");

		List<MerchantRecipe> recipes = new ArrayList<>();
		MerchantRecipe first = new MerchantRecipe(new ItemStack(Material.DIAMOND, 2), 10000);

		first.addIngredient(new ItemStack(Material.PAPER, 7));
		recipes.add(first);

		merchant.setRecipes(recipes);

		player.openMerchant(merchant, true);
		return true;
	}
}
