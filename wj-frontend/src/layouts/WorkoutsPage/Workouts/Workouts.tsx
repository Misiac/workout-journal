import {useState} from "react";
import WorkoutsSlider from "./Components/WorkoutsSlider";
import WorkoutExplorer from "./Components/WorkoutExplorer";
import {WorkoutExplorerContext} from '../WorkoutExplorerContext.tsx';
import WorkoutExercise from "../../../models/WorkoutExercise.ts";
import {confirmModal} from "../../Utils/ConfirmModal.tsx";

export const Workouts = () => {

    //workout
    const [selectedWorkoutId, setSelectedWorkoutId] = useState(0);
    const [workoutName, setWorkoutName] = useState('');
    const [workoutDate, setWorkoutDate] = useState('');
    const [exercises, setExercises] = useState<WorkoutExercise[]>([]);

    // edit mode vars
    const [isEditModeOn, setIsEditModeOn] = useState(false);
    const [wasChangeMade, setWasChangeMade] = useState(false);
    const [deletedExercises, setDeletedExercises] = useState<number[]>([])

    //triggers
    const [workoutReloadTrigger, setWorkoutReloadTrigger] = useState(0);
    const [sliderReloadTrigger, setSliderReloadTrigger] = useState(0);

    const handleOpenModal = async (): Promise<boolean> => {

        if (isEditModeOn && wasChangeMade) {
            if (await confirmModal('This will revert changes, are you sure?')) {
                resetChanges();
                return true;
            } else {
                return false;
            }
        }
        setIsEditModeOn(!isEditModeOn);
        return false;
    }
    const resetChanges = () => {
        setWasChangeMade(false);
        setWorkoutReloadTrigger(prev => prev + 1);
        setIsEditModeOn(!isEditModeOn);
    }

    return (
        <WorkoutExplorerContext.Provider value={{
            isEditModeOn,
            setIsEditModeOn,
            selectedWorkoutId,
            setSelectedWorkoutId,
            exercises,
            setExercises,
            workoutName,
            setWorkoutName,
            workoutDate,
            setWorkoutDate,
            wasChangeMade,
            setWasChangeMade,
            deletedExercises,
            setDeletedExercises,
            sliderReloadTrigger,
            setSliderReloadTrigger,
            workoutReloadTrigger,
            setWorkoutReloadTrigger
        }}>
            <div className="flex justify-between">
                <h1 className="py-6 text-3xl font-bold tracking-tight text-gray-900">Workouts</h1>

                <label className='flex cursor-pointer select-none items-center py-6'>
                    <div className='relative'>
                        <input
                            type='checkbox'
                            checked={isEditModeOn}
                            onChange={handleOpenModal}
                            className='sr-only'
                        />
                        <div
                            className={`box block h-8 w-14 rounded-full ${
                                isEditModeOn ? 'bg-regal-blue' : 'bg-black'
                            }`}
                        ></div>
                        <div
                            className={`absolute left-1 top-1 flex h-6 w-6 items-center justify-center rounded-full bg-white transition ${
                                isEditModeOn ? 'translate-x-full' : ''
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
