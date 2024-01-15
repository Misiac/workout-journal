import {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";

export const TotalSetsCard = () => {

    const {authState} = useOktaAuth();

    const [sets, setSets] = useState()

    useEffect(() => {
        const fetchData = async () => {
            const url = 'http://localhost:8080/api/stats/total/sets';
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
                setSets(reps)

            } catch (error) {
                console.error('Error fetching exercise data', error);
            }
        };

        fetchData();
    }, [authState]);
    return (
        <div
            className="relative mx-auto max-w-md rounded-lg bg-gradient-to-bl from-regal-blue to-blue-900 p-0.5 shadow-xl">
            <div className="bg-white p-7 rounded-md">
                <h1 className="font-bold text-2xl mb-2">{sets}</h1>
                <p className="">Total Sets</p>
            </div>
        </div>
    );
}
export default TotalSetsCard;