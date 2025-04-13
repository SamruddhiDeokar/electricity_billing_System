package ElectricityBilingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
    private Connection con;  // This holds the connection
    public Connection connection; // Public connection to access it outside the class
    Statement st;

    public database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL Driver
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bill_system", "root", "root");
            st = con.createStatement();
            connection = con; // Assign the private connection to the public one
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("MySQL JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    // Getter method for connection
    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
