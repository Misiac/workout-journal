package ovh.workoutjournal.requestmodels;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiPlanRequest {

    @NotBlank
    private String goal;

    @NotBlank
    private String level;

    @NotBlank
    private String daysPerWeek;

    @NotBlank
    private String gender;

    @NotBlank
    private String weight;

    @NotBlank
    private String age;

    @NotBlank
    private String height;

    private String health;

    @NotBlank
    private String intensity;

    private String specialGoal;
}
