import Exercise from "./Exercise";
import {useContext, useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {WorkoutExercise, WorkoutExerciseSet} from "../../../../models/Workout.ts";
import WorkoutTotals from "./WorkoutTotals.tsx";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import EditorOptions from "./EditorOptions.tsx";
import LogNewExercise from "./LogNewExercise.tsx";
import {formatDate} from "../../../Utils/DateFormatter.ts";


export const WorkoutExplorer = () => {

    const {authState} = useOktaAuth();

    const context = useContext(WorkoutExplorerContext);

    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const {selectedWorkoutId, workout, isEditModeOn} = context; //todo


    const [totals, setTotals] = useState({totalExercises: 0, totalSets: 0, totalReps: 0, tvl: 0});

    const fetchWorkout = async () => {
        const url = `${import.meta.env.VITE_API_ADDRESS}/api/workout/${selectedWorkoutId}`;
        const requestOptions = {
            method: 'GET',
            headers: {
                Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                'Content-Type': 'application/json'
            }
        };

        const response = await fetch(url, requestOptions);
        if (!response.ok) {
            throw new Error('Something went wrong!');
        }

        const workout = await response.json();

        console.log(workout);
        context.setState(prevState => ({
            ...prevState,
            workout: workout
        }));
    };

    useEffect(() => {
        if (selectedWorkoutId !== 0) {
            fetchWorkout();
            console.log("fetch workout")
        } else {
            context.setState(prevState => ({
                ...prevState,
            }));
        }
    }, [authState, selectedWorkoutId, context.workoutReloadTrigger]);

    useEffect(() => {
        if (selectedWorkoutId !== 0) {
            const totalExercises: number = workout?.workoutExercises.length ?? 0;
            let totalSets: number = 0;
            let totalReps: number = 0;
            let tvl: number = 0;

            workout?.workoutExercises.forEach((exercise: WorkoutExercise) => {
                totalSets += exercise.workoutExerciseSets.length;
                exercise.workoutExerciseSets.forEach((set: WorkoutExerciseSet) => {
                    totalReps += set.reps;
                    tvl += set.reps * set.load;
                })
            })
            setTotals({totalExercises, totalSets, totalReps, tvl});
        }
    }, [context.workout]);

    return (
        <>
            <div className="w-full overflow-y-auto px-2">
                <div className="flex w-full items-start gap-2 py-4 h-[100px] fade-animation">
                    <div className='w-1/2'>
                        {workout && (
                            <>
                                <p className='text-2xl font-bold'> {workout.name}</p>
                                <p> {formatDate(workout.date)}</p>
                            </>
                        )}
                    </div>
                    <div className='h-full w-1/2'>

                        {selectedWorkoutId !== 0 &&
                            (isEditModeOn ? <EditorOptions/> :
                                <WorkoutTotals {...totals}/>)
                        }

                    </div>
                </div>

                {selectedWorkoutId !== 0 && <hr/>}

                <div className="grid grid-cols-2 gap-y-4 px-4 pt-4">

                    {workout?.workoutExercises.map((exercise) => (
                        <Exercise exercise={exercise} key={exercise.sequenceNumber}/>
                    ))}

                    {isEditModeOn && <LogNewExercise/>}

                </div>
            </div>
        </>
    );
}
export default WorkoutExplorer;