package com.onofreiflavius.adoptieanimale.view.authentication;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * The {@code AuthenticationScene} class provides the common structure for authentication-related scenes,
 * such as login and signup. It defines the UI components (email, password fields, buttons, etc.) that are shared
 * across these scenes.
 *
 * @author Onofrei Flavius
 */
public abstract class AuthenticationScene {

    protected Label emailLabel;
    protected TextField emailTextField;
    protected Label passwordLabel;
    protected PasswordField passwordTextField;
    protected Button submitButton;
    protected Button alternativeButton;
    protected Label errorLabel;

    /**
     * Constructs an {@code AuthenticationScene} with the specified buttons.
     *
     * @param submitButton    The button used for submitting the form (e.g., login or signup).
     * @param alternativeButton The button used for an alternative action (e.g., switching between login and signup).
     */
    public AuthenticationScene(Button submitButton, Button alternativeButton) {
        emailLabel = new Label("Email: ");
        emailTextField = new TextField();
        passwordLabel = new Label("Password: ");
        passwordTextField = new PasswordField();
        this.submitButton = submitButton;
        this.alternativeButton = alternativeButton;
        errorLabel = new Label();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Constructs the layout for the authentication scene. The layout includes fields for email, password,
     * and optionally, a confirmation password field, along with submit and alternative buttons, and an error label.
     *
     * @param confirmPasswordLabel  The label for the confirmation password field, or {@code null} if not needed.
     * @param confirmPasswordField  The confirmation password field, or {@code null} if not needed.
     * @return A {@link GridPane} layout containing the UI components for the authentication form.
     */
    public GridPane layout(Label confirmPasswordLabel, PasswordField confirmPasswordField) {
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.add(emailLabel, 0, 0);
        layout.add(emailTextField, 1, 0);
        layout.add(passwordLabel, 0, 1);
        layout.add(passwordTextField, 1, 1);

        if(confirmPasswordLabel != null && confirmPasswordField != null) {
            layout.add(confirmPasswordLabel, 0, 2);
            layout.add(confirmPasswordField, 1, 2);
        }

        layout.add(submitButton, 0, 3);
        layout.add(alternativeButton, 1, 3);
        layout.add(errorLabel, 1, 4);

        layout.setAlignment(Pos.CENTER);

        return layout;
    }
}
