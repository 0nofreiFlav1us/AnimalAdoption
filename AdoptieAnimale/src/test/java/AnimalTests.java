import static org.junit.Assert.*;

import com.onofreiflavius.adoptieanimale.model.Animal;
import org.junit.Before;
import org.junit.Test;

public class AnimalTests {

    private static final String IMAGE_PATH = "path/to/image.jpg";
    private static final String SPECIES = "Dog";
    private static final String BREED = "Labrador";
    private static final int AGE = 5;
    private static final String GENDER = "Male";
    private static final String SIZE = "Medium";
    private static final String DESCRIPTION = "Friendly and playful";
    private static final String ANIMAL_ID = "AD12345";
    private static final int ID = 1;

    private Animal animal;

    @Before
    public void setUp() {
        animal = new Animal(ID, IMAGE_PATH, SPECIES, BREED, AGE, GENDER, SIZE, DESCRIPTION, ANIMAL_ID);
    }

    @Test
    public void testGetId() {
        assertEquals(ID, animal.getId());
    }

    @Test
    public void testGetImagePath() {
        assertEquals(IMAGE_PATH, animal.getImagePath());
    }

    @Test
    public void testGetSpecies() {
        assertEquals(SPECIES, animal.getSpecies());
    }

    @Test
    public void testGetBreed() {
        assertEquals(BREED, animal.getBreed());
    }

    @Test
    public void testGetAge() {
        assertEquals(AGE, animal.getAge());
    }

    @Test
    public void testGetGender() {
        assertEquals(GENDER, animal.getGender());
    }

    @Test
    public void testGetSize() {
        assertEquals(SIZE, animal.getSize());
    }

    @Test
    public void testGetDescription() {
        assertEquals(DESCRIPTION, animal.getDescription());
    }

    @Test
    public void testGetAnimalID() {
        assertEquals(ANIMAL_ID, animal.getAnimalID());
    }

}
