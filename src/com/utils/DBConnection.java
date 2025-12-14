package com.utils;
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            }
        catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found.", e);
        }

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/FOOD", "root", "03122005");
    }
}
