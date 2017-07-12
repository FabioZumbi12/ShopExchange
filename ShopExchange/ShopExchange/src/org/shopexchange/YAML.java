package org.shopexchange;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YAML {
	static ShopExchange plugin = ShopExchange.getPlugin(ShopExchange.class);

	public static void register(String code, double buy, double sell, Location location, String name, String type,
			boolean bypass) {
		FileConfiguration config = getConfig("shops");
		if (config.getConfigurationSection(code) == null || bypass) {
			config.set(code + ".name", name);
			config.set(code + ".exchange.BUY", buy);
			config.set(code + ".exchange.SELL", sell);

			if (config.getConfigurationSection(code + ".original") == null) {
				config.set(code + ".original.BUY", buy);
				config.set(code + ".original.SELL", sell);
			}

			config.set(code + ".location.x", location.getBlockX());
			config.set(code + ".location.y", location.getBlockY());
			config.set(code + ".location.z", location.getBlockZ());
			config.set(code + ".location.world", location.getWorld().getName());

			try {
				config.save(getFile("shops"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		exchange(code, type);

	}

	public static void remove(String code) {
		FileConfiguration config = getConfig("shops");
		if (config.getConfigurationSection(code) != null) {
			config.set(code, null);
			try {
				config.save(getFile("shops"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void exchange(String code, String type) {
		FileConfiguration config = getConfig("exchange");
		int result = config.getInt(code + "." + type);
		config.set(code + "." + type, result + 1);
		try {
			config.save(getFile("exchange"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void resetExchange(String code) {
		FileConfiguration config = getConfig("exchange");
		config.set(code, null);
		try {
			config.save(getFile("exchange"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static double getValue(String code, String type) {
		FileConfiguration config = getConfig("shops");
		return config.getDouble(code + ".exchange." + type);
	}

	public static double getOriginalValue(String code, String type) {
		FileConfiguration config = getConfig("shops");
		return config.getDouble(code + ".original." + type);
	}

	public static int getExchange(String code, String type) {
		FileConfiguration config = getConfig("exchange");
		return config.getInt(code + "." + type);
	}

	public static List<Location> getLocations() {
		List<Location> list = new ArrayList<Location>();
		FileConfiguration config = getConfig("shops");

		Set<String> keys = config.getKeys(false);
		for (String key : keys) {
			int x = config.getInt(key + ".location.x");
			int y = config.getInt(key + ".location.y");
			int z = config.getInt(key + ".location.z");
			String worldName = config.getString(key + ".location.world");
			World world = Bukkit.getWorld(worldName);

			list.add(new Location(world, x, y, z));
		}

		return list;
	}
	
	public static long getTimer() {
		FileConfiguration config = getConfig("exchange");
		return config.getLong("time");
	}
	
	public static void setTime(long time) {
		FileConfiguration config = getConfig("exchange");
		config.set("time", time);
		try {
			config.save(getFile("exchange"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static FileConfiguration getConfig(String name) {
		return YamlConfiguration.loadConfiguration(getFile(name));
	}

	private static File getFile(String name) {
		return new File(plugin.getDataFolder(), name + ".yml");
	}
}
