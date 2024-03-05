package ovh.workoutjournal.service;

import ovh.workoutjournal.repository.ExerciseTypeRepository;
import ovh.workoutjournal.responsemodels.ExerciseTinyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    public ExerciseService(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public List<ExerciseTinyDTO> getAllExercisesTiny() {

        var exercises = exerciseTypeRepository.findAll();
        List<ExerciseTinyDTO> returnList = new LinkedList<>();
        exercises.forEach(exercise -> {
            ExerciseTinyDTO exerciseTinyDTO = new ExerciseTinyDTO(
                    exercise.getId(),
                    exercise.getName(),
                    exercise.getImage() == null ? null : Base64.getEncoder().encodeToString(exercise.getImage())
            );
            returnList.add(exerciseTinyDTO);
        });

        return returnList;
    }
}
