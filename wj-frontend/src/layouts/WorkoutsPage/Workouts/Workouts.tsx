import {useState} from "react";
import WorkoutsSlider from "./Components/WorkoutsSlider";
import WorkoutExplorer from "./Components/WorkoutExplorer";
import {EditModeContext} from '../../Utils/EditModeContext.tsx';

export const Workouts = () => {

    const [selected, setSelected] = useState(0);
    const [workoutName, setWorkoutName] = useState('');
    const [workoutDate, setWorkoutDate] = useState('');

    // edit mode vars
    const [isEditModeOn, setIsEditModeOn] = useState(false);


    const handleCheckboxChange = () => {
        setIsEditModeOn(!isEditModeOn)
    }

    return (
        <EditModeContext.Provider value={{isEditModeOn, setIsEditModeOn}}>
            <div className="flex justify-between">
                <h1 className="py-6 text-3xl font-bold tracking-tight text-gray-900">Workouts</h1>

                <label className='flex cursor-pointer select-none items-center py-6'>
                    <div className='relative'>
                        <input
                            type='checkbox'
                            checked={isEditModeOn}
                            onChange={handleCheckboxChange}
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

                <WorkoutsSlider selected={selected} setSelected={setSelected} setWorkoutName={setWorkoutName}
                                setWorkoutDate={setWorkoutDate}/>

                <WorkoutExplorer selected={selected} workoutName={workoutName} workoutDate={workoutDate}/>
            </div>
        </EditModeContext.Provider>
    );
}
export default Workouts;
