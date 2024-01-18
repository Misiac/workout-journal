export class WorkoutExercise {
    counter: number;
    name: string;
    exerciseId: number;
    entry: WorkoutExerciseSet[];

    constructor(counter: number, name: string, exerciseId: number, entry: WorkoutExerciseSet[]) {
        this.counter = counter;
        this.name = name;
        this.exerciseId = exerciseId;
        this.entry = entry;
    }
}

export class WorkoutExerciseSet {
    id: number;
    load: number;
    reps: number;
    setNumber: number;

    constructor(id: number, load: number, reps: number, setNumber: number) {
        this.id = id;
        this.load = load;
        this.reps = reps;
        this.setNumber = setNumber;
    }
}

export default {WorkoutExercise, WorkoutExerciseSet};