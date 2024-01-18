import test from "../../../../resources/test.png";
import {useState} from "react";
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
                tabIndex={1}
                onClick={handleToggle}
            >
                <div className={`flex flex-col items-center rounded-lg md:flex-row md:max-w-xl `}>
                    <img
                        className="object-cover rounded-t-lg h-28 md:w-48 md:rounded-none md:rounded-s-lg w-1/3"
                        src={test}
                    />
                    <div className="flex flex-col justify-between p-4 leading-normal w-2/3">
                        <h5 className="mb-2 text-4xl tracking-tight text-gray-900">{props.test}</h5>
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
