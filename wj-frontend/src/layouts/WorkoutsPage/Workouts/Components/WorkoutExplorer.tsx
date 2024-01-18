import Exercise from "./Exercise";
import React from "react";

export const WorkoutExplorer: React.FC<{
    selected: number,
}> = (props) => {
    return (
        <>
            <div className="w-full overflow-y-auto">
                <div className="grid grid-cols-2 px-4 gap-y-4 ">
                    <Exercise test={'1'}/>
                    <Exercise test={'2'}/>
                    <Exercise test={'3'}/>
                    <Exercise test={'4'}/>
                    <Exercise test={'5'}/>
                    <Exercise test={'6'}/>
                    <Exercise test={'7'}/>
                    <Exercise test={'8'}/>
                </div>
            </div>
        </>
    );
}
export default WorkoutExplorer;