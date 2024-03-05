package ovh.workoutjournal.repository;

import ovh.workoutjournal.entity.WorkoutExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface WorkoutExerciseSetRepository extends JpaRepository<WorkoutExerciseSet, Long> {
}