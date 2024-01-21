import React, {useContext} from "react";
import {WorkoutTiny} from "../../../../models/WorkoutTiny";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";

export const SliderCard: React.FC<{
    workout: WorkoutTiny
}> = (props) => {

    const context = useContext(WorkoutExplorerContext);

    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const {selectedWorkoutId, setSelectedWorkoutId, setWorkoutName, setWorkoutDate} = context;

    function setActive() {
        setSelectedWorkoutId(props.workout.id)
        setWorkoutName(props.workout.name);
        setWorkoutDate(props.workout.date);
    }

    return (
        <div className="relative mx-auto max-w-md" onClick={setActive}>
            <div className="flex items-center rounded-md border-gray-100 bg-white p-3 hover:bg-gray-100">
                <div
                    className={`w-1 h-10 bg-regal-blue mr-3 rounded-t-lg rounded-b-lg ${selectedWorkoutId === props.workout.id ? '' : 'invisible'}`}
                ></div>

                <div>
                    <h1 className="font-bold">{props.workout.name}</h1>
                    <p className="">{props.workout.date}</p>
                </div>
            </div>
        </div>
    );
}

export default SliderCard;
