package org.shopexchange.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.shopexchange.ShopExchange;
import org.shopexchange.db.DatabaseHandler;

public class PricesUtil {
	static DatabaseHandler database = ShopExchange.get().getDB();

	public static double getPriceByKey(String key, TransactionType type) {
		double value;
		switch (type) {
		case BUY:
			value = database.getBuyValue(key);
			break;
		case SELL:
			value = database.getSellValue(key);
			break;
		default:
			value = 0;

		}
		return value;
	}

	public static Map<TransactionType, Double> getAllPricesByKey(String key) {
		Map<TransactionType, Double> pricesMap = new HashMap<TransactionType, Double>();

		double buyValue = database.getBuyValue(key);
		double sellValue = database.getBuyValue(key);

		pricesMap.put(TransactionType.BUY, buyValue);
		pricesMap.put(TransactionType.SELL, sellValue);

		return pricesMap;
	}

	public static Map<String, Double> getPriceOfAllKeys(List<String> keys, TransactionType type) {
		Map<String, Double> pricesMap = new HashMap<String, Double>();
		for (String key : keys) {
			pricesMap.put(key, getPriceByKey(key, type));
		}

		return pricesMap;
	}

	public static Map<String, Map<TransactionType, Double>> getAllPricesOfAllKeys(List<String> keys) {
		Map<String, Map<TransactionType, Double>> pricesMap = new HashMap<String, Map<TransactionType, Double>>();

		for (String key : keys) {
			pricesMap.put(key, getAllPricesByKey(key));
		}

		return pricesMap;
	}

	public static boolean keyExists(String key) {
		return database.keyExists(key);
	}
	
	public static Map<String, Double> distribuitePrices(List<String> keys, double total) {
		Map<String, Double> prices = new HashMap<String, Double>();
		List<Integer> indexValue = new ArrayList<Integer>();
		for(String key : keys) {
			if(Material.getMaterial(key) != null) {
				int indexOfKey = keys.indexOf(key);
				indexValue.set(indexOfKey, 1);
			}
			
			if(Enchantment.getByName(key) != null) {
				Enchantment enchant = Enchantment.getByName(key);
				int enchantLevel = Integer.parseInt("" + key.charAt((key.length() - 1)));
				int enchantMaxLevel = enchant.getMaxLevel();
				int enchantValue = 10 + (enchantLevel - enchantMaxLevel);
				
				int indexOfKey = keys.indexOf(key);
				indexValue.set(indexOfKey, enchantValue);
			}
		}
		
		int totalMathValue = 1;
		for(int mathValue :  indexValue) {
			totalMathValue += mathValue;
		}
		
		for(String key : keys) {
			double valueModifier = total / totalMathValue;
			int keyModifier = indexValue.get(keys.indexOf(key));
			double keyValue = valueModifier * keyModifier;
			
			prices.put(key, keyValue);
		}
		
		return prices;
	}
}
