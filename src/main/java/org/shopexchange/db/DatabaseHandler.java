package org.shopexchange.db;

import org.bukkit.inventory.ItemStack;

public interface DatabaseHandler {

	void setBuyValue(ItemStack line, double price);

	void setSellValue(ItemStack line, double price);

}
