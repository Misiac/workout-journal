package ovh.workoutjournal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ovh.workoutjournal.entity.EquipmentCategory;
import ovh.workoutjournal.entity.ExerciseType;
import ovh.workoutjournal.entity.MuscleGroup;
import ovh.workoutjournal.entity.MuscleGroupCategory;
import ovh.workoutjournal.exception.EntityAlreadyExistsException;
import ovh.workoutjournal.exception.EntityDoesNotExistException;
import ovh.workoutjournal.mapper.ExerciseMapper;
import ovh.workoutjournal.repository.EquipmentCategoryRepository;
import ovh.workoutjournal.repository.ExerciseTypeRepository;
import ovh.workoutjournal.repository.MuscleGroupCategoryRepository;
import ovh.workoutjournal.requestmodels.AdminCreateExerciseRequest;

import static ovh.workoutjournal.requestmodels.AdminCreateExerciseRequest.MuscleGroupRequest;
import static ovh.workoutjournal.util.MessageProvider.*;

@Service
@Transactional
public class AdminService {
    private final EquipmentCategoryRepository equipmentCategoryRepository;
    private final MuscleGroupCategoryRepository muscleGroupCategoryRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public AdminService(EquipmentCategoryRepository equipmentCategoryRepository, MuscleGroupCategoryRepository muscleGroupCategoryRepository,
                        ExerciseTypeRepository exerciseTypeRepository, ExerciseMapper exerciseMapper) {
        this.equipmentCategoryRepository = equipmentCategoryRepository;
        this.muscleGroupCategoryRepository = muscleGroupCategoryRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
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
        exerciseTypeRepository.findExerciseByName(adminExerciseRequest.getName()).ifPresent(e -> {
            throw new EntityAlreadyExistsException(EXERCISE_ALREADY_EXISTS);
        });

        for (String name : adminExerciseRequest.getEquipmentCategories()) {
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

        ExerciseType newExercise = exerciseMapper.toExercise(adminExerciseRequest);
        exerciseTypeRepository.save(newExercise);
    }

    public void bindEquipmentCategory(Long exerciseId, String categoryName) {

        ExerciseType exercise = exerciseTypeRepository.findById(exerciseId).orElseThrow(
                () -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));

        EquipmentCategory category = equipmentCategoryRepository.findEquipmentCategoryByName(categoryName)
                .orElseThrow(() -> new EntityDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        if (exercise.getEquipmentCategories().contains(category)) {
            throw new EntityAlreadyExistsException(EXERCISE_ALREADY_HAS_THIS_CAT);
        }
        exercise.getEquipmentCategories().add(category);
        exerciseTypeRepository.save(exercise);
    }

    public void unbindEquipmentCategory(Long exerciseId, String categoryName) {

        ExerciseType exercise = exerciseTypeRepository.findById(exerciseId).orElseThrow(
                () -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));

        EquipmentCategory category = equipmentCategoryRepository.findEquipmentCategoryByName(categoryName)
                .orElseThrow(() -> new EntityDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        if (!exercise.getEquipmentCategories().contains(category)) {
            throw new EntityDoesNotExistException(EXERCISE_DOES_NOT_HAVE_THIS_CAT);
        }
        exercise.getEquipmentCategories().remove(category);
        exerciseTypeRepository.save(exercise);
    }

    public void bindMuscleCategory(Long exerciseId, String categoryName, boolean isPrimary) {

        ExerciseType exercise = exerciseTypeRepository.findById(exerciseId).orElseThrow(
                () -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));

        MuscleGroupCategory category = muscleGroupCategoryRepository.findMuscleGroupCategoryByName(categoryName)
                .orElseThrow(() -> new EntityDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        MuscleGroup muscleGroup = constructMuscleGroup(category, isPrimary, exercise);

        if (exercise.getMuscleGroups().contains(muscleGroup)) {
            throw new EntityAlreadyExistsException(EXERCISE_ALREADY_HAS_THIS_CAT);
        }
        exercise.getMuscleGroups().add(muscleGroup);
        exerciseTypeRepository.save(exercise);
    }

    public void unbindMuscleCategory(Long exerciseId, String categoryName) {
        ExerciseType exercise = exerciseTypeRepository.findById(exerciseId).orElseThrow(
                () -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));

        MuscleGroupCategory category = muscleGroupCategoryRepository.findMuscleGroupCategoryByName(categoryName)
                .orElseThrow(() -> new EntityDoesNotExistException(CATEGORY_DOES_NOT_EXIST));

        MuscleGroup muscleGroup = constructMuscleGroup(category, null, exercise);

        if (!exercise.getMuscleGroups().contains(muscleGroup)) {
            throw new EntityDoesNotExistException(EXERCISE_DOES_NOT_HAVE_THIS_CAT);
        }
        exercise.getMuscleGroups().remove(muscleGroup);
        exerciseTypeRepository.save(exercise);
    }

    MuscleGroup constructMuscleGroup(MuscleGroupCategory mgc, Boolean isPrimary, ExerciseType exercise) {
        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setCategory(mgc);
        if (isPrimary != null) {
            muscleGroup.setIsPrimary((byte) (isPrimary ? 1 : 0));
        }
        muscleGroup.setExercise(exercise);
        return muscleGroup;
    }
}