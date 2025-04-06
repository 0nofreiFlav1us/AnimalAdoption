package com.onofreiflavius.adoptieanimale.view.menu.animals;

import com.onofreiflavius.adoptieanimale.controller.adoption.AdoptionServices;
import com.onofreiflavius.adoptieanimale.controller.authentication.AuthenticationServices;
import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import com.onofreiflavius.adoptieanimale.controller.scenes.SceneManager;
import com.onofreiflavius.adoptieanimale.model.Animal;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;

/**
 * The AnimalsScene class represents the scene where users can view a list of available animals for adoption,
 * as well as manage their adoption requests, add, update, or delete animals (if they are an admin).
 * It interacts with the database to retrieve and manage animal data and handles different user actions.
 *
 * @author Onofrei Flavius
 */
public class AnimalsScene {

    private final Button addAnimalButton;
    private final Button backButton;
    private ScrollPane animalsScrollPane;
    private Stage stage;

    /**
     * Constructs an AnimalsScene object with the necessary buttons and scroll pane.
     */
    public AnimalsScene() {
        addAnimalButton = new Button("Add Animal");
        backButton = new Button("Back");
        animalsScrollPane = new ScrollPane();
    }

    /**
     * Refreshes the animal scene by reloading the list of animals.
     */
    public void refreshAnimalsScene() {
        createScrollableAnimalList();
    }

    /**
     * Sets the action for the "Add Animal" button. When clicked, it navigates to the "Add Animal" scene.
     *
     * @param stage the primary stage for the scene.
     */
    public void addAnimalButtonAction(Stage stage) {
        SceneManager.buttonsForAdd();
        addAnimalButton.setOnAction(_ -> SceneManager.switchScene(stage, SceneType.ADD_ANIMAL));
    }

    /**
     * Sets the action for the "Back" button. When clicked, it navigates back to the "Main Menu" scene.
     *
     * @param stage the primary stage for the scene.
     */
    public void backButtonAction(Stage stage) {
        backButton.setOnAction(_ -> SceneManager.switchScene(stage, SceneType.MAIN_MENU));
    }

    /**
     * Creates a scrollable list of animals by retrieving data from the database and displaying it in a vertical layout.
     * Each animal's details are displayed in a grid layout with options to update, delete, or request adoption.
     */
    public void createScrollableAnimalList() {
        VBox animalListLayout = new VBox();
        animalListLayout.getStyleClass().add("animal-list");
        animalListLayout.setSpacing(10);

        List<Animal> animals;
        animals = DatabaseServices.getAllAnimals();

        for (Animal animal : animals) {
            GridPane gp = new GridPane();

            if (animal.getImagePath() != null) {
                File imageFile = new File(animal.getImagePath());

                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(500); // Set image width
                    imageView.setFitHeight(500); // Set image height
                    imageView.setPreserveRatio(true); // Maintain aspect ratio
                    gp.add(imageView, 0, 0); // Add image spanning multiple rows
                } else {
                    System.out.println("Image file not found: " + imageFile.getAbsolutePath());
                }
            }

            // Add other animal details
            gp.add(new Label("Species: " + animal.getSpecies()), 0, 1);
            gp.add(new Label("Breed: " + animal.getBreed()), 0, 2);
            gp.add(new Label("Age: " + animal.getAge()), 0, 3);
            gp.add(new Label("Gender: " + animal.getGender()), 0, 4);
            gp.add(new Label("Size: " + animal.getSize()), 0, 5);

            Label desc = new Label("Description: " + animal.getDescription());
            desc.setMaxWidth(500);
            desc.setWrapText(true);
            desc.setStyle("-fx-padding: 10;");
            gp.add(desc, 0, 6);

            Button deleteAnimalButton = new Button("Delete");
            deleteAnimalButton.setOnAction(_ -> {
                DatabaseServices.deleteAnimal(animal.getId());
                if (animal.getImagePath() != null && !animal.getImagePath().isEmpty()) {
                    File oldFile = new File(animal.getImagePath());
                    if (oldFile.exists()) {
                        boolean deleted = oldFile.delete();
                        if (!deleted) {
                            System.out.println("Error deleting old image.");
                            return;
                        }
                    }
                }
                refreshAnimalsScene();
                SceneManager.switchScene(stage, SceneType.ANIMALS);
            });

            Button updateAnimalButton = new Button("Update");
            updateAnimalButton.setOnAction(_ -> {
                SceneManager.addDataToUpdateAnimal(animal);
                SceneManager.buttonsForUpdate();
                SceneManager.switchScene(stage, SceneType.ADD_ANIMAL);
            });

            Button adoptionRequestButton = new Button("Adoption Request");
            adoptionRequestButton.setOnAction(_ -> {
                AdoptionServices.generateAdoptionRequestPDF(AuthenticationServices.getUser(), animal);
                SceneManager.switchScene(stage, SceneType.ANIMALS);

            });

            Button cancelAdoptionRequestButton = new Button("Cancel Request");
            cancelAdoptionRequestButton.setOnAction(_ -> {
                AdoptionServices.removeAdoptionRequestPDF(DatabaseServices.getPdfPath(AuthenticationServices.getEmailFromSession(), animal.getId()));
                DatabaseServices.cancelAdoptionRequest(AuthenticationServices.getEmailFromSession(), animal.getId());
                SceneManager.switchScene(stage, SceneType.ANIMALS);
            });

            if (!AuthenticationServices.getEmailFromSession().equals("flavius@gmail.com")) {
                if (!DatabaseServices.requestExists(AuthenticationServices.getUser().getEmail(), animal.getId())) {
                    gp.add(adoptionRequestButton, 0, 8);
                } else {
                    gp.add(cancelAdoptionRequestButton, 1, 8);
                }
                addAnimalButton.setVisible(false);
            } else {
                gp.add(updateAnimalButton, 1, 7);
                gp.add(deleteAnimalButton, 0, 7);
                addAnimalButton.setVisible(true);
            }

            gp.add(new Label(), 0, 9);
            gp.add(new Label(), 0, 10);

            animalListLayout.getChildren().add(gp);
        }

        animalsScrollPane = new ScrollPane(animalListLayout);
        animalsScrollPane.setMinWidth(750); // Make the scroll pane adjust to its parent's width
    }

    /**
     * Constructs the layout of the Animals scene, including buttons for navigation and a scrollable animal list.
     *
     * @return a GridPane containing the entire scene layout.
     */
    public GridPane layout() {
        GridPane layout = new GridPane();

        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(backButton, 0, 0);
        layout.add(addAnimalButton, 0, 1);
        createScrollableAnimalList();
        layout.add(animalsScrollPane, 0, 2); // Spanning across row

        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    /**
     * Initializes the Animals scene by setting up the actions for the buttons and loading the scene.
     *
     * @param stage the primary stage for the scene.
     */
    public void init(Stage stage) {
        AnimalsScene.this.stage = stage;

        backButtonAction(stage);
        addAnimalButtonAction(stage);

        SceneManager.loadScene("Animals", stage, layout(), "/styling/animals_scene.css");
    }
}
