package org.shopexchange.util;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shopexchange.ShopExchange;

public class HookUtil {
	static Map<Plugin, Boolean> hooks = ShopExchange.get().hooks;

	public static Map<Plugin, Boolean> getHooks() {
		return hooks;
	}

	public static boolean isHooked(String pluginName) {
		return isHooked(Bukkit.getPluginManager().getPlugin(pluginName));
	}

	public static boolean isHooked(Plugin plugin) {
		if (hooks.containsKey(plugin)) {
			return hooks.get(plugin);
		} else {
			return false;
		}
	}

	public static void addHook(Plugin plugin) {
		hooks.put(plugin, true);
	}
	
	public static void delHook(Plugin plugin) {
		hooks.remove(plugin);
	}
	
	public static void changeHook(Plugin plugin, boolean state) {
		hooks.put(plugin, state);
	}
}
