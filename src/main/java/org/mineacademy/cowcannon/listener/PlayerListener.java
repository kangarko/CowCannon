package org.mineacademy.cowcannon.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.cowcannon.api.CrawlEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onCrawl(CrawlEvent event) {
		event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&8[&cCrawl&8] &7You are crawling!"));
	}
}
