package org.shopexchange.db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOStatement {

	public static void insertDouble(String key, TYPE type, double price) {
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
				e.printStackTrace();
			}
		}
	}

	public static double selectDouble(String key, TYPE type) {
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

	private static String replaceType(TYPE type) {
		switch (type) {
		case BUY:
			return "Buy";
		case SELL:
			return "Sell";
		default:
			return null;
		}
	}

	public enum TYPE {
		BUY, SELL;
	}
}
