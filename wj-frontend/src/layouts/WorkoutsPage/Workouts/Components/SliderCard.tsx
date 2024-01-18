import React from "react";

export const SliderCard = () => {
    return (
        <div className="relative mx-auto max-w-md">
            <div className="bg-white p-3 rounded-md hover:bg-gray-100 border-gray-100 flex items-center">
                <div className="w-1 h-10 bg-regal-blue mr-3 rounded-t-lg rounded-b-lg" ></div>
                <div>
                    <h1 className="font-bold">Monday Morning Workout</h1>
                    <p className="">19.01.2024 19:54</p>
                </div>
            </div>
        </div>
    );
}

export default SliderCard;
