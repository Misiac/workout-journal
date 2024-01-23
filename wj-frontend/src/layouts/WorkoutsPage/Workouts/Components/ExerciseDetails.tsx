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


    const handleLoadChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = Number(event.target.value);

        if (newValue > 0) {
            setLoad(newValue);
            props.set.load = newValue;

            context.setState(prevState => ({
                ...prevState,
                wasChangeMade: true
            }));
        }
    };

    const handleRepsChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = Number(event.target.value);
        if (newValue > 0 && Number.isInteger(newValue)) {
            setReps(newValue);
            props.set.reps = newValue;

            context.setState(prevState => ({
                ...prevState,
                wasChangeMade: true
            }));
        }
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
                           onChange={(e) => handleLoadChange(e)}
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
                           onChange={(e) => handleRepsChange(e)}
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