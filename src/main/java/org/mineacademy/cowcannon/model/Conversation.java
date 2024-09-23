package org.mineacademy.cowcannon.model;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.cowcannon.CowCannon;
import org.mineacademy.cowcannon.util.Common;

import com.google.gson.Gson;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

public class Conversation {

	private final static OpenAiService OPEN_AI_SERVICE = new OpenAiService("YOURKEY", Duration.ofSeconds(8));
	private final static Gson GSON = new Gson();

	private final Player player;
	private final Entity npc;
	private final String role;

	private final StringBuilder conversation = new StringBuilder();

	public Conversation(Player player, Entity npc, String role) {
		this.player = player;
		this.npc = npc;
		this.role = role;
	}

	public void start() {
		this.conversation.append("The following is a conversation with an AI who represents a {role} NPC character in Minecraft. " +
				"The AI should limit his knowledge of the world to Minecraft and being a {role} and try not to stray even if asked about something else. " +
				"Return responses in a valid parsable JSON format. The 'type' is the response type, and 'answer' is your answer. Initially, the type is 'chat' before you agree on traded items. " +
				"After you agree on traded items, put a message to the answer still, make sure to keep the player engaged, but now set the type to 'trade' and there will be keys 'player_item' and " +
				"'player_amount' for what the player is giving to you, and 'npc_item' and 'npc_amount' for what you are giving to the player. " +
				"The items must be a valid Bukkit material name for Spigot API 1.17+, and the amounts must be a positive integer of max 64. " +
				"ONLY provide the items after explicitely agreeing to the trade. When the player is simply asking, give him a list of options and keep the type to 'answer'. " +
				"When you receive a message saying 'agree', change the type back to 'chat' and act as if the trade was finished. " +
				"\n\nHuman: Hey!\nAI: ".replace("{role}", this.role));

		this.processQueue("&#f2c773Starting...");
	}

	public boolean onTalk(String message) {
		if (this.npc.getLocation().distance(this.player.getLocation()) > 10) {
			Common.tell(this.player, "&cThe " + this.role + " stopped talking to you because you moved too far away.");
			clearTradeMetadata();

			return false;
		}

		if (message.equalsIgnoreCase("stop") || message.equalsIgnoreCase("exit")) {
			Common.tell(this.player, "&cYou stopped talking to the " + this.role + ".");
			clearTradeMetadata();

			return false;
		}

		if (this.player.hasMetadata("NPCTrade")) {
			if (message.equalsIgnoreCase("agree")) {
				final Map<String, Object> tradeMap = (Map<String, Object>) this.player.getMetadata("NPCTrade").get(0).value();

				final Material playerItem = Material.valueOf(tradeMap.get("player_item").toString().toUpperCase());
				int playerAmount = (int) Double.parseDouble(tradeMap.get("player_amount").toString());
				final Material npcItem = Material.valueOf(tradeMap.get("npc_item").toString().toUpperCase());
				final int npcAmount = (int) Double.parseDouble(tradeMap.get("npc_amount").toString());

				if (!this.hasEnoughItems(playerItem, playerAmount)) {
					Common.tell(this.player, "&cYou don't have enough " + playerItem + " to sell " + playerAmount + " of them.");

					return true;
				}

				if (!this.hasFreeSpace(npcItem, npcAmount)) {
					Common.tell(this.player, "&cYou don't have enough space in your inventory to buy " + npcAmount + " " + npcItem + ".");

					return true;
				}

				for (int i = 0; i < this.player.getInventory().getSize(); i++) {
					if (this.player.getInventory().getItem(i) != null && this.player.getInventory().getItem(i).getType() == playerItem) {
						final int amount = this.player.getInventory().getItem(i).getAmount();

						if (amount >= playerAmount) {
							this.player.getInventory().getItem(i).setAmount(amount - playerAmount);

							break;
						} else {
							this.player.getInventory().getItem(i).setAmount(0);

							playerAmount -= amount;
						}
					}
				}

				this.player.getInventory().addItem(new ItemStack(npcItem, npcAmount));
				clearTradeMetadata();

				Common.tell(this.player, "&8[&6!&8] &7You agreed to the trade. The " + this.role + " will now give you " + npcAmount + " " + npcItem + " and you will give him " + playerAmount + " " + playerItem + ".");
				Common.tell(this.player, "&8[&6!&8] &7This trade was finished.");

				return false;
			}
		}

		this.conversation.append("\n\nHuman: " + message + "\nAI: ");
		this.processQueue("&#f2c773Thinking...");

		return true;
	}

