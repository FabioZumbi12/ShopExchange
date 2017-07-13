package org.shopexchange.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.shopexchange.db.mysql.MySQL;

public class MySQLHandler implements DatabaseHandler {

	@Override
	public void setBuyValue(String key, double price) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("INSERT INTO Items (Key,Buy) VALUES (?,?)");
			ps.setString(1, key);
			ps.setDouble(2, price);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setSellValue(String key, double price) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("INSERT INTO Items (Key,Sell) VALUES (?,?)");
			ps.setString(1, key);
			ps.setDouble(2, price);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double getBuyValue(String key) {
		double value = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Buy FROM Items WHERE Key = ?");
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == true)
				value = rs.getDouble("Buy");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return value;
	}

	@Override
	public double getSellValue(String key) {
		double value = 0;
		if (keyExists(key)) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Sell FROM Items WHERE Key = ?");
				ps.setString(1, key);
				ResultSet rs = ps.executeQuery();

				if (rs.next() == true)
					value = rs.getDouble("Sell");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return value;
	}

	public boolean keyExists(String key) {
		boolean exists = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Sell FROM Items WHERE Key = ?");
			ps.setString(1, key);
			ResultSet rs = ps.executeQuery();

			if (rs.next() == true)
				exists = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

}
