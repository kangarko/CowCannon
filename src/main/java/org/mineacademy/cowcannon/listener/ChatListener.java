package org.mineacademy.cowcannon.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatListener implements Listener {

	/*@EventHandler
	public void onChat(AsyncChatEvent event) {
		//for (Audience audience : event.viewers()) {
		//	System.out.println(audience);
		//}

		TextComponent textComponent = (TextComponent) event.message();
		MiniMessage miniMessage = MiniMessage.miniMessage();

		Component replacedText = miniMessage.deserialize(textComponent.content());
		event.message(replacedText);
	}*/

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);

		boolean papiPresent = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

		for (Player recipient : event.getRecipients()) {
			String message = event.getMessage();

			message = message.replace("{player}", event.getPlayer().getName());

			if (papiPresent) {
				message = PlaceholderAPI.setPlaceholders(event.getPlayer(), message);
				message = PlaceholderAPI.setRelationalPlaceholders(event.getPlayer(), recipient, message);
			}

			recipient.sendMessage(ChatColor.GRAY + event.getPlayer().getName() + ": " + ChatColor.WHITE + message);
		}
	}
}
