package com.onofreiflavius.adoptieanimale.view.menu.profile;

import com.onofreiflavius.adoptieanimale.controller.authentication.AuthenticationServices;
import com.onofreiflavius.adoptieanimale.controller.scenes.SceneManager;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * ProfileScene class represents the user profile scene where users can view
 * and update their personal information, log out, or navigate to the main menu.
 * This class is responsible for displaying the user's details and handling
 * user interactions related to profile management.
 *
 * @author Onofrei Flavius
 */
public class ProfileScene {

    private final Label email;
    private final Label password;
    private final Label firstname;
    private final Label lastname;
    private final Label phoneNumber;
    private final Label dateOfBirth;
    private final Label livingConditions;
    private final Label petExperience;
    private final Label motivation;

    private final Button updateProfile;
    private final Button backButton;
    private final Button logoutButton;

    /**
     * Constructor that initializes the labels for displaying user information
     * and buttons for interacting with the profile scene.
     */
    public ProfileScene() {
        email = new Label("Email: " + AuthenticationServices.getEmailFromSession());
        password = new Label("Password: " + AuthenticationServices.getPasswordFromSession());
        firstname = new Label("First Name: " + AuthenticationServices.getFirstnameFromSession());
        lastname = new Label("Last Name: " + AuthenticationServices.getLastnameFromSession());
        phoneNumber = new Label("Phone Number: " + AuthenticationServices.getPhoneNumberFromSession());
        dateOfBirth = new Label("Date of Birth: " + AuthenticationServices.getDateOfBirthFromSession());
        livingConditions = new Label("Living Conditions: " + AuthenticationServices.getLivingConditionsFromSession());
        petExperience = new Label("Pet Experience: " + AuthenticationServices.getPetExperienceFromSession());
        motivation = new Label("Motivation: " + AuthenticationServices.getMotivationFromSession());
        updateProfile = new Button("Update Profile");
        backButton = new Button("Back");
        logoutButton = new Button("Logout");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Updates the profile scene with the latest data from the user's session.
     */
    public void updateSceneData() {
        email.setText("Email: " + AuthenticationServices.getEmailFromSession());
        password.setText("Password: " + AuthenticationServices.getPasswordFromSession());
        firstname.setText("First Name: " + AuthenticationServices.getFirstnameFromSession());
        lastname.setText("Last Name: " + AuthenticationServices.getLastnameFromSession());
        phoneNumber.setText("Phone Number: " + AuthenticationServices.getPhoneNumberFromSession());
        dateOfBirth.setText("Date of Birth: " + AuthenticationServices.getDateOfBirthFromSession());
        livingConditions.setText("Living Conditions: " + AuthenticationServices.getLivingConditionsFromSession());
        petExperience.setText("Pet Experience: " + AuthenticationServices.getPetExperienceFromSession());
        motivation.setText("Motivation: " + AuthenticationServices.getMotivationFromSession());
    }

    /**
     * Defines the action when the "Update Profile" button is clicked.
     * Redirects the user to the update profile scene.
     *
     * @param stage The stage to load the new scene onto.
     */
    public void updateProfileAction(Stage stage) {
        updateProfile.setOnAction(_ -> SceneManager.switchScene(stage, SceneType.UPDATE_PROFILE));
    }

    /**
     * Defines the action when the "Back" button is clicked.
     * Redirects the user to the main menu scene.
     *
     * @param stage The stage to load the new scene onto.
     */
    public void backButtonAction(Stage stage) {
        backButton.setOnAction(_ -> SceneManager.switchScene(stage, SceneType.MAIN_MENU));
    }

    /**
     * Defines the action when the "Logout" button is clicked.
     * Removes the user's session and redirects to the login screen.
     *
     * @param stage The stage to load the login scene onto.
     */
    public void logoutButtonAction(Stage stage) {
        logoutButton.setOnAction(_ -> {
            AuthenticationServices.removeSession();
            SceneManager.switchScene(stage, SceneType.LOGIN);
        });
    }

    /**
     * Constructs the layout for the profile scene, including user information
     * labels and action buttons.
     *
     * @return The layout for the profile scene.
     */
    public GridPane layout() {
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(email, 0, 0);
        layout.add(firstname, 0, 1);
        layout.add(lastname, 0, 2);
        layout.add(phoneNumber, 0, 3);
        layout.add(dateOfBirth, 0, 4);
        layout.add(livingConditions, 0, 5);
        layout.add(petExperience, 0, 6);
        layout.add(motivation, 0, 7);
        layout.add(updateProfile, 0, 8);
        layout.add(backButton, 0, 9);
        layout.add(logoutButton, 0, 10);

        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    /**
     * Initializes the profile scene by setting up button actions and loading
     * the scene into the provided stage.
     *
     * @param stage The stage to load the scene onto.
     */
    public void init(Stage stage) {
        updateProfileAction(stage);
        backButtonAction(stage);
        logoutButtonAction(stage);

        SceneManager.loadScene("Account", stage, layout(), "/styling/profile.css");
    }
}
