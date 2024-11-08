package com.tabarak.useraccess.utils;



/**
 * 
 */
/**
 * @author USER
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/user_access_management";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Admin";

    public static Connection getConnection() throws SQLException {
    	 try {
             Class.forName("org.postgresql.Driver");
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
             throw new SQLException("PostgreSQL JDBC driver not found.");
         }
    	return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

