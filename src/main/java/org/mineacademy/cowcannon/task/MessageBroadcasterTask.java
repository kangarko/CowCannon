package org.mineacademy.cowcannon.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mineacademy.cowcannon.util.Common;

import java.util.ArrayList;
import java.util.List;

public final class MessageBroadcasterTask implements Runnable {

	private static final MessageBroadcasterTask instance = new MessageBroadcasterTask();

	private List<String> messages = new ArrayList<>();
	private int index = 0;

	private MessageBroadcasterTask() {
		this.messages.add("&#cc44ffHello");
		this.messages.add("My cats are awesome");
		this.messages.add("Love this tut");
	}

	@Override
	public void run() {
		if (this.index >= this.messages.size())
			this.index = 0;

		String pickedMessage = this.messages.get(this.index);

		for (Player player : Bukkit.getOnlinePlayers())
			player.sendMessage(Common.colorize(pickedMessage));

		this.index++;
	}

	public static MessageBroadcasterTask getInstance() {
		return instance;
	}
}
