package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.MuscleGroupCategory;
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
class MuscleGroupCategoryRepositoryTest {

    @Autowired
    MuscleGroupCategoryRepository mgcRepository;


    @Test
    @DisplayName("Saving new category")
    void testSaveNewCategory() {
        MuscleGroupCategory muscleGroupCategory = new MuscleGroupCategory();
        muscleGroupCategory.setName("Chest");

        mgcRepository.save(muscleGroupCategory);
        var savedCategory = mgcRepository.findById(muscleGroupCategory.getId());

        assertTrue(savedCategory.isPresent());
        assertEquals(muscleGroupCategory.getName(), savedCategory.get().getName());
    }

    @Test
    @DisplayName("Find category with name")
    void testFindCategoryWithName() {

        MuscleGroupCategory mgc = new MuscleGroupCategory();
        mgc.setName("Chest");

        mgcRepository.save(mgc);

        var savedCategory = mgcRepository.findMuscleGroupCategoryByName(mgc.getName());

        assertTrue(savedCategory.isPresent());
        assertEquals(mgc.getName(), savedCategory.get().getName());
    }

    @Test
    @DisplayName("Find category with name that does not exist")
    void testFindCategoryWithNameThatDoesNotExist() {

        MuscleGroupCategory mgc = new MuscleGroupCategory();
        mgc.setName("Chest");

        mgcRepository.save(mgc);

        var savedCategory = mgcRepository.findMuscleGroupCategoryByName("Shoulders");

        assertTrue(savedCategory.isEmpty());

    }

    @Test
    @DisplayName("Save category that already exists")
    void testSaveDuplicateNameCategory() {
        MuscleGroupCategory mgc = new MuscleGroupCategory();
        mgc.setName("Chest");
        mgcRepository.save(mgc);

        MuscleGroupCategory duplicateMgc = new MuscleGroupCategory();
        duplicateMgc.setName("Chest");

        assertThrows(DataIntegrityViolationException.class,
                () -> mgcRepository.save(duplicateMgc));
    }
}
