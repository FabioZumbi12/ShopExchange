package org.shopexchange.db;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shopexchange.ShopExchange;

public class YmlHandler implements DatabaseHandler {

	@Override
	public void setBuyValue(String id, double price) {
		getConfig().set(id + ".prices.buy", price);
		saveConfig();
	}

	@Override
	public void setSellValue(String id, double price) {
		getConfig().set(id + ".prices.sell", price);
		saveConfig();

	}

	@Override
	public double getBuyValue(String id) {
		return getConfig().getDouble(id + ".prices.buy");
	}

	@Override
	public double getSellValue(String id) {
		return getConfig().getDouble(id + ".prices.buy");
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
