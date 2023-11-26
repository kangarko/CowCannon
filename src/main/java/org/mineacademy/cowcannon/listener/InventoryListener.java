package org.mineacademy.cowcannon.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryOpen(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Villager) {
			// See TradeCommand
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (event.getInventory() instanceof MerchantInventory) {
			MerchantInventory inventory = (MerchantInventory) event.getInventory();
			Merchant merchant = inventory.getMerchant();

			if (event.getInventory().getHolder() == null)
				return;

			System.out.println("Recipes total: " + merchant.getRecipeCount());

			for (MerchantRecipe recipe : merchant.getRecipes())
				System.out.println("Recipe: " + recipe.getIngredients() + " -> " + recipe.getResult());

			List<MerchantRecipe> newRecipes = new ArrayList<>();

			ItemStack customBrick = new ItemStack(Material.NETHER_BRICK, 50);
			ItemMeta customBrickMeta = customBrick.getItemMeta();
			customBrickMeta.setDisplayName(ChatColor.RED + "Hell Brick");
			customBrick.setItemMeta(customBrickMeta);

			MerchantRecipe firstRecipe = new MerchantRecipe(customBrick, 10000);

			firstRecipe.addIngredient(new ItemStack(Material.NETHER_STAR, 1));
			firstRecipe.addIngredient(new ItemStack(Material.DIAMOND, 7));

			newRecipes.add(firstRecipe);

			merchant.setRecipes(newRecipes);
		}
	}
}
