import React from "react";

export const WorkoutTotals: React.FC<{
    totalExercises: number,
    totalSets: number,
    totalReps: number,
    tvl: number
}> = (props) => {

    return (
        <table className="mr-auto">
            <tbody className=" tracking-wider justify-center">
            <tr>
                <td scope="col" className="px-6 py-4 border-x border-gray-400">
                    <span className='font-bold text-2xl'>{props.totalExercises}</span>
                    <span className='text-xs'>{props.totalExercises > 1 ? ' Exercises' : ' Exercise'}</span>
                </td>
                <td scope="col" className="px-6 py-4 border-x border-gray-400">
                    <span className='font-bold text-2xl'>{props.totalReps}</span>
                    <span className='text-xs'>{props.totalReps > 1 ? ' Reps' : ' Rep'}</span>
                </td>
                <td scope="col" className="px-6 py-4 border-x border-gray-400">
                    <span className='font-bold text-2xl'>{props.totalSets}</span>
                    <span className='text-xs'> {props.totalSets > 1 ? ' Sets' : ' Set'}</span>
                </td>
                <td scope="col" className="px-6 py-4 border-x border-gray-400">
                    <span className='font-bold text-2xl'>{props.tvl}</span>
                    <span className='text-xs'> TVL</span>
                </td>
            </tr>
            </tbody>
        </table>
    );
}
export default WorkoutTotals;