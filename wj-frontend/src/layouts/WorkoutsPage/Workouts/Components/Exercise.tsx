import test from "../../../../resources/test.png";
import arrow from "../../../../resources/collapse-arrow.png";
import React, {useState} from "react";
import ExerciseDetails from "./ExerciseDetails";

export const Exercise: React.FC<{
    test: string;
}> = (props) => {
    const [isExpanded, setIsExpanded] = useState(false);

    const handleToggle = () => {
        setIsExpanded(!isExpanded);
    };

    return (
        <div className="w-full px-2">
            <div
                className={`flex flex-col w-full rounded-lg shadow border border-gray-200 bg-white focus:outline-none`}
            >
                <div
                    className={`flex flex-col items-center rounded-lg md:flex-row md:max-w-xl `}
                >
                    <img
                        className={`object-cover h-28 md:w-48 w-1/3 ${
                            isExpanded ? 'md:rounded-none md:rounded-tl-lg duration-300' : 'md:rounded-none md:rounded-l-lg transition-all duration-700'
                        }`}
                        src={test}
                        alt="Exercise image"/>
                    <div className="flex flex-row justify-between p-4 leading-normal w-2/3 items-center">
                        <h5 className="mb-2 text-4xl tracking-tight text-gray-900">
                            {props.test}
                        </h5>
                        <div className="flex flex-col w-full px-3 ">
                            <h3 className="font-bold pb-2 text-xl">Lat Raise</h3>
                            <hr/>
                            <div className="flex flex-row justify-center pt-2">
                                <h5>3 Sets</h5>
                                <h3>&nbsp; &#x2022; &nbsp;</h3>
                                <h5>6-9 Reps</h5>
                                <h3>&nbsp; &#x2022; &nbsp; </h3>
                                <h5>4 Kg</h5>
                            </div>

                        </div>
                        <button
                            className={`focus:outline-none h-8 w-8 transition-all duration-500 ${
                                isExpanded ? "rotate-180" : ""
                            }`}
                            onClick={handleToggle}
                            style={{outline: "none"}}
                        >
                            <img className="h-full w-full" src={arrow} alt="Toggle arrow"/>
                        </button>
                    </div>
                </div>

                <div
                    className={`overflow-hidden transition-all ease-in-out duration-500 ${
                        isExpanded ? "max-h-96 opacity-100 px-4 py-4" : "max-h-0 opacity-0 "
                    }`}
                >
                    <ExerciseDetails/>
                </div>
            </div>
        </div>
    );
};

export default Exercise;
