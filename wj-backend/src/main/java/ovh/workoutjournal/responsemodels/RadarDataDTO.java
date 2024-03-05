package ovh.workoutjournal.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RadarDataDTO {

    private Long chest = 0L;
    private Long shoulders = 0L;
    private Long legs = 0L;
    private Long core = 0L;
    private Long back = 0L;
    private Long arms = 0L;

}