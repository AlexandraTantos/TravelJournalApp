package repository;

import exceptions.InvalidLocationException;
import model.Location;
import java.sql.*;

public class LocationRepository {
    private Connection connection;

    public LocationRepository(Connection connection) {
        this.connection = connection;
    }

    public Location findLocationById(int id) throws SQLException {
        String sql = "SELECT * FROM locations WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Location(
                            resultSet.getInt("id"),
                            resultSet.getString("country"),
                            resultSet.getString("city")
                    );
                }
            }
        }
        return null;
    }

    public void addLocation(Location location) throws InvalidLocationException {
        if (location.getCountry() == null || location.getCountry().isEmpty()) {
            throw new InvalidLocationException("Country cannot be null or empty.");
        }
        if (location.getCity() == null || location.getCity().isEmpty()) {
            throw new InvalidLocationException("City cannot be null or empty.");
        }

        String sql = "INSERT INTO locations (country, city) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, location.getCountry());
            statement.setString(2, location.getCity());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidLocationException("Database error while adding location: " + e.getMessage());
        }
    }


}
