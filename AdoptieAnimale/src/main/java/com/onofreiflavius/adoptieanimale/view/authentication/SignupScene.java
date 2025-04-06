package com.onofreiflavius.adoptieanimale.view.authentication;

import com.onofreiflavius.adoptieanimale.controller.authentication.AuthenticationServices;
import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import com.onofreiflavius.adoptieanimale.controller.scenes.SceneManager;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import com.onofreiflavius.adoptieanimale.model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The {@code SignupScene} class represents the signup scene in the authentication process.
 * It extends {@link AuthenticationScene} and provides specific functionality for user signup.
 * The scene allows users to enter their email, password, and confirm their password for registration.
 *
 * @author Onofrei Flavius
 */
public class SignupScene extends AuthenticationScene {

    private final Label confirmPasswordLabel;
    private final PasswordField confirmPasswordTextField;

    /**
     * Constructs a new {@code SignupScene} with default signup and login buttons, as well as
     * the confirmation password label and text field.
     */
    public SignupScene() {
        super(new Button("Signup"), new Button("Login"));

        confirmPasswordLabel = new Label("Confirm Password: ");
        confirmPasswordTextField = new PasswordField();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Defines the action for the submit button in the signup form. When clicked, it validates the user input,
     * including the email, password, and password confirmation. If valid, it adds the user to the database and redirects
     * them to the login scene. If invalid, it shows an error message.
     *
     * @param stage The primary stage for the scene.
     */
    public void submitButtonAction(Stage stage) {
        submitButton.setOnAction(_ -> {
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            String passwordConfirmation = confirmPasswordTextField.getText();

            if( ! AuthenticationServices.dataIsValid(email, password, passwordConfirmation)) {
                errorLabel.setText("Invalid email or password!");
            }
            else {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                DatabaseServices.addUser(new User(email, hashedPassword));

                SceneManager.switchScene(stage, SceneType.LOGIN);
            }
        });
    }

    /**
     * Defines the action for the alternative button in the signup scene. When clicked, it redirects the user to
     * the login scene.
     *
     * @param stage The primary stage for the scene.
     */
    public void alternativeButtonAction(Stage stage) {
        alternativeButton.setOnAction(_ -> SceneManager.switchScene(stage, SceneType.LOGIN));
    }

    /**
     * Initializes the signup scene by setting up actions for the submit and alternative buttons and loading the
     * scene layout with proper styling.
     *
     * @param stage The primary stage for the scene.
     */
    public void init(Stage stage) {
        submitButtonAction(stage);
        alternativeButtonAction(stage);

        SceneManager.loadScene("Signup", stage, layout(confirmPasswordLabel, confirmPasswordTextField), "/styling/authentication.css");
    }

}
