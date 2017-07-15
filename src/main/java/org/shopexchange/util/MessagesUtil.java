package org.shopexchange.util;

import net.md_5.bungee.api.ChatColor;

public interface MessagesUtil {
	
	static String setColored(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
