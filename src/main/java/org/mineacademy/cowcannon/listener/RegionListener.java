package org.mineacademy.cowcannon.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.mineacademy.cowcannon.model.Region;
import org.mineacademy.cowcannon.model.Regions;
import org.mineacademy.cowcannon.util.Common;

public final class RegionListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Regions regions = Regions.getInstance();

		Region standingIn = regions.findRegion(event.getBlock().getLocation());

		if (standingIn != null) {
			Common.tell(player, "Cannot break blocks in protected region " + standingIn.getName() + ".");

			event.setCancelled(true);
		}
	}
}
