package org.shopexchange.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.shopexchange.ShopExchange;
import org.shopexchange.util.KeyUtil;
import org.shopexchange.util.PricesUtil;

import com.Acrobot.ChestShop.Events.TransactionEvent;

public class ChestShopListener implements Listener {

	/*
	 * private HashMap<ItemStack,Double> lastItem = new
	 * HashMap<ItemStack,Double>();
	 * 
	 * @SuppressWarnings("deprecation")
	 * 
	 * @EventHandler public void onTransaction(TransactionEvent e) { ItemStack
	 * item = e.getStock()[0];
	 * 
	 * //debug ShopExchange.get().getLogger("item: "+item.getType().toString());
	 * ShopExchange.get().getLogger("type: "+e.getTransactionType().toString());
	 * ShopExchange.get().getLogger("stock: "+e.getStock().length);
	 * ShopExchange.get().getLogger("data: "+item.getData().getData());
	 * 
	 * String key = ShopExchange.get().getItemDB().setItem(item); if (key ==
	 * null){ key = item.getType().toString()+":"+item.getData().getData(); }
	 * 
	 * if (!lastItem.containsKey(item) || lastItem.get(item) != e.getPrice()){
	 * switch (e.getTransactionType()){ case BUY:
	 * ShopExchange.get().getDB().setBuyValue(key, e.getPrice()); case SELL:
	 * ShopExchange.get().getDB().setSellValue(key, e.getPrice()); } } }
	 */

	@EventHandler
	public void onTransaction(TransactionEvent e) {
		ItemStack[] items = e.getStock();
		Map<ItemStack, List<String>> keys = new HashMap<ItemStack, List<String>>();
		for (ItemStack item : items) {
			keys.put(item, KeyUtil.getKeys(item));
		}

		Map<String, Double> prices = new HashMap<String, Double>();
		for (List<String> keyList : keys.values()) {
			Map<String, Double> keyPriceMap = PricesUtil.distribuitePrices(keyList, e.getPrice());
			prices.putAll(keyPriceMap);
		}

		for (String key : prices.keySet()) {
			ShopExchange.get().getDB().setLog(key, prices.get(key));
		}
	}
}
