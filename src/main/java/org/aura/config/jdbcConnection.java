package org.aura.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class jdbcConnection {

    private jdbcConnection() {}
    public static Connection getConnection(){
        Connection connection = null;
        try {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BatiCuisine","postgres","admin123");
        }catch (Exception e){
          e.printStackTrace();
        }
        return connection;
    }

//    public static void main(String[] args) {
//        jdbcConnection.getConnection();
//    }
}
