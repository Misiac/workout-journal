import SliderCard from "./SliderCard";
import React from "react";

export const WorkoutsSlider = () => {
    return (

            <div className="scroll-container overflow-y-auto h-full">
                <div className="flex flex-col gap-4">
                    <SliderCard/>
                    <SliderCard/>
                    <SliderCard/>
                    <SliderCard/>
                    <SliderCard/>
                    <SliderCard/>
                    <SliderCard/>
                </div>
            </div>

    );
}
export default WorkoutsSlider;