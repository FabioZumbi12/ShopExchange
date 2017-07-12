package org.shopexchange;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.shopexchange.db.DatabaseHandler;
import org.shopexchange.db.YmlHandler;
import org.shopexchange.listeners.ChestShopListener;

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
	
	private void initDB(){
		String type = getConfig().getString("database.type").toLowerCase();
		switch (type) {
		case "file": dbHandler = new YmlHandler();
		}
	}
	
	public static ShopExchange get(){
		return plugin;
	}
	
	public DatabaseHandler getDB(){
		return dbHandler;
	}
	
	public ItemStackDB getItemDB(){
		return itemDB;
	}
	
	public void getLogger(String str){
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', str));
	}
}
