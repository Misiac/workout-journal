import React, {useState} from "react";
import WorkoutsSlider from "./Components/WorkoutsSlider";
import WorkoutExplorer from "./Components/WorkoutExplorer";

export const Workouts = () => {

    const [selected, setSelected] = useState(0);

    return (
        <>
            <h1 className="text-3xl font-bold tracking-tight text-gray-900 py-6">Workouts</h1>
            <div className="flex flex-row mx-auto h-[60vh]">

                <WorkoutsSlider selected={selected} setSelected={setSelected}/>

                <WorkoutExplorer selected={selected}/>
            </div>
        </>
    );
}
export default Workouts;
