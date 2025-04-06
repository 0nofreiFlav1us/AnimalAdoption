import static org.junit.Assert.*;

import com.onofreiflavius.adoptieanimale.model.User;
import org.junit.Before;
import org.junit.Test;
import java.sql.Date;

public class UserTests {

    private static final String EMAIL = "flavius@gmail.com";
    private static final String PASSWORD = "password123";
    private static final String FIRSTNAME = "Onofrei";
    private static final String LASTNAME = "Flavius";
    private static final Date DATE_OF_BIRTH = Date.valueOf("2004-01-01");
    private static final String LIVING_CONDITIONS = "Apartment, no pets allowed";
    private static final String PET_EXPERIENCE = "I have had dogs in the past";
    private static final String MOTIVATION = "Looking for a companion";
    private static final String PHONE_NUMBER = "123-456-7890";

    private User user;

    @Before
    public void setUp() {
        user = new User(EMAIL, PASSWORD);
    }

    @Test
    public void testGetEmail() {
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void testGetFirstname() {
        user.setFirstname(FIRSTNAME);
        assertEquals(FIRSTNAME, user.getFirstname());
    }

    @Test
    public void testSetFirstname() {
        user.setFirstname(FIRSTNAME);
        assertEquals(FIRSTNAME, user.getFirstname());
    }

    @Test
    public void testGetLastname() {
        user.setLastname(LASTNAME);
        assertEquals(LASTNAME, user.getLastname());
    }

    @Test
    public void testSetLastname() {
        user.setLastname(LASTNAME);
        assertEquals(LASTNAME, user.getLastname());
    }

    @Test
    public void testGetDateOfBirth() {
        user.setDateOfBirth(DATE_OF_BIRTH);
        assertEquals(DATE_OF_BIRTH, user.getDateOfBirth());
    }

    @Test
    public void testSetDateOfBirth() {
        user.setDateOfBirth(DATE_OF_BIRTH);
        assertEquals(DATE_OF_BIRTH, user.getDateOfBirth());
    }

    @Test
    public void testGetLivingConditions() {
        user.setLivingConditions(LIVING_CONDITIONS);
        assertEquals(LIVING_CONDITIONS, user.getLivingConditions());
    }

    @Test
    public void testSetLivingConditions() {
        user.setLivingConditions(LIVING_CONDITIONS);
        assertEquals(LIVING_CONDITIONS, user.getLivingConditions());
    }

    @Test
    public void testGetPetExperience() {
        user.setPetExperience(PET_EXPERIENCE);
        assertEquals(PET_EXPERIENCE, user.getPetExperience());
    }

    @Test
    public void testSetPetExperience() {
        user.setPetExperience(PET_EXPERIENCE);
        assertEquals(PET_EXPERIENCE, user.getPetExperience());
    }

    @Test
    public void testGetMotivation() {
        user.setMotivation(MOTIVATION);
        assertEquals(MOTIVATION, user.getMotivation());
    }

    @Test
    public void testSetMotivation() {
        user.setMotivation(MOTIVATION);
        assertEquals(MOTIVATION, user.getMotivation());
    }

    @Test
    public void testGetPhoneNumber() {
        user.setPhoneNumber(PHONE_NUMBER);
        assertEquals(PHONE_NUMBER, user.getPhoneNumber());
    }

    @Test
    public void testSetPhoneNumber() {
        user.setPhoneNumber(PHONE_NUMBER);
        assertEquals(PHONE_NUMBER, user.getPhoneNumber());
    }

    @Test
    public void testUserConstructorWithAllDetails() {
        User newUser = new User(EMAIL, PASSWORD, FIRSTNAME, LASTNAME, DATE_OF_BIRTH, LIVING_CONDITIONS, PET_EXPERIENCE, MOTIVATION, PHONE_NUMBER);

        assertEquals(EMAIL, newUser.getEmail());
        assertEquals(PASSWORD, newUser.getPassword());
        assertEquals(FIRSTNAME, newUser.getFirstname());
        assertEquals(LASTNAME, newUser.getLastname());
        assertEquals(DATE_OF_BIRTH, newUser.getDateOfBirth());
        assertEquals(LIVING_CONDITIONS, newUser.getLivingConditions());
        assertEquals(PET_EXPERIENCE, newUser.getPetExperience());
        assertEquals(MOTIVATION, newUser.getMotivation());
        assertEquals(PHONE_NUMBER, newUser.getPhoneNumber());
    }
}
