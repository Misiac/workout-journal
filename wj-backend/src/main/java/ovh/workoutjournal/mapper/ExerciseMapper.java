package ovh.workoutjournal.mapper;

import ovh.workoutjournal.entity.EquipmentCategory;
import ovh.workoutjournal.entity.ExerciseType;
import ovh.workoutjournal.entity.MuscleGroup;
import ovh.workoutjournal.repository.EquipmentCategoryRepository;
import ovh.workoutjournal.repository.MuscleGroupCategoryRepository;
import ovh.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
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

    public ExerciseType toExercise(AdminCreateExerciseRequest adminExerciseRequest) {
        ExerciseType exercise = new ExerciseType();

        exercise.setName(adminExerciseRequest.getName());
        Set<EquipmentCategory> equipmentCategories = new LinkedHashSet<>();
        Set<MuscleGroup> muscleGroups = new LinkedHashSet<>();

        for (String equipmentCategoryName : adminExerciseRequest.getEquipmentCategories()) {
            var equipmentCategory = equipmentCategoryRepository.findEquipmentCategoryByName(equipmentCategoryName);
            equipmentCategories.add(equipmentCategory.orElseThrow());
        }
        exercise.setEquipmentCategories(equipmentCategories);

        for (AdminCreateExerciseRequest.MuscleGroupRequest muscleGroupRequest : adminExerciseRequest.getMuscleGroups()) {

            MuscleGroup muscleGroup = new MuscleGroup();
            String requestCategoryName = muscleGroupRequest.getName();
            var muscleGroupCategory = muscleGroupCategoryRepository.findMuscleGroupCategoryByName(requestCategoryName)
                    .orElseThrow();

            muscleGroup.setCategory(muscleGroupCategory);
            muscleGroup.setIsPrimary((byte) (muscleGroupRequest.getIsPrimary() ? 1 : 0));
            muscleGroup.setExercise(exercise);
            muscleGroups.add(muscleGroup);
        }
        exercise.setMuscleGroups(muscleGroups);

        return exercise;
    }
}
