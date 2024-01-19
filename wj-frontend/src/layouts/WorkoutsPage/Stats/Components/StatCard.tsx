import React from "react";

export const TotalRepsCard: React.FC<{
    heading: string
    caption: string
    gradientMode: string
}> = (props) => {

    const baseClassName = `relative mx-auto max-w-md rounded-lg p-0.5 shadow-xl ${props.gradientMode} from-regal-blue to-blue-900`;

    return (
        <div className={baseClassName}>
            <div className="rounded-md bg-white p-7">
                <h1 className="mb-2 text-2xl font-bold">{props.heading}</h1>
                <p className="">{props.caption}</p>
            </div>
        </div>
    );
}
export default TotalRepsCard;