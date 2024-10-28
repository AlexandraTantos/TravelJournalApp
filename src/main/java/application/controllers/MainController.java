package application.controllers;

import database.DatabaseConnection;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repositories.JournalEntryRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainController {

    @FXML
    private Button toggleMenuButton;
    @FXML
    private Button addExperienceButton;
    @FXML
    private Button logoutButton;
    @FXML
    private ChoiceBox<String> experienceChoiceBox;
    @FXML
    private VBox dropdownMenu;
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView5;
    @FXML
    private ImageView imageView6;
    @FXML
    private ImageView imageView7;
    @FXML
    private ImageView imageView8;

    @FXML
    private ImageView scrollBackgroundImageView;

    @FXML
    private void initialize() {
        loadBackgroundImage();
        loadScrollBackgroundImage();
        populateExperienceChoiceBox();
        setupButtonActions();
        loadImages();
    }

    private void loadBackgroundImage() {
        try {
            Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Background.jpg")));
            backgroundImageView.setImage(backgroundImage);
        } catch (Exception e) {
            showAlert("Error", "The background image could not be loaded.");
        }
    }

    private void loadScrollBackgroundImage() {
        try {
            Image scrollBackgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Background.jpg")));
            scrollBackgroundImageView.setImage(scrollBackgroundImage);
        } catch (Exception e) {
            showAlert("Error", "The background image for the ScrollPane could not be loaded.");
        }
    }

    private void populateExperienceChoiceBox() {
        JournalEntryRepository journalEntryRepository = new JournalEntryRepository(DatabaseConnection.getConnection());
        List<String> countries = journalEntryRepository.getCountries();
        experienceChoiceBox.setItems(FXCollections.observableArrayList(countries));
    }

    private void setupButtonActions() {
        toggleMenuButton.setOnAction(e -> toggleDropdownMenu());
        addExperienceButton.setOnAction(e -> handleAddExperience());
        logoutButton.setOnAction(e -> handleLogout());

        experienceChoiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                handleExperienceInfo(newValue);
            }
        });
    }

    private void toggleDropdownMenu() {
        boolean isVisible = dropdownMenu.isVisible();
        dropdownMenu.setVisible(!isVisible);
        dropdownMenu.setManaged(!isVisible);
    }

    private void handleAddExperience() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Experience");
        dialog.setHeaderText("Enter the name of the experience.");
        dialog.setContentText("Experience name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty() && !experienceChoiceBox.getItems().contains(name)) {
                experienceChoiceBox.getItems().add(name);
                showAlert("Success", "The experience \"" + name + "\" has been added.");
            } else {
                showAlert("Error", "The experience cannot be empty.");
            }
        });
    }

    private void handleExperienceInfo(String experienceName) {
        showAlert("Information" + experienceName, "Information about " + experienceName + ".");
    }

    private void handleLogout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadImages() {
        try {
            imageView1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Italy.jpg"))));
            imageView2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Turkey.jpg"))));
            imageView3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Japan.jpg"))));
            imageView4.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Mexico.jpg"))));
            //imageView5.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/image5.jpg"))));
            //imageView6.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/image6.jpg"))));
            //imageView7.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/image7.jpg"))));
            //imageView8.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/image8.jpg"))));
        } catch (Exception e) {
            showAlert("Error", "One or more images could not be loaded.");
        }
    }
}
