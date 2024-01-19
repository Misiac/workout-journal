import Exercise from "./Exercise";
import React, {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {WorkoutExercise, WorkoutExerciseSet} from "../../../../models/WorkoutExercise";


export const WorkoutExplorer: React.FC<{
    selected: number,
}> = (props) => {

    const {authState} = useOktaAuth();

    const [exercises, setExercises] = useState<WorkoutExercise[]>();

    interface ExerciseType {
        id: number;
        name: string;
    }

    interface ResponseExercise {
        id: number;
        load: number;
        reps: number;
        setNumber: number;
        exerciseType: ExerciseType;
    }

    const parseExercises = (response: ResponseExercise[]) => {
        const exercises: WorkoutExercise[] = [];
        let wes: WorkoutExerciseSet[] = [];
        let counter = 1;

        response.forEach((we: ResponseExercise, i: number) => {
            wes.push(new WorkoutExerciseSet(we.id, we.load, we.reps, we.setNumber));

            if (i === response.length - 1 || we.exerciseType.id !== response[i + 1]?.exerciseType.id) {
                exercises.push(new WorkoutExercise(counter, we.exerciseType.name, we.exerciseType.id, wes));
                wes = [];
                counter++;
            }
        });

        return exercises;
    };

    useEffect(() => {

        const fetchData = async () => {
            const url = `http://localhost:8080/api/workout/${props.selected}`;
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

                setExercises(
                    parseExercises(data.workoutExercises)
                );

            } catch (error) {
                console.error('Error fetching exercise data', error);
            }
        };
        if (props.selected !== 0) {
            fetchData();
        }
    }, [authState, props.selected]);

    return (
        <>
            <div className="w-full overflow-y-auto">
                <div className="grid grid-cols-2 gap-y-4 px-4">

                    {exercises?.map((exercise) => (
                        <Exercise exercise={exercise} key={exercise.counter}/>
                    ))}

                </div>
            </div>
        </>
    );
}
export default WorkoutExplorer;