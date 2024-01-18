import React, {useState} from "react";
import WorkoutsSlider from "./Components/WorkoutsSlider";
import WorkoutExplorer from "./Components/WorkoutExplorer";

export const Workouts = () => {

    const [selected, setSelected] = useState(0);

    const [isChecked, setIsChecked] = useState(false)

    const handleCheckboxChange = () => {
        setIsChecked(!isChecked)
    }

    return (
        <>
            <div className="flex justify-between">
                <h1 className="text-3xl font-bold tracking-tight text-gray-900 py-6">Workouts</h1>

                <label className='flex cursor-pointer select-none items-center py-6'>
                    <div className='relative'>
                        <input
                            type='checkbox'
                            checked={isChecked}
                            onChange={handleCheckboxChange}
                            className='sr-only'
                        />
                        <div
                            className={`box block h-8 w-14 rounded-full ${
                                isChecked ? 'bg-regal-blue' : 'bg-black'
                            }`}
                        ></div>
                        <div
                            className={`absolute left-1 top-1 flex h-6 w-6 items-center justify-center rounded-full bg-white transition ${
                                isChecked ? 'translate-x-full' : ''
                            }`}
                        ></div>
                    </div>
                    <span className="px-3 font-bold text-sm">EDIT MODE</span>
                </label>

            </div>


            <div className="flex flex-row mx-auto h-[60vh]">

                <WorkoutsSlider selected={selected} setSelected={setSelected}/>

                <WorkoutExplorer selected={selected}/>
            </div>
        </>
    );
}
export default Workouts;
