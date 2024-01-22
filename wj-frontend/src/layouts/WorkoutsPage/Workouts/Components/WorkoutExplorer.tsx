import Exercise from "./Exercise";
import {useContext, useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import {WorkoutExercise, WorkoutExerciseSet} from "../../../../models/WorkoutExercise";
import WorkoutTotals from "./WorkoutTotals.tsx";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import EditorOptions from "./EditorOptions.tsx";


export const WorkoutExplorer = () => {

    const {authState} = useOktaAuth();

    const context = useContext(WorkoutExplorerContext);

    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const {selectedWorkoutId, workoutName, workoutDate, isEditModeOn} = context;

    const [exercises, setExercises] = useState<WorkoutExercise[]>();

    const [totalExercises, setTotalExercises] = useState(0);
    const [totalSets, setTotalSets] = useState(0);
    const [totalReps, setTotalReps] = useState(0);
    const [tvl, setTvl] = useState(0);

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
                const url = `http://localhost:8080/api/workout/${selectedWorkoutId}`;
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
            if (selectedWorkoutId !== 0) {
                fetchData();
            } else {
                setExercises([]);
            }
        }
        ,
        [authState, selectedWorkoutId]);

    useEffect(() => {
        if (selectedWorkoutId !== 0) {
            const totalExercises: number = exercises?.length ?? 0;

            let totalSets: number = 0;
            let totalReps: number = 0;
            let tvl: number = 0;

            exercises?.forEach((exercise: WorkoutExercise) => {
                totalSets += exercise.entry.length;
                exercise.entry.forEach((set: WorkoutExerciseSet) => {
                    totalReps += set.reps;
                    tvl += set.reps * set.load;
                })
            })
            setTotalExercises(totalExercises);
            setTotalSets(totalSets);
            setTotalReps(totalReps);
            setTvl(tvl);
        }

    }, [exercises]);

    return (
        <>
            <div className="w-full overflow-y-auto">
                <div className="flex w-full items-start gap-2 px-2 py-4 h-[100px] fade-animation">
                    <div className='w-1/2'>
                        <p className='text-2xl font-bold'> {workoutName}</p>
                        <p> {workoutDate}</p>
                    </div>
                    <div className='h-full w-1/2'>

                        {selectedWorkoutId !== 0 &&
                            (isEditModeOn ? <EditorOptions/> :
                                <WorkoutTotals totalExercises={totalExercises} totalSets={totalSets}
                                               totalReps={totalReps}
                                               tvl={tvl}/>)
                        }

                    </div>
                </div>

                {selectedWorkoutId !== 0 && <hr/>}

                <div className="grid grid-cols-2 gap-y-4 px-4 pt-4">

                    {exercises?.map((exercise) => (
                        <Exercise exercise={exercise} key={exercise.counter}/>
                    ))}

                </div>
            </div>
        </>
    )
        ;
}
export default WorkoutExplorer;