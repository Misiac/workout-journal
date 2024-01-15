import React from "react";
import WorkoutsSlider from "./Components/WorkoutsSlider";
import WorkoutExplorer from "./Components/WorkoutExplorer";

export const Workouts = () => {
    return (
        <>
            <h1 className="text-3xl font-bold tracking-tight text-gray-900 py-6">Workouts</h1>
            <div className="container mx-auto mt-8 h-[50vh] flex flex-row border-1 border-slate-100">
                <WorkoutsSlider/>
                <div className="flex-grow flex justify-center border-2 border-red-500">
                    <WorkoutExplorer/>
                </div>
            </div>
        </>
    );

}
export default Workouts;
