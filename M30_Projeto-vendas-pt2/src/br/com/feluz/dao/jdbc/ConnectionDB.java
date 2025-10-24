package br.com.feluz.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static Connection connection;
    private static String url = "jdbc:postgresql://localhost:5432/M30_projeto-vendas";
    private static String user = "postgres";
    private static String password = "admin";

    public static void ConnectionDB (Connection connection) {}

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = initConnection();
            return connection;
        } else if (connection.isClosed()) {
            connection = initConnection();
            return connection;
        } else {
            return connection;
        }
    }

    public static Connection initConnection() {
        try{
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
