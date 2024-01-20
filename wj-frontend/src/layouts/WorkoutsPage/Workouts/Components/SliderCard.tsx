import React from "react";
import {WorkoutTiny} from "../../../../models/WorkoutTiny";

export const SliderCard: React.FC<{
    workout: WorkoutTiny
    selected: number,
    setSelected: any,
    setWorkoutName: any,
    setWorkoutDate: any
}> = (props) => {

    function setActive() {
        props.setSelected(props.workout.id)
        props.setWorkoutName(props.workout.name);
        props.setWorkoutDate(props.workout.date);
    }

    return (
        <div className="relative mx-auto max-w-md" onClick={setActive}>
            <div className="flex items-center rounded-md border-gray-100 bg-white p-3 hover:bg-gray-100">
                <div
                    className={`w-1 h-10 bg-regal-blue mr-3 rounded-t-lg rounded-b-lg ${props.selected === props.workout.id ? '' : 'invisible'}`}
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
