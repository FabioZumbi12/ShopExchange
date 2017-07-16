package org.shopexchange.db;

import java.util.List;
import java.util.Map;

public abstract interface DatabaseHandler {

	void setBuyValue(String key, double price);

	void setSellValue(String key, double price);

	double getBuyValue(String key);

	double getSellValue(String key);

	void setBuyValue(Map<String, Double> keysMap);

	void setSellValue(Map<String, Double> keysMap);

	Map<String, Double> getBuyValue(List<String> keys);

	Map<String, Double> getSellValue(List<String> keys);

	boolean keyExists(String key);

}
