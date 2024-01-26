import {WorkoutExerciseSet} from "../../../../models/Workout.ts";
import React, {useContext, useEffect, useState} from "react";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import DeleteSVG from "../../../Utils/DeleteSVG.tsx";

export const ExerciseDetails: React.FC<{
    set: WorkoutExerciseSet,
    recountSets: () => void
}> = (props) => {

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }
    const {isEditModeOn, setState, workout} = context;

    const [load, setLoad] = useState(props.set.load);
    const [reps, setReps] = useState(props.set.reps);

    const handleValueChange = (event: React.ChangeEvent<HTMLInputElement>, isLoad: boolean) => {
        const newValue = Number(event.target.value);

        if (newValue > 0) {
            isLoad ? setLoad(newValue) : setReps(newValue);
            isLoad ? (props.set.load = newValue) : (props.set.reps = newValue);
            setState(prevState => ({
                ...prevState,
                wasChangeMade: true
            }));
        }
    };

    useEffect(() => {
        setLoad(props.set.load);
        setReps(props.set.reps);
    }, [props.set.load, props.set.reps]);

    const deleteSet = () => {
        setState((prevState) => {
            if (!workout) return prevState;
            const exercise = workout.workoutExercises.find((ex) => ex.workoutExerciseSets.includes(props.set));
            if (!exercise) return prevState;

            const newExercise = {
                ...exercise,
                workoutExerciseSets: exercise.workoutExerciseSets.filter(s => s !== props.set)
            };
            let counter = 1;
            for (const set of newExercise.workoutExerciseSets) {
                set.setNumber = counter;
                counter++;
            }
            return {
                ...prevState,
                workout: {
                    ...workout,
                    workoutExercises: workout.workoutExercises.map(ex => ex === exercise ? newExercise : ex)
                },
                wasChangeMade: true,
            };
        });
    };

    return (
        <tr className={isEditModeOn ? 'hover:bg-gray-100 group' : 'group'}>
            <td className="border-b border-slate-300 relative items-center">
                {isEditModeOn && <button onClick={deleteSet}><DeleteSVG/></button>}
                {props.set.setNumber}
            </td>
            <td className="border-b border-slate-300 relative items-center">
                {isEditModeOn ? (
                    <input className="text-center w-1/2 ml-3.5" type="number" value={load}
                           onChange={(e) => handleValueChange(e, true)}
                           onKeyDown={(e) => {
                               if (e.key === 'Enter') e.currentTarget.blur();
                           }}
                    />
                ) : load}
            </td>
            <td className="border-b border-slate-300 relative">
                {isEditModeOn ? (
                    <input type="number" className="text-center w-1/2 ml-3.5" value={reps}
                           onChange={(e) => handleValueChange(e, false)}
                           onKeyDown={(e) => {
                               if (e.key === 'Enter') e.currentTarget.blur();
                           }}
                    />
                ) : reps}
            </td>
        </tr>
    );
}

export default ExerciseDetails;