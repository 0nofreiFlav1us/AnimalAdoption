package com.onofreiflavius.adoptieanimale.view.menu.profile;

import com.onofreiflavius.adoptieanimale.controller.authentication.AuthenticationServices;
import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import com.onofreiflavius.adoptieanimale.controller.scenes.SceneManager;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.*;

/**
 * This class represents the UI for the "Update Profile" scene in the application, allowing the user to update their personal
 * information such as name, date of birth, phone number, living conditions, pet experience, and motivation.
 * It interacts with the Authentication and Database services to retrieve and save data.
 *
 * @author Onofrei Flavius
 */
public class UpdateProfile {

    private final Label firstnameLabel;
    private final TextField firstnameTextField;
    private final Label lastnameLabel;
    private final TextField lastnameTextField;
    private final Label phoneNumberLabel;
    private final TextField phoneNumberTextField;
    private final Label dateOfBirthLabel;
    private final DatePicker dateOfBirthDatePicker;
    private final Label livingConditionsLabel;
    private final TextField livingConditionsTextField;
    private final Label petExperienceLabel;
    private final TextField petExperienceTextField;
    private final Label motivationLabel;
    private final TextField motivationTextField;
    private final Label errorLabel;

    private final Button updateProfile;
    private final Button backButton;

    /**
     * Constructor that initializes the components of the "Update Profile" scene with the current user data from the session.
     */
    public UpdateProfile() {
        firstnameLabel = new Label("First Name: ");
        firstnameTextField = new TextField(AuthenticationServices.getFirstnameFromSession());
        lastnameLabel = new Label("Last Name: ");
        lastnameTextField = new TextField(AuthenticationServices.getLastnameFromSession());
        phoneNumberLabel = new Label("Phone Number: ");
        phoneNumberTextField = new TextField(AuthenticationServices.getPhoneNumberFromSession());
        dateOfBirthLabel = new Label("Date of Birth: ");
        LocalDate localDate = null;
        if (AuthenticationServices.getDateOfBirthFromSession() != null) {
            localDate = AuthenticationServices.getDateOfBirthFromSession().toLocalDate();
        }
        dateOfBirthDatePicker = new DatePicker(localDate);
        livingConditionsLabel = new Label("Living Conditions: ");
        livingConditionsTextField = new TextField(AuthenticationServices.getLivingConditionsFromSession());
        petExperienceLabel = new Label("Pet Experience: ");
        petExperienceTextField = new TextField(AuthenticationServices.getPetExperienceFromSession());
        motivationLabel = new Label("Motivation: ");
        motivationTextField = new TextField(AuthenticationServices.getMotivationFromSession());
        errorLabel = new Label();
        updateProfile = new Button("Update");
        backButton = new Button("Back");
    }

    /**
     * Updates the text fields in the UI to reflect the current user's data from the session.
     */
    public void updateTextFields() {
        firstnameTextField.setText(AuthenticationServices.getFirstnameFromSession());
        lastnameTextField.setText(AuthenticationServices.getLastnameFromSession());
        LocalDate localDate = null;
        if (AuthenticationServices.getDateOfBirthFromSession() != null) {
            localDate = AuthenticationServices.getDateOfBirthFromSession().toLocalDate();
        }
        dateOfBirthDatePicker.setValue(localDate);
        phoneNumberTextField.setText(AuthenticationServices.getPhoneNumberFromSession());
        livingConditionsTextField.setText(AuthenticationServices.getLivingConditionsFromSession());
        petExperienceTextField.setText(AuthenticationServices.getPetExperienceFromSession());
        motivationTextField.setText(AuthenticationServices.getMotivationFromSession());
    }

    /**
     * Sets the action for the "Update" button. It validates the input and updates the user's profile if valid.
     */
    public void updateProfileButtonAction() {
        updateProfile.setOnAction(_ -> {
            if (firstnameTextField.getText().length() > 25 || lastnameTextField.getText().length() > 25 ||
                    phoneNumberTextField.getText().length() > 11 || livingConditionsTextField.getText().length() > 250 ||
                    petExperienceTextField.getText().length() > 250 || motivationTextField.getText().length() > 250) {

                errorLabel.setText("Respect the conditions!");
            } else {
                LocalDate localDate = dateOfBirthDatePicker.getValue();
                Date dateOfBirth = Date.valueOf(localDate);
                AuthenticationServices.updateUser(firstnameTextField.getText(), lastnameTextField.getText(), dateOfBirth, livingConditionsTextField.getText(), petExperienceTextField.getText(), motivationTextField.getText(), phoneNumberTextField.getText());
                DatabaseServices.updateUserProfile(AuthenticationServices.getUser());

                SceneManager.refreshProfileScene();
                backButton.fire();
            }
        });
    }

    /**
     * Sets the action for the "Back" button. It switches the scene back to the Profile scene.
     *
     * @param stage the Stage object representing the current window
     */
    public void backButtonAction(Stage stage) {
        backButton.setOnAction(_ -> {
            SceneManager.switchScene(stage, SceneType.PROFILE);
            updateTextFields();
        });
    }

    /**
     * Creates the layout for the "Update Profile" scene using a GridPane.
     *
     * @return a GridPane containing the UI elements of the Update Profile scene
     */
    public GridPane layout() {
        GridPane layout = new GridPane();

        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(firstnameLabel, 0, 0);
        layout.add(firstnameTextField, 1, 0);

        layout.add(lastnameLabel, 0, 1);
        layout.add(lastnameTextField, 1, 1);

        layout.add(phoneNumberLabel, 0, 2);
        layout.add(phoneNumberTextField, 1, 2);

        layout.add(dateOfBirthLabel, 0, 3);
        layout.add(dateOfBirthDatePicker, 1, 3);

        layout.add(livingConditionsLabel, 0, 4);
        layout.add(livingConditionsTextField, 0, 5);

        layout.add(petExperienceLabel, 0, 6);
        layout.add(petExperienceTextField, 0, 7);

        layout.add(motivationLabel, 0, 8);
        layout.add(motivationTextField, 0, 9);

        layout.add(errorLabel, 0, 10);

        layout.add(updateProfile, 0, 11);
        layout.add(backButton, 1, 11);

        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    /**
     * Initializes the scene by setting actions for the buttons and loading the scene.
     *
     * @param stage the Stage object representing the current window
     */
    public void init(Stage stage) {
        updateProfileButtonAction();
        backButtonAction(stage);

        SceneManager.loadScene("Account", stage, layout(), "/styling/update_profile.css");
    }
}
