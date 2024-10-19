package traveljournal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import repository.UserRepository;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML
    public ImageView banner;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private final UserRepository userRepository = new UserRepository();

    @FXML
    public void initialize() {
        URL imageUrl = getClass().getResource("/images/travel.jpg");
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            banner.setImage(image);
        }

        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Input Error", "Please enter both username and password.");
            return;
        }

        if (userRepository.isUserValid(username, password)) {
            showAlert("Login successful!", "Welcome, " + username);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main-view.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 800));
                stage.setTitle("Travel Journal - Main Page");
                usernameField.clear();
                passwordField.clear();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Could not load main page.");
            }

        } else {
            showAlert("Login failed!", "Invalid username or password.");
        }
    }

    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/register-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 350, 400));
            stage.setTitle("Travel Journal - Register");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load register page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
