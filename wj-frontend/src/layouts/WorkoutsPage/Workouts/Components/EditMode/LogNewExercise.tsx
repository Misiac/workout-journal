import React from "react";

export const LogNewExercise: React.FC<{
    addNewExercise: () => void
}> = (props) => {
    return (
        <button onClick={props.addNewExercise}>
            <div
                className="flex h-28 w-full items-center justify-center rounded-lg border border-gray-100 bg-white px-2
             shadow-lg fade-animation focus:outline-none transition-colors duration-300 hover:bg-gray-100">
                <div className="text-center">
                    <h3 className="py-1 font-bold">Log next exercise</h3>
                </div>
            </div>
        </button>
    );
};

export default LogNewExercise;