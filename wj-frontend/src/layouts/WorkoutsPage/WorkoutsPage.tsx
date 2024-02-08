import Stats from "./Stats/Stats";
import Workouts from "./Workouts/Workouts";
import {useState} from "react";

export const WorkoutsPage = () => {

    const [reloadStats, setReloadStats] = useState(0);

    return (
        <>
            <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                <Stats reloadStats={reloadStats}/>
                <hr/>
                <Workouts reloadStats={reloadStats} setReloadStats={setReloadStats}/>
            </div>
        </>
    );
}

export default WorkoutsPage;