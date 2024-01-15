import {
    Chart as ChartJS,
    RadialLinearScale,
    PointElement,
    LineElement,
    Filler,
    Tooltip,
    Legend
} from 'chart.js';
import {Radar} from 'react-chartjs-2';
import {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import RadarData from "../../../../models/RadarData";
import ProcessingSpinner from "../../../Utils/ProcessingSpinner";

ChartJS.register(
    RadialLinearScale,
    PointElement,
    LineElement,
    Filler,
    Tooltip,
    Legend
);
export const MuscleRadar = () => {

    const {authState} = useOktaAuth();
    const [isLoading, setIsLoading] = useState(true);
    const [chartData, setChartData] = useState({
        labels: [''],
        datasets: [
            {
                data: [0],
            },
        ],
    });


    useEffect(() => {
        setIsLoading(true);
        const fetchData = async (): Promise<RadarData | undefined> => {
            const url = 'http://localhost:8080/api/stats/radar';
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

                return await response.json();
            } catch (error) {
                console.error('Error fetching data', error);
                return undefined;
            }
        };

        const fetchDataAndSetState = async () => {
            try {
                const data = await fetchData();

                if (data) {
                    const updatedChartData = {
                        labels: ['Chest', 'Shoulders', 'Legs', 'Core', 'Back', 'Arms'],
                        datasets: [
                            {
                                label: 'Reps',
                                data: [data.chest, data.shoulders, data.legs, data.core, data.back, data.arms],
                                backgroundColor: 'rgba(40, 141, 255, 0.5)',
                                borderColor: 'rgba(30, 58, 138, 1)',
                                borderWidth: 1,
                            },
                        ],
                    };
                    setChartData(updatedChartData);
                    setIsLoading(false);
                }
            } catch (error) {
                console.error('Error setting chart data', error);
            }
        };

        fetchDataAndSetState();

    }, [authState]);
    const chartOptions: {
        plugins: {
            legend: {
                display: false;
            };
            title: { display: false };
        };
        scales: {
            r: {
                beginAtZero: true;
                ticks: { display: false };
                pointLabels: { font: { size: 13 } }
            }
        };
    } = {
        plugins: {
            legend: {display: false},
            title: {display: false},
        },
        scales: {
            r: {
                beginAtZero: true,
                ticks: {display: false},
                pointLabels: {font: {size: 13}}
            }
        },
    };

    return (


        <div>
            {isLoading ? (
                <ProcessingSpinner/>
            ) : (
                chartData && <Radar data={chartData} options={chartOptions}/>
            )}
        </div>
    );
}
export default MuscleRadar;

