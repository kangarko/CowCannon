package org.mineacademy.cowcannon.model;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.mineacademy.cowcannon.CowCannon;

public final class CustomRecipe {

	public static void register() {
		final ItemStack superPaper = new ItemStack(Material.PAPER);
		final ItemMeta superPaperMeta = superPaper.getItemMeta();
		superPaperMeta.setDisplayName(ChatColor.GOLD + "Super Paper");

		try {
			superPaperMeta.addEnchant(Enchantment.SHARPNESS, 1, true);
		} catch (final NoSuchFieldError err) {
			superPaperMeta.addEnchant(Enchantment.getByName("DAMAGE_ALL"), 1, true);
		}

		superPaperMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		superPaper.setItemMeta(superPaperMeta);

		final ShapelessRecipe recipe0 = new ShapelessRecipe(new NamespacedKey(CowCannon.getInstance(), "SuperPaperRecipe"), superPaper);
		recipe0.addIngredient(3, Material.BOOK);
		Bukkit.addRecipe(recipe0);

		final ItemStack dickSword = new ItemStack(Material.DIAMOND_SWORD);
		final ItemMeta dickSwordMeta = dickSword.getItemMeta();
		dickSwordMeta.setDisplayName(ChatColor.WHITE + "Dick Sword");
		dickSwordMeta.setLore(Arrays.asList("", ChatColor.GRAY + "Dig more!"));
		dickSword.setItemMeta(dickSwordMeta);

		final FurnaceRecipe recipe1 = new FurnaceRecipe(new NamespacedKey(CowCannon.getInstance(), "DickSwordRecipe"),
				dickSword, new RecipeChoice.ExactChoice(superPaper), 10, 20 /*1 sec*/);
		Bukkit.addRecipe(recipe1);

		final ItemStack laserPointer = new ItemStack(Material.NETHER_STAR);
		final ItemMeta laserPointerMeta = laserPointer.getItemMeta();
		laserPointerMeta.setDisplayName(ChatColor.WHITE + "Laser Pointer");
		laserPointer.setItemMeta(laserPointerMeta);

		final ShapedRecipe recipe2 = new ShapedRecipe(new NamespacedKey(CowCannon.getInstance(), "LaserPointerRecipe"), laserPointer);
		recipe2.shape(
				" D ",
				"DBD",
				" D ");

		recipe2.setIngredient('D', dickSword);
		recipe2.setIngredient('B', new ItemStack(Material.BOOK));
		Bukkit.addRecipe(recipe2);
	}
}
