package org.mineacademy.cowcannon;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.collection.StrictMap;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuContainer;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.button.StartPosition;
import org.mineacademy.fo.menu.button.annotation.Position;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.model.MenuClickLocation;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MainMenu extends Menu {

	@Position(start = StartPosition.CENTER, value = -2)
	private final Button openPreferencesButton;

	@Position(start = StartPosition.CENTER)
	private final Button selectMobEggsButton;

	@Position(start = StartPosition.CENTER, value = 2)
	private final Button getItemsButton;

	public MainMenu() {
		setSlotNumbersVisible();
		setTitle("&8Main Menu");
		setSize(9 * 3);

		this.openPreferencesButton = new ButtonMenu(new PreferencesMenu(), CompMaterial.BUCKET,
				"&6&lOpen Preferences",
				"",
				"Click to open the menu");

		this.selectMobEggsButton = new ButtonMenu(new SelectMobEggsMenu(), CompMaterial.SPAWNER,
				"&6&lSelect Mob Eggs",
				"",
				"Click to open the menu");

		this.getItemsButton = new ButtonMenu(new GetItemsMenu(), CompMaterial.DIAMOND,
				"&6&lGet Items",
				"",
				"Click to open the menu");
	}

	private class PreferencesMenu extends Menu {

		@Position(start = StartPosition.CENTER, value = -1)
		private final Button clearInventoryButton;

		@Position(start = StartPosition.CENTER, value = 1)
		private final Button refillHealthButton;

		PreferencesMenu() {
			super(MainMenu.this);

			setTitle("&8Preferences");
			setSize(9 * 3);

			this.clearInventoryButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					player.getInventory().clear();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.LAVA_BUCKET, "&6&lClear Inventory").make();
				}
			};

			this.refillHealthButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {
					player.setHealth(player.getMaxHealth());

					restartMenu("&2Refilled health!");
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(CompMaterial.GOLDEN_APPLE,
							"&6&lRefill Health",
							"",
							"&fCurrent Health: &c" + Math.round(getViewer().getHealth()),
							"",
							"Click to refill.").make();
				}
			};
		}

		@Override
		protected void onPostDisplay(Player viewer) {

			animate(20, new MenuRunnable() {
				boolean toggle = true;

				@Override
				public void run() {
					setItem(getCenterSlot(), ItemCreator.of(toggle ? CompMaterial.RED_WOOL : CompMaterial.BLUE_WOOL).make());

					toggle = !toggle;
				}
			});
		}

		@Override
		protected String[] getInfo() {
			return new String[]{
					"Click bucket to clear your inventory.",
					"",
					"Click apple to refill your health."
			};
		}
	}

	private class SelectMobEggsMenu extends MenuPagged<EntityType> {

		SelectMobEggsMenu() {
			super(MainMenu.this, Arrays.asList(EntityType.values())
					.stream()
					.filter(type -> type.isSpawnable() && type.isAlive())
					.collect(Collectors.toList()));

			setTitle("&8Select Mob Eggs");
			//setSize(9 * 4);
		}

		@Override
		protected ItemStack convertToItemStack(EntityType item) {
			return ItemCreator.ofEgg(item,
					"&6&l" + ItemUtil.bountifyCapitalized(item.name()), // EXP_ORB -> Exp Orb
					"",
					"Click to obtain the egg").make();
		}

		@Override
		protected void onPageClick(Player player, EntityType entityType, ClickType clickType) {
			ItemCreator.ofEgg(entityType).give(player);
		}
	}

	private class GetItemsMenu extends MenuContainer {

		GetItemsMenu() {
			super(MainMenu.this);

			setTitle("&8Get Items");
			setSize(9 * 2);
		}

		@Override
		protected ItemStack getDropAt(int slot) {
			if (slot < 9 * 1)
				return ItemCreator.ofPotion(PotionEffectType.INVISIBILITY).make();

			return NO_ITEM;
		}

		@Override
		protected void onMenuClose(StrictMap<Integer, ItemStack> items) {
			// TODO custom logic > save items to your config / db
		}

		@Override
		protected boolean isActionAllowed(MenuClickLocation location, int slot, @Nullable ItemStack clicked, @Nullable ItemStack cursor) {
			return slot <= 9 * 1;
		}
	}
}
