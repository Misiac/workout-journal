import {useContext} from "react";
import {WorkoutExplorerContext} from "../../../WorkoutExplorerContext.tsx";
import {useOktaAuth} from "@okta/okta-react";
import {confirmModal} from "../../../../Utils/ConfirmModal.tsx";
import {Check, Trash2} from "lucide-react";

export const EditorOptions = () => {

    const {authState} = useOktaAuth();

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const deleteWorkout = async () => {

        try {
            const response = await fetch(`${import.meta.env.VITE_API_ADDRESS}/api/workouts/${context.selectedWorkoutId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) throw new Error('Something went wrong!');
        } catch (error) {
            console.error('Error deleting workout', error);
        }
        context.setState(prevState => ({
            ...prevState,
            selectedWorkoutId: 0,
            workout: null,
            isEditModeOn: false,
            sliderReloadTrigger: prevState.sliderReloadTrigger + 1
        }));
    };

    const saveWorkout = async () => {
        const isNew = context.selectedWorkoutId < 0;
        try {
            const response = await fetch(`${import.meta.env.VITE_API_ADDRESS}/api/workouts`, {
                method: isNew ? 'POST' : 'PUT',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(context.workout)
            });
            if (!response.ok) {
                const responseText = await response.text()
                throw new Error(responseText);
            }
        } catch (error) {
            console.error('Error updating workout', error);
            context.setState(prevState => ({
                ...prevState,
                workoutReloadTrigger: prevState.workoutReloadTrigger + 1
            }));
        }
    };

    const handleDeleteClick = async () => {
        const confirm = await confirmModal('Are you sure you want to delete this workout?');
        if (confirm) {
            if (context.selectedWorkoutId < 0) {

                context.setState(prevState => ({
                    ...prevState,
                    selectedWorkoutId: 0,
                    workout: null,
                    isEditModeOn: false
                }));
                return;
            } else {
                await deleteWorkout();
            }
        }
    };

    const handleSave = () => {

        saveWorkout();
        context.setState(prevState => ({
            ...prevState,
            wasChangeMade: false,
            isEditModeOn: !prevState.isEditModeOn
        }));
    };
    return (
        <>
            <div className='flex h-full w-full items-center justify-center gap-4'>
                <button onClick={handleDeleteClick}
                        className="inline-flex items-center rounded-md bg-red-500 px-4 py-2 text-sm font-medium text-white transition-colors duration-300 fade-animation hover:bg-red-700">
                    <Trash2 className='mr-2'/>
                    Delete Workout
                </button>

                <button
                    disabled={!context.wasChangeMade} onClick={handleSave}
                    className={`inline-flex items-center px-4 py-2 text-white text-sm font-medium rounded-md transition-colors duration-300 fade-animation ${context.wasChangeMade ? 'bg-regal-blue hover:bg-blue-700' : 'bg-gray-400 cursor-not-allowed'}`}
                >
                    <Check className='mr-2'/>
                    Save
                </button>
            </div>
        </>
    );
}
export default EditorOptions;