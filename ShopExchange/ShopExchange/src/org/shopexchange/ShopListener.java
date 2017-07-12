package org.shopexchange;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.Acrobot.Breeze.Utils.MaterialUtil;
import com.Acrobot.Breeze.Utils.PriceUtil;
import com.Acrobot.ChestShop.Events.ShopDestroyedEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Signs.ChestShopSign;

public class ShopListener implements Listener {

	@EventHandler
	public void onTransaction(TransactionEvent event) {
		Sign sign = event.getSign();
		if (ChestShopSign.isAdminShop(sign)) {
			Location location = sign.getLocation();
			ItemStack item = event.getStock()[0];
			String name = MaterialUtil.getSignName(item);
			String code = MaterialUtil.Metadata.getItemCode(item);
			String text = sign.getLine(2);
			double buy = PriceUtil.getBuyPrice(text);
			double sell = PriceUtil.getSellPrice(text);
			String type = event.getTransactionType().name();
			
			Database.register(code, buy, sell, location, name, type, false);
		}
	}
	
	@EventHandler
	public void onDestroy(ShopDestroyedEvent event) {
		Sign sign = event.getSign();
		if (ChestShopSign.isAdminShop(sign)) {
			String itemName = sign.getLine(3);
			ItemStack item = MaterialUtil.getItem(itemName);
			String code = MaterialUtil.Metadata.getItemCode(item);
			
			Database.remove(code);
		}
	}
}
