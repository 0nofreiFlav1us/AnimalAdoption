package com.onofreiflavius.adoptieanimale;

import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import com.onofreiflavius.adoptieanimale.controller.authentication.AuthenticationServices;
import com.onofreiflavius.adoptieanimale.controller.scenes.SceneManager;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import javafx.stage.Stage;

/**
 * The main entry point of the application. It extends {@link javafx.application.Application} to launch the JavaFX application.
 * The start method is called when the application is launched, setting up the initial scene based on the session status.
 *
 * @author  Onofrei Flavius
 */
public class Application extends javafx.application.Application {

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * The start method is called when the JavaFX application is launched. It establishes a database connection
     * and checks if there is an active user session. Based on the session status, it switches the scene to either
     * the MAIN MENU scene (if the user is logged in) or the LOGIN scene (if no active session is found).
     *
     * @param stage the primary stage (window) of the application
     */
    @Override
    public void start(Stage stage) {

        DatabaseServices.establishConnection();

        if (AuthenticationServices.checkSession()) {
            SceneManager.switchScene(stage, SceneType.MAIN_MENU);
        } else {
            SceneManager.switchScene(stage, SceneType.LOGIN);
        }

    }
}
