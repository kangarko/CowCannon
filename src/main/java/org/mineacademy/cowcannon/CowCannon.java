package org.mineacademy.cowcannon;

import org.bukkit.plugin.java.JavaPlugin;

public final class CowCannon extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getCommand("cow").setExecutor(new CowCommand());
		CowSettings.getInstance().load();
	}

	@Override
	public void onDisable() {
	}

	public static CowCannon getInstance() {
		return getPlugin(CowCannon.class);
	}
}
