package com.onofreiflavius.adoptieanimale.controller.database;

import com.onofreiflavius.adoptieanimale.model.Animal;
import com.onofreiflavius.adoptieanimale.model.User;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * The DatabaseServices class provides utility methods for interacting with the application's database.
 * It allows managing users, animals, and adoption requests.
 *
 * @author Onofrei Flavius
 */
public class DatabaseServices {

    private static Connection connection;

    /**
     * Establishes a connection to the database using properties defined in the config.properties file.
     */
    public static void establishConnection() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(Objects.requireNonNull(DatabaseServices.class.getResource("/config.properties")).getPath()));

            Class.forName(properties.getProperty("db.driver"));

            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );

            System.out.println("Connected to the database!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user The user object containing the user's email and password.
     */
    public static void addUser(User user) {
        try {
            String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new user was inserted successfully!");
            } else {
                System.out.println("A new user was not inserted successfully!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks whether a user exists in the database.
     *
     * @param user The user object containing the email and password to check.
     * @return true if the user exists, false otherwise.
     */
    public static boolean userExists(User user) {
        boolean exists = false;
        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return exists;
    }

    /**
     * Updates a user's profile information in the database.
     *
     * @param user The user object containing updated profile details.
     */
    public static void updateUserProfile(User user) {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ?, livingconditions = ?, petexperience = ?, motivation = ?, phonenumber= ? WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getDateOfBirth().toString());
            statement.setString(4, user.getLivingConditions());
            statement.setString(5, user.getPetExperience());
            statement.setString(6, user.getMotivation());
            statement.setString(7, user.getPhoneNumber());
            statement.setString(8, user.getEmail());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("The user was updated successfully!");
            } else {
                System.out.println("The user was not updated successfully!");
            }

        } catch (Exception e) {
            System.out.println("Update user profile error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a user's profile data from the database.
     *
     * @param email The user's email address.
     * @return A ResultSet containing the user's profile data.
     */
    public static ResultSet getUserData(String email) {
        ResultSet resultSet = null;

        String sql = "SELECT firstname, lastname, dateofbirth, livingconditions, petexperience, motivation, phonenumber FROM users WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            resultSet = statement.executeQuery();
        } catch (Exception e) {
            System.out.println("Get user data: " + e.getMessage());
        }

        return resultSet;
    }

    /**
     * Retrieves all animals from the database.
     *
     * @return A list of Animal objects representing all animals in the database.
     */
    public static List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Animal animal = new Animal(resultSet.getInt("id"),
                        resultSet.getString("image_path"),
                        resultSet.getString("species"),
                        resultSet.getString("breed"),
                        resultSet.getInt("age"),
                        resultSet.getString("gender"),
                        resultSet.getString("size"),
                        resultSet.getString("description"),
                        resultSet.getString("animal_id"));

                animals.add(animal);
            }

        } catch (Exception e) {
            System.out.println("getAllAnimals Error: " + e.getMessage());
        }

        return animals;
    }

    /**
     * Inserts a new animal into the database.
     *
     * @param animal The Animal object containing details of the animal to save.
     */
    public static void saveAnimal(Animal animal) {
        String sql = "INSERT INTO animals (image_path, species, breed, age, gender, size, description, animal_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = completeInsertUpdateStatement(sql, animal, false);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new animal was inserted successfully!");
            } else {
                System.out.println("A new animal was not inserted successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error saving animal: " + e.getMessage());
        }
    }

    /**
     * Deletes an animal from the database.
     *
     * @param id The ID of the animal to delete.
     */
    public static void deleteAnimal(int id) {
        String sql = "DELETE FROM animals WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("An animal was deleted successfully!");
            } else {
                System.out.println("An animal was not deleted successfully!");
            }

        } catch (Exception e) {
            System.out.println("deleteAnimal Error: " + e.getMessage());
        }
    }

    /**
     * Updates an animal's information in the database.
     *
     * @param animal The Animal object containing updated animal details.
     */
    public static void updateAnimal(Animal animal) {
        String sql = "UPDATE animals SET image_path = ?, species = ?, breed = ?, age = ?, gender = ?, size = ?, description = ?, animal_id = ? WHERE id = ?";
        try {
            PreparedStatement statement = completeInsertUpdateStatement(sql, animal, true);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Animal updated successfully!");
            } else {
                System.out.println("No animal was updated.");
            }

        } catch (Exception e) {
            System.out.println("updateAnimal Error: " + e.getMessage());
        }
    }

    /**
     * Creates a PreparedStatement for inserting or updating an animal's data.
     *
     * @param sql    The SQL query string.
     * @param animal The Animal object with data to populate the statement.
     * @return The completed PreparedStatement.
     * @throws SQLException If an error occurs while preparing the statement.
     */
    public static PreparedStatement completeInsertUpdateStatement(String sql, Animal animal, boolean lastParameter) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, animal.getImagePath());
        statement.setString(2, animal.getSpecies());
        statement.setString(3, animal.getBreed());
        statement.setInt(4, animal.getAge());
        statement.setString(5, animal.getGender());
        statement.setString(6, animal.getSize());
        statement.setString(7, animal.getDescription());
        statement.setString(8, animal.getAnimalID());
        if (lastParameter) {
            statement.setInt(9, animal.getId());
        }

        return statement;
    }

    /**
     * Inserts an adoption request for an animal.
     *
     * @param email     The user's email.
     * @param animal_id The ID of the animal being requested for adoption.
     * @param path      The file path for the adoption request document.
     */
    public static void adoptionRequest(String email, int animal_id, String path) {
        String sql = "INSERT INTO adoption_requests (user_email, animal_id, path) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setInt(2, animal_id);
            statement.setString(3, path);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Adoption request updated successfully!");
            } else {
                System.out.println("Adoption request was not updated successfully!");
            }

        } catch (Exception e) {
            System.out.println("adoptionRequest Error: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing adoption request.
     *
     * @param email     The user's email.
     * @param animal_id The ID of the animal in the request.
     */
    public static void cancelAdoptionRequest(String email, int animal_id) {
        String sql = "DELETE FROM adoption_requests WHERE user_email = ? AND animal_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setInt(2, animal_id);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Adoption request was cancelled successfully!");
            } else {
                System.out.println("Adoption request was not cancelled successfully!");
            }

        } catch (Exception e) {
            System.out.println("cancelAdoptionRequest Error: " + e.getMessage());
        }
    }

    /**
     * Checks whether an adoption request exists for a user and an animal.
     *
     * @param email     The user's email.
     * @param animal_id The ID of the animal.
     * @return true if the request exists, false otherwise.
     */
    public static boolean requestExists(String email, int animal_id) {
        boolean exists = false;
        String sql = "SELECT * FROM adoption_requests WHERE user_email = ? AND animal_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setInt(2, animal_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }

        } catch (Exception e) {
            System.out.println("requestExists Error: " + e.getMessage());
        }

        return exists;
    }

    /**
     * Retrieves the file path of the adoption request document.
     *
     * @param user_email The user's email.
     * @param animal_id  The ID of the animal.
     * @return The file path of the adoption request document, or null if not found.
     */
    public static String getPdfPath(String user_email, int animal_id) {
        String pdfPath = null;
        String sql = "SELECT * FROM adoption_requests WHERE user_email = ? AND animal_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user_email);
            statement.setInt(2, animal_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pdfPath = resultSet.getString("path");
            }

        } catch (Exception e) {
            System.out.println("getPdfPath Error: " + e.getMessage());
        }

        return pdfPath;
    }

    public static String getPasswordFromUser(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password");
            }

        } catch (Exception e) {
            System.out.println("getPasswordFromUser Error: " + e.getMessage());
        }

        return null;
    }

}
