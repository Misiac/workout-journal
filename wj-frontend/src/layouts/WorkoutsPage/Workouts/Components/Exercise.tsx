import test from "../../../../resources/test.png";
import arrow from "../../../../resources/collapse-arrow.png";
import React, {useContext, useState} from "react";
import ExerciseDetails from "./ExerciseDetails";
import {WorkoutExercise} from "../../../../models/WorkoutExercise";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";


export const Exercise: React.FC<{
    exercise: WorkoutExercise
}> = (props) => {

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const [isExpanded, setIsExpanded] = useState(false);

    const totalSets = props.exercise.entry.length;

    const minReps = props.exercise.entry.reduce((min, set) => Math.min(min, set.reps), Infinity);
    const maxReps = props.exercise.entry.reduce((max, set) => Math.max(max, set.reps), -Infinity);

    const minKg = props.exercise.entry.reduce((min, set) => Math.min(min, set.load), Infinity);
    const maxKg = props.exercise.entry.reduce((max, set) => Math.max(max, set.load), -Infinity);

    const addToDelete = () => {

        const newDeletedExercises: number[] = [...context.deletedExercises];
        props.exercise.entry.forEach(set => {
            newDeletedExercises.push(set.id);
        });
        const filtered = context.exercises.filter(exercise => exercise !== props.exercise);

        context.setExercises(filtered);
        context.setDeletedExercises(newDeletedExercises);
        context.setWasChangeMade(true);

    };


    const handleToggle = () => {
        setIsExpanded(!isExpanded);
    };

    return (
        <div className="w-full px-2 fade-animation">
            <div
                className="relative flex flex-col w-full rounded-lg shadow border border-gray-200 bg-white focus:outline-none"
            >
                <div
                    className="flex flex-col items-center rounded-lg md:flex-row md:max-w-xl"
                >
                    {context.isEditModeOn &&
                        <button onClick={addToDelete}
                                className="absolute top-0 right-0 m-2 h-7 w-7 rounded-full bg-red-500 flex items-center justify-center text-white fade-animation hover:bg-red-700"
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24"
                                 stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.8"
                                      d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                            </svg>
                        </button>}

                    <img
                        className={`object-cover h-28 md:w-48 w-1/3 ${
                            isExpanded ? 'md:rounded-none md:rounded-tl-lg duration-300' : 'md:rounded-none md:rounded-l-lg transition-all duration-700'
                        }`}
                        src={test}
                        alt="Exercise"/>
                    <div className="flex max-h-20 w-2/3 flex-row items-center justify-between p-4 leading-normal">
                        <h5 className="mb-2 text-4xl tracking-tight text-gray-900">
                            {props.exercise.counter}
                        </h5>
                        <div className="flex w-full flex-col px-3">
                            <h3 className="py-1 font-bold">
                                {props.exercise.name}

                            </h3>
                            <hr/>
                            <div className="flex flex-row justify-center py-1 text-xs">
                                <h5>
                                    {totalSets > 1 ? totalSets + ' Sets' : totalSets + ' Set'}
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
                            className={`focus:outline-none h-8 w-8 transition-all duration-500 ${
                                isExpanded ? "rotate-180" : ""
                            }`}
                            onClick={handleToggle}
                            style={{outline: "none"}}
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
                        {props.exercise.entry.map((set) => (
                            <ExerciseDetails set={set} key={set.id}/>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Exercise;
