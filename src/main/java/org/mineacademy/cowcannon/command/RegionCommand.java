package org.mineacademy.cowcannon.command;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mineacademy.cowcannon.model.Region;
import org.mineacademy.cowcannon.model.Regions;

public final class RegionCommand implements CommandExecutor {

	// TODO Move into PlayerCache
	private final Map<UUID, Tuple<Location, Location>> selections = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		if (args.length == 0) {
			sender.sendMessage("Usage: /region <pos1|pos2|save <name>|paste <name>>");

			return true;
		}

		Regions regions = Regions.getInstance();

		Player player = (Player) sender;
		String param = args[0].toLowerCase();

		Tuple<Location, Location> selection = selections.getOrDefault(player.getUniqueId(), new Tuple<>(null, null));

		if ("pos1".equals(param)) {
			selection.setFirst(player.getLocation());

			sender.sendMessage("§8[§a✔§8] §7First location set!");
			selections.put(player.getUniqueId(), selection);

		} else if ("pos2".equals(param)) {
			selection.setSecond(player.getLocation());

			sender.sendMessage("§8[§a✔§8] §7Second location set!");
			selections.put(player.getUniqueId(), selection);

		} else if ("save".equals(param)) {
			if (selection.getFirst() == null || selection.getSecond() == null) {
				sender.sendMessage("§8[§c❌§8] §7Please select both positions first using /region pos1 and /region pos2");

				return true;
			}

			if (args.length != 2) {
				sender.sendMessage("§8[§6🛑§8] §7Usage: /region save <name>");

				return true;
			}

			/*File file = new File(plugin.getDataFolder(), "schematic/" + args[1] + ".schem");
			
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			
			WorldEditHook.save(selection.getFirst(), selection.getSecond(), file);*/

			String name = args[1];

			if (regions.findRegion(name) != null) {
				sender.sendMessage(ChatColor.RED + "Region by this name already exists.");

				return true;
			}

			regions.saveRegion(name, selection.getFirst(), selection.getSecond());

			sender.sendMessage("§8[§a✔§8] §7Schematic saved!");

		} else if ("list".equals(param)) {
			sender.sendMessage(ChatColor.GOLD + "Installed regions: " + String.join(", ", regions.getRegionsNames()));

		} else if ("current".equals(param)) {
			Region standingIn = regions.findRegion(player.getLocation());

			sender.sendMessage(ChatColor.GOLD + "You are standing in region: "
					+ (standingIn == null ? "none" : standingIn.getName()));

			/*} else if ("paste".equals(param)) {
				if (args.length != 2) {
					sender.sendMessage("§8[§6🛑§8] §7Usage: /region paste <name>");
			
					return true;
				}
			
				File file = new File(plugin.getDataFolder(), "schematic/" + args[1] + ".schem");
			
				if (!file.exists()) {
					sender.sendMessage("§8[§c❌§8] §7Schematic not found!");
			
					return true;
				}
			
				WorldEditHook.paste(player.getLocation(), file);
				sender.sendMessage("§8[§a✔§8] §7Schematic pasted at " + player.getLocation());*/

		} else
			sender.sendMessage("§8[§6🛑§8] §7Usage: /region <pos1|pos2|save <name>|paste <name>>");

		return true;
	}

	private static class Tuple<A, B> {
		private A first;
		private B second;

		public Tuple(A first, B second) {
			this.first = first;
			this.second = second;
		}

		public A getFirst() {
			return first;
		}

		public void setFirst(A first) {
			this.first = first;
		}

		public B getSecond() {
			return second;
		}

		public void setSecond(B second) {
			this.second = second;
		}
	}
}
