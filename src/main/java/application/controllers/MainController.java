package application.controllers;

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
        experienceChoiceBox.setItems(FXCollections.observableArrayList(
                "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Democratic Republic of the", "Congo, Republic of the", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, North", "Korea, South", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"
        ));
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
