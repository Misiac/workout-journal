import test from "../../../../resources/test.png";
import arrow from "../../../../resources/collapse-arrow.png";
import React, {useState} from "react";
import ExerciseDetails from "./ExerciseDetails";
import {WorkoutExercise} from "../../../../models/WorkoutExercise";


export const Exercise: React.FC<{
    exercise: WorkoutExercise
}> = (props) => {
    const [isExpanded, setIsExpanded] = useState(false);

    const totalSets = props.exercise.entry.length;

    const minReps = props.exercise.entry.reduce((min, set) => Math.min(min, set.reps), Infinity);
    const maxReps = props.exercise.entry.reduce((max, set) => Math.max(max, set.reps), -Infinity);

    const minKg = props.exercise.entry.reduce((min, set) => Math.min(min, set.load), Infinity);
    const maxKg = props.exercise.entry.reduce((max, set) => Math.max(max, set.load), -Infinity);

    const handleToggle = () => {
        setIsExpanded(!isExpanded);
    };

    return (
        <div className="w-full px-2">
            <div
                className={`flex flex-col w-full rounded-lg shadow border border-gray-200 bg-white focus:outline-none`}
            >
                <div
                    className={`flex flex-col items-center rounded-lg md:flex-row md:max-w-xl`}
                >
                    <img
                        className={`object-cover h-28 md:w-48 w-1/3 ${
                            isExpanded ? 'md:rounded-none md:rounded-tl-lg duration-300' : 'md:rounded-none md:rounded-l-lg transition-all duration-700'
                        }`}
                        src={test}
                        alt="Exercise image"/>
                    <div className="flex flex-row justify-between p-4 leading-normal w-2/3 items-center max-h-20">
                        <h5 className="mb-2 text-4xl tracking-tight text-gray-900">
                            {props.exercise.counter}
                        </h5>
                        <div className="flex flex-col w-full px-3 ">
                            <h3 className="font-bold py-1">
                                {props.exercise.name}
                            </h3>
                            <hr/>
                            <div className="flex flex-row justify-center py-1 text-sm">
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

                    <table className="table-fixed w-full text-center py-5">
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
