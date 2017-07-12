package org.shopexchange;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class ItemStackDB {
	private YamlConfiguration items;
	
	public ItemStackDB(ShopExchange plugin){
		items = new YamlConfiguration();
		File itemFile = new File(plugin.getDataFolder(), "itemIDs.yml");
		if (!itemFile.exists()){
			try {
				itemFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			items.load(itemFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public boolean setItem(ItemStack item){
		String itemId = item.getType().toString();
		String itemData = SEUtil.getColor(item);				
		
		if (item.hasItemMeta() || !item.getEnchantments().isEmpty()){
			//se tem enchants gerar id random
			itemData = itemData+"#"+(items.getKeys(false).size()+1);
		}
		
		if (!items.getValues(false).values().contains(item) && !itemData.isEmpty()){
			//se não consta adicionar
			items.set(itemId+":"+itemData, item);
			return true;
		}
		return false;
	}
}
