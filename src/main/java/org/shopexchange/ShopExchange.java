package org.shopexchange;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.shopexchange.db.DatabaseHandler;
import org.shopexchange.db.MySQLHandler;
import org.shopexchange.db.YmlHandler;
import org.shopexchange.db.mysql.MySQL;
import org.shopexchange.listeners.ChestShopListener;

import net.md_5.bungee.api.ChatColor;

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
			MySQL.init();
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
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', str));
	}
}
