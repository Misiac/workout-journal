import React from "react";
import Workout from "../../models/Workout.ts";

interface WorkoutExplorerContextTypes {
    selectedWorkoutId: number;
    workout: Workout | null,
    isEditModeOn: boolean;
    wasChangeMade: boolean;
    workoutReloadTrigger: number;
    sliderReloadTrigger: number;
    setState: React.Dispatch<React.SetStateAction<{
        selectedWorkoutId: number;
        workout: Workout | null;
        isEditModeOn: boolean;
        wasChangeMade: boolean;
        workoutReloadTrigger: number;
        sliderReloadTrigger: number;
    }>>;
}

export const WorkoutExplorerContext = React.createContext<WorkoutExplorerContextTypes | null>(null);