package com.misiac.workoutjournal.mapper;

import com.misiac.workoutjournal.entity.EquipmentCategory;
import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.MuscleGroup;
import com.misiac.workoutjournal.repository.EquipmentCategoryRepository;
import com.misiac.workoutjournal.repository.MuscleGroupCategoryRepository;
import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest.MuscleGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class ExerciseMapper {

    private final EquipmentCategoryRepository equipmentCategoryRepository;
    private final MuscleGroupCategoryRepository muscleGroupCategoryRepository;

    @Autowired
    public ExerciseMapper(EquipmentCategoryRepository equipmentCategoryRepository, MuscleGroupCategoryRepository muscleGroupCategoryRepository) {
        this.equipmentCategoryRepository = equipmentCategoryRepository;
        this.muscleGroupCategoryRepository = muscleGroupCategoryRepository;
    }

    public Exercise toExercise(AdminCreateExerciseRequest adminExerciseRequest) {
        Exercise exercise = new Exercise();

        exercise.setName(adminExerciseRequest.getName());
        List<EquipmentCategory> equipmentCategories = new LinkedList<>();
        Set<MuscleGroup> muscleGroups = new LinkedHashSet<>();

        for (String equipmentCategoryName : adminExerciseRequest.equipmentCategories) {
            var equipmentCategory = equipmentCategoryRepository.findEquipmentCategoryByName(equipmentCategoryName);
            equipmentCategories.add(equipmentCategory.orElseThrow());
        }
        exercise.setEquipmentCategories(equipmentCategories);

        for (MuscleGroupRequest muscleGroupRequest : adminExerciseRequest.getMuscleGroups()) {

            MuscleGroup muscleGroup = new MuscleGroup();
            String requestCategoryName = muscleGroupRequest.getName();
            var muscleGroupCategory = muscleGroupCategoryRepository.findMuscleGroupCategoryByName(requestCategoryName).get();
            muscleGroup.setCategory(muscleGroupCategory);
            muscleGroup.setIsPrimary((byte) (muscleGroupRequest.isPrimary() ? 1 : 0));
            muscleGroup.setExercise(exercise);
            muscleGroups.add(muscleGroup);
        }
        exercise.setMuscleGroups(muscleGroups);

        return exercise;
    }
}
