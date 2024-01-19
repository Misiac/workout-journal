export class WorkoutTiny {
    id: number;
    date: string;
    name: string;

    constructor(id: number, date: string, name: string) {
        this.id = id;
        this.date = this.formatDateString(date);
        this.name = name;

    }

    private formatDateString(originalDateString: string): string {
        const dateObject = new Date(originalDateString);
        return dateObject.toLocaleString('en-GB', {
            day: 'numeric',
            month: 'numeric',
            year: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
            hour12: false,
        });
    }
}