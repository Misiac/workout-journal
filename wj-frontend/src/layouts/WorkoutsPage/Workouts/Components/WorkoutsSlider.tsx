import SliderCard from "./SliderCard";
import {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {WorkoutTiny} from "../../../../models/WorkoutTiny";

export const WorkoutsSlider:React.FC<{
    handleOpenModal:any
}> = (props, context) => {

    const {authState} = useOktaAuth();

    const [workouts, setWorkouts] = useState<WorkoutTiny[]>([])

    useEffect(() => {
        const fetchData = async () => {
            const url = 'http://localhost:8080/api/workout/tiny';
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
                const workoutInstances = data.map((workoutData: any) => new WorkoutTiny(workoutData.id, workoutData.date, workoutData.name));
                setWorkouts(workoutInstances);


            } catch (error) {
                console.error('Error fetching exercise data', error);
            }
        };
        fetchData();
        console.log("fetch list")

    }, [authState, context.sliderReloadTrigger]);

    return (

        <div className="h-full w-1/5 overflow-y-auto px-1 scroll-container">
            <div className="flex flex-col gap-4">

                {workouts.map((workout) => (
                    <SliderCard workout={workout} key={workout.id} handleOpenModal={props.handleOpenModal}/>
                ))}

            </div>
        </div>

    );
}
export default WorkoutsSlider;
