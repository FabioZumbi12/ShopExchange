package org.shopexchange.db;

import org.shopexchange.db.mysql.DAOStatement;
import org.shopexchange.db.mysql.DAOStatement.TYPE;

public class MySQLHandler implements DatabaseHandler {

	@Override
	public void setBuyValue(String key, double price) {
		DAOStatement.insertDouble(key, TYPE.BUY, price);
	}

	@Override
	public void setSellValue(String key, double price) {
		DAOStatement.insertDouble(key, TYPE.SELL, price);
	}

	@Override
	public double getBuyValue(String key) {
		return DAOStatement.selectDouble(key, TYPE.BUY);
	}

	@Override
	public double getSellValue(String key) {
		return DAOStatement.selectDouble(key, TYPE.SELL);
	}

	public boolean keyExists(String key) {
		return DAOStatement.keyExists(key);
	}

}
