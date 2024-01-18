import SliderCard from "./SliderCard";
import React from "react";

export const WorkoutsSlider = () => {
    return (

        <div className="scroll-container overflow-y-auto h-full w-1/5 px-1 ">
            <div className="flex flex-col gap-4">

                <SliderCard/>
                <SliderCard/>
                <SliderCard/>
                <SliderCard/>


            </div>
        </div>

    );
}
export default WorkoutsSlider;