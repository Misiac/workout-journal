import React from "react";
import WorkoutsSlider from "./Components/WorkoutsSlider";
import WorkoutExplorer from "./Components/WorkoutExplorer";

export const Workouts = () => {
    return (
        <>
            <h1 className="text-3xl font-bold tracking-tight text-gray-900 py-6">Workouts</h1>
            <div className="flex flex-row mx-auto h-[60vh]">

                <WorkoutsSlider/>
                <WorkoutExplorer/>
            </div>
        </>
    );
}
export default Workouts;
