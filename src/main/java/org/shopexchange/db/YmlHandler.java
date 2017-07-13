package org.shopexchange.db;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shopexchange.ShopExchange;

public class YmlHandler implements DatabaseHandler {

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

}
