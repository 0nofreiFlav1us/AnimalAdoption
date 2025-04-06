package com.onofreiflavius.adoptieanimale.model;

import java.sql.Date;

/**
 * The {@code User} class represents a user in the adoption system.
 * It stores details about the user's personal information, contact details,
 * and motivations related to adopting a pet.
 *
 * @author Onofrei Flavius
 */
public class User {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String livingConditions;
    private String petExperience;
    private String motivation;
    private String phoneNumber;

    /**
     * Constructor to create a user with email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor to create a user with all details.
     *
     * @param email           The user's email address.
     * @param password        The user's password.
     * @param firstname       The user's first name.
     * @param lastname        The user's last name.
     * @param dateOfBirth     The user's date of birth.
     * @param livingConditions The user's living conditions.
     * @param petExperience   The user's experience with pets.
     * @param motivation      The user's motivation for adopting a pet.
     * @param phoneNumber     The user's phone number.
     */
    public User(String email, String password, String firstname, String lastname, Date dateOfBirth,
                String livingConditions, String petExperience, String motivation, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.livingConditions = livingConditions;
        this.petExperience = petExperience;
        this.motivation = motivation;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters

    /**
     * Gets the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstname The first name to set.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastname The last name to set.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets the date of birth of the user.
     *
     * @return The date of birth of the user.
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the user.
     *
     * @param dateOfBirth The date of birth to set.
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the living conditions of the user.
     *
     * @return The living conditions of the user.
     */
    public String getLivingConditions() {
        return livingConditions;
    }

    /**
     * Sets the living conditions of the user.
     *
     * @param livingConditions The living conditions to set.
     */
    public void setLivingConditions(String livingConditions) {
        this.livingConditions = livingConditions;
    }

    /**
     * Gets the pet experience of the user.
     *
     * @return The pet experience of the user.
     */
    public String getPetExperience() {
        return petExperience;
    }

    /**
     * Sets the pet experience of the user.
     *
     * @param petExperience The pet experience to set.
     */
    public void setPetExperience(String petExperience) {
        this.petExperience = petExperience;
    }

    /**
     * Gets the motivation of the user for adopting a pet.
     *
     * @return The motivation of the user.
     */
    public String getMotivation() {
        return motivation;
    }

    /**
     * Sets the motivation of the user for adopting a pet.
     *
     * @param motivation The motivation to set.
     */
    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return The phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber The phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
