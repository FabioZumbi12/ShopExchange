package org.shopexchange.db;


public interface DatabaseHandler {

	void setBuyValue(String item, double price);

	void setSellValue(String item, double price);
	
	double getBuyValue(String item);
	
	double getSellValue(String item);

}
