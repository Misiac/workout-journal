export class AiPlanRequest {
    constructor(
        public goal: string,
        public level: string,
        public daysPerWeek: string,
        public gender: string,
        public weight: string,
        public age: string,
        public height: string,
        public health: string,
        public intensity: string,
        public specialGoal: string
    ) {
    }
}