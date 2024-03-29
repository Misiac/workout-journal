package ovh.workoutjournal.repository;

import ovh.workoutjournal.entity.EquipmentCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EquipmentCategoryRepositoryTest {

    @Autowired
    EquipmentCategoryRepository equipmentCategoryRepository;


    @Test
    @DisplayName("Saving new category")
    void testSaveNewCategory() {
        EquipmentCategory equipmentCategory = new EquipmentCategory();
        equipmentCategory.setName("Barbell");

        equipmentCategoryRepository.save(equipmentCategory);
        var savedCategory = equipmentCategoryRepository.findById(equipmentCategory.getId());

        assertTrue(savedCategory.isPresent());
        assertEquals(equipmentCategory.getName(), savedCategory.get().getName());

    }

    @Test
    @DisplayName("Find category with name")
    void testFindCategoryWithName() {
        EquipmentCategory equipmentCategory = new EquipmentCategory();
        equipmentCategory.setName("Barbell");

        equipmentCategoryRepository.save(equipmentCategory);
        var savedCategory = equipmentCategoryRepository.findEquipmentCategoryByName(equipmentCategory.getName());

        assertTrue(savedCategory.isPresent());
        assertEquals(equipmentCategory.getName(), savedCategory.get().getName());
    }

    @Test
    @DisplayName("Find category with name that does not exist")
    void testFindCategoryWithNameThatDoesNotExist() {

        EquipmentCategory equipmentCategory = new EquipmentCategory();
        equipmentCategory.setName("Dumbbell");

        equipmentCategoryRepository.save(equipmentCategory);

        var savedCategory = equipmentCategoryRepository.findEquipmentCategoryByName("Barbell");

        assertTrue(savedCategory.isEmpty());

    }

    @Test
    @DisplayName("Save category that already exists")
    void testSaveDuplicateNameCategory() {
        EquipmentCategory equipmentCategory = new EquipmentCategory();
        equipmentCategory.setName("Barbell");
        equipmentCategoryRepository.save(equipmentCategory);

        EquipmentCategory duplicateCategory = new EquipmentCategory();
        duplicateCategory.setName("Barbell");


        assertThrows(DataIntegrityViolationException.class,
                () -> equipmentCategoryRepository.save(duplicateCategory));
    }
}