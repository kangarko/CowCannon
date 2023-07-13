package org.mineacademy.cowcannon.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		//for (Audience audience : event.viewers()) {
		//	System.out.println(audience);
		//}

		TextComponent textComponent = (TextComponent) event.message();
		MiniMessage miniMessage = MiniMessage.miniMessage();

		Component replacedText = miniMessage.deserialize(textComponent.content());
		event.message(replacedText);
	}
}
