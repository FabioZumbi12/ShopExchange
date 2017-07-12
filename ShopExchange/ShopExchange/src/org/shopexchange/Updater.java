package org.shopexchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

import com.Acrobot.Breeze.Utils.MaterialUtil;
import com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType;

import net.md_5.bungee.api.ChatColor;

public class Updater {
	ShopExchange plugin;

	public Updater(ShopExchange plugin) {
		this.plugin = plugin;
	}

	public void set() {
		long interval = (plugin.getConfig().getLong("interval")) * 20;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				if (!(Database.getTime() > 0)) {
					updateAll();
					plugin.getServer().broadcastMessage(ChatColor.GOLD + "Preços atualizados!");
					plugin.resetTimer();
				}
			}
		}, 0L, interval);

	}

	public void execute(String code, Sign sign) {
		int buy = Database.getExchange(code, TransactionType.BUY.name());
		int sell = Database.getExchange(code, TransactionType.SELL.name());

		double buyValue = Database.getValue(code, TransactionType.BUY.name());
		double sellValue = Database.getValue(code, TransactionType.SELL.name());

		double buyOriginalValue = Database.getOriginalValue(code, TransactionType.BUY.name());
		double sellOriginalValue = Database.getOriginalValue(code, TransactionType.SELL.name());

		double modifier = Database.getModifier();
		if (buy > (sell * 2)) {
			double newBuy = buyValue + (buyValue * modifier);
			double newSell = sellValue + (sellValue * modifier);

			if (newBuy > (buyOriginalValue * 2)) {
				newBuy = buyOriginalValue * 2;
			}

			if (newSell > (sellOriginalValue * 2)) {
				newSell = sellOriginalValue * 2;
			}

			newBuy = round(newBuy);
			newSell = round(newSell);

			Database.register(code, newBuy, newSell, sign.getLocation(), sign.getLine(3), TransactionType.BUY.name(),
					true);
			Database.register(code, newBuy, newSell, sign.getLocation(), sign.getLine(3), TransactionType.SELL.name(),
					true);
			Database.resetExchange(code);

			sign.setLine(2, "B " + newBuy + " : " + newSell + " S");
			sign.update();
		} else if ((buy * 2) < sell) {
			double newBuy = buyValue - (buyValue * modifier);
			double newSell = sellValue - (sellValue * modifier);

			if (newBuy < buyOriginalValue) {
				newBuy = buyOriginalValue;
			}

			if (newSell < sellOriginalValue) {
				newSell = sellOriginalValue;
			}

			Database.register(code, newBuy, newSell, sign.getLocation(), sign.getLine(3), TransactionType.BUY.name(),
					true);
			Database.register(code, newBuy, newSell, sign.getLocation(), sign.getLine(3), TransactionType.SELL.name(),
					true);
			Database.resetExchange(code);

			sign.setLine(2, "B " + newBuy + " : " + newSell + " S");
			sign.update();
		}
	}

	public void updateAll() {
		List<Sign> signList = getSigns();
		for (Sign sign : signList) {
			String itemName = sign.getLine(3);
			ItemStack item = MaterialUtil.getItem(itemName);
			String code = MaterialUtil.Metadata.getItemCode(item);

			execute(code, sign);
		}
	}

	public List<Sign> getSigns() {
		List<Sign> list = new ArrayList<Sign>();
		if (Database.getLocations() != null) {
			List<Location> locationList = Database.getLocations();
			for (Location location : locationList) {
				Block block = location.getBlock();
				if (block.getState() instanceof Sign) {
					Sign sign = (Sign) location.getWorld().getBlockAt(location).getState();
					list.add(sign);
				}
			}
		}

		return list;
	}

	private double round(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
