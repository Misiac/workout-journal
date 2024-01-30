import {NotebookText} from "lucide-react";
import React from "react";

export const LogNewWorkout: React.FC<{
    addNewWorkout: () => void
}> = (props) => {
    return (
        <button onClick={props.addNewWorkout}>
            <div
                className="flex items-center space-x-2.5 p-2.5
            bg-gradient-to-r from-regal-blue to-blue-600 transition-colors duration-300 hover:from-blue-600 hover:to-blue-700
             rounded-lg mt-4 mx-2 ">
                <NotebookText className="h-6 text-white transition-colors duration-300"/>
                <h2 className="font-semibold text-white">Log new workout</h2>
            </div>
        </button>
    );
}

export default LogNewWorkout;