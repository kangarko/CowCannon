package org.mineacademy.cowcannon.gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.mineacademy.cowcannon.CowCannon;

public class Menu {

	private final List<Button> buttons = new ArrayList<>();

	private int size = 9 * 3;
	private String title = "Custom Menu";

	private final Menu parent;
	private boolean extraButtonsRegistered = false;

	public Menu() {
		this(null);
	}

	public Menu(@Nullable Menu parent) {
		this.parent = parent;
	}

	public final List<Button> getButtons() {
		return buttons;
	}

	protected final void addButton(Button button) {
		this.buttons.add(button);
	}

	protected final void setSize(int size) {
		this.size = size;
	}

	protected final void setTitle(String title) {
		this.title = title;
	}

	public final void displayTo(Player player) {
		final Inventory inventory = Bukkit.createInventory(player, this.size,
				ChatColor.translateAlternateColorCodes('&', this.title));

		for (final Button button : this.buttons)
			inventory.setItem(button.getSlot(), button.getItem());

		if (this.parent != null && !this.extraButtonsRegistered) {
			this.extraButtonsRegistered = true;

			final Button returnBackButton = new Button(this.size - 1) {
				@Override
				public ItemStack getItem() {
					final ItemStack item = new ItemStack(Material.OAK_DOOR);
					final ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(ChatColor.WHITE + "Return Back");
					item.setItemMeta(meta);

					return item;
					//return ItemCreator.of(CompMaterial.OAK_DOOR, "Return Back").make();
				}

				@Override
				public void onClick(Player player) {
					try {
						final Menu newMenuInstance = parent.getClass().getConstructor().newInstance();

						newMenuInstance.displayTo(player);

					} catch (final ReflectiveOperationException ex) {
						ex.printStackTrace();
					}
				}
			};

			this.buttons.add(returnBackButton);
			inventory.setItem(returnBackButton.getSlot(), returnBackButton.getItem());
		}

		if (player.hasMetadata("CoolMenuPlugin"))
			player.closeInventory();

		player.setMetadata("CoolMenuPlugin", new FixedMetadataValue(CowCannon.getInstance(), this));

		System.out.println("Opening inventory");
		player.openInventory(inventory);
	}
}
