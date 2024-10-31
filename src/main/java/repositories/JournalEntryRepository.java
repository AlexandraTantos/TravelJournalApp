package repositories;

import models.JournalEntry;
import models.Location;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalEntryRepository {
    private Connection connection;

    public JournalEntryRepository(Connection connection) {
        this.connection = connection;
    }

    public void addEntry(JournalEntry entry) {
        String sql = "INSERT INTO journal_entries (title, content, location_id, cost, is_public, image_path, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entry.getTitle());
            statement.setString(2, entry.getContent());
            if (entry.getLocation() != null) {
                statement.setInt(3, entry.getLocation().getId());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER); // Dacă nu există locație
            }

            statement.setDouble(4, entry.getCost());
            statement.setBoolean(5, entry.isPublic());
            statement.setString(6, entry.getImagePath());
            statement.setInt(7, entry.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JournalEntry> getAllEntries() {
        List<JournalEntry> entries = new ArrayList<>();
        String sql = "SELECT je.*, l.country, l.city FROM journal_entries je JOIN locations l ON je.location_id = l.id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Location location = new Location(
                        resultSet.getInt("location_id"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getInt("countryId")
                );

                JournalEntry entry = new JournalEntry(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        location,
                        resultSet.getBoolean("is_public"),
                        resultSet.getDouble("cost"),
                        resultSet.getString("image_path"),
                        resultSet.getInt("user_id")
                );
                entries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        String sql = "SELECT DISTINCT name FROM countries";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                countries.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    public void removeEntry(JournalEntry entry) {
        String sql = "DELETE FROM journal_entries WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entry.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEntry(JournalEntry entry) {
        String sql = "UPDATE journal_entries SET title = ?, content = ?, location_id = ?, cost = ?, is_public = ?, image_path = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entry.getTitle());
            statement.setString(2, entry.getContent());
            if (entry.getLocation() != null) {
                statement.setInt(3, entry.getLocation().getId());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER); // Dacă nu există locație
            }

            statement.setDouble(4, entry.getCost());
            statement.setBoolean(5, entry.isPublic());
            statement.setString(6, entry.getImagePath());
            statement.setInt(7, entry.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JournalEntry> getEntriesByUserId(int userId) {
        List<JournalEntry> entries = new ArrayList<>();
        String sql = "SELECT je.*, l.country, l.city, l.countryId FROM journal_entries je JOIN locations l ON je.location_id = l.id WHERE je.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Location location = new Location(
                            resultSet.getInt("location_id"),
                            resultSet.getString("country"),
                            resultSet.getString("city"),
                            resultSet.getInt("countryId")
                    );

                    JournalEntry entry = new JournalEntry(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("content"),
                            location,
                            resultSet.getBoolean("is_public"),
                            resultSet.getDouble("cost"),
                            resultSet.getString("image_path"),
                            resultSet.getInt("user_id")
                    );
                    entries.add(entry);
                    System.out.println("Found entry: " + entry.getTitle());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Total entries for userId " + userId + ": " + entries.size());
        return entries;
    }
}
