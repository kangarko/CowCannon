package org.mineacademy.cowcannon.listener;

import de.themoep.minedown.adventure.MineDown;
import io.papermc.paper.event.player.AsyncChatEvent;
import meteordevelopment.starscript.StandardLib;
import meteordevelopment.starscript.Starscript;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatListener implements Listener {

	private final Starscript starscript;

	public ChatListener() {
		this.starscript = new Starscript();

		StandardLib.init(starscript);
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		TextComponent textComponent = (TextComponent) event.message();

		//MiniMessage miniMessage = MiniMessage.miniMessage();
		//Component replacedText = miniMessage.deserialize(textComponent.content());

		event.message(MineDown.parse(textComponent.content()));
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		/*event.setCancelled(true);

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
		}*/

		/*Player player = event.getPlayer();
		Parser.Result result = Parser.parse(event.getMessage());

		// Check for errors
		if (result.hasErrors()) {
			for (Error error : result.errors)
				Common.tell(player, error.toString());

			event.setCancelled(true);
			return;
		}

		Script script = Compiler.compile(result);

		// {tps}
		this.starscript.set("name", player.getName());
		this.starscript.set("good", true);
		this.starscript.set("tps", Math.round(Bukkit.getTPS()[0])); // /tps

		// {player.health}
		final ValueMap playerMap = new ValueMap();

		playerMap.set("name", player.getName());
		playerMap.set("display_name", player.getDisplayName());
		playerMap.set("health", player.getHealth());
		playerMap.set("ping", player.getPing());

		this.starscript.set("player", playerMap);

		System.out.println(starscript.run(script)); // Hello MineGame159!*/
	}
}
