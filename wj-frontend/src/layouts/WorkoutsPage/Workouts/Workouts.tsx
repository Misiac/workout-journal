import React, {useState} from "react";
import WorkoutsSlider from "./Components/Slider/WorkoutsSlider.tsx";
import WorkoutExplorer from "./Components/WorkoutExplorer";
import {WorkoutExplorerContext} from '../WorkoutExplorerContext.tsx';
import Workout, {ExerciseType} from "../../../models/Workout.ts";
import {confirmModal} from "../../Utils/ConfirmModal.tsx";
import Toggle from "../../Utils/Toggle.tsx";
import AiPersonalTrainer from "./AI/AiPersonalTrainer.tsx";

export const Workouts: React.FC<{
    reloadStats: number
    setReloadStats: (value: number) => void
}> = (props) => {

    const [state, setState] = useState({
        selectedWorkoutId: 0,
        workout: null as Workout | null,
        exerciseTypes: [] as ExerciseType[],
        isEditModeOn: false,
        wasChangeMade: false,
        workoutReloadTrigger: 0,
        sliderReloadTrigger: 0
    });

    const handleOpenModal = async (): Promise<boolean> => {
        if (state.isEditModeOn && state.wasChangeMade) {
            if (await confirmModal('This will revert changes, are you sure?')) {
                resetChanges();
                return true;
            } else {
                return false;
            }
        }
        setState(prevState => ({...prevState, isEditModeOn: !prevState.isEditModeOn}));
        return false;
    }

    const resetChanges = () => {
        setState(prevState => ({
            ...prevState,
            wasChangeMade: false,
            workoutReloadTrigger: prevState.workoutReloadTrigger + 1,
            isEditModeOn: !prevState.isEditModeOn,
        }));
    }

    return (
        <WorkoutExplorerContext.Provider value={{...state, setState}}>
            <div className="flex justify-between">
                <h1 className="py-6 text-3xl font-bold tracking-tight text-gray-900">Workouts</h1>
                <div className="flex flex-row">
                    <AiPersonalTrainer/>
                    <Toggle isEditModeOn={state.isEditModeOn} handleOpenModal={handleOpenModal}/>
                </div>
            </div>
            <div className="mx-auto flex flex-row h-[60vh]">
                <WorkoutsSlider handleOpenModal={handleOpenModal}/>
                <WorkoutExplorer reloadStats={props.reloadStats} setReloadStats={props.setReloadStats}/>
            </div>
        </WorkoutExplorerContext.Provider>
    );
}
export default Workouts;