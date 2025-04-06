package com.onofreiflavius.adoptieanimale.model;

/**
 * The {@code Animal} class represents an animal entity in the application.
 * It contains attributes such as ID, image path, species, breed, age, gender, size, description, and a unique animal identifier.
 * This class is used to store and retrieve data related to animals.
 *
 * @author Onofrei Flavius
 */
public class Animal {

    private int id;
    private String imagePath;
    private String species;
    private String breed;
    private int age;
    private String gender;
    private String size;
    private String description;
    private String animalID;

    /**
     * Constructs a new {@code Animal} object with all attributes, including an ID.
     *
     * @param id          the unique ID of the animal.
     * @param imagePath   the file path to the animal's image.
     * @param species     the species of the animal (e.g., Dog, Cat).
     * @param breed       the breed of the animal (e.g., Labrador, Siamese).
     * @param age         the age of the animal in years.
     * @param gender      the gender of the animal (e.g., Male, Female).
     * @param size        the size of the animal (e.g., Small, Medium, Large).
     * @param description a short description of the animal.
     * @param animalID    a unique identifier for the animal (e.g., adoption ID).
     */
    public Animal(int id, String imagePath, String species, String breed, int age, String gender, String size, String description, String animalID) {
        this.id = id;
        this.imagePath = imagePath;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.description = description;
        this.animalID = animalID;
    }

    /**
     * Constructs a new {@code Animal} object without an ID (for creating new records).
     *
     * @param imagePath   the file path to the animal's image.
     * @param species     the species of the animal (e.g., Dog, Cat).
     * @param breed       the breed of the animal (e.g., Labrador, Siamese).
     * @param age         the age of the animal in years.
     * @param gender      the gender of the animal (e.g., Male, Female).
     * @param size        the size of the animal (e.g., Small, Medium, Large).
     * @param description a short description of the animal.
     * @param animalID    a unique identifier for the animal (e.g., adoption ID).
     */
    public Animal(String imagePath, String species, String breed, int age, String gender, String size, String description, String animalID) {
        this.imagePath = imagePath;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.description = description;
        this.animalID = animalID;
    }

    /**
     * Gets the unique ID of the animal.
     *
     * @return the ID of the animal.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the file path to the animal's image.
     *
     * @return the image path of the animal.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Gets the species of the animal.
     *
     * @return the species of the animal (e.g., Dog, Cat).
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Gets the breed of the animal.
     *
     * @return the breed of the animal (e.g., Labrador, Siamese).
     */
    public String getBreed() {
        return breed;
    }

    /**
     * Gets the age of the animal in years.
     *
     * @return the age of the animal.
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the gender of the animal.
     *
     * @return the gender of the animal (e.g., Male, Female).
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the size of the animal.
     *
     * @return the size of the animal (e.g., Small, Medium, Large).
     */
    public String getSize() {
        return size;
    }

    /**
     * Gets a short description of the animal.
     *
     * @return the description of the animal.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the unique identifier for the animal.
     *
     * @return the animal's unique ID (e.g., adoption ID).
     */
    public String getAnimalID() {
        return animalID;
    }
}