	private boolean hasEnoughItems(Material item, int amount) {
		int count = 0;

		for (int i = 0; i < this.player.getInventory().getSize(); i++)
			if (this.player.getInventory().getItem(i) != null && this.player.getInventory().getItem(i).getType() == item)
				count += this.player.getInventory().getItem(i).getAmount();

		return count >= amount;
	}

	private boolean hasFreeSpace(Material npcItem, int npcAmount) {
		int freeSpace = 0;
		for (int i = 0; i < this.player.getInventory().getSize(); i++) {
			if (this.player.getInventory().getItem(i) == null) {
				freeSpace += 64;
			} else if (this.player.getInventory().getItem(i).getType() == npcItem) {
				freeSpace += 64 - this.player.getInventory().getItem(i).getAmount();
			}
		}

		return freeSpace >= npcAmount;
	}

	private void clearTradeMetadata() {
		this.player.removeMetadata("NPCTrade", CowCannon.getInstance());
	}

	private void processQueue(String actionBarMessage) {
		Common.actionBar(this.player, actionBarMessage);

		new BukkitRunnable() {
			@Override
			public void run() {
				System.out.println("----------------------------------------");
				System.out.println("Entire conversation so far:");
				System.out.println(conversation);
				System.out.println("----------------------------------------");

				final ChatCompletionRequest request = ChatCompletionRequest.builder()
						.model("gpt-4-1106-preview")
						.maxTokens(4096)
						.temperature(0.50)
						.topP(1.0)
						.presencePenalty(0.6)
						.frequencyPenalty(0.0)
						.stop(Arrays.asList("Human:", "AI:"))
						.messages(Arrays.asList(
								new ChatMessage("system", "You are a pirate " + role + " in Minecraft."),
								new ChatMessage("user", conversation.toString())))
						.build();

				final List<ChatCompletionChoice> choices = OPEN_AI_SERVICE.createChatCompletion(request).getChoices();

				if (choices.isEmpty()) {
					Common.tell(player, "&cThe AI failed to respond. Please try again.");

					return;
				}

				final String[] rawJson = choices.get(0).getMessage().getContent().trim().split("\n");

				final String[] json = new String[rawJson.length - 2];
				System.arraycopy(rawJson, 1, json, 0, json.length);

				final Map<String, Object> map = GSON.fromJson(String.join("\n", json), HashMap.class);

				final String answer = map.get("answer").toString();
				final String type = map.get("type").toString();

				conversation.append(answer);
				Common.tell(player, "&8[&6" + npc.getName() + "&8] &7" + answer);

				if (type.equals("trade")) {

					// RAW_FISH
					final Material playerItem = Material.valueOf(map.get("player_item").toString().toUpperCase());
					final int playerAmount = (int) Double.parseDouble(map.get("player_amount").toString());
					final Material npcItem = Material.valueOf(map.get("npc_item").toString().toUpperCase());
					final int npcAmount = (int) Double.parseDouble(map.get("npc_amount").toString());

					// RAW FISH > Raw Fish
					final String playerItemFormatted = WordUtils.capitalizeFully(playerItem.toString().replace("_", " "));
					final String npcItemFormatted = WordUtils.capitalizeFully(npcItem.toString().replace("_", " "));

					Common.tell(player, "&8[&6!&8] &7Offer: To trade " + playerAmount + " " + playerItemFormatted + " for " + npcAmount + " " + npcItemFormatted + ".");
					Common.tell(player, "&8[&6!&8] &7Type '&aagree&7' or '&cstop&7', or simply keep negotating.");

					final Map<String, Object> tradeMap = new HashMap<>();
					tradeMap.put("player_amount", playerAmount);
					tradeMap.put("npc_amount", npcAmount);
					tradeMap.put("player_item", playerItem);
					tradeMap.put("npc_item", npcItem);

					player.setMetadata("NPCTrade", new FixedMetadataValue(CowCannon.getInstance(), tradeMap));
				}
			}
		}.runTaskAsynchronously(CowCannon.getInstance());
	}
}
