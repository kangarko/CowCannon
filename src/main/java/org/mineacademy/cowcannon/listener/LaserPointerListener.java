package org.mineacademy.cowcannon.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public final class LaserPointerListener implements Listener {

	//private final EffectManager effectManager = new EffectManager(CowCannon.getInstance());

	@EventHandler
	public void onClick(final PlayerInteractEvent event) {

		try {
			if (event.getHand() != EquipmentSlot.HAND)
				return;
		} catch (Throwable t) {
			// MC 1.8
		}

		if (event.getAction() != Action.RIGHT_CLICK_AIR)
			return;

		Player player = event.getPlayer();
		ItemStack hand = player.getItemInHand();
		int distance = 100;

		//if (player.hasPermission("cowcannon.laserpointer")) {
		//	return;
		//}

		if (hand.hasItemMeta() && hand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Laser Pointer")) {
			//RayTraceResult result = player.rayTraceBlocks(distance);
			Block hitBlock = player.getTargetBlock(new HashSet<>(), distance);

			if (hitBlock != null && hitBlock.isSolid()) {
				//if (result != null && result.getHitBlock() != null && result.getHitBlock().isSolid()) {
				/*BigBangEffect effect = new BigBangEffect(effectManager);

				effect.radius = 4;
				effect.setLocation(result.getHitBlock().getLocation().add(0, 2, 0));
				effect.start();

				new BukkitRunnable() {
					@Override
					public void run() {
						effect.cancel();
					}
				}.runTaskLater(CowCannon.getInstance(), 20 * 2);*/

				player.getWorld().createExplosion(hitBlock.getLocation(), 5F, true);
				//player.getWorld().createExplosion(result.getHitBlock().getLocation(), 5F, true);
			} else
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[Laser]" + ChatColor.WHITE + " Target is too far or not a solid block!");
		}
	}
}
