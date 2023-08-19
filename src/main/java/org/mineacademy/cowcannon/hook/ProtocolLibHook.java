package org.mineacademy.cowcannon.hook;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.mineacademy.cowcannon.CowCannon;

import java.util.ArrayList;

public final class ProtocolLibHook {

	public static void register() {
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();

		manager.addPacketListener(new PacketAdapter(CowCannon.getInstance(), PacketType.Play.Client.CHAT) {

			@Override
			public void onPacketReceiving(PacketEvent event) {
				PacketContainer packet = event.getPacket();
				String message = packet.getStrings().read(0);
				Player player = event.getPlayer();

				if (message.contains("shit") || message.contains("damn")) {
					event.setCancelled(true);
					event.getPlayer().sendMessage("Bad manners!");

					Vector vector = player.getLocation().getDirection().multiply(-1.5);

					PacketContainer explosion = new PacketContainer(PacketType.Play.Server.EXPLOSION);
					explosion.getDoubles()
							.write(0, player.getLocation().getX())
							.write(1, player.getLocation().getY())
							.write(2, player.getLocation().getZ());
					explosion.getFloat().write(0, 3.0F);
					explosion.getBlockPositionCollectionModifier().write(0, new ArrayList<>());
					explosion.getFloat()
							.write(1, (float) vector.getX())
							.write(2, (float) vector.getY())
							.write(3, (float) vector.getZ());

					manager.sendServerPacket(player, explosion);
				}
			}
		});

		/*manager.addPacketListener(new PacketAdapter(CowCannon.getInstance(), PacketType.Play.Server.PLAYER_INFO) {

			@Override
			public void onPacketSending(PacketEvent event) {
				PacketContainer packet = event.getPacket();

				if (packet.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
					List<PlayerInfoData> list = packet.getPlayerInfoDataLists().read(0);

					for (int i = 0; i < list.size(); i++) {
						PlayerInfoData data = list.get(i);

						if (data == null)
							continue;

						UUID uniqueId = data.getProfile().getUUID();

						if (CowCannon.getPlayerTags().containsKey(uniqueId)) {
							String tag = CowCannon.getPlayerTags().get(uniqueId);

							if (tag != null)
								list.set(i, new PlayerInfoData(
										new WrappedGameProfile(uniqueId, tag),
										data.getLatency(),
										data.getGameMode(),
										WrappedChatComponent.fromLegacyText(tag)));
						}
					}

					packet.getPlayerInfoDataLists().write(0, list);
				}
			}
		});*/
	}
}
