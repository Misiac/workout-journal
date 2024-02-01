import {NotebookText} from "lucide-react";
import React from "react";

export const LogNewWorkout: React.FC<{
    addNewWorkout: () => void
}> = (props) => {
    return (
        <button onClick={props.addNewWorkout} className="log-new-workout-btn">
            <div className="flex items-center space-x-2.5 p-2.5">
                <NotebookText className="h-6 text-white transition-colors duration-300"/>
                <h2 className="font-semibold text-white">Log new workout</h2>
            </div>
        </button>
    );
}

export default LogNewWorkout;