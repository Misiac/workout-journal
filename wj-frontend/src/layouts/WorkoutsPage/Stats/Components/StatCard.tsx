import React from "react";

export const TotalRepsCard: React.FC<{
    heading: string
    caption: string
    gradientDirection: string
}> = (props) => {

    const baseClassName = 'relative mx-auto max-w-md rounded-lg p-0.5 shadow-xl';
    const gradientClass = `bg-gradient-to-${props.gradientDirection} from-regal-blue to-blue-900`;

    return (
        <div className={`${baseClassName} ${gradientClass}`}>
            <div className="bg-white p-7 rounded-md">
                <h1 className="font-bold text-2xl mb-2">{props.heading}</h1>
                <p className="">{props.caption}</p>
            </div>
        </div>
    );
}
export default TotalRepsCard;