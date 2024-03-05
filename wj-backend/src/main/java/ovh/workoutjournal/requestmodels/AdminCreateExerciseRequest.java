package ovh.workoutjournal.requestmodels;

import ovh.workoutjournal.util.MessageProvider;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdminCreateExerciseRequest {

    @NotBlank(message = MessageProvider.REQUEST_NAME_BLANK)
    private String name;
    private List<String> equipmentCategories;
    private List<MuscleGroupRequest> muscleGroups;


    @Getter
    @AllArgsConstructor
    public static class MuscleGroupRequest {

        private String name;
        private Boolean isPrimary;

    }
}
