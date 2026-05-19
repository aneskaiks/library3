package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USER = "myUser";
    private static final String PASSWORD = "myPass";

    public static Connection connect(){

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

}
