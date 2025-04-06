package com.onofreiflavius.adoptieanimale.view.menu.animals;

import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import com.onofreiflavius.adoptieanimale.controller.scenes.SceneManager;
import com.onofreiflavius.adoptieanimale.model.Animal;
import com.onofreiflavius.adoptieanimale.model.Gender;
import com.onofreiflavius.adoptieanimale.model.SceneType;
import com.onofreiflavius.adoptieanimale.model.Size;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * The AddAnimalScene class represents the scene where an animal can be added, updated, or deleted in the animal adoption application.
 * It provides functionality for filling in the animal's details, selecting an image, and interacting with the database to store the data.
 *
 * @author Onofrei Flavius
 */
public class AddAnimalScene {

    private final Label imagePathLabel;
    private final FileChooser imagePathChooser;
    private final Button chooseImageButton;
    private final Label selectedImageLabel;
    private final Label speciesLabel;
    private final TextField speciesTextField;
    private final Label breedLabel;
    private final TextField breedTextField;
    private final Label ageLabel;
    private final Spinner<Integer> ageSpinner;
    private final Label genderLabel;
    private final ChoiceBox<Gender> genderChoiceBox;
    private final Label sizeLabel;
    private final ChoiceBox<Size> sizeChoiceBox;
    private final Label descriptionLabel;
    private final TextField descriptionTextField;
    private final Label animalIdLabel;
    private final TextField animalIdTextField;
    private final Label errorLabel;

    private final Button addAnimalButton;
    private final Button updateAnimalButton;
    private final Button backButton;

    private String oldImagePath;

    /**
     * Constructor for the AddAnimalScene class.
     * Initializes the UI components and sets up default values.
     */
    public AddAnimalScene() {
        imagePathLabel = new Label("Image Path: ");
        imagePathChooser = new FileChooser();
        chooseImageButton = new Button("Choose Image");
        selectedImageLabel = new Label("Selected Image: ");

        speciesLabel = new Label("Species: ");
        speciesTextField = new TextField();

        breedLabel = new Label("Breed: ");
        breedTextField = new TextField();

        ageLabel = new Label("Age: ");
        ageSpinner = new Spinner<>();

        genderLabel = new Label("Gender: ");
        genderChoiceBox = new ChoiceBox<>();

        genderChoiceBox.getItems().addAll(Gender.values());
        sizeLabel = new Label("Size: ");

        sizeChoiceBox = new ChoiceBox<>();
        sizeChoiceBox.getItems().addAll(Size.values());

        descriptionLabel = new Label("Description: ");
        descriptionTextField = new TextField();

        animalIdLabel = new Label("Animal ID: ");
        animalIdTextField = new TextField();

        errorLabel = new Label();

        addAnimalButton = new Button("Add");
        updateAnimalButton = new Button("Update");
        backButton = new Button("Back");
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void buttonsForAdd() {
        addAnimalButton.setVisible(true);
        updateAnimalButton.setVisible(false);
    }

    public void buttonsForUpdate() {
        addAnimalButton.setVisible(false);
        updateAnimalButton.setVisible(true);
    }

    /**
     * Populates the form with the animal's current data for updating.
     *
     * @param animal The animal object to be used to populate the form.
     */
    public void addData(Animal animal) {
        oldImagePath = animal.getImagePath();
        updateAnimalButton.setUserData(animal.getId());
        selectedImageLabel.setText("Selected image: ");
        speciesTextField.setText(animal.getSpecies());
        breedTextField.setText(animal.getBreed());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, animal.getAge());
        ageSpinner.setValueFactory(valueFactory);
        genderChoiceBox.setValue(Gender.valueOf(animal.getGender().toUpperCase()));
        sizeChoiceBox.setValue(Size.valueOf(animal.getSize().toUpperCase()));
        descriptionTextField.setText(animal.getDescription());
        animalIdTextField.setText(animal.getAnimalID());
    }

    /**
     * Handles the action of updating an animal's information.
     * Checks if all necessary fields are filled, saves the updated data, and stores the image file.
     */
    private void updateAnimalButtonAction() {
        updateAnimalButton.setOnAction(_ -> {
            String imagePath = selectedImageLabel.getText().replace("Selected image: ", "");
            String species = speciesTextField.getText();
            String breed = breedTextField.getText();
            Integer age = ageSpinner.getValue();
            Gender gender = genderChoiceBox.getValue();
            Size size = sizeChoiceBox.getValue();
            String description = descriptionTextField.getText();
            String animalID = animalIdTextField.getText();

            if (species.isEmpty() || breed.isEmpty() || description.isEmpty() || gender == null || size == null) {
                errorLabel.setText("Please fill in all fields, including image.");
                return;
            }

            String destinationDir = "/home/onofreiflavius/Desktop/animals/images/";  // Folder where images will be saved
            File destinationFolder = new File(destinationDir);

            File selectedFile = new File(imagePath);

            String uniqueImageName = System.currentTimeMillis() + "_" + selectedFile.getName(); // Use current time in ms as part of the unique name
            File destinationFile = new File(destinationFolder, uniqueImageName);

            if ( ! selectedImageLabel.getText().equals("Selected image: ")) {
                try {
                    if (oldImagePath != null && !oldImagePath.isEmpty()) {
                        File oldFile = new File(oldImagePath);
                        if (oldFile.exists()) {
                            boolean deleted = oldFile.delete();
                            if (!deleted) {
                                errorLabel.setText("Error deleting old image.");
                                return;
                            }
                        }
                    }

                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    String newImagePath = "/home/onofreiflavius/Desktop/animals/images/" + uniqueImageName;
                    DatabaseServices.updateAnimal(new Animal((int) updateAnimalButton.getUserData(), newImagePath, species, breed, age, gender.toString(), size.toString(), description, animalID));

                    errorLabel.setText("Animal updated successfully!");

                    speciesTextField.clear();
                    breedTextField.clear();
                    descriptionTextField.clear();
                    ageSpinner.getValueFactory().setValue(1); // Reset spinner value
                    genderChoiceBox.getSelectionModel().clearSelection();
                    sizeChoiceBox.getSelectionModel().clearSelection();
                    selectedImageLabel.setText("Selected image: ");

                    oldImagePath = newImagePath;

                } catch (Exception e) {
                    errorLabel.setText("Error saving image: " + e.getMessage());
                }
            } else {
                DatabaseServices.updateAnimal(new Animal((int) updateAnimalButton.getUserData(), oldImagePath, species, breed, age, gender.toString(), size.toString(), description, animalID));

            }
        });
    }

