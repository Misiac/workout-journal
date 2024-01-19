export class RadarData {
    chest: number;
    shoulders: number;
    legs: number;
    core: number;
    back: number;
    arms: number;

    constructor(chest: number, shoulders: number, legs: number, core: number, back: number, arms: number) {
        this.chest = chest;
        this.shoulders = shoulders;
        this.legs = legs;
        this.core = core;
        this.back = back;
        this.arms = arms;
    }
}