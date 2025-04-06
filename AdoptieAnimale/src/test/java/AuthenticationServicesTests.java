import com.onofreiflavius.adoptieanimale.controller.authentication.AuthenticationServices;
import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;

public class AuthenticationServicesTests {

    @Test
    public void testGetEmailFromSession() throws SQLException {
        AuthenticationServices.createSession("flavius@gmail.com", "password");

        String email = AuthenticationServices.getEmailFromSession();

        assertEquals("flavius@gmail.com", email);
    }

    @Test
    public void testGetPasswordFromSession() throws SQLException {
        AuthenticationServices.createSession("flavius@gmail.com", "password");

        String password = AuthenticationServices.getPasswordFromSession();

        assertEquals("password", password);
    }

    @Test
    public void testGetFirstnameFromSession() throws SQLException {
        AuthenticationServices.createSession("flavius@gmail.com", "password");
        AuthenticationServices.updateUser("Onofrei", "Flavius", null, null, null, null, null);

        String firstname = AuthenticationServices.getFirstnameFromSession();

        assertEquals("Onofrei", firstname);
    }

    @Test
    public void testGetLastnameFromSession() throws SQLException {
        DatabaseServices.establishConnection();
        AuthenticationServices.createSession("flavius@gmail.com", "password");
        AuthenticationServices.updateUser("Onofrei", "Flavius", null, null, null, null, null);

        String lastname = AuthenticationServices.getLastnameFromSession();

        assertEquals("Flavius", lastname);
    }

    @Test
    public void testGetDateOfBirthFromSession() throws SQLException {
        AuthenticationServices.createSession("flavius@gmail.com", "password");
        Date dob = Date.valueOf("1990-01-01");
        AuthenticationServices.updateUser("Onofrei", "Flavius", dob, null, null, null, null);

        Date dateOfBirth = AuthenticationServices.getDateOfBirthFromSession();

        assertEquals(dob, dateOfBirth);
    }

    @Test
    public void testGetLivingConditionsFromSession() throws SQLException {
        AuthenticationServices.createSession("flavius@gmail.com", "password");
        AuthenticationServices.updateUser("Onofrei", "Flavius", null, "Apartment", null, null, null);

        String livingConditions = AuthenticationServices.getLivingConditionsFromSession();

        assertEquals("Apartment", livingConditions);
    }

    @Test
    public void testGetMotivationFromSession() throws SQLException {
        AuthenticationServices.createSession("flavius@gmail.com", "password");
        AuthenticationServices.updateUser("Onofrei", "Flavius", null, null, null, "Adoption", null);

        String motivation = AuthenticationServices.getMotivationFromSession();

        assertEquals("Adoption", motivation);
    }

    @Test
    public void testGetPhoneNumberFromSession() throws SQLException {
        AuthenticationServices.createSession("flavius@gmail.com", "password");
        AuthenticationServices.updateUser("Onofrei", "Flavius", null, null, null, null, "123456789");

        String phoneNumber = AuthenticationServices.getPhoneNumberFromSession();

        assertEquals("123456789", phoneNumber);
    }

    @Test
    public void testDataIsValid() {
        boolean isValid = AuthenticationServices.dataIsValid("flavius@gmail.com", "password", "password");

        assertTrue(isValid);
    }

    @Test
    public void testDataIsValidInvalidEmail() {
        boolean isValid = AuthenticationServices.dataIsValid("invalid-email", "password", "password");

        assertFalse(isValid);
    }

    @Test
    public void testDataIsValidNonMatchingPasswords() {
        boolean isValid = AuthenticationServices.dataIsValid("flavius@gmail.com", "password", "differentPassword");

        assertFalse(isValid);
    }

    @Test
    public void testRemoveSession() throws SQLException {
        AuthenticationServices.createSession("flavius@gmail.com", "password");
        AuthenticationServices.removeSession();

        assertNull(AuthenticationServices.getUser());
    }
}
