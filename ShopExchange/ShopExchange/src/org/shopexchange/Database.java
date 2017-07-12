package org.shopexchange;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Database {

	public static void register(String code, double buy, double sell, Location location, String name, String type, boolean bypass) {
		if(databaseType().equalsIgnoreCase("Files")) {
			YAML.register(code, buy, sell, location, name, type, bypass);
		}
	}

	public static void remove(String code) {
		if(databaseType().equalsIgnoreCase("Files")) {
			YAML.remove(code);
			resetExchange(code);
		}
	}

	public static void exchange(String code, String name) {
		if(databaseType().equalsIgnoreCase("Files")) {
			YAML.exchange(code, name);
		}
	}
	
	public static void resetExchange(String code) {
		if(databaseType().equalsIgnoreCase("Files")) {
			YAML.resetExchange(code);
		}
	}
	
	public static List<Location> getLocations() {
		if(databaseType().equalsIgnoreCase("Files")) {
			return YAML.getLocations();
		} else {
			return null;
		}
	}
	
	public static int getExchange(String code, String type) {
		if(databaseType().equalsIgnoreCase("Files")) {
			return YAML.getExchange(code, type);			
		} else {
			return 0;
		}
	}
	
	public static double getValue(String code, String type) {
		if(databaseType().equalsIgnoreCase("Files")) {
			return YAML.getValue(code, type);
		} else {
			return 0;
		}
	}	
	
	public static double getOriginalValue(String code, String type) {
		if(databaseType().equalsIgnoreCase("Files")) {
			return YAML.getOriginalValue(code, type);
		} else {
			return 0;
		}
	}
	
	public static long getTime() {
		if(databaseType().equalsIgnoreCase("Files")) {
			return YAML.getTimer();
		} else {
			return 0;
		}
	}
	
	public static void setTime(long time) {
		if(databaseType().equalsIgnoreCase("Files")) {
			YAML.setTime(time);
		}
	}
	
	private static String databaseType() {
		return pluginConfig().getString("database");
	}
	
	public static double getModifier() {
		return pluginConfig().getDouble("modifier");
	}
	
	private static FileConfiguration pluginConfig() {
		return Bukkit.getServer().getPluginManager().getPlugin("ShopExchange").getConfig();
	}
}
