import {useOktaAuth} from "@okta/okta-react";
import {useEffect, useState} from "react";

export const TotalWorkoutsCard = () => {

    const {authState} = useOktaAuth();

    const [workouts, setWorkouts] = useState()

    useEffect(() => {
        const fetchData = async () => {
            const url = 'http://localhost:8080/api/stats/total/workouts';
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

                const reps = await response.json();
                setWorkouts(reps)

            } catch (error) {
                console.error('Error fetching exercise data', error);
            }
        };

        fetchData();
    }, [authState]);


    return (
        <div
            className="relative mx-auto max-w-md rounded-lg bg-gradient-to-br from-regal-blue to-blue-900 p-0.5 shadow-xl">
            <div className="bg-white p-7 rounded-md">
                <h1 className="font-bold text-2xl mb-2">{workouts}</h1>
                <p className="">Total workouts</p>
            </div>
        </div>
    );
}
export default TotalWorkoutsCard;