    /**
     * Handles the action of adding a new animal to the system.
     * Ensures that all fields are filled in and the image is properly saved.
     */
    private void addAnimalButtonOnAction() {
        addAnimalButton.setOnAction(_ -> {
            String species = speciesTextField.getText();
            String breed = breedTextField.getText();
            Integer age = ageSpinner.getValue();
            Gender gender = genderChoiceBox.getValue();
            Size size = sizeChoiceBox.getValue();
            String description = descriptionTextField.getText();
            String animalId = animalIdTextField.getText();

            String imagePath = selectedImageLabel.getText().replace("Selected image: ", "");

            if (imagePath.isEmpty() || species.isEmpty() || breed.isEmpty() || description.isEmpty() || gender == null || size == null) {
                errorLabel.setText("Please fill in all fields, including image.");
                return;
            }

            String destinationDir = "/home/onofreiflavius/Desktop/animals/images/";  // Folder where images will be saved
            File destinationFolder = new File(destinationDir);

            File selectedFile = new File(imagePath);

            String uniqueImageName = System.currentTimeMillis() + "_" + selectedFile.getName(); // Use current time in ms as part of the unique name
            File destinationFile = new File(destinationFolder, uniqueImageName);

            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                String image_path = "/home/onofreiflavius/Desktop/animals/images/" + uniqueImageName;
                DatabaseServices.saveAnimal(new Animal(image_path, species, breed, age, gender.toString(), size.toString(), description, animalId));

                speciesTextField.clear();
                breedTextField.clear();
                descriptionTextField.clear();
                ageSpinner.getValueFactory().setValue(1); // Reset spinner value
                genderChoiceBox.getSelectionModel().clearSelection();
                sizeChoiceBox.getSelectionModel().clearSelection();
                selectedImageLabel.setText("Selected image: ");

            } catch (Exception e) {
                errorLabel.setText("Error saving image: " + e.getMessage());
            }

        });
    }

    /**
     * Configures the file chooser for selecting an image.
     * The user can choose only image files (.png, .jpg, .jpeg).
     */
    private void fileChooserConfiguration() {
        imagePathChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        chooseImageButton.setOnAction(_ -> {
            File file = imagePathChooser.showOpenDialog(new Stage());
            if (file != null) {
                selectedImageLabel.setText("Selected image: " + file.getAbsolutePath());
            }
        });
    }

    /**
     * Sets the action for the back button, which will navigate to the main animal list scene.
     *
     * @param stage The current stage to switch the scene.
     */
    public void backButtonAction(Stage stage) {
        backButton.setOnAction(_ -> SceneManager.switchScene(stage, SceneType.ANIMALS));
    }

    /**
     * Constructs the layout of the scene, including labels, text fields, buttons, and error messages.
     *
     * @return The GridPane layout for the AddAnimalScene.
     */
    public GridPane layout() {
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(imagePathLabel, 0, 0);
        layout.add(chooseImageButton, 1, 0);
        layout.add(selectedImageLabel, 0, 1, 2, 1);

        layout.add(speciesLabel, 0, 2);
        layout.add(speciesTextField, 1, 2);

        layout.add(breedLabel, 0, 3);
        layout.add(breedTextField, 1, 3);

        layout.add(ageLabel, 0, 4);
        layout.add(ageSpinner, 1, 4);
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 1));

        layout.add(genderLabel, 0, 5);
        layout.add(genderChoiceBox, 1, 5);

        layout.add(sizeLabel, 0, 6);
        layout.add(sizeChoiceBox, 1, 6);

        layout.add(descriptionLabel, 0, 7);
        layout.add(descriptionTextField, 1, 7);

        layout.add(animalIdLabel, 0, 8);
        layout.add(animalIdTextField, 1, 8);

        layout.add(errorLabel, 0, 9, 2, 1);

        layout.add(addAnimalButton, 0, 10);
        layout.add(updateAnimalButton, 0, 11);
        layout.add(backButton, 1, 11);

        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    /**
     * Initializes the AddAnimalScene. Sets up actions for buttons and loads the styling.
     *
     * @param stage The stage for the scene.
     */
    public void init(Stage stage) {
        fileChooserConfiguration();
        addAnimalButtonOnAction();
        updateAnimalButtonAction();
        backButtonAction(stage);

        SceneManager.loadScene("Account", stage, layout(), "/styling/add_animal_scene.css");
    }
}
