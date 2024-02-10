import {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import ProcessingSpinner from "../../../../Utils/ProcessingSpinner.tsx";

export const AnalyzeWorkouts = () => {
    const {authState} = useOktaAuth();
    const [isLoading, setIsLoading] = useState(false);
    const [response, setResponse] = useState('');

    const handleAnalyze = async () => {

        const url = `${import.meta.env.VITE_API_ADDRESS}/api/ai/analyze/workouts`;

        try {
            setIsLoading(true);
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                const data = await response.text();
                console.log(data);
                setResponse(data);
                setIsLoading(false);
                throw new Error('HTTP error ' + response.status);
            }
            setIsLoading(false);

            const data = await response.json();

            setResponse(data.generation);
            console.log(data);

        } catch (error) {
            console.error('Fetch error:', error);
            setIsLoading(false);
        }
    };

    useEffect(() => {
        handleAnalyze();
    }, [authState]);

return (
    <div className="flex flex-col p-4 overflow-y-auto max-h-full">
        {isLoading ?
            <div className='flex flex-col items-center'>
                <ProcessingSpinner/>
                <span className='font-semibold text-gray-500'>This can take up to 30 seconds</span>
            </div>
            :
            <div className='flex flex-col'>
                {response.split('\n').map((line, index) => (
                    <span key={index} className='font-semibold text-gray-500'>
                        {line === '' ? '\u00A0' : line}
                    </span>
                ))}
            </div>
        }
    </div>
);
}

export default AnalyzeWorkouts;