package org.shopexchange;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.shopexchange.db.DatabaseHandler;
import org.shopexchange.db.MySQLHandler;
import org.shopexchange.db.YmlHandler;
import org.shopexchange.db.mysql.DAOConnection;
import org.shopexchange.listeners.ChestShopListener;
import org.shopexchange.util.MessagesUtil;

public class ShopExchange extends JavaPlugin {

	private DatabaseHandler dbHandler;
	private static ShopExchange plugin;
	private ItemStackDB itemDB;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		plugin = this;
		initDB();
		itemDB = new ItemStackDB(this);
		getServer().getPluginManager().registerEvents(new ChestShopListener(), this);
	}

	private void initDB() {
		String type = getConfig().getString("database.type").toLowerCase();
		switch (type) {
		case "file":
			dbHandler = new YmlHandler();
			break;
		case "mysql":
			DAOConnection.init();
			dbHandler = new MySQLHandler();
		}
	}

	public static ShopExchange get() {
		return plugin;
	}

	public DatabaseHandler getDB() {
		return dbHandler;
	}

	public ItemStackDB getItemDB() {
		return itemDB;
	}

	public void getLogger(String str) {
		Bukkit.getConsoleSender().sendMessage(MessagesUtil.setColored(str));
	}
	
	public String getLang() {
		return getConfig().getString("lang");
	}
}
