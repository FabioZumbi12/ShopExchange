package org.shopexchange;

import org.bukkit.plugin.java.JavaPlugin;

public class ShopExchange extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new ShopListener(), this);
		startTimer();
		new Updater(this).set();
	}

	private void startTimer() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				long timer = Database.getTime();
				if (timer > 0) {
					long result = timer - 1;
					Database.setTime(result);
				}
			}
		}, 0L, 20L);
	}

	public void resetTimer() {
		long time = getConfig().getLong("time");
		Database.setTime(time);
	}
}
