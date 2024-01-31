export class Workout {
    id: number;
    date: string;
    name: string;
    workoutExercises: WorkoutExercise[];

    constructor(id: number, date: string, name: string, workoutExercises: WorkoutExercise[]) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.workoutExercises = workoutExercises;
    }
}

export class WorkoutExercise {
    id: number;
    exerciseType: ExerciseType;
    sequenceNumber: number;
    workoutExerciseSets: WorkoutExerciseSet[];

    constructor(id: number, exerciseType: ExerciseType, sequenceNumber: number, workoutExerciseSets: WorkoutExerciseSet[]) {
        this.id = id;
        this.exerciseType = exerciseType;
        this.sequenceNumber = sequenceNumber;
        this.workoutExerciseSets = workoutExerciseSets;
    }
}

export class ExerciseType {
    id: number;
    name: string;
    image: string;


    constructor(id: number, name: string, image: string) {
        this.id = id;
        this.name = name;
        this.image = image;
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

export default Workout;
