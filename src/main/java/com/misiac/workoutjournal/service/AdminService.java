package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.EquipmentCategory;
import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.MuscleGroupCategory;
import com.misiac.workoutjournal.mapper.ExerciseMapper;
import com.misiac.workoutjournal.repository.EquipmentCategoryRepository;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.MuscleGroupCategoryRepository;
import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest.*;

@Service
@Transactional
public class AdminService {
    private final EquipmentCategoryRepository equipmentCategoryRepository;
    private final MuscleGroupCategoryRepository muscleGroupCategoryRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public AdminService(EquipmentCategoryRepository equipmentCategoryRepository, MuscleGroupCategoryRepository muscleGroupCategoryRepository,
                        ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.equipmentCategoryRepository = equipmentCategoryRepository;
        this.muscleGroupCategoryRepository = muscleGroupCategoryRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    public void addMuscleGroupCategory(String newCategoryName) throws Exception {

        var muscleGroupCategory = muscleGroupCategoryRepository.findMuscleGroupCategoryByName(newCategoryName);

        if (muscleGroupCategory.isPresent()) {
            throw new Exception("Muscle Group Category already exists");
        }

        var newCategory = new MuscleGroupCategory(newCategoryName);
        muscleGroupCategoryRepository.save(newCategory);
    }

    public void addEquipmentCategory(String newCategoryName) throws Exception {

        var equipmentCategory = equipmentCategoryRepository.findEquipmentCategoryByName(newCategoryName);

        if (equipmentCategory.isPresent()) {
            throw new Exception("Equipment Category already exists");
        }

        var newCategory = new EquipmentCategory(newCategoryName);
        equipmentCategoryRepository.save(newCategory);
    }

    public void addExercise(AdminCreateExerciseRequest adminExerciseRequest) throws Exception {
        var exercise = exerciseRepository.findExerciseByName(adminExerciseRequest.getName());

        //VALIDATION
        if (exercise.isPresent()) {
            throw new RuntimeException("Exercise already exists");
        }
        if (adminExerciseRequest.getName() == null) throw new Exception("Request's name is blank");
        for (String name : adminExerciseRequest.equipmentCategories) {
            if (equipmentCategoryRepository.findEquipmentCategoryByName(name).isEmpty()) {
                EquipmentCategory equipmentCategory = new EquipmentCategory(name);
                equipmentCategoryRepository.save(equipmentCategory);

            }
        }
        for (MuscleGroupRequest muscleGroupRequest : adminExerciseRequest.getMuscleGroups()) {
            String name = muscleGroupRequest.getName();
            if (muscleGroupCategoryRepository.findMuscleGroupCategoryByName(name).isEmpty()) {
                MuscleGroupCategory muscleGroupCategory = new MuscleGroupCategory(name);
                muscleGroupCategoryRepository.save(muscleGroupCategory);
            }
        }

        Exercise newExercise = exerciseMapper.toExercise(adminExerciseRequest);
        exerciseRepository.save(newExercise);
    }
}
