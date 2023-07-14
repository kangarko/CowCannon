package org.mineacademy.cowcannon.listener;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.WebhookUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.mineacademy.cowcannon.hook.DiscordSRVHook;

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
		String message = event.getMessage();

		if (papiPresent)
			message = PlaceholderAPI.setPlaceholders(event.getPlayer(), message);

		message = message.replace("{player}", event.getPlayer().getName());

		for (Player recipient : event.getRecipients()) {
			if (papiPresent)
				message = PlaceholderAPI.setRelationalPlaceholders(event.getPlayer(), recipient, message);

			recipient.sendMessage(ChatColor.GRAY + event.getPlayer().getName() + ": " + ChatColor.WHITE + message);
		}

		if (DiscordSRVHook.isDiscordSRVHooked()) {
			TextChannel textChannel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName("standard");

			WebhookUtil.deliverMessage(textChannel, event.getPlayer(), event.getMessage());
		}
	}
}
