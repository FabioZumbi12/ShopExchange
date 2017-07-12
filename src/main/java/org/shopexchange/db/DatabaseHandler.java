package org.shopexchange.db;

import org.bukkit.inventory.ItemStack;

public interface DatabaseHandler {

	void setBuyValue(ItemStack item, double price);

	void setSellValue(ItemStack item, double price);
	
	double getBuyValue(ItemStack item);
	
	double getSellValue(ItemStack item);

}
