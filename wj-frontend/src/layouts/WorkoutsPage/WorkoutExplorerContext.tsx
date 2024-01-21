import React from "react";

interface EditModeContextTypes {
    isEditModeOn: boolean;
    setIsEditModeOn: React.Dispatch<React.SetStateAction<boolean>>;
    selectedWorkoutId: number;
    setSelectedWorkoutId: React.Dispatch<React.SetStateAction<number>>;
    workoutName: string;
    setWorkoutName: React.Dispatch<React.SetStateAction<string>>;
    workoutDate: string;
    setWorkoutDate: React.Dispatch<React.SetStateAction<string>>;

}

export const WorkoutExplorerContext = React.createContext<EditModeContextTypes | null>(null);