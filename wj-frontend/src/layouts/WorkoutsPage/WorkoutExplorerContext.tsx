import React from "react";
import WorkoutExercise, {WorkoutExerciseSet} from "../../models/WorkoutExercise.ts";

interface WorkoutExplorerContextTypes {
    selectedWorkoutId: number;
    workoutName: string;
    workoutDate: string;
    exercises: WorkoutExercise[];
    isEditModeOn: boolean;
    wasChangeMade: boolean;
    deletedExercises: number[];
    editedExercises: WorkoutExerciseSet[]
    workoutReloadTrigger: number;
    sliderReloadTrigger: number;
    setState: React.Dispatch<React.SetStateAction<{
        selectedWorkoutId: number;
        workoutName: string;
        workoutDate: string;
        exercises: WorkoutExercise[];
        isEditModeOn: boolean;
        wasChangeMade: boolean;
        deletedExercises: number[];
        editedExercises: WorkoutExerciseSet[];
        workoutReloadTrigger: number;
        sliderReloadTrigger: number;
    }>>;
}

export const WorkoutExplorerContext = React.createContext<WorkoutExplorerContextTypes | null>(null);