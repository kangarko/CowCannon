package org.mineacademy.cowcannon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public final class CustomRecipes {

	public static void register() {
		ItemStack superPaper = new ItemStack(Material.PAPER);
		ItemMeta superPaperMeta = superPaper.getItemMeta();
		superPaperMeta.setDisplayName(ChatColor.GOLD + "Super Paper");
		superPaperMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		superPaperMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		superPaper.setItemMeta(superPaperMeta);

		ShapelessRecipe recipe0 = new ShapelessRecipe(new NamespacedKey(CowCannon.getInstance(), "SuperPaperRecipe"), superPaper);
		recipe0.addIngredient(3, Material.BOOK);
		Bukkit.addRecipe(recipe0);

		ItemStack dickSword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta dickSwordMeta = dickSword.getItemMeta();
		dickSwordMeta.setDisplayName(ChatColor.WHITE + "Dick Sword");
		dickSwordMeta.setLore(Arrays.asList("", ChatColor.GRAY + "Dig more!"));
		dickSword.setItemMeta(dickSwordMeta);

		FurnaceRecipe recipe1 = new FurnaceRecipe(new NamespacedKey(CowCannon.getInstance(), "DickSwordRecipe"),
				dickSword, new RecipeChoice.ExactChoice(superPaper), 10, 20 /*1 sec*/);
		Bukkit.addRecipe(recipe1);

		ItemStack laserPointer = new ItemStack(Material.NETHER_STAR);
		ItemMeta laserPointerMeta = laserPointer.getItemMeta();
		laserPointerMeta.setDisplayName(ChatColor.WHITE + "Laser Pointer");
		laserPointer.setItemMeta(laserPointerMeta);

		ShapedRecipe recipe2 = new ShapedRecipe(new NamespacedKey(CowCannon.getInstance(), "LaserPointerRecipe"), laserPointer);
		recipe2.shape(
				" D ",
				"DBD",
				" D ");

		recipe2.setIngredient('D', dickSword);
		recipe2.setIngredient('B', new ItemStack(Material.BOOK));
		Bukkit.addRecipe(recipe2);
	}
}
