import {WorkoutTiny} from "../../../../../models/WorkoutTiny.ts";
import {WorkoutExplorerContext} from "../../../WorkoutExplorerContext.tsx";
import React, {useContext} from "react";

export const SliderCard: React.FC<{
    workout: WorkoutTiny
    handleOpenModal: () => Promise<boolean>
}> = ({workout, handleOpenModal}) => {

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }
    const {selectedWorkoutId, isEditModeOn, wasChangeMade, setState} = context;

    const handleClick = async () => {
        if (workout.id !== selectedWorkoutId && (!isEditModeOn || !wasChangeMade || await handleOpenModal())) {
            setState(prevState => ({
                ...prevState,
                selectedWorkoutId: workout.id,
                workoutName: workout.name,
                workoutDate: workout.date
            }));
        }
    }

    return (
        <div className="relative mx-auto w-full max-w-md" onClick={handleClick}>
            <div className="flex items-center rounded-md border-gray-100 bg-white p-3 hover:bg-gray-100">

                <div
                    className={`w-1 h-11 bg-regal-blue mr-3 rounded-t-lg rounded-b-lg flex-none ${selectedWorkoutId === workout.id ? '' : 'invisible'}`}
                ></div>

                <div className=''>
                    <h1 className="overflow-hidden font-bold text-overflow ellipsis">
                        {workout.name}
                    </h1>
                    <p className="overflow-hidden text-overflow ellipsis">{workout.date}</p>
                </div>
            </div>
        </div>
    );

}

export default SliderCard;