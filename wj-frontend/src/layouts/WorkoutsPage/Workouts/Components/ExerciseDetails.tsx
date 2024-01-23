import {WorkoutExerciseSet} from "../../../../models/WorkoutExercise";
import React, {useContext, useEffect, useState} from "react";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";

export const ExerciseDetails: React.FC<{
    set: WorkoutExerciseSet
}> = (props) => {

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }
    const {isEditModeOn} = context;

    const [load, setLoad] = useState(props.set.load);
    const [reps, setReps] = useState(props.set.reps);

    const handleValueChange = (event: React.ChangeEvent<HTMLInputElement>, isLoad: boolean) => {
        const newValue = Number(event.target.value);

        if (newValue > 0) {
            isLoad ? setLoad(newValue) : setReps(newValue);
            isLoad ? (props.set.load = newValue) : (props.set.reps = newValue);
            addToEdited(isLoad ? newValue : load, isLoad ? reps : newValue);
            context.setState(prevState => ({
                ...prevState,
                wasChangeMade: true
            }));
        }
    };

    const addToEdited = (load: number, reps: number) => {
        const editedExercise = new WorkoutExerciseSet(
            props.set.id,
            load,
            reps,
            props.set.setNumber
        );

        let newEditedExercises = context.editedExercises.map(exercise =>
            exercise.id === editedExercise.id ? editedExercise : exercise
        );

        if (!newEditedExercises.some(exercise => exercise.id === editedExercise.id)) {
            newEditedExercises = [...newEditedExercises, editedExercise];
        }

        context.setState(prevState => ({
            ...prevState,
            editedExercises: newEditedExercises
        }))
    };


    useEffect(() => {
        setLoad(props.set.load);
        setReps(props.set.reps);
    }, [props.set.load, props.set.reps]);

    return (
        <tr className={isEditModeOn ? 'hover:bg-gray-100' : ''}>
            <td className="border-b border-slate-300">
                {props.set.setNumber}
            </td>
            <td className="border-b border-slate-300 relative group items-center">
                {isEditModeOn ? (
                    <input className="text-center w-1/2 ml-3.5" type="number" value={load}
                           onChange={(e) => handleValueChange(e, true)}
                           onKeyPress={(e) => {
                               if (e.key === 'Enter') e.currentTarget.blur();
                           }}
                    />
                ) : (
                    load
                )}

            </td>
            <td className="border-b border-slate-300 relative group">
                {isEditModeOn ? (
                    <input type="number" className="text-center w-1/2 ml-3.5" value={reps}
                           onChange={(e) => handleValueChange(e, false)}
                           onKeyPress={(e) => {
                               if (e.key === 'Enter') e.currentTarget.blur();
                           }}
                    />
                ) : (
                    reps
                )}

            </td>
        </tr>
    );
}

export default ExerciseDetails;