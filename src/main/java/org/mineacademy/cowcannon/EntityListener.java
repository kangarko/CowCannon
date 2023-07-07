package org.mineacademy.cowcannon;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityListener implements Listener {

	private Map<UUID, PermissionAttachment> permissions = new HashMap<>();

	@EventHandler
	public void onEntityRightClick(PlayerInteractEntityEvent event) {

		if (event.getHand() != EquipmentSlot.HAND)
			return;

		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();

		// iterate through player.getEffectivePermissions() as foreach
		/*for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
			PermissionAttachment attachment = permission.getAttachment();

			System.out.println("Permission: " + permission.getPermission() + " from " + (attachment == null ? "default" : attachment.getPlugin().getName()));
		}

		System.out.println("Before: " + permissions);

		if (permissions.containsKey(player.getUniqueId())) {
			PermissionAttachment permission = permissions.remove(player.getUniqueId());
			player.removeAttachment(permission);

			player.sendMessage("You no longer have the perm!");

		} else {
			PermissionAttachment permission = player.addAttachment(CowCannon.getInstance(), "funky.demo.test", true);

			permissions.put(player.getUniqueId(), permission);
			player.sendMessage("You now have the perm!");
		}

		System.out.println("After: " + permissions);*/

		if (player.getItemInHand().getItemMeta() != null) {
			PersistentDataContainer entityContainer = entity.getPersistentDataContainer();
			PersistentDataContainer handItemContainer = player.getItemInHand().getItemMeta().getPersistentDataContainer();

			if (entity.getType() == CowSettings.getInstance().getExplodingType()
					&& entityContainer.has(Keys.CUSTOM_COW)
					&& handItemContainer.has(Keys.CUSTOM_BUCKET)) {
				
				if (!player.hasPermission("cowcannon.cow.use")) {
					player.sendMessage("You don't have permission to milk cows ;)");

					return;
				}

				entity.getWorld().createExplosion(entity.getLocation(), 2.5F);
			}
		}
	}
}
