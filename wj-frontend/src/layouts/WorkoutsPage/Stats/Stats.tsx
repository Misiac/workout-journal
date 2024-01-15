import TotalVolumeCard from "./Components/TotalVolumeCard";
import TotalRepsCard from "./Components/TotalRepsCard";
import TotalSetsCard from "./Components/TotalSetsCard";
import TotalWorkoutsCard from "./Components/TotalWorkoutsCard";
import MuscleRadar from "./Components/MuscleRadar";


export const Stats = () => {

    return (
        <div className="grid grid-cols-4 grid-rows-2 gap-6 py-6">
            <div className=""><TotalRepsCard/></div>
            <div className="col-start-1 row-start-2"><TotalSetsCard/></div>
            <div className="col-start-2 row-start-1"><TotalVolumeCard/></div>
            <div className="col-start-2 row-start-2"><TotalWorkoutsCard/></div>
            <div className="col-span-2 row-span-3 col-start-3 row-start-1 items-center justify-center flex">
                <MuscleRadar/>
            </div>
        </div>

    );
}
export default Stats;

