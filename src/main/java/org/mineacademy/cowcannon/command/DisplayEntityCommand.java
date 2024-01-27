package org.mineacademy.cowcannon.command;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public final class DisplayEntityCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		final Player player = (Player) sender;

		try {
			// 1: Display items
			/*if (false) {
				ItemDisplay item = player.getWorld().spawn(player.getLocation(), ItemDisplay.class);
				item.setItemStack(new ItemStack(Material.DIAMOND));
			
				Transformation transformation = item.getTransformation();
			
				transformation.getScale().set(2D);
			
				//transformation.getLeftRotation().x = 1; // 1 to -1, forward/backward lay
				//transformation.getLeftRotation().y = 0.5F; // 1 to -1, horizontal rotation
				//transformation.getLeftRotation().z = -1F; // 1 to -1, right/left tilt
			
				item.setTransformation(transformation);
			
				//item.setViewRange(0.1F); // 0.1 = 16 blocks
				//item.setShadowRadius(0.3F); // 1 = 1 block
				//item.setShadowRadius(1F);
				//item.setShadowStrength(5F); // >= 5F = "black hole"
			
				//item.setDisplayWidth(50F);
				//item.setDisplayHeight(50F);
			
				//item.setBillboard(Billboard.CENTER); // auto-rotate
			
				//item.setGlowColorOverride(Color.RED); // only works for scoreboard
			
				//item.setBrightness(new Brightness(15, 15)); // 0-15, will override auto brightness
			}*/

			// 2: Blocks
			/*if (false) {
				BlockDisplay block = player.getWorld().spawn(player.getLocation(), BlockDisplay.class);
				block.setBlock(Bukkit.createBlockData(Material.DIAMOND_BLOCK));
			
				Transformation transformation = block.getTransformation();
				transformation.getScale().set(2D);
			
				block.setTransformation(transformation);
			}*/

			// 3: Texts
			if (true) {
				final TextDisplay text = player.getWorld().spawn(player.getLocation(), TextDisplay.class);
				text.setText(ChatColor.BOLD + "Warning: \n" + ChatColor.GREEN + "You Are An Idiot");

				text.setBackgroundColor(Color.RED);
				text.setLineWidth(50);
				text.setBillboard(Billboard.CENTER);
				//text.setTextOpacity(Byte.MAX_VALUE); // transparent
			}

		} catch (final Throwable t) {
			sender.sendMessage("Error: " + t.getMessage());

			t.printStackTrace();
		}

		return true;
	}
}
