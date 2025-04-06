package com.onofreiflavius.adoptieanimale.controller.authentication;

import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import com.onofreiflavius.adoptieanimale.model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * The AuthenticationServices class provides functionalities for user authentication
 * and session management. It handles operations such as creating, checking, and removing sessions,
 * and provides methods to retrieve user details.
 *
 * @author Onofrei Flavius
 */
public class AuthenticationServices {

    /**
     * File path where session data (email and password) is stored.
     */
    private static final String sessionFilePath = "/home/onofreiflavius/Documents/UVT/P3/Proiect/p3-proiect-gr6-0nofreiFlav1us/AdoptieAnimale/src/main/resources/session.txt";

    /**
     * The currently authenticated user. Null if no session exists.
     */
    private static User user;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Checks if a valid session exists by reading the session file.
     * Retrieves user details from the database if valid credentials are found.
     *
     * @return true if a session exists; false otherwise.
     */
    public static boolean checkSession() {
        user = null;
        try {
            File sessionFile = new File(sessionFilePath);
            if (sessionFile.exists()) {
                Scanner scanner = new Scanner(sessionFile);

                String email = scanner.nextLine();
                String password = scanner.nextLine();

                if (DatabaseServices.userExists(new User(email, password))) {
                    setUser(email, password);
                } else {
                    removeSession();
                }
            }
        } catch (Exception e) {
            System.out.println("File not found, file is empty or the file does not contain two rows!");
        }

        return user != null;
    }

    /**
     * Creates a session by writing user credentials to the session file.
     * If the user is null, it retrieves the user details from the database.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @throws SQLException if a database error occurs.
     */
    public static void createSession(String email, String password) throws SQLException {
        writeToFile(email, password);
        if (user == null) {
            setUser(email, password);
        }
    }

    /**
     * Builds the current user's profile.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @throws SQLException if a database error occurs.
     */
    public static void setUser(String email, String password) throws SQLException {
        ResultSet userData = DatabaseServices.getUserData(email);
        if (userData.next()) {
            Date dateOfBirth = userData.getDate("dateOfBirth");
            user = new User(email, password, userData.getString("firstname"),
                    userData.getString("lastname"), dateOfBirth, userData.getString("livingconditions"),
                    userData.getString("petexperience"), userData.getString("motivation"),
                    userData.getString("phonenumber"));
        } else {
            user = new User(email, password);
        }
    }

    /**
     * Removes the current session by clearing the session file and resetting the user to null.
     */
    public static void removeSession() {
        writeToFile("", "");
        user = null;
    }

    /**
     * Retrieves the email of the user from the current session.
     *
     * @return The email of the user, or "?" if no session exists.
     */
    public static String getEmailFromSession() {
        return (user != null) ? user.getEmail() : "?";
    }

    /**
     * Retrieves the password of the user from the current session.
     *
     * @return The password of the user, or "?" if no session exists.
     */
    public static String getPasswordFromSession() {
        return (user != null) ? user.getPassword() : "?";
    }

    /**
     * Retrieves the first name of the user from the current session.
     *
     * @return The first name of the user, or "?" if not available.
     */
    public static String getFirstnameFromSession() {
        return (user != null && user.getFirstname() != null) ? user.getFirstname() : "?";
    }

    /**
     * Retrieves the last name of the user from the current session.
     *
     * @return The last name of the user, or "?" if not available.
     */
    public static String getLastnameFromSession() {
        return (user != null && user.getLastname() != null) ? user.getLastname() : "?";
    }

    /**
     * Retrieves the date of birth of the user from the current session.
     *
     * @return The date of birth of the user, or null if not available.
     */
    public static Date getDateOfBirthFromSession() {
        return (user != null && user.getDateOfBirth() != null) ? user.getDateOfBirth() : null;
    }

    /**
     * Retrieves the living conditions of the user from the current session.
     *
     * @return The living conditions of the user, or "?" if not available.
     */
    public static String getLivingConditionsFromSession() {
        return (user != null && user.getLivingConditions() != null) ? user.getLivingConditions() : "?";
    }

    /**
     * Retrieves the pet experience of the user from the current session.
     *
     * @return The pet experience of the user, or "?" if not available.
     */
    public static String getPetExperienceFromSession() {
        return (user != null && user.getPetExperience() != null) ? user.getPetExperience() : "?";
    }

    /**
     * Retrieves the motivation of the user from the current session.
     *
     * @return The motivation of the user, or "?" if not available.
     */
    public static String getMotivationFromSession() {
        return (user != null && user.getMotivation() != null) ? user.getMotivation() : "?";
    }

    /**
     * Retrieves the phone number of the user from the current session.
     *
     * @return The phone number of the user, or "?" if not available.
     */
    public static String getPhoneNumberFromSession() {
        return (user != null && user.getPhoneNumber() != null) ? user.getPhoneNumber() : "?";
    }

    /**
     * Updates the user details in the current session.
     *
     * @param firstname       The user's first name.
     * @param lastname        The user's last name.
     * @param dateOfBirth     The user's date of birth.
     * @param livingConditions The user's living conditions.
     * @param petExperience   The user's pet experience.
     * @param motivation      The user's motivation.
     * @param phoneNumber     The user's phone number.
     */
    public static void updateUser(String firstname, String lastname, Date dateOfBirth,
                                  String livingConditions, String petExperience, String motivation,
                                  String phoneNumber) {
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setDateOfBirth(dateOfBirth);
        user.setLivingConditions(livingConditions);
        user.setPetExperience(petExperience);
        user.setMotivation(motivation);
        user.setPhoneNumber(phoneNumber);
    }

    /**
     * Retrieves the current authenticated user.
     *
     * @return The User object, or null if no session exists.
     */
    public static User getUser() {
        return user;
    }

    /**
     * Writes the given email and password to the session file.
     *
     * @param email    The email to write.
     * @param password The password to write.
     */
    public static void writeToFile(String email, String password) {
        try {
            FileWriter writer = new FileWriter(sessionFilePath);
            writer.write(email + "\n" + password);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Validates the given user data.
     *
     * @param email               The user's email.
     * @param password            The user's password.
     * @param passwordConfirmation Confirmation of the password.
     * @return true if the data is valid; false otherwise.
     */
    public static boolean dataIsValid(String email, String password, String passwordConfirmation) {
        boolean valid = !email.isEmpty() && !password.isEmpty() && !passwordConfirmation.isEmpty();
        if (!email.matches("^.+@.+\\..+$")) {
            valid = false;
        }
        if (!password.equals(passwordConfirmation)) {
            valid = false;
        }

        return valid;
    }
}
