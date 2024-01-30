import SliderCard from "./SliderCard.tsx";
import React, {useContext, useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {WorkoutTiny} from "../../../../../models/WorkoutTiny.ts";
import {WorkoutExplorerContext} from "../../../WorkoutExplorerContext.tsx";
import LogNewWorkout from "../EditMode/LogNewWorkout.tsx";
import ProcessingSpinner from "../../../../Utils/ProcessingSpinner.tsx";
import Workout from "../../../../../models/Workout.ts";
import {formatDateToInput, getNameFromDate} from "../../../../Utils/DateUtils.ts";

export const WorkoutsSlider: React.FC<{
    handleOpenModal: () => Promise<boolean>
}> = ({handleOpenModal}) => {
    const {authState} = useOktaAuth();

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }
    const {sliderReloadTrigger, setState} = context;

    const [workouts, setWorkouts] = useState<WorkoutTiny[]>([])
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchWorkoutsTiny = async () => {
            const response = await fetch(`${import.meta.env.VITE_API_ADDRESS}/api/workouts/tiny`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) throw new Error('Something went wrong!');

            setIsLoading(false);
            const data = await response.json();
            setWorkouts(data.map((workoutData: Workout) => new WorkoutTiny(workoutData.id, workoutData.date, workoutData.name)));
        };

        fetchWorkoutsTiny();
        console.log("slider reload");
    }, [authState, sliderReloadTrigger]);

    const addNewWorkout = async () => {
        const dateNow = new Date();
        const newWorkout = new Workout(-1, formatDateToInput(dateNow), getNameFromDate(dateNow), []);

        if (!context.wasChangeMade || await handleOpenModal()) {
            setState(prevState => ({
                ...prevState,
                workout: newWorkout,
                isEditModeOn: true,
                selectedWorkoutId: -1
            }));
        }
    };

    return (
        <div className="h-full w-1/5 overflow-y-auto scroll-container">
            <div className="flex flex-col gap-4">
                <LogNewWorkout addNewWorkout={addNewWorkout}/>
                {isLoading ?
                    <div className='flex h-full justify-center'>
                        <ProcessingSpinner/>
                    </div>
                    :
                    (
                        workouts.map((workout) => (
                            <SliderCard workout={workout} key={workout.id} handleOpenModal={handleOpenModal}/>
                        ))
                    )
                }
            </div>
        </div>
    );
}
export default WorkoutsSlider;