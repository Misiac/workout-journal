package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.EquipmentCategory;
import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.MuscleGroup;
import com.misiac.workoutjournal.entity.MuscleGroupCategory;
import com.misiac.workoutjournal.exception.EntityAlreadyExistsException;
import com.misiac.workoutjournal.exception.EntityDoesNotExistException;
import com.misiac.workoutjournal.exception.IncorrectInputException;
import com.misiac.workoutjournal.mapper.ExerciseMapper;
import com.misiac.workoutjournal.repository.EquipmentCategoryRepository;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.MuscleGroupCategoryRepository;
import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest.MuscleGroupRequest;
import static com.misiac.workoutjournal.util.MessageProvider.*;

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

    public void addMuscleGroupCategory(String newCategoryName) {

        muscleGroupCategoryRepository.findMuscleGroupCategoryByName(newCategoryName).ifPresent(mgc -> {
            throw new EntityAlreadyExistsException(MUSCLE_GROUP_ALREADY_EXISTS);
        });

        var newCategory = new MuscleGroupCategory(newCategoryName);
        muscleGroupCategoryRepository.save(newCategory);
    }

    public void addEquipmentCategory(String newCategoryName) {

        equipmentCategoryRepository.findEquipmentCategoryByName(newCategoryName).ifPresent(ec -> {
            throw new EntityAlreadyExistsException(EQUIPMENT_CATEGORY_ALREADY_EXISTS);
        });

        var newCategory = new EquipmentCategory(newCategoryName);
        equipmentCategoryRepository.save(newCategory);
    }

    public void addExercise(AdminCreateExerciseRequest adminExerciseRequest) {
        exerciseRepository.findExerciseByName(adminExerciseRequest.getName()).ifPresent(e -> {
            throw new EntityAlreadyExistsException(EXERCISE_ALREADY_EXISTS);
        });

        if (adminExerciseRequest.getName() == null) {
            throw new IncorrectInputException(REQUEST_NAME_BLANK);
        }

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

    public void bindEquipmentCategory(Long exerciseId, String categoryName) {

        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));

        EquipmentCategory category = equipmentCategoryRepository.findEquipmentCategoryByName(categoryName)
                .orElseThrow(() -> new EntityDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        if (exercise.getEquipmentCategories().contains(category)) {
            throw new EntityAlreadyExistsException(EXERCISE_ALREADY_HAS_THIS_CAT);
        }
        exercise.getEquipmentCategories().add(category);
        exerciseRepository.save(exercise);
    }

    public void unbindEquipmentCategory(Long exerciseId, String categoryName) {

        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));

        EquipmentCategory category = equipmentCategoryRepository.findEquipmentCategoryByName(categoryName)
                .orElseThrow(() -> new EntityDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        if (!exercise.getEquipmentCategories().contains(category)) {
            throw new EntityDoesNotExistException(EXERCISE_DOES_NOT_HAVE_THIS_CAT);
        }
        exercise.getEquipmentCategories().remove(category);
        exerciseRepository.save(exercise);
    }

    public void bindMuscleCategory(Long exerciseId, String categoryName, boolean isPrimary) {

        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));

        MuscleGroupCategory category = muscleGroupCategoryRepository.findMuscleGroupCategoryByName(categoryName)
                .orElseThrow(() -> new EntityDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        MuscleGroup muscleGroup = constructMuscleGroup(category, isPrimary, exercise);

        if (exercise.getMuscleGroups().contains(muscleGroup)) {
            throw new EntityAlreadyExistsException(EXERCISE_ALREADY_HAS_THIS_CAT);
        }
        exercise.getMuscleGroups().add(muscleGroup);
        exerciseRepository.save(exercise);
    }

    public void unbindMuscleCategory(Long exerciseId, String categoryName) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));

        MuscleGroupCategory category = muscleGroupCategoryRepository.findMuscleGroupCategoryByName(categoryName)
                .orElseThrow(() -> new EntityDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        MuscleGroup muscleGroup = constructMuscleGroup(category, null, exercise);

        if (!exercise.getMuscleGroups().contains(muscleGroup)) {
            throw new EntityDoesNotExistException(EXERCISE_DOES_NOT_HAVE_THIS_CAT);
        }
        exercise.getMuscleGroups().remove(muscleGroup);
        exerciseRepository.save(exercise);
    }

    public static MuscleGroup constructMuscleGroup(MuscleGroupCategory mgc, Boolean isPrimary, Exercise exercise) {
        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setCategory(mgc);
        if (isPrimary != null) {
            muscleGroup.setIsPrimary((byte) (isPrimary ? 1 : 0));
        }
        muscleGroup.setExercise(exercise);
        return muscleGroup;
    }
}