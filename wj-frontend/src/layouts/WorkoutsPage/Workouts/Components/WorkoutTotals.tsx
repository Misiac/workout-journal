import React from "react";

export const WorkoutTotals: React.FC<{
    totalExercises: number,
    totalSets: number,
    totalReps: number,
    tvl: number
}> = (props) => {

    return (
        <table className="mr-auto fade-animation">
            <tbody className="justify-center tracking-wider">
            <tr>
                <td scope="col" className="border-x border-gray-400 px-6 py-4">
                    <span className='text-2xl font-bold'>{props.totalExercises}</span>
                    <span className='text-xs'>{props.totalExercises > 1 ? ' Exercises' : ' Exercise'}</span>
                </td>
                <td scope="col" className="border-x border-gray-400 px-6 py-4">
                    <span className='text-2xl font-bold'>{props.totalReps}</span>
                    <span className='text-xs'>{props.totalReps > 1 ? ' Reps' : ' Rep'}</span>
                </td>
                <td scope="col" className="border-x border-gray-400 px-6 py-4">
                    <span className='text-2xl font-bold'>{props.totalSets}</span>
                    <span className='text-xs'> {props.totalSets > 1 ? ' Sets' : ' Set'}</span>
                </td>
                <td scope="col" className="border-x border-gray-400 px-6 py-4">
                    <span className='text-2xl font-bold'>{props.tvl}</span>
                    <span className='text-xs'> TVL</span>
                </td>
            </tr>
            </tbody>
        </table>
    );
}
export default WorkoutTotals;