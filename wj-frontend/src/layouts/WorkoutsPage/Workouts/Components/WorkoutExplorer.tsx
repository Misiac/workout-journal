import Exercise from "./Exercise";
import React, {useContext, useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {WorkoutExercise, WorkoutExerciseSet} from "../../../../models/Workout.ts";
import WorkoutTotals from "./WorkoutTotals.tsx";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import EditorOptions from "./EditorOptions.tsx";
import LogNewExercise from "./EditMode/LogNewExercise.tsx";
import {formatDate} from "../../../Utils/DateFormatter.ts";
import {Edit} from "lucide-react";


export const WorkoutExplorer = () => {

    const {authState} = useOktaAuth();

    const context = useContext(WorkoutExplorerContext);

    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const {selectedWorkoutId, workout, isEditModeOn, setState} = context; //todo

    const [totals, setTotals] = useState({totalExercises: 0, totalSets: 0, totalReps: 0, tvl: 0});
    const [isEditing, setIsEditing] = useState(false);

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

    useEffect(() => {
        setIsEditing(false);
    }, [isEditModeOn,workout]);

    const changeWorkoutName = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.value.length <= 50) {
            const newName = e.target.value;
            setState(prevState => ({
                ...prevState,
                workout: prevState.workout ? {
                    ...prevState.workout,
                    name: newName
                } : null,
                wasChangeMade: true
            }));
        }
    };

    const changeWorkoutDate = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newDate = e.target.value;
        setState(prevState => ({
            ...prevState,
            workout: prevState.workout ? {
                ...prevState.workout,
                date: newDate
            } : null,
            wasChangeMade: true
        }));
    };

    return (
        <>
            <div className="w-full overflow-y-auto px-2">
                {workout &&
                    <div className="flex w-full items-start gap-2 py-4 h-[100px] fade-animation">

                        <div className='w-1/2 flex flex-col items-start'>
                            {isEditModeOn && isEditing ? (
                                <>
                                    <input
                                        className='text-2xl py-1 font-bold w-full border border-slate-500 rounded-lg shadow-md focus:outline-none focus:ring-2 focus:ring-slate-500'
                                        value={workout.name}
                                        onChange={(e) => changeWorkoutName(e)}
                                    />
                                    <div className='flex items-center'>
                                        <input
                                            type='datetime-local'
                                            value={workout.date}
                                            onChange={(e) => changeWorkoutDate(e)}
                                        />
                                        <button onClick={() => setIsEditing(!isEditing)} className='px-2'>
                                            <Edit size={24}/>
                                        </button>
                                    </div>
                                </>
                            ) : (
                                <>
                                    <p className='text-2xl py-1 font-bold'> {workout.name}</p>
                                    <div className='flex items-center'>
                                        <p className='py-1'>{formatDate(workout.date)}</p>
                                        {isEditModeOn && (
                                            <button onClick={() => setIsEditing(!isEditing)} className='px-2'>
                                                <Edit size={24}/>
                                            </button>
                                        )}
                                    </div>
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
                }

                {selectedWorkoutId !== 0 && <hr/>}

                <div className="grid grid-cols-2 gap-y-4 px-4 pt-4">

                    {workout?.workoutExercises.map((exercise) => (
                        <Exercise exercise={exercise} key={exercise.sequenceNumber}/>
                    ))}

                    {isEditModeOn && workout && <LogNewExercise/>}

                </div>
            </div>
        </>
    );
}
export default WorkoutExplorer;