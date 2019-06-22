package pl.sda.jdbc.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcMain {

    public static void main(String[] args) {

    }

    private static Connection getConnection(){
        try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
