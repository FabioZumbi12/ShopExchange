package org.shopexchange;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.shopexchange.db.DatabaseHandler;
import org.shopexchange.db.MySQLHandler;
import org.shopexchange.db.YmlHandler;
import org.shopexchange.db.mysql.DAOConnection;
import org.shopexchange.listeners.ChestShopListener;
import org.shopexchange.util.HookUtil;
import org.shopexchange.util.MessagesUtil;

public class ShopExchange extends JavaPlugin {

	private DatabaseHandler dbHandler;
	private static ShopExchange plugin;
	private ItemStackDB itemDB;
	public Map<Plugin, Boolean> hooks;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		plugin = this;
		initDB();
		itemDB = new ItemStackDB(this);
		hooks = new HashMap<Plugin, Boolean>();
		setHooks();
		registerEvents();
	}

	private void setHooks() {
		ConfigurationSection configSection = getConfig().getConfigurationSection("Hooks");
		for (String key : configSection.getKeys(false)) {
			boolean enable = getConfig().getBoolean(key);
			Plugin plugin = Bukkit.getPluginManager().getPlugin(key);
			if (plugin != null && enable) {
				HookUtil.addHook(plugin);
			}
		}
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

	private void registerEvents() {
		if (HookUtil.isHooked("ChestShop")) {
			getServer().getPluginManager().registerEvents(new ChestShopListener(), this);
		}
	}
}
