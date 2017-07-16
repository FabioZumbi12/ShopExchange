package org.shopexchange.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.shopexchange.ShopExchange;
import org.shopexchange.lang.DatabaseLang;

public class DAOConnection {

    public static String host = ShopExchange.get().getConfig().getString("MySQL.host");
    public static String port = ShopExchange.get().getConfig().getString("MySQL.port");
    public static String database = ShopExchange.get().getConfig().getString("MySQL.database");
    public static String username = ShopExchange.get().getConfig().getString("MySQL.user");
    public static String password = ShopExchange.get().getConfig().getString("MySQL.pass");
    public static Connection con;
    private static String createdDb = DatabaseLang.getMySQLCreated();
    private static String createdDbError = DatabaseLang.getMySQLCreatedError();
    private static String openDbError = DatabaseLang.getMySQLOpenError();
    private static String closeDbError = DatabaseLang.getMySQLCloseError();

    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    // connect
    public static void open() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            } catch (SQLException e) {
            	ShopExchange.get().getLogger(openDbError);
                e.printStackTrace();
            }
        }
    }

    // disconnect
    public static void close() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
            	ShopExchange.get().getLogger(closeDbError);
                e.printStackTrace();
            }
        }
    }

    // isConnected
    public static boolean isConnected() {
        return (con == null ? false : true);
    }

    // getConnection
    public static Connection getConnection() {
        return con;
    }

	public static void init() {
		initItems();
		initLog();
	}
	
	private static void initItems() {
			try {
				open();
				PreparedStatement ps = getConnection().prepareStatement(
						"CREATE TABLE IF NOT EXISTS Items (Key VARCHAR(100),Buy DOUBLE(100),Sell DOUBLE(100),PRIMARY KEY (Key))");
				ps.executeUpdate();
				
				ShopExchange.get().getLogger(createdDb);
				
				close();			
				} catch (SQLException e) {
					ShopExchange.get().getLogger(createdDbError);
				e.printStackTrace();
			}
		
	}
	
	private static void initLog() {
		try {
			open();			
			PreparedStatement ps = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS Log (Key VARCHAR(100),Price DOUBLE(100), PRIMARY KEY (Key))");
			ps.executeUpdate();
			
			ShopExchange.get().getLogger(createdDb);
			
			close();			
			} catch (SQLException e) {
				ShopExchange.get().getLogger(createdDbError);
			e.printStackTrace();
		}
	
}
}
