package application.controllers;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import repositories.JournalEntryRepository;

import java.io.IOException;
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
    private ComboBox<String> experienceComboBox;
    @FXML
    private VBox dropdownMenu;

    @FXML
    private Label box1Label;
    @FXML
    private Label box2Label;
    @FXML
    private Label box3Label;
    @FXML
    private Label box4Label;

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private Text text1;


    @FXML
    private Rectangle box1Rectangle;
    @FXML
    private Rectangle box2Rectangle;
    @FXML
    private Rectangle box3Rectangle;
    @FXML
    private Rectangle box4Rectangle;
    @FXML
    private Button myJournalButton;
    @FXML
    private ImageView scrollBackgroundImageView;
    private User user;

    public void setUser(User user) {
        if(user != null) {this.user = user;}
    }

    public User getUser() {
        return user;
    }

    @FXML
    private VBox scrollableContainer;
    @FXML
    private Text experienceDetailsText;

    @FXML
    private void initialize() {
        loadScrollBackgroundImage();
        populateExperienceComboBox();
        setupButtonActions();
        loadImages();
        setupLabels();

        scrollableContainer.setVisible(false);
        scrollableContainer.setManaged(false);
    }

    private void setupLabels() {
        box1Label.setOnMouseClicked(event -> handleCountrySelection(box1Label.getText()));
        box2Label.setOnMouseClicked(event -> handleCountrySelection(box2Label.getText()));
        box3Label.setOnMouseClicked(event -> handleCountrySelection(box3Label.getText()));
        box4Label.setOnMouseClicked(event -> handleCountrySelection(box4Label.getText()));
    }

    private void handleCountrySelection(String countryName) {
        clearExperienceBoxes();
        showAlert("Selected Country", "You selected: " + countryName);
        displayExperienceDetails(countryName);

        if (experienceDetailsText != null) {
            experienceDetailsText.setText("Details about " + countryName + " will go here.");
            scrollableContainer.setVisible(true);
            scrollableContainer.setManaged(true);
        }
    }

    private void displayExperienceDetails(String countryName) {
        if (experienceDetailsText != null) {
            experienceDetailsText.setText("Details about your experience in " + countryName);
        }
    }

    private void clearExperienceBoxes() {
        box1Label.setVisible(false);
        box2Label.setVisible(false);
        box3Label.setVisible(false);
        box4Label.setVisible(false);

        imageView1.setVisible(false);
        imageView2.setVisible(false);
        imageView3.setVisible(false);
        imageView4.setVisible(false);

        box1Rectangle.setVisible(false);
        box2Rectangle.setVisible(false);
        box3Rectangle.setVisible(false);
        box4Rectangle.setVisible(false);
        text1.setVisible(false);

        scrollableContainer.setVisible(false);
        scrollableContainer.setManaged(false);
    }

    private void loadScrollBackgroundImage() {
        try {
            Image scrollBackgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Background.jpg")));
            scrollBackgroundImageView.setImage(scrollBackgroundImage);
        } catch (Exception e) {
            showAlert("Error", "The background image for the ScrollPane could not be loaded.");
        }
    }

    private void populateExperienceComboBox() {
        JournalEntryRepository journalEntryRepository = new JournalEntryRepository(DatabaseConnection.getConnection());
        List<String> countries = journalEntryRepository.getCountries();
        experienceComboBox.setItems(FXCollections.observableArrayList(countries));

        experienceComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                handleCountrySelection(newValue);
            }
        });
    }

    private void setupButtonActions() {
        toggleMenuButton.setOnAction(e -> toggleDropdownMenu());
        addExperienceButton.setOnAction(e -> handleAddExperience());
        logoutButton.setOnAction(e -> handleLogout());
        myJournalButton.setOnAction(e -> openMyJournal());

        experienceComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
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
            if (!name.trim().isEmpty() && !experienceComboBox.getItems().contains(name)) {
                experienceComboBox.getItems().add(name);
                showAlert("Success", "The experience \"" + name + "\" has been added.");
            } else {
                showAlert("Error", "The experience cannot be empty.");
            }
        });
    }

    private void handleExperienceInfo(String experienceName) {
        showAlert("Information: " + experienceName, "Information about " + experienceName + ".");
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

    private void openMyJournal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/my-journal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 750));
            stage.setTitle("My Journal");
            MyJournalController controller = loader.getController();
            controller.setUser(user);
            controller.loadJournalEntries();
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Could not open My Journal.\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadImages() {
        try {
            imageView1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Italy.jpg"))));
            imageView2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Turkey.jpg"))));
            imageView3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Japan.jpg"))));
            imageView4.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Mexico.jpg"))));
            // Load additional images as needed
        } catch (Exception e) {
            showAlert("Error", "One or more images could not be loaded.");
        }
    }
}
