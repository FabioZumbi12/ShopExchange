package org.shopexchange;

import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Colorable;

public class SEUtil {
	
	public static String getColor(ItemStack item){
		if (item instanceof Colorable){
			Colorable color = (Colorable)item;		
			return color.getColor().name();
		}
		return new String();
	}
}
