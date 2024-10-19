package traveljournal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button addEntryButton;

    @FXML
    private Button viewEntriesButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void initialize() {
        addEntryButton.setOnAction(e -> handleAddEntry());
        viewEntriesButton.setOnAction(e -> handleViewEntries());
        logoutButton.setOnAction(e -> handleLogout());
    }

    private void handleAddEntry() {
        showAlert("Add Entry", "Feature to add an entry will be implemented.");
    }

    private void handleViewEntries() {
        showAlert("View Entries", "Feature to view entries will be implemented.");
    }

    private void handleLogout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
