package ovh.workoutjournal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ovh.workoutjournal.util.MessageProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "workout_exercise_sets")
public class WorkoutExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonBackReference
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExercise parentWorkoutExercise;

    @NotNull(message = MessageProvider.LOAD_NULL)
    @Positive(message = MessageProvider.LOAD_NEGATIVE)
    @Column(name = "`load`", nullable = false)
    private Float load;

    @NotNull(message = MessageProvider.REPS_NULL)
    @Positive(message = MessageProvider.REPS_NEGATIVE)
    @Column(name = "reps", nullable = false)
    private Integer reps;

    @NotNull(message = MessageProvider.SET_NUMBER_NULL)
    @Positive(message = MessageProvider.SET_NUMBER_NEGATIVE)
    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExerciseSet that = (WorkoutExerciseSet) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}