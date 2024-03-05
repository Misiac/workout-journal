package ovh.workoutjournal.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StatsDTO {

    private TotalsDTO totalsDTO;
    private RadarDataDTO radarDataDTO;
}