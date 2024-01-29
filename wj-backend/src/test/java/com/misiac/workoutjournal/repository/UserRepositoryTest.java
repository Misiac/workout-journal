package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutRepository workoutRepository;

    public static final String TEST_EMAIL = "test@email.com";

    @Test
    @DisplayName("Saving user with Identity Generation ID")
    void testSaveUser() {

        User user = new User();
        user.setEmail(TEST_EMAIL);
        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).orElse(null);

        assertNotNull(savedUser);
        assertTrue(user.getId() > 0);
        assertEquals(TEST_EMAIL, savedUser.getEmail());
    }

    @Test
    @DisplayName("Saving user with duplicate email")
    void testSaveUserWithDuplicateEmail() {

        User user1 = new User();
        user1.setEmail(TEST_EMAIL);
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail(TEST_EMAIL);

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user2));
    }

    @Test
    @DisplayName("Find user by email")
    void testFindUserByEmail() {

        User user = new User();
        user.setEmail(TEST_EMAIL);
        userRepository.save(user);

        User foundUser = userRepository.findUserByEmail(TEST_EMAIL).get();

        assertNotNull(foundUser);
        assertEquals(TEST_EMAIL, foundUser.getEmail());
    }

    @Test
    @DisplayName("Cascade: delete user and its workouts")
    void testCheckWorkoutsOnUserDelete() {

        User user = new User();
        user.setEmail(TEST_EMAIL);
        userRepository.save(user);

        Workout workout = new Workout();
        workout.setDate(LocalDateTime.now());
        workout.setUser(user);
        user.getWorkouts().add(workout);
        workoutRepository.save(workout);

        userRepository.delete(user);

        assertThrows(NoSuchElementException.class,
                () -> userRepository.findUserByEmail(TEST_EMAIL).get()
        );

        assertNull(workoutRepository.findById(workout.getId()).orElse(null));
    }
}