package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Page<Workout> findWorkoutsByUserEmailOrderByDateDesc(String userEmail, Pageable pageable);

    List<WorkoutTiny> findByUserEmailOrderByDateDesc(String userEmail);


    interface WorkoutTiny {
        Long getId();

        LocalDateTime getDate();

        default String getName() {

            String day = getDate().getDayOfWeek().name().toLowerCase();
            day = day.replace(day.charAt(0), Character.toUpperCase(day.charAt(0)));

            String time = switch (getDate().getHour()) {
                case 0, 1, 2, 3, 4, 5, 6, 22, 23 -> "Night";
                case 7, 8, 9, 10, 11 -> "Morning";
                case 12, 13, 14, 15, 16, 17 -> "Afternoon";
                case 18, 19, 20, 21 -> "Evening";
                default -> "Unknown";
            };
            return day + " " + time + " Workout";
        }
    }
}