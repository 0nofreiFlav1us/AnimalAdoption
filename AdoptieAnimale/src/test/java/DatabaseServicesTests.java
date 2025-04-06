import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.onofreiflavius.adoptieanimale.model.Animal;
import com.onofreiflavius.adoptieanimale.model.User;
import com.onofreiflavius.adoptieanimale.controller.database.DatabaseServices;
import org.junit.Test;

import java.util.List;

public class DatabaseServicesTests {

    private static final String TEST_EMAIL = "flavius@gmail.com";
    private static final String TEST_PASSWORD = "123";
    private static final String TEST_FIRST_NAME = "Onofrei";
    private static final String TEST_LAST_NAME = "Flavius";

    @Test
    public void testAddUser() {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);
        DatabaseServices.addUser(user);

        assertFalse(DatabaseServices.userExists(user));
    }

    @Test
    public void testUserExists() {
        DatabaseServices.establishConnection();

        User user = new User(TEST_EMAIL, TEST_PASSWORD);
        boolean existsBefore = DatabaseServices.userExists(user);

        assertTrue(DatabaseServices.userExists(user));
    }

    @Test
    public void testGetAllAnimals() {
        DatabaseServices.establishConnection();
        List<Animal> animals = DatabaseServices.getAllAnimals();
        assertNotNull(animals);
        assertTrue(animals.size() > 0); // Assuming there are animals in the database
    }

    @Test
    public void testGetPdfPath() {
        DatabaseServices.establishConnection();
        int animalId = 1; // Assuming an animal ID
        String expectedPath = null;
        DatabaseServices.adoptionRequest(TEST_EMAIL, animalId, expectedPath);

        String actualPath = DatabaseServices.getPdfPath(TEST_EMAIL, animalId);

        assertEquals(expectedPath, actualPath);
    }
}
