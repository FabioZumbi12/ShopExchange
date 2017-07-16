package org.shopexchange.db;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shopexchange.ShopExchange;

public class YmlHandler implements DatabaseHandler {
	
	@Override
	public void setLog(String key, double price) {
		int id = getLogConfig().getConfigurationSection(key).getKeys(false).size();
		getLogConfig().set(key + "." + id, price);
		saveLogConfig();
	}
	
	@Override
	public Map<Integer, Double> getLog(String key) {
		Map<Integer, Double> log = new HashMap<Integer, Double>();
		
		Set<String> ids = getLogConfig().getConfigurationSection(key).getKeys(false);
		for(String id : ids) {
			int idNumber = Integer.parseInt(id);
			double price = getLogConfig().getDouble(key + "." + id);
			log.put(idNumber, price);
		}
		
		return log;
	}

	@Override
	public void setBuyValue(String key, double price) {
		getConfig().set(key + ".prices.buy", price);
		saveConfig();
	}

	@Override
	public void setSellValue(String key, double price) {
		getConfig().set(key + ".prices.sell", price);
		saveConfig();

	}

	@Override
	public double getBuyValue(String key) {
		return getConfig().getDouble(key + ".prices.buy");
	}

	@Override
	public double getSellValue(String key) {
		return getConfig().getDouble(key + ".prices.buy");
	}

	@Override
	public void setBuyValue(Map<String, Double> keysMap) {
		for (String key : keysMap.keySet()) {
			double price = keysMap.get(key);
			setBuyValue(key, price);
		}
	}

	@Override
	public void setSellValue(Map<String, Double> keysMap) {
		for (String key : keysMap.keySet()) {
			double price = keysMap.get(key);
			setSellValue(key, price);
		}
	}

	@Override
	public Map<String, Double> getBuyValue(List<String> keys) {
		Map<String, Double> keysMap = new HashMap<String, Double>();
		for (String key : keys) {
			double value = getBuyValue(key);
			keysMap.put(key, value);
		}
		return keysMap;
	}

	@Override
	public Map<String, Double> getSellValue(List<String> keys) {
		Map<String, Double> keysMap = new HashMap<String, Double>();
		for (String key : keys) {
			double value = getSellValue(key);
			keysMap.put(key, value);
		}
		return keysMap;
	}

	private File getFile() {
		return new File(ShopExchange.get().getDataFolder(), "exchange.yml");
	}

	private FileConfiguration getConfig() {
		return YamlConfiguration.loadConfiguration(getFile());
	}

	private void saveConfig() {
		try {
			getConfig().save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getLogFile() {
		return new File(ShopExchange.get().getDataFolder(), "exchange-log.yml");
	}

	private FileConfiguration getLogConfig() {
		return YamlConfiguration.loadConfiguration(getLogFile());
	}

	private void saveLogConfig() {
		try {
			getLogConfig().save(getLogFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean keyExists(String key) {
		if (getConfig().getConfigurationSection(key) != null) {
			return true;
		} else {
			return false;
		}
	}
}
