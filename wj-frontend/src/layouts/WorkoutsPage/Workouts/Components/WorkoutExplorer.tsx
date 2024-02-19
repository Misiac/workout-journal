import Exercise from "./Exercise";
import React, {useContext, useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {WorkoutExercise, WorkoutExerciseSet} from "../../../../models/Workout.ts";
import WorkoutTotals from "./WorkoutTotals.tsx";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import EditorOptions from "./EditMode/EditorOptions.tsx";
import LogNewExercise from "./EditMode/LogNewExercise.tsx";
import {prettyFormatDate} from "../../../Utils/DateUtils.ts";
import {Edit2Icon} from "lucide-react";
import ProcessingSpinner from "../../../Utils/ProcessingSpinner.tsx";

export const WorkoutExplorer: React.FC<{
    reloadStats: number
    setReloadStats: (value: number) => void
}> = (props) => {

    const {authState} = useOktaAuth();

    const context = useContext(WorkoutExplorerContext);

    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const {selectedWorkoutId, workout, isEditModeOn, setState, workoutReloadTrigger, exerciseTypes} = context;

    const [totals, setTotals] = useState({totalExercises: 0, totalSets: 0, totalReps: 0, tvl: 0});
    const [isNameEditing, setIsNameEditing] = useState(false);
    const [exerciseTempId, setExerciseTempId] = useState(-1);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchExercises = async () => {
            const url = `${import.meta.env.VITE_API_ADDRESS}/api/exercises/tiny`;
            const requestOptions = {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                }
            };

            try {
                const response = await fetch(url, requestOptions);

                if (!response.ok) {
                    throw new Error('Something went wrong!');
                }

                const data = await response.json();
                setState(prevState => ({
                    ...prevState,
                    exerciseTypes: data
                }));
            } catch (error) {
                console.error('Error fetching exercise data', error);
            }
        };
        setIsLoading(true);
        fetchExercises();
        setIsLoading(false);

    }, []);


    const fetchWorkout = async () => {
        const url = `${import.meta.env.VITE_API_ADDRESS}/api/workouts/${selectedWorkoutId}`;
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
        setState(prevState => ({
            ...prevState,
            workout: workout
        }));
    };

    useEffect(() => {
        setIsNameEditing(false);
    }, [isEditModeOn]);

    useEffect(() => {
        if (selectedWorkoutId > 0) {
            fetchWorkout();
        } else {
            setState(prevState => ({
                ...prevState,
            }));
        }
    }, [authState, selectedWorkoutId, workoutReloadTrigger]);

    useEffect(() => {
        if (selectedWorkoutId != 0 && !isEditModeOn) {
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
    }, [authState, workout, workoutReloadTrigger]);

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

    const addNewExercise = () => {
        if (!workout) return;

        const lastExercise = workout?.workoutExercises[workout?.workoutExercises.length - 1];

        const newExercise = new WorkoutExercise(exerciseTempId,
            lastExercise ? lastExercise.exerciseType : exerciseTypes[0],
            lastExercise ? lastExercise.sequenceNumber + 1 : 1,
            []
        )
        setExerciseTempId(exerciseTempId - 1);
        workout.workoutExercises.push(newExercise);

        setState(prevState => ({
            ...prevState,
            workout: workout,
            wasChangeMade: true
        }));

    };

    return (
        <>
            <div className="w-full overflow-y-auto px-2">

                {workout ?
                    <div className="flex w-full items-start gap-2 py-1 h-[100px] fade-animation">

                        <div className='flex w-1/2 flex-col items-start px-3'>
                            {isEditModeOn ? (
                                <>
                                    <div className='flex w-full'>
                                        {isNameEditing ? (
                                            <input
                                                className='w-full rounded-md py-1 text-2xl font-bold ring-1 ring-black'
                                                value={workout.name}
                                                onChange={(e) => changeWorkoutName(e)}
                                                onBlur={() => setIsNameEditing(false)}
                                                onKeyDown={(e) => {
                                                    if (e.key === 'Enter') {
                                                        e.preventDefault();
                                                        setIsNameEditing(false);
                                                    }
                                                }}
                                            />
                                        ) : (
                                            <>
                                                <p className='py-1 text-2xl font-bold'>{workout.name}</p>
                                                <Edit2Icon className='ml-2 h-5 w-5 mt-2.5 fade-animation'
                                                           onClick={() => setIsNameEditing(true)}/>
                                            </>
                                        )}
                                    </div>
                                    <div className='pt-1'>
                                        <input className='text-xl fade-animation'
                                               type='datetime-local'
                                               value={workout.date}
                                               onChange={(e) => changeWorkoutDate(e)}
                                        />
                                    </div>
                                </>
                            ) : (
                                <>
                                    <p className='py-1 text-2xl font-bold'> {workout.name}</p>
                                    <div className='flex items-center'>
                                        <p className='py-1 text-xl'>{prettyFormatDate(workout.date)}</p>
                                    </div>
                                </>
                            )}
                        </div>

                        <div className='flex h-full w-1/2 flex-col items-center justify-center'>
                            {selectedWorkoutId !== 0 &&
                                (isEditModeOn ?
                                    <div className='flex h-full w-full flex-col items-center justify-center'>
                                        <EditorOptions reloadStats={props.reloadStats}
                                                       setReloadStats={props.setReloadStats}/>
                                    </div>
                                    :
                                    <WorkoutTotals {...totals}/>)
                            }
                        </div>
                    </div>
                    :
                    <div className='mt-10 flex justify-center'>
                        <h3 className='font-semibold'>Select or log workout</h3>
                    </div>
                }

                {selectedWorkoutId !== 0 && <hr/>}

                <div className="grid grid-cols-2 gap-y-4 px-4 pt-4">
                    {isLoading ? <ProcessingSpinner/> :
                        workout?.workoutExercises.map((exercise) => (
                            <Exercise exercise={exercise} key={exercise.sequenceNumber}/>
                        ))
                    }
                    {isEditModeOn && workout && <LogNewExercise addNewExercise={addNewExercise}/>}
                </div>
            </div>
        </>
    );
}
export default WorkoutExplorer;