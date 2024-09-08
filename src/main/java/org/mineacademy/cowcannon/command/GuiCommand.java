package org.mineacademy.cowcannon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public final class GuiCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String s,  String[] strings) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command!");

			return true;
		}

		// TODO replaced by Foundation menu system
		//new MenuOne().displayTo((Player) sender);

		// TODO replaced by Foundation menu system above
		/*try {
			System.out.println("Opening menu...");
			new StaffMenu().displayTo((Player) sender);

			System.out.println("Menu opened...");

		} catch (final Throwable t) {
			t.printStackTrace();
		}*/

		// Replaced by gui package menu system
		/*Player player = (Player) sender;
		
		Inventory inventory = Bukkit.createInventory(player, 9 * 3, ChatColor.DARK_BLUE + "Preferences Menu");
		
		ItemStack getDiamondButton = new ItemStack(Material.DIAMOND);
		ItemMeta diamondMeta = getDiamondButton.getItemMeta();
		diamondMeta.setDisplayName(ChatColor.AQUA + "Get Diamond");
		getDiamondButton.setItemMeta(diamondMeta);
		
		ItemStack clearInventoryButton = new ItemStack(Material.LAVA_BUCKET);
		ItemMeta clearInventoryMeta = clearInventoryButton.getItemMeta();
		clearInventoryMeta.setDisplayName(ChatColor.RED + "Clear Inventory");
		clearInventoryButton.setItemMeta(clearInventoryMeta);
		
		Material sunflower;
		
		try {
			sunflower = Material.valueOf("SUNFLOWER");
		
		} catch (IllegalArgumentException e) {
			sunflower = Material.valueOf("DOUBLE_PLANT");
		}
		
		ItemStack clearWeatherButton = new ItemStack(sunflower);
		ItemMeta clearWeatherMeta = clearWeatherButton.getItemMeta();
		clearWeatherMeta.setDisplayName(ChatColor.YELLOW + "Clear Weather");
		clearWeatherButton.setItemMeta(clearWeatherMeta);
		
		inventory.setItem(11, getDiamondButton);
		inventory.setItem(13, clearInventoryButton);
		inventory.setItem(15, clearWeatherButton);
		
		player.openInventory(inventory);
		player.setMetadata("OpenedMenu", new FixedMetadataValue(CowCannon.getInstance(), "Preferences Menu"));*/

		return true;
	}
}
