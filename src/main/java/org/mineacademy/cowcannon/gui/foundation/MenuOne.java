/*package org.mineacademy.cowcannon.gui.foundation;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.StartPosition;
import org.mineacademy.fo.menu.button.annotation.Position;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.slider.ColoredTextSlider;
import org.mineacademy.fo.slider.ItemSlider;
import org.mineacademy.fo.slider.Slider;

import java.util.List;

public class MenuOne extends Menu {

	//@Position(start = StartPosition.CENTER, value = -2)
	private final Button currentTimeButton;

	public MenuOne() {

		this.setSlotNumbersVisible();
		this.setSize(9 * 3);
		this.setTitle("Animated Menu");

		this.currentTimeButton = new Button() {

			int ticksLived = 0;
			boolean toggle = false;

			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				//animateTitle("&2Hello There");
				new AnotherPage().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				this.ticksLived++;

				if ((this.ticksLived % 20) == 0) { // 20 / 20 = 1.00
					this.ticksLived = 0;

					this.toggle = !this.toggle;
				}

				return ItemCreator.of(
								this.toggle ? CompMaterial.RECOVERY_COMPASS : CompMaterial.COMPASS,
								"Current Time",
								"",
								"Value: " + TimeUtil.getFormattedDate())
						.glow(true)
						.make();
			}

			@Override
			public int getSlot() {
				return getSize() - 5;
			}
		};
	}

	@Override
	protected void onPostDisplay(Player viewer) {
		this.animate(1, this::redrawButtons);

		final String menuTitle = "Animated Menu";
		final Slider<String> textSlider = ColoredTextSlider
				.from(menuTitle)
				.width(10)
				.primaryColor("&6&l")
				.secondaryColor("&0&l");

		this.animateAsync(2, () -> setTitle(textSlider.next()));

		final Slider<List<ItemStack>> itemsSlider = ItemSlider.from(
						ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE, ""),
						ItemCreator.of(CompMaterial.NETHER_STAR, "Slided Item!"))
				.width(9);

		this.animate(10, new MenuRunnable() {
			@Override
			public void run() {
				final List<ItemStack> items = itemsSlider.next();

				for (int i = 0; i < items.size(); i++)
					setItem(9 + i, items.get(i));

				// If the slider has moved the start to the last slot, cancel the animation
				if (items.get(items.size() - 1).getType() == CompMaterial.NETHER_STAR.getMaterial())
					this.cancel();
			}
		});
	}

	//@Override
	//public Menu newInstance() {
	//    return new MenuOne(this.time);
	//}

    /*@Override
    public ItemStack getItemAt(int slot) {

        YamlConfiguration bukkitConfig; // TODO you need to create a config in another class and then link to it here

        if (bukkitConfig.isSet("inventory." + slot))
            return bukkitConfig.getItemStack("inventory." + slot);

        if (slot == 0)
            return ItemCreator.of(CompMaterial.RED_STAINED_GLASS_PANE).name(" ").make();

        return super.getItemAt(slot);
    }

	@Override
	protected boolean isActionAllowed(MenuClickLocation location, int slot, @Nullable ItemStack clicked, @Nullable ItemStack cursor, InventoryAction action) {
		if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY)
			return false;

		if (slot < this.getSize() - 9) // protect the last row with return back and info buttons
			return true;

		return super.isActionAllowed(location, slot, clicked, cursor, action);
	}

    @Override
    protected void onMenuClose(Player player, Inventory inventory) {
        YamlConfig config; // TODO you need to create a config in another class and then link to it here

        ItemStack[] content = inventory.getContents();
        for (int i = 0; i < content.length; i++) {
            ItemStack item = content[i];

            if (item != null) {
                config.set("inventory." + i, item);
            }
        }

        config.save();
    }

	@Override
	protected String[] getInfo() {
		return new String[]{
				"This is the first menu we",
				"create using the Foundation",
				"framework, yay!"
		};
	}

	private class AnotherPage extends Menu {

		@Position(start = StartPosition.CENTER)
		private final Button setDayButton;

		private AnotherPage() {
			super(MenuOne.this); // add ", true" to make a new instance of parent when returning to it

			this.setTitle("Another menu page");

			this.setDayButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					player.getWorld().setTime(1000);

					animateTitle("Time set to day!");
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(
									CompMaterial.PLAYER_HEAD)
							.skullOwner(getViewer().getName())
							.make();
				}
			};
		}
	}
}*/