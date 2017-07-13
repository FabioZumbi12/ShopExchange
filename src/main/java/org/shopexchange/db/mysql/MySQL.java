package org.shopexchange.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.shopexchange.ShopExchange;

public class MySQL {
    public static String host = ShopExchange.get().getConfig().getString("MySQL.host");
    public static String port = ShopExchange.get().getConfig().getString("MySQL.port");
    public static String database = ShopExchange.get().getConfig().getString("MySQL.database");
    public static String username = ShopExchange.get().getConfig().getString("MySQL.user");
    public static String password = ShopExchange.get().getConfig().getString("MySQL.pass");
    public static Connection con;
    private static String consuccess = "Conexão com MySQL estabelecida";
    private static String dissuccess = "Conexão com MySQL fechada";

    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    // connect
    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                console.sendMessage("[ShopExchange]" + consuccess);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // disconnect
    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                console.sendMessage("[ShopExchange]" + dissuccess);
            } catch (SQLException e) {
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
}
