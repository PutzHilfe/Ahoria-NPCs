package util;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	public static ItemStack setName(ItemStack is, String name) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack setLore(ItemStack is, String lore) {
		ItemMeta im = is.getItemMeta();
		im.setLore(Arrays.asList(lore.split(";")));
		is.setItemMeta(im);
		return is;
	}
	
}