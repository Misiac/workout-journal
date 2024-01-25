import {useState} from "react";
import WorkoutsSlider from "./Components/Slider/WorkoutsSlider.tsx";
import WorkoutExplorer from "./Components/WorkoutExplorer";
import {WorkoutExplorerContext} from '../WorkoutExplorerContext.tsx';
import Workout from "../../../models/Workout.ts";
import {confirmModal} from "../../Utils/ConfirmModal.tsx";

export const Workouts = () => {
    const [state, setState] = useState({
        selectedWorkoutId: 0,
        workout: null as Workout | null,
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
                <label className='flex cursor-pointer select-none items-center py-6'>
                    <div className='relative'>
                        <input
                            type='checkbox'
                            checked={state.isEditModeOn}
                            onChange={handleOpenModal}
                            className='sr-only'
                        />
                        <div
                            className={`box block h-8 w-14 rounded-full ${
                                state.isEditModeOn ? 'bg-regal-blue' : 'bg-black'
                            }`}
                        ></div>
                        <div
                            className={`absolute left-1 top-1 flex h-6 w-6 items-center justify-center rounded-full bg-white transition ${
                                state.isEditModeOn ? 'translate-x-full' : ''
                            }`}
                        ></div>
                    </div>
                    <span className="px-3 text-sm font-bold">EDIT MODE</span>
                </label>
            </div>
            <div className="mx-auto flex flex-row h-[60vh]">
                <WorkoutsSlider handleOpenModal={handleOpenModal}/>
                <WorkoutExplorer/>
            </div>
        </WorkoutExplorerContext.Provider>
    );
}
export default Workouts;