import MuscleRadar from "./Components/MuscleRadar";
import {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import StatCard from "./Components/StatCard";


export const Stats = () => {
    const {authState} = useOktaAuth();

    const [reps, setReps] = useState('')
    const [sets, setSets] = useState('')
    const [volume, setVolume] = useState('')
    const [workouts, setWorkouts] = useState('')

    useEffect(() => {
        const fetchData = async () => {
            const url = 'http://localhost:8080/api/stats/total';
            const requestOptions = {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                }
            };

            try {
                const response = await fetch(url, requestOptions);

                if (!response.ok) {
                    throw new Error('Something went wrong!');
                }

                const data = await response.json();
                setReps(data.reps)
                setSets(data.sets)
                setWorkouts(data.workouts)
                setVolume(data.volume)

            } catch (error) {
                console.error('Error fetching exercise data', error);
            }
        };

        fetchData();
    }, [authState]);
    return (
        <>
            <h1 className="text-3xl font-bold tracking-tight text-gray-900">Dashboard</h1>
            <div className="grid grid-cols-4 grid-rows-2 gap-6 py-6">
                <div className="">
                    <StatCard heading={reps} caption={'Total Reps'} gradientMode={'bg-gradient-to-tl'}/>
                </div>

                <div className="col-start-1 row-start-2">
                    <StatCard heading={sets} caption={'Total Sets'} gradientMode={'bg-gradient-to-bl'}/>
                </div>
                <div className="col-start-2 row-start-1">
                    <StatCard heading={volume + ' Kg'} caption={'Total Volume'} gradientMode={'bg-gradient-to-tr'}/>
                </div>
                <div className="col-start-2 row-start-2">
                    <StatCard heading={workouts} caption={'Total Workouts'} gradientMode={'bg-gradient-to-br'}/>
                </div>
                <div className="col-span-2 col-start-3 row-span-3 row-start-1 flex items-center justify-center">
                    <MuscleRadar/>
                </div>
            </div>
        </>

    )
        ;
}
export default Stats;

