package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.responsemodels.ExerciseTinyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }


    public List<ExerciseTinyDTO> getAllExercisesTiny() {

        var exercises = exerciseRepository.findAll();
        List<ExerciseTinyDTO> returnList = new LinkedList<>();
        exercises.forEach(exercise -> {
            ExerciseTinyDTO exerciseTinyDTO = new ExerciseTinyDTO(
                    exercise.getId(),
                    exercise.getName()
            );
            returnList.add(exerciseTinyDTO);
        });

        return returnList;
    }
}
