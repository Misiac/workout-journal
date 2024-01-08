class AdminCreateExerciseRequest {
    name: string;
    equipmentCategories: string[];
    muscleGroups: MuscleGroupRequest[];

    constructor(
        name: string,
        equipmentCategories: string[],
        muscleGroups: MuscleGroupRequest[]
    ) {
        this.name = name;
        this.equipmentCategories = equipmentCategories;
        this.muscleGroups = muscleGroups;
    }
}

class MuscleGroupRequest {
    name: string;
    isPrimary: boolean;

    constructor(name: string, isPrimary: boolean) {
        this.name = name;
        this.isPrimary = isPrimary;
    }

}

export {AdminCreateExerciseRequest, MuscleGroupRequest};
