package org.shopexchange.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shopexchange.db.mysql.DAOStatement;
import org.shopexchange.util.TransactionType;

public class MySQLHandler implements DatabaseHandler {

	@Override
	public void setLog(String key, double price) {
		DAOStatement.insertLog(key, price);
	}
	
	@Override
	public Map<Integer, Double> getLog(String key) {
		return DAOStatement.selectLog(key);
	}

	@Override
	public void setBuyValue(String key, double price) {
		DAOStatement.insertDouble(key, TransactionType.BUY, price);
	}

	@Override
	public void setSellValue(String key, double price) {
		DAOStatement.insertDouble(key, TransactionType.SELL, price);
	}

	@Override
	public double getBuyValue(String key) {
		return DAOStatement.selectDouble(key, TransactionType.BUY);
	}

	@Override
	public double getSellValue(String key) {
		return DAOStatement.selectDouble(key, TransactionType.SELL);
	}

	public boolean keyExists(String key) {
		return DAOStatement.keyExists(key);
	}

	@Override
	public void setBuyValue(Map<String, Double> keysMap) {
		for (String key : keysMap.keySet()) {
			double price = keysMap.get(key);
			setBuyValue(key, price);
		}
	}

	@Override
	public void setSellValue(Map<String, Double> keysMap) {
		for (String key : keysMap.keySet()) {
			double price = keysMap.get(key);
			setBuyValue(key, price);
		}
	}

	@Override
	public Map<String, Double> getBuyValue(List<String> keys) {
		Map<String, Double> keysMap = new HashMap<String, Double>();
		for (String key : keys) {
			double value = getBuyValue(key);
			keysMap.put(key, value);
		}
		return keysMap;
	}

	@Override
	public Map<String, Double> getSellValue(List<String> keys) {
		Map<String, Double> keysMap = new HashMap<String, Double>();
		for (String key : keys) {
			double value = getSellValue(key);
			keysMap.put(key, value);
		}
		return keysMap;
	}

}
