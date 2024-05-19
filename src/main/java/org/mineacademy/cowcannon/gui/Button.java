package org.mineacademy.cowcannon.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

    private final int slot;

    public Button(int slot) {
        this.slot = slot;
    }

    public final int getSlot() {
        return slot;
    }

    public abstract ItemStack getItem();

    public abstract void onClick(Player player);
}
