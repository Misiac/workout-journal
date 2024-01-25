import SliderCard from "./SliderCard";
import React, {useContext, useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {WorkoutTiny} from "../../../../models/WorkoutTiny";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import LogNewWorkoutSliderCard from "./LogNewWorkoutSliderCard.tsx";

export const WorkoutsSlider: React.FC<{
    handleOpenModal: () => Promise<boolean>
}> = ({handleOpenModal}) => {
    const {authState} = useOktaAuth();

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }
    const {sliderReloadTrigger} = context;

    const [workouts, setWorkouts] = useState<WorkoutTiny[]>([])

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(`${import.meta.env.VITE_API_ADDRESS}/api/workout/tiny`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) throw new Error('Something went wrong!');

            const data = await response.json();
            setWorkouts(data.map((workoutData: any) => new WorkoutTiny(workoutData.id, workoutData.date, workoutData.name)));
        };

        fetchData();
        console.log("slider reload");
    }, [authState, sliderReloadTrigger]);

    return (
        <div className="h-full w-1/5 overflow-y-auto scroll-container">
            <div className="flex flex-col gap-4">
                <LogNewWorkoutSliderCard/>
                {workouts.map((workout) => (
                    <SliderCard workout={workout} key={workout.id} handleOpenModal={handleOpenModal}/>
                ))}
            </div>
        </div>
    );
}
export default WorkoutsSlider;