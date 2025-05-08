import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {

    private Connection conn;

    // Connect to a specific SQLite database by passing the database name
    public void connectToDatabase(String dbName) {
        try {
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            System.out.println("Connection to SQLite has been established.");

            // Ensure the user table is created
            createUserTableIfNotExists();  // This ensures the user table exists when connected

        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    // Create designs table if it does not exist (for the designs database)
    public void createDesignTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS designs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "design_name TEXT," +
                "customer_name TEXT," +
                "room_length INTEGER," +
                "room_width INTEGER)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error creating designs table: " + e.getMessage());
        }
    }

    // Create users table if it does not exist (for the user management database)
    private void createUserTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "designerName TEXT," +
                "email TEXT," +
                "phone TEXT," +
                "password TEXT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);  // This will create the table if it doesn't exist
        } catch (SQLException e) {
            System.err.println("Error creating user table: " + e.getMessage());
        }
    }

    // Insert design data into the designs table
    public void insertDesignData(String designName, String customerName, int roomLength, int roomWidth) {
        String insertSQL = "INSERT INTO designs (design_name, customer_name, room_length, room_width) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, designName);
            pstmt.setString(2, customerName);
            pstmt.setInt(3, roomLength);
            pstmt.setInt(4, roomWidth);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting design data: " + e.getMessage());
        }
    }

    // Insert user data into the users table
    public void insertUserData(String designerName, String email, String phone, String password) {
        String insertSQL = "INSERT INTO users (designername, email, phone, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, designerName);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, password);
            pstmt.executeUpdate();
            System.out.println("User data inserted successfully"); // Add this line to confirm
        } catch (SQLException e) {
            System.err.println("Error inserting user data: " + e.getMessage());
        }
    }

    // Retrieve all design data from the designs database
    public ArrayList<String[]> getDesignData() {
        ArrayList<String[]> designs = new ArrayList<>();
        String selectSQL = "SELECT design_name, customer_name, room_length, room_width FROM designs";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                String designName = rs.getString("design_name");
                String customerName = rs.getString("customer_name");
                int roomLength = rs.getInt("room_length");
                int roomWidth = rs.getInt("room_width");
                designs.add(new String[]{designName, customerName, String.valueOf(roomLength), String.valueOf(roomWidth)});
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving design data: " + e.getMessage());
        }
        return designs;
    }

    // Retrieve all user data from the users database
    public ArrayList<String[]> getUserData() {
        ArrayList<String[]> users = new ArrayList<>();
        String selectSQL = "SELECT designername, email, phone FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                String designerName = rs.getString("designername");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                users.add(new String[]{designerName, email, phone});
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user data: " + e.getMessage());
        }
        return users;
    }

    public boolean validateUserCredentials(String email, String password) {
        String selectSQL = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            // If a user is found, credentials are correct
            return rs.next();  // Returns true if there's a matching user
        } catch (SQLException e) {
            System.err.println("Error validating user credentials: " + e.getMessage());
            return false;
        }
    }

    // Close database connection
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing the connection: " + e.getMessage());
        }
    }
}
