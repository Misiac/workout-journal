import React from "react";
import workoutTiny from "../../../../models/WorkoutTiny";

export const SliderCard: React.FC<{
    workout: workoutTiny
    selected: number,
    setSelected: any
}> = (props) => {

    function setActive() {
        props.setSelected(props.workout.id)
    }

    return (
        <div className="relative mx-auto max-w-md" onClick={setActive}>
            <div className="bg-white p-3 rounded-md hover:bg-gray-100 border-gray-100 flex items-center">
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
