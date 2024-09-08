package org.mineacademy.cowcannon.model;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;


public class Bungee implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived( String channel,  Player player,  byte[] message) {
		System.out.println("Received msg from " + channel);

		if (!channel.equals("BungeeCord"))
			return;

		ByteArrayDataInput in = ByteStreams.newDataInput(message);

		String argument = in.readUTF();
		System.out.println("Argument is: " + argument);

		if (argument.equals("IP")) {
			String ip = in.readUTF();
			int port = in.readInt();

			System.out.println("ip: " + ip + " port: " + port);
		}
	}
}
