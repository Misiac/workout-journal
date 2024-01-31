import defaultExerciseImage from "../../../../resources/defaultExerciseImage.jpg";
import arrow from "../../../../resources/collapse-arrow.png";
import React, {useContext, useEffect, useState} from "react";
import {WorkoutExercise, WorkoutExerciseSet} from "../../../../models/Workout.ts";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import ExerciseDetails from "./ExerciseDetails.tsx";
import LogNewSet from "./EditMode/LogNewSet.tsx";
import {Trash2} from "lucide-react";

export const Exercise: React.FC<{
    exercise: WorkoutExercise,
}> = (props) => {

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const {workout, setState, isEditModeOn, exerciseTypes} = context;

    const [isExpanded, setIsExpanded] = useState(false);
    const [setTempId, setSetTempId] = useState(-1);

    const totalSets = props.exercise.workoutExerciseSets.length;

    let minReps = 0;
    let maxReps = 0;
    let minKg = 0;
    let maxKg = 0;

    if (totalSets > 0) {
        minReps = Math.min(...props.exercise.workoutExerciseSets.map(set => set.reps));
        maxReps = Math.max(...props.exercise.workoutExerciseSets.map(set => set.reps));
        minKg = Math.min(...props.exercise.workoutExerciseSets.map(set => set.load));
        maxKg = Math.max(...props.exercise.workoutExerciseSets.map(set => set.load));
    }

    const deleteExercise = () => {
        const filtered = workout?.workoutExercises.filter(ex => ex !== props.exercise) || [];
        let newWorkout = workout;

        let counter = 1;
        filtered.forEach((exercise) => {
            exercise.sequenceNumber = counter;
            counter++;
        });

        if (newWorkout) {
            newWorkout = {...newWorkout, workoutExercises: filtered};
        }

        setState(prevState => ({
            ...prevState,
            workout: newWorkout,
            wasChangeMade: true
        }));
    };

    const [selectedExerciseName, setSelectedExerciseName] = useState(props.exercise.exerciseType.name);

    const recountSets = () => {
        let counter = 1;
        for (const set of props.exercise.workoutExerciseSets) {
            set.setNumber = counter;
            counter++;
        }
    }

    const setExerciseType = (exerciseName: string) => {

        if (exerciseName != props.exercise.exerciseType.name) {
            const exerciseType = exerciseTypes.find(exercise => exercise.name === exerciseName);

            if (exerciseType && workout) {
                const exerciseIndex = workout.workoutExercises.findIndex(exercise => exercise === props.exercise);

                if (exerciseIndex !== -1) {
                    props.exercise.exerciseType = exerciseType;

                    workout.workoutExercises[exerciseIndex] = props.exercise;
                    setState(prevState => ({
                        ...prevState,
                        workout: workout,
                        wasChangeMade: true
                    }));

                    setSelectedExerciseName(exerciseName);
                }
            }
        }
    }

    const addNewSet = () => {
        if (!workout) return;

        const lastSet = props.exercise.workoutExerciseSets.length > 0
            ? props.exercise.workoutExerciseSets[props.exercise.workoutExerciseSets.length - 1]
            : null

        const newSet = new WorkoutExerciseSet(
            setTempId,
            lastSet ? lastSet.load : 10,
            lastSet ? lastSet.reps : 10,
            lastSet ? lastSet.setNumber + 1 : 1
        );

        setSetTempId(setTempId - 1);
        props.exercise.workoutExerciseSets.push(newSet);

        const exerciseIndex = workout.workoutExercises.findIndex(exercise => exercise === props.exercise);
        workout.workoutExercises[exerciseIndex] = props.exercise;

        setState(prevState => ({
            ...prevState,
            workout: workout,
            wasChangeMade: true
        }));
    }

    useEffect(() => {
        setSelectedExerciseName(props.exercise.exerciseType.name)
    }, [isEditModeOn]);

    const handleToggle = () => setIsExpanded(!isExpanded);

const searchForImage = (exerciseTypeId: number): string => {
    const exerciseType = exerciseTypes.find(type => type.id === exerciseTypeId);
    return exerciseType?.image ? `data:image/jpeg;base64,${exerciseType.image}` : defaultExerciseImage;
}

    return (
        <div className="w-full px-2 fade-animation">

            <div
                className="relative flex w-full flex-col rounded-lg border border-gray-200 bg-white shadow-lg focus:outline-none"
            >
                <div
                    className="flex flex-col items-center rounded-lg md:max-w-xl md:flex-row"
                >
                    {isEditModeOn &&
                        <button onClick={deleteExercise}
                                className="absolute top-0 right-0 m-2 flex h-7 w-7 items-center justify-center rounded-full bg-red-500 text-white transition-colors duration-300 fade-animation hover:bg-red-700"
                        >
                            <Trash2 className='w-5'/>
                        </button>}

                    <img
                        className={`object-cover h-28 md:w-48 w-1/3 ${
                            isExpanded ? 'md:rounded-none md:rounded-tl-lg duration-300' : 'md:rounded-none md:rounded-l-lg transition-all duration-700'
                        }`}
                        src={searchForImage(props.exercise.exerciseType.id)}
                        alt="Exercise"/>
                    <div className="flex max-h-20 w-2/3 flex-row items-center justify-between p-4 leading-normal">
                        <h5 className="mb-2 text-4xl tracking-tight text-gray-900">
                            {props.exercise.sequenceNumber}
                        </h5>
                        <div className="flex w-full flex-col px-3">
                            {isEditModeOn ?
                                <div className="py-1">
                                    <select id="exercises"
                                            className="block w-full rounded-lg border border-gray-300 bg-gray-50 font-bold text-gray-900 p-1.5 focus:border-blue-500 focus:ring-blue-500"
                                            value={selectedExerciseName}
                                            onChange={(e) => setExerciseType(e.target.value)}>

                                        {exerciseTypes.map((exercise) => (
                                            <option key={exercise.id} value={exercise.name}>
                                                {exercise.name}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                                :
                                <h3 className="py-2 font-bold">
                                    {props.exercise.exerciseType.name}
                                </h3>
                            }
                            <hr/>
                            <div className="flex flex-row justify-center py-1 text-xs">
                                <h5>
                                    {totalSets !== 1 ? totalSets + ' Sets' : totalSets + ' Set'}
                                </h5>
                                <h3>&nbsp; &#x2022; &nbsp;</h3>
                                <h5>
                                    {minKg === maxKg ? minKg + ' Kg' : minKg + '-' + maxKg + ' Kg'}
                                </h5>
                                <h3>&nbsp; &#x2022; &nbsp; </h3>
                                <h5>
                                    {minReps === maxReps ? minReps + ' Reps' : minReps + '-' + maxReps + ' Reps'}
                                </h5>
                            </div>
                        </div>
                        <button
                            className={`focus:outline-none h-8 w-8 transition-all duration-500 outline-none ${
                                isExpanded ? "rotate-180" : ""
                            }`}
                            onClick={handleToggle}
                        >
                            <img className="h-full w-full" src={arrow} alt="Toggle arrow"/>
                        </button>
                    </div>
                </div>

                <div
                    className={`overflow-hidden transition-all ease-in-out duration-500 ${
                        isExpanded ? "max-h-96 opacity-100 px-4 py-4" : "max-h-0 opacity-0 "
                    }`}
                >

                    <table className="w-full table-fixed py-5 text-center">
                        <thead>
                        <tr>
                            <th className="border-b border-slate-300">Set</th>
                            <th className="border-b border-slate-300">Kg</th>
                            <th className="border-b border-slate-300">Reps</th>
                        </tr>
                        </thead>

                        <tbody>
                        {props.exercise.workoutExerciseSets.map((set) => (
                            <ExerciseDetails set={set} key={set.id} recountSets={recountSets}/>
                        ))}
                        {isEditModeOn &&
                            <LogNewSet addNewSet={addNewSet}/>
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Exercise;
