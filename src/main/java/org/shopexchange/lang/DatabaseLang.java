package org.shopexchange.lang;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.shopexchange.ShopExchange;
import org.shopexchange.util.MessagesUtil;

public interface DatabaseLang {
	String lang = ShopExchange.get().getLang();
	File file = new File(ShopExchange.get().getDataFolder(), lang + ".yml");
	FileConfiguration config = YamlConfiguration.loadConfiguration(file);

	static String getMySQLCreated() {
		return MessagesUtil.setColored(config.getString("mysql-created"));
	}

	static String getMySQLCreatedError() {
		return MessagesUtil.setColored(config.getString("mysql-created-error"));
	}

	static String getMySQLOpenError() {
		return MessagesUtil.setColored(config.getString("mysql-open-error"));
	}

	static String getMySQLCloseError() {
		return MessagesUtil.setColored(config.getString("mysql-close-error"));
	}
	
	static String getMySQLInsertError() {
		return MessagesUtil.setColored(config.getString("mysql-insert-error"));
	}
	
	static String getMySQLSelectError() {
		return MessagesUtil.setColored(config.getString("mysql-select-error"));
	}
}
