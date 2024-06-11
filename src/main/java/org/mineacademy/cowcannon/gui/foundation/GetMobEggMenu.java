/*package org.mineacademy.cowcannon.gui.foundation;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.model.ItemCreator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GetMobEggMenu extends MenuPagged<EntityType> {

	public GetMobEggMenu() {
		super(9 * 3, Arrays.stream(EntityType.values())
				.filter(type -> type.isSpawnable() && type.isAlive())
				.collect(Collectors.toList()));

		this.setTitle("Get Mob Egg");
	}


	@Override
	protected ItemStack convertToItemStack(EntityType item) {
		return ItemCreator.ofEgg(item).lore(
				"",
				"Click to receive",
				"the item to inventory.").make();
	}

	@Override
	protected void onPageClick(Player player, EntityType type, ClickType click) {
		ItemCreator.ofEgg(type).give(player);

		this.animateTitle("&2Egg received!");
	}
}*/
