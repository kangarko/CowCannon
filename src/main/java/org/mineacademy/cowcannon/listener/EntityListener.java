package org.mineacademy.cowcannon.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.mineacademy.cowcannon.hook.VaultHook;
import org.mineacademy.cowcannon.setting.CowSettings;
import org.mineacademy.cowcannon.util.Keys;

public final class EntityListener implements Listener {

	//private Map<UUID, PermissionAttachment> permissions = new HashMap<>();

	@EventHandler
	public void onEntityKill(EntityDeathEvent event) {
		final Player killer = event.getEntity().getKiller();

		if (killer != null && event.getEntity() instanceof Cow) {
			VaultHook.deposit(killer, 1);

			killer.sendMessage(ChatColor.GOLD + "You have earned 1 Cow!");
		}
	}

	@EventHandler
	public void onEntityRightClick(PlayerInteractEntityEvent event) {

		try {
			if (event.getHand() != EquipmentSlot.HAND)
				return;
		} catch (final Throwable t) {
			// Ignore
		}

		final Player player = event.getPlayer();
		final Entity entity = event.getRightClicked();

		/*player.sendMessage(Component
				.text("Hello, click me!")
				.color(NamedTextColor.RED)
				.decorate(TextDecoration.BOLD, TextDecoration.UNDERLINED)
				.clickEvent(ClickEvent.runCommand("/help"))
				.hoverEvent(HoverEvent.showText(Component.text("Hello, \nI am a cow!")))
				.append(Component.keybind("key.jump")
						.color(NamedTextColor.LIGHT_PURPLE)
						.decoration(TextDecoration.BOLD, true))
		);*/

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
			try {
				final PersistentDataContainer entityContainer = entity.getPersistentDataContainer();
				final PersistentDataContainer handItemContainer = player.getItemInHand().getItemMeta().getPersistentDataContainer();

				if (entity.getType() == CowSettings.getInstance().getExplodingType()
						&& entityContainer.has(Keys.CUSTOM_COW)
						&& handItemContainer.has(Keys.CUSTOM_BUCKET)) {

					if (!player.hasPermission("cowcannon.cow.use")) {
						player.sendMessage("You don't have permission to milk cows ;)");

						return;
					}

					entity.getWorld().createExplosion(entity.getLocation(), 2.5F);
				}
			} catch (final LinkageError err) {
				// Ignore
			}
		}
	}
}
