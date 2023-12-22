package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void testSaveWorkoutBlank() {

        User user = new User();
        user.setEmail("test@email.com");

        Workout workout = Workout.builder()
                .user(user)
                .date(LocalDateTime.now())
                .build();
        userRepository.save(user);
        workoutRepository.save(workout);

        Assertions.assertNotNull(workout);
        Assertions.assertTrue(workout.getId() > 0);
    }

}