package org.shopexchange.db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.shopexchange.ShopExchange;
import org.shopexchange.lang.DatabaseLang;
import org.shopexchange.util.TransactionType;

public class DAOStatement {
	private static String insertDbError = DatabaseLang.getMySQLInsertError();
	private static String selectDbError = DatabaseLang.getMySQLSelectError();

	public static void insertDouble(String key, TransactionType type, double price) {
		String replacedType = replaceType(type);
		if (replacedType != null) {
			try {
				DAOConnection.open();
				PreparedStatement ps = DAOConnection.getConnection()
						.prepareStatement("INSERT INTO Items (Key," + replacedType + ") VALUES (?,?)");
				ps.setString(1, key);
				ps.setDouble(2, price);
				ps.executeUpdate();
				DAOConnection.close();
			} catch (SQLException e) {
				ShopExchange.get().getLogger(insertDbError);
				e.printStackTrace();
			}
		}
	}

	public static void insertLog(String key, double price) {
		try {
			DAOConnection.open();
			PreparedStatement ps = DAOConnection.getConnection()
					.prepareStatement("INSERT IGNORE INTO Log (Key, Price) VALUES (?,?)");
			ps.setString(1, key);
			ps.setDouble(2, price);
			ps.executeUpdate();
			DAOConnection.close();
		} catch (SQLException e) {
			ShopExchange.get().getLogger(insertDbError);
			e.printStackTrace();
		}
	}
	
	public static Map<Integer, Double> selectLog(String key) {
		Map<Integer, Double> log = new HashMap<Integer, Double>();
			try {
				DAOConnection.open();
				PreparedStatement ps = DAOConnection.getConnection()
						.prepareStatement("SELECT * FROM Log WHERE Key = ?");
				ps.setString(1, key);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					int id = rs.getInt("PRIMARY KEY");
					double price = rs.getDouble("Price");
					
					log.put(id, price);
				}

				DAOConnection.close();
			} catch (SQLException e) {
				ShopExchange.get().getLogger(selectDbError);
				e.printStackTrace();
			}

		return log;
	}

	public static double selectDouble(String key, TransactionType type) {
		double value = 0;
		String replacedType = replaceType(type);
		if (replacedType != null) {
			try {
				DAOConnection.open();
				PreparedStatement ps = DAOConnection.getConnection()
						.prepareStatement("SELECT" + replacedType + "FROM Items WHERE Key = ?");
				ps.setString(1, key);
				ResultSet rs = ps.executeQuery();

				if (rs.next() == true)
					value = rs.getDouble("Buy");

				DAOConnection.close();
			} catch (SQLException e) {
				ShopExchange.get().getLogger(selectDbError);
				e.printStackTrace();
			}
		}

		return value;
	}

	public static boolean keyExists(String key) {
		boolean exists = false;
		try {
			DAOConnection.open();
			PreparedStatement ps = DAOConnection.getConnection()
					.prepareStatement("SELECT Sell FROM Items WHERE Key = ?");
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == true)
				exists = true;

			DAOConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	private static String replaceType(TransactionType type) {
		switch (type) {
		case BUY:
			return "Buy";
		case SELL:
			return "Sell";
		default:
			return null;
		}
	}
}
