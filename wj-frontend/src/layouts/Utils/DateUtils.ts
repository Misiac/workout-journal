export function prettyFormatDate(dateString: string): string {

    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}-${month}-${year} ${hours}:${minutes}`;
}

export function getNameFromDate(date: Date): string {

    const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    const dayOfWeek = days[date.getDay()];

    let timeOfDay;
    const hour = date.getHours();

    if (hour < 12) {
        timeOfDay = 'Morning';
    } else if (hour < 18) {
        timeOfDay = 'Afternoon';
    } else {
        timeOfDay = 'Evening';
    }

    return `${dayOfWeek} ${timeOfDay} Workout`;
}

export function formatDateToInput(date: Date): string {

    return date.getFullYear() + '-' + String(date.getMonth() + 1)
        .padStart(2, '0') + '-' + String(date.getDate())
        .padStart(2, '0') + 'T' + String(date.getHours())
        .padStart(2, '0') + ':' + String(date.getMinutes())
        .padStart(2, '0');
}
