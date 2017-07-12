package org.shopexchange.listeners;

import java.util.HashMap;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.shopexchange.ShopExchange;

import com.Acrobot.Breeze.Utils.MaterialUtil;
import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType;

public class ChestShopListener implements Listener {
	
	private HashMap<ItemStack,Double> lastItem = new HashMap<ItemStack,Double>();
			
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTransaction(TransactionEvent e) {
		Sign s = e.getSign();
		ItemStack item = MaterialUtil.getItem(s.getLine(3));
		
		//debug
		ShopExchange.get().getLogger("item: "+item.getType().toString());
		ShopExchange.get().getLogger("type: "+e.getTransactionType().toString());
		ShopExchange.get().getLogger("stock: "+e.getStock().length);
		ShopExchange.get().getLogger("data: "+item.getData().getData());
		
		if (!lastItem.containsKey(item) || lastItem.get(item) != e.getPrice()){
			switch (e.getTransactionType()){
			case BUY: ShopExchange.get().getDB().setBuyValue(item, e.getPrice());
			case SELL: ShopExchange.get().getDB().setSellValue(item, e.getPrice());
			}
		}			
	}
}
