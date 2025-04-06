package com.onofreiflavius.adoptieanimale.view.menu;

import com.onofreiflavius.adoptieanimale.controller.scenes.SceneManager;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * This class represents the "Main Menu" scene in the application. It contains buttons that allow the user to navigate
 * to the "Profile" scene and the "Animals" scene. The buttons perform the scene switching logic using the SceneManager.
 *
 * @author Onofrei Flavius
 */
public class MainMenuScene {

    private final Button accountSceneButton;
    private final Button animalsButton;

    /**
     * Constructor that initializes the buttons for navigating to the "Profile" and "Animals" scenes.
     */
    public MainMenuScene() {
        accountSceneButton = new Button("Profile");
        animalsButton = new Button("Animals");
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Sets the action for the "Profile" button. It switches the scene to the "Profile" scene and refreshes the profile.
     *
     * @param stage the Stage object representing the current window
     */
    public void accountButtonAction(Stage stage) {
        accountSceneButton.setOnAction(_ -> {
            // Redirecting the user to the ACCOUNT scene.
            SceneManager.switchScene(stage, SceneType.PROFILE);
            SceneManager.refreshProfileScene();
        });
    }

    /**
     * Sets the action for the "Animals" button. It switches the scene to the "Animals" scene.
     *
     * @param stage the Stage object representing the current window
     */
    public void animalsButtonAction(Stage stage) {
        animalsButton.setOnAction(_ -> SceneManager.switchScene(stage, SceneType.ANIMALS));
    }

    /**
     * Creates the layout for the "Main Menu" scene using a GridPane. The layout consists of two buttons: one for the "Profile"
     * scene and one for the "Animals" scene.
     *
     * @return a GridPane containing the UI elements of the Main Menu scene
     */
    public GridPane layout() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(accountSceneButton, 0, 0);
        gridPane.add(animalsButton, 1, 0);

        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }

    /**
     * Initializes the scene by setting actions for the buttons and loading the scene.
     *
     * @param stage the Stage object representing the current window
     */
    public void init(Stage stage) {
        accountButtonAction(stage);
        animalsButtonAction(stage);

        SceneManager.loadScene("Menu", stage, layout(), "/styling/menu.css");
    }
}
