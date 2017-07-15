package org.shopexchange.lang;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shopexchange.ShopExchange;
import org.shopexchange.util.MessagesUtil;

public interface DatabaseLang {
	String lang = ShopExchange.get().getLang();
	File file = new File(ShopExchange.get().getDataFolder(), lang +".yml");
	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	static String getMySQLCreated() {
		return MessagesUtil.setColored(config.getString("mysql-created"));
	}
}
