package org.mineacademy.cowcannon;

import org.bukkit.plugin.java.JavaPlugin;

public final class CowCannon extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
	}

	@Override
	public void onDisable() {
	}
}
