package org.aura.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static DbConnection instance ;

    Connection connection = null;

    Dotenv dotenv = Dotenv.load();

    private DbConnection() {
        try {
            String host = dotenv.get("DB_HOST");
            String port = dotenv.get("DB_PORT");
            String user = dotenv.get("DB_USER");
            String dbName = dotenv.get("DB_NAME");
            String password = dotenv.get("DB_PASSWORD");
            String url = "jdbc:postgresql://"+host+":"+port+"/"+dbName;
            connection = DriverManager.getConnection(url,user,password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static DbConnection getInstance(){
        if (instance ==null ){
             instance = new DbConnection();
        }
        return instance;
    }
    public  Connection getConnection(){
        return connection;
    }

    public static void main(String[] args) {
        DbConnection.getInstance().getConnection();
    }
}
