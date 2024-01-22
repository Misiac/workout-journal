import React from "react";

interface EditModeContextTypes {

    //workout
    selectedWorkoutId: number;
    setSelectedWorkoutId: React.Dispatch<React.SetStateAction<number>>;
    workoutName: string;
    setWorkoutName: React.Dispatch<React.SetStateAction<string>>;
    workoutDate: string;
    setWorkoutDate: React.Dispatch<React.SetStateAction<string>>;

    //editing
    isEditModeOn: boolean;
    setIsEditModeOn: React.Dispatch<React.SetStateAction<boolean>>;
    deleteTrigger: any;
    wasChangeMade: boolean;
    setWasChangeMade: React.Dispatch<React.SetStateAction<boolean>>;
}

export const WorkoutExplorerContext = React.createContext<EditModeContextTypes | null>(null);