package com.misiac.workoutjournal.util;

import com.misiac.workoutjournal.entity.MuscleGroupCategory;
import com.misiac.workoutjournal.responsemodels.RadarDataDTO;
import org.springframework.stereotype.Component;

@Component
public class RadarAllocator {
    public void incrementRadarCategory(RadarDataDTO radarDataDTO, MuscleGroupCategory cat, Integer reps) {

        switch (cat.getName()) {
            case "Chest":
                radarDataDTO.setChest(radarDataDTO.getChest() + reps);
                break;
            case "Shoulders", "Traps":
                radarDataDTO.setShoulders(radarDataDTO.getShoulders() + reps);
                break;
            case "Calves", "Glutes", "Hamstrings", "Quads":
                radarDataDTO.setLegs(radarDataDTO.getLegs() + reps);
                break;
            case "Abs", "Obliques":
                radarDataDTO.setCore(radarDataDTO.getCore() + reps);
                break;
            case "Lats", "Lower Back":
                radarDataDTO.setBack(radarDataDTO.getBack() + reps);
                break;
            case "Biceps", "Forearms", "Triceps":
                radarDataDTO.setArms(radarDataDTO.getArms() + reps);
                break;
        }
    }
}
