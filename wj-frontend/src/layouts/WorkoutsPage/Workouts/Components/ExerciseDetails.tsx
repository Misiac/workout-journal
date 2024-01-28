import {WorkoutExerciseSet} from "../../../../models/Workout.ts";
import React, {useContext, useEffect, useState} from "react";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import {Trash2} from "lucide-react";

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

            const updatedSet = {
                ...props.set,
                load: isLoad ? newValue : props.set.load,
                reps: !isLoad ? newValue : props.set.reps,
            };

            setState(prevState => {
                if (!workout) return prevState;
                const exercise = workout.workoutExercises.find(ex => ex.workoutExerciseSets.includes(props.set));
                if (!exercise) return prevState;

                const newExercise = {
                    ...exercise,
                    workoutExerciseSets: exercise.workoutExerciseSets.map(set => set === props.set ? updatedSet : set)
                };

                return {
                    ...prevState,
                    workout: {
                        ...workout,
                        workoutExercises: workout.workoutExercises.map(ex => ex === exercise ? newExercise : ex)
                    },
                    wasChangeMade: true,
                };
            });
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
            <td className="relative items-center border-b border-slate-300">
                {isEditModeOn && <button onClick={deleteSet}>
                    <Trash2
                        className='absolute left-2 mr-2 h-5 w-5 opacity-0 transition-colors duration-200 top-0.5 group-hover:opacity-100 hover:text-red-500'/>
                </button>}
                {props.set.setNumber}
            </td>
            <td className="relative items-center border-b border-slate-300">
                {isEditModeOn ? (
                    <input className="w-1/2 text-center ml-3.5" type="number" value={load}
                           onChange={(e) => handleValueChange(e, true)}
                           onKeyDown={(e) => {
                               if (e.key === 'Enter') e.currentTarget.blur();
                           }}
                    />
                ) : load}
            </td>
            <td className="relative border-b border-slate-300">
                {isEditModeOn ? (
                    <input type="number" className="w-1/2 text-center ml-3.5" value={reps}
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