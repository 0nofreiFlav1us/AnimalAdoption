package com.onofreiflavius.adoptieanimale.view.authentication;

import com.onofreiflavius.adoptieanimale.controller.authentication.AuthenticationServices;
import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import com.onofreiflavius.adoptieanimale.controller.scenes.SceneManager;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import com.onofreiflavius.adoptieanimale.model.User;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

/**
 * The {@code LoginScene} class represents the login scene in the authentication process.
 * It extends {@link AuthenticationScene} and provides specific functionality for user login.
 * The scene allows users to enter their email and password, and it handles user authentication.
 *
 * @author Onofrei Flavius
 */
public class LoginScene extends AuthenticationScene {

    /**
     * Constructs a new {@code LoginScene} with default login and signup buttons.
     */
    public LoginScene() {
        super(new Button("Login"), new Button("Signup"));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Defines the action for the submit button in the login form. When clicked, it validates the user credentials
     * against the database. If the credentials are invalid, it shows an error message. If they are valid, it creates
     * a session for the user and redirects to the main menu.
     *
     * @param stage The primary stage for the scene.
     */
    public void submitButtonAction(Stage stage) {
        submitButton.setOnAction(_ -> {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();

            // Fetch the stored hashed password for the given email
            String storedHashedPassword = DatabaseServices.getPasswordFromUser(email);

            if (storedHashedPassword == null) {
                // If the email doesn't exist, show an error
                errorLabel.setText("Invalid Credentials!");
            } else {
                // Compare the entered password with the stored hashed password
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    // Password matches, create a session for the user
                    try {
                        AuthenticationServices.createSession(email, storedHashedPassword);

                        // Switch to the main menu scene
                        SceneManager.switchScene(stage, SceneType.MAIN_MENU);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Password doesn't match, show an error
                    errorLabel.setText("Invalid Credentials!");
                }
            }
        });
    }


    /**
     * Defines the action for the alternative button in the login scene. When clicked, it redirects the user to
     * the signup scene.
     *
     * @param stage The primary stage for the scene.
     */
    public void alternativeButtonAction(Stage stage) {
        alternativeButton.setOnAction(_ -> SceneManager.switchScene(stage, SceneType.SIGNUP));
    }

    /**
     * Initializes the login scene by setting up actions for the submit and alternative buttons and loading the
     * scene layout with proper styling.
     *
     * @param stage The primary stage for the scene.
     */
    public void init(Stage stage) {
        submitButtonAction(stage);
        alternativeButtonAction(stage);

        SceneManager.loadScene("Login", stage, layout(null, null), "/styling/authentication.css");
    }

}
