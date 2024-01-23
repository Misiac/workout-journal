import React from "react";
import WorkoutExercise from "../../models/WorkoutExercise.ts";

interface EditModeContextTypes {

    //workout
    selectedWorkoutId: number;
    setSelectedWorkoutId: React.Dispatch<React.SetStateAction<number>>;
    exercises: WorkoutExercise[];
    setExercises: React.Dispatch<React.SetStateAction<WorkoutExercise[]>>;
    workoutName: string;
    setWorkoutName: React.Dispatch<React.SetStateAction<string>>;
    workoutDate: string;
    setWorkoutDate: React.Dispatch<React.SetStateAction<string>>;

    //editing
    isEditModeOn: boolean;
    setIsEditModeOn: React.Dispatch<React.SetStateAction<boolean>>;
    wasChangeMade: boolean;
    setWasChangeMade: React.Dispatch<React.SetStateAction<boolean>>;
    deletedExercises: number[];
    setDeletedExercises: React.Dispatch<React.SetStateAction<number[]>>;

    //triggers
    sliderReloadTrigger: number;
    setSliderReloadTrigger: React.Dispatch<React.SetStateAction<number>>;
    workoutReloadTrigger: number;
    setWorkoutReloadTrigger: React.Dispatch<React.SetStateAction<number>>;


}

export const WorkoutExplorerContext = React.createContext<EditModeContextTypes | null>(null);