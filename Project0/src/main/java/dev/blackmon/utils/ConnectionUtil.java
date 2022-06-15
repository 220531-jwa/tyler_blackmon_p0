package dev.blackmon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static ConnectionUtil cu;

    private static Properties databaseProps;

    private ConnectionUtil() {
        databaseProps = new Properties();

        InputStream props = ConnectionUtil.class.getClassLoader().getResourceAsStream("connection.properties");

        try {
            databaseProps.load(props);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized ConnectionUtil getConnectionUtil() {
        if (cu == null) {
            cu = new ConnectionUtil();
        }

        return cu;
    }

    public Connection getConnection() {
        Connection conn = null;

        String url = databaseProps.getProperty("url");
        String username = databaseProps.getProperty("username");
        String password = databaseProps.getProperty("password");

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
