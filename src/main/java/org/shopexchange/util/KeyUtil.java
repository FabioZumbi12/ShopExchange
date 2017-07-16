package org.shopexchange.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KeyUtil {

	public static List<String> getKeys(ItemStack item) {
		List<String> keys = new ArrayList<String>();

		String itemKey = getKey(item);
		keys.add(itemKey);

		if (item.hasItemMeta()) {
			ItemMeta im = item.getItemMeta();
			if (!im.getEnchants().isEmpty()) {
				for (Enchantment enchant : im.getEnchants().keySet()) {
					String enchantKey = enchant.getName() + "#" + im.getEnchantLevel(enchant);
					keys.add(enchantKey);
				}
			}
		}
		return keys;
	}

	@SuppressWarnings("deprecation")
	public static String getKey(ItemStack item) {
		String type = item.getType().name();
		double data = item.getData().getData();

		return type + "#" + data;
	}
}
