import MuscleRadar from "./Components/MuscleRadar";
import React, {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import StatCard from "./Components/StatCard";
import ProcessingSpinner from "../../Utils/ProcessingSpinner.tsx";
import {RadarData} from "../../../models/RadarData.ts";


export const Stats: React.FC<{
    reloadStats: number
}> = (props) => {
    const {authState} = useOktaAuth();
    const [isLoading, setIsLoading] = useState(true);

    const [reps, setReps] = useState('')
    const [sets, setSets] = useState('')
    const [volume, setVolume] = useState('')
    const [workouts, setWorkouts] = useState('')

    const [radarData, setRadarData] = useState<RadarData | null>(null);

    const userName = authState?.accessToken?.claims.given_name;

    useEffect(() => {
        const fetchStats = async () => {
            const url = `${import.meta.env.VITE_API_ADDRESS}/api/stats`;
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
                const stats = data.totalsDTO;
                setRadarData(data.radarDataDTO);

                setReps(stats.reps)
                setSets(stats.sets)
                setWorkouts(stats.workouts)
                setVolume(stats.volume)

                setIsLoading(false);
            } catch (error) {
                console.error('Error fetching exercise data', error);
            }
        };
        if (authState?.isAuthenticated) {
            fetchStats();
            console.log("fetch stats")
        }

    }, [authState, props.reloadStats]);
    return (
        <>
            <h1 className="text-3xl font-bold tracking-tight text-gray-900">
                {`${userName}'s Dashboard`}
            </h1>
            <div className="grid h-auto grid-cols-4 grid-rows-2 gap-6 py-6">
                {isLoading ?
                    <div className='col-span-4 row-span-2 flex h-full w-full items-center justify-center'>
                        <ProcessingSpinner/>
                    </div>
                    :
                    <>
                        <div className="">
                            <StatCard heading={reps} caption={'Total Reps'} gradientMode={'bg-gradient-to-tl'}/>
                        </div>

                        <div className="col-start-1 row-start-2">
                            <StatCard heading={sets} caption={'Total Sets'} gradientMode={'bg-gradient-to-bl'}/>
                        </div>
                        <div className="col-start-2 row-start-1">
                            <StatCard heading={volume + ' Kg'} caption={'Total Volume'}
                                      gradientMode={'bg-gradient-to-tr'}/>
                        </div>
                        <div className="col-start-2 row-start-2">
                            <StatCard heading={workouts} caption={'Total Workouts'} gradientMode={'bg-gradient-to-br'}/>
                        </div>

                        <div className="col-span-2 col-start-3 row-span-3 row-start-1 flex items-center justify-center">
                            {radarData && <MuscleRadar radarData={radarData}/>}
                        </div>

                    </>
                }

            </div>
        </>

    );
}
export default Stats;

