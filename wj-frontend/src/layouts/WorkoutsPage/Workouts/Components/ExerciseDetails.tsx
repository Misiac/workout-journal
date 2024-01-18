import {WorkoutExerciseSet} from "../../../../models/WorkoutExercise";

export const ExerciseDetails: React.FC<{
    set: WorkoutExerciseSet
}> = (props) => {

    return (

        <tr>
            <td className="border-b border-slate-300">{props.set.setNumber}</td>
            <td className="border-b border-slate-300">{props.set.load}</td>
            <td className="border-b border-slate-300">{props.set.reps}</td>
        </tr>

    );
}

export default ExerciseDetails;