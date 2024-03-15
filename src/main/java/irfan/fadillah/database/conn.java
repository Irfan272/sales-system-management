package irfan.fadillah.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conn {
    public static Connection getConnection() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_penjualan";
        String username = "root";
        String password = "";
        try {
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
