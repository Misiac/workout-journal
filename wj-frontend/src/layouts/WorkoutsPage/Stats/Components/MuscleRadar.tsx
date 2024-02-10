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
import React, {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {RadarData} from "../../../../models/RadarData";

ChartJS.register(
    RadialLinearScale,
    PointElement,
    LineElement,
    Filler,
    Tooltip,
    Legend
);
export const MuscleRadar: React.FC<{
    radarData: RadarData
}> = (props) => {

    const {authState} = useOktaAuth();
    const [chartData, setChartData] = useState({
        labels: [''],
        datasets: [
            {
                data: [0],
            },
        ],
    });

    useEffect(() => {

        const setRadarState = async () => {
            try {
                const data = props.radarData;

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
                }
            } catch (error) {
                console.error('Error setting chart data', error);
            }
        };

        setRadarState();

    }, [authState,props.radarData]);
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
            {
                chartData && <Radar data={chartData} options={chartOptions}/>
            }
        </div>
    );
}
export default MuscleRadar;

