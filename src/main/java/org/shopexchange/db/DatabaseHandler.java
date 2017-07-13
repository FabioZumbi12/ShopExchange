package org.shopexchange.db;

public abstract interface DatabaseHandler {

	void setBuyValue(String id, double price);

	void setSellValue(String id, double price);
	
	double getBuyValue(String id);
	
	double getSellValue(String id);

}
