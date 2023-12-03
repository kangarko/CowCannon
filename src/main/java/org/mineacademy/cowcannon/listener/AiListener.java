package org.mineacademy.cowcannon.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.mineacademy.cowcannon.model.Conversation;
import org.mineacademy.cowcannon.util.Common;
import org.mineacademy.cowcannon.util.Keys;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AiListener implements Listener {

	private final Map<UUID, Conversation> conversingPlayers = new HashMap<>();

	@EventHandler
	public void onChatMessage(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final Conversation conversation = this.conversingPlayers.get(player.getUniqueId());
		String message = event.getMessage();

		if (conversation != null) {
			event.setCancelled(true);

			if (message.length() > 1)
				message = message.substring(0, 1).toUpperCase() + message.substring(1);

			final boolean remove = !conversation.onTalk(message);

			if (remove)
				this.conversingPlayers.remove(player.getUniqueId());
			else
				Common.tell(player, "&8[&6You&8] &7" + message);
		}
	}

	@EventHandler
	public void onEntityRightClick(PlayerInteractEntityEvent event) {
		Entity entity = event.getRightClicked();

		if (!entity.getPersistentDataContainer().has(Keys.NPC_NAME, PersistentDataType.STRING))
			return;

		if (event.getHand() != EquipmentSlot.HAND)
			return;

		final Player player = event.getPlayer();

		if (this.conversingPlayers.containsKey(player.getUniqueId())) {
			Common.tell(event.getPlayer(), "&cYou are already talking to an NPC. Type 'stop' to stop.");

			return;
		}

		final String role = entity.getPersistentDataContainer().get(Keys.NPC_ROLE, PersistentDataType.STRING);
		final Conversation conversation = new Conversation(player, entity, role);

		this.conversingPlayers.put(player.getUniqueId(), conversation);
		conversation.start();
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.conversingPlayers.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		if (event.getEntity().getPersistentDataContainer().has(Keys.NPC_NAME))
			event.setCancelled(true);
	}
}
