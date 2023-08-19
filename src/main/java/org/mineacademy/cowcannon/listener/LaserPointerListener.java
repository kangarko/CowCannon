package org.mineacademy.cowcannon.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class LaserPointerListener implements Listener {

	//private final EffectManager effectManager = new EffectManager(CowCannon.getInstance());

	@EventHandler
	public void onClick(final PlayerInteractEvent event) {
		// MC 1.9+
		//if (event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_AIR)
		//	return;

		Player player = event.getPlayer();
		ItemStack hand = player.getItemInHand();
		int distance = 100;

		//if (player.hasPermission("cowcannon.laserpointer")) {
		//	return;
		//}

		/*if (hand.hasItemMeta() && hand.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Laser Pointer")) {
			RayTraceResult result = player.rayTraceBlocks(distance);

			if (result != null && result.getHitBlock() != null && result.getHitBlock().isSolid()) {*/
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

		/*		player.getWorld().createExplosion(result.getHitBlock().getLocation(), 5F, true);
			} else
				player.sendMessage(ChatColor.LIGHT_PURPLE + "[Laser]" + ChatColor.WHITE + " Target is too far or not a solid block!");
		}*/
	}
}
