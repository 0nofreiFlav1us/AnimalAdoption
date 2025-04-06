package com.onofreiflavius.adoptieanimale.controller.scenes;

import com.onofreiflavius.adoptieanimale.model.Animal;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import com.onofreiflavius.adoptieanimale.view.authentication.LoginScene;
import com.onofreiflavius.adoptieanimale.view.authentication.SignupScene;
import com.onofreiflavius.adoptieanimale.view.menu.MainMenuScene;
import com.onofreiflavius.adoptieanimale.view.menu.animals.AddAnimalScene;
import com.onofreiflavius.adoptieanimale.view.menu.animals.AnimalsScene;
import com.onofreiflavius.adoptieanimale.view.menu.profile.ProfileScene;
import com.onofreiflavius.adoptieanimale.view.menu.profile.UpdateProfile;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The {@code SceneManager} class manages the application's scenes and transitions between them.
 * It provides utility methods to load and switch scenes dynamically based on user interactions.
 * The class includes references to various scene types such as login, signup, main menu, profile, and animal-related scenes.
 *
 * @author Onofrei Flavius
 */
public class SceneManager {

    private static final LoginScene loginScene = new LoginScene();
    private static final SignupScene signupScene = new SignupScene();
    private static final MainMenuScene mainMenuScene = new MainMenuScene();
    private static final ProfileScene profileScene = new ProfileScene();
    private static final UpdateProfile updateProfile = new UpdateProfile();
    private static final AnimalsScene animalsScene = new AnimalsScene();
    private static final AddAnimalScene addAnimalScene = new AddAnimalScene();

    public static void buttonsForAdd() {
        addAnimalScene.buttonsForAdd();
    }

    public static void buttonsForUpdate() {
        addAnimalScene.buttonsForUpdate();
    }

    /**
     * Refreshes the profile scene to update its data dynamically.
     * This method is typically called when user profile information changes.
     */
    public static void refreshProfileScene() {
        profileScene.updateSceneData();
    }

    /**
     * Prepares the {@link AddAnimalScene} by adding data of a specific animal to the form.
     * This method is useful for editing existing animal records.
     *
     * @param animal the {@link Animal} object containing data to populate the form.
     */
    public static void addDataToUpdateAnimal(Animal animal) {
        addAnimalScene.addData(animal);
    }

    /**
     * Loads a new scene with the specified title, layout, and CSS styling.
     * Sets the new scene on the given stage.
     *
     * @param title        the title of the scene to be displayed in the window.
     * @param stage        the primary stage on which the scene is displayed.
     * @param layout       the {@link GridPane} layout of the scene.
     * @param stylingPath  the path to the CSS stylesheet for the scene.
     */
    public static void loadScene(String title, Stage stage, GridPane layout, String stylingPath) {
        stage.setTitle(title);
        stage.setResizable(false);

        StackPane centeredLayout = new StackPane(layout);

        centeredLayout.getStylesheets().add(Objects.requireNonNull(SceneManager.class.getResource(stylingPath)).toExternalForm());

        stage.setScene(new Scene(centeredLayout, 1500, 1500));
        stage.show();
    }

    /**
     * Switches between scenes dynamically based on the {@link SceneType} parameter.
     * Initializes and displays the appropriate scene on the given stage.
     *
     * @param stage     the primary stage on which the scene is displayed.
     * @param sceneName the {@link SceneType} enum representing the target scene.
     */
    public static void switchScene(Stage stage, SceneType sceneName) {
        switch (sceneName) {
            case SceneType.LOGIN:
                loginScene.init(stage);
                break;

            case SceneType.SIGNUP:
                signupScene.init(stage);
                break;

            case SceneType.MAIN_MENU:
                mainMenuScene.init(stage);
                break;

            case SceneType.PROFILE:
                profileScene.init(stage);
                break;

            case SceneType.UPDATE_PROFILE:
                updateProfile.init(stage);
                break;

            case SceneType.ANIMALS:
                animalsScene.init(stage);
                break;

            case SceneType.ADD_ANIMAL:
                addAnimalScene.init(stage);
                break;

            default:
                break;
        }
    }

}
