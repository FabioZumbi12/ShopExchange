package org.shopexchange.db;

public abstract interface DatabaseHandler {

	void setBuyValue(String key, double price);

	void setSellValue(String key, double price);
	
	double getBuyValue(String key);
	
	double getSellValue(String key);

}
