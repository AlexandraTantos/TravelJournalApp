package database;

import model.Location;
import model.JournalEntry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        connect();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:travel_journal.db");
            String createLocationsTable = "CREATE TABLE IF NOT EXISTS locations ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "country TEXT NOT NULL,"
                    + "dateVisited TEXT,"
                    + "description TEXT,"
                    + "imagePath TEXT)";
            connection.createStatement().execute(createLocationsTable);

            String createEntriesTable = "CREATE TABLE IF NOT EXISTS journal_entries ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "locationId INTEGER,"
                    + "notes TEXT,"
                    + "FOREIGN KEY(locationId) REFERENCES locations(id))";
            connection.createStatement().execute(createEntriesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLocation(Location location) {
        String sql = "INSERT INTO locations(name, country, dateVisited, description) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getCountry());
            pstmt.setString(3, location.getDateVisited());
            pstmt.setString(4, location.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addJournalEntry(JournalEntry entry) {
        String sql = "INSERT INTO journal_entries(locationId, notes) VALUES(?,?)";
        // Add logic to get the location ID based on the location object
    }
}
