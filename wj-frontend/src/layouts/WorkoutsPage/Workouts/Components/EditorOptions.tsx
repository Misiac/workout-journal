import {useContext} from "react";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import {useOktaAuth} from "@okta/okta-react";
import {confirmModal} from "../../../Utils/ConfirmModal.tsx";

export const EditorOptions = () => {

    const {authState} = useOktaAuth();

    const context = useContext(WorkoutExplorerContext);
    if (!context) {
        throw new Error('Component must be used within a WorkoutExplorerContext Provider')
    }

    const deleteWorkout = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/workout/${context.selectedWorkoutId}`, {
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
            workoutName: '',
            workoutDate: '',
            sliderReloadTrigger: prevState.sliderReloadTrigger + 1
        }));
    };

    const deleteExercises = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/workout/exercise", {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(context.deletedExercises)
            });
            if (!response.ok) throw new Error('Something went wrong!');
        } catch (error) {
            console.error('Error deleting workout', error);
        }
    };

    const updateExercises = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/workout/exercise", {
                method: 'PUT',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(context.editedExercises)
            });
            if (!response.ok) throw new Error('Something went wrong!');
        } catch (error) {
            console.error('Error deleting workout', error);
        }
    };

    const handleModalOpen = async () => {
        const confirm = await confirmModal('Are you sure you want to delete this workout?');
        if (confirm) {
            deleteWorkout();
        }
    };

    const handleSave = () => {

        deleteExercises();
        updateExercises();


        context.setState(prevState => ({
            ...prevState,
            wasChangeMade: false,
            isEditModeOn: !prevState.isEditModeOn,
            editedExercises: [],
            deletedExercises: []
        }));
    };
    return (
        <>
            <div className='flex h-full w-full items-center justify-center gap-4'>
                <button onClick={handleModalOpen}
                        className="inline-flex items-center rounded-md bg-red-500 px-4 py-2 text-sm font-medium text-white fade-animation hover:bg-red-700">
                    <svg xmlns="http://www.w3.org/2000/svg" className="mr-2 h-5 w-5" fill="none" viewBox="0 0 24 24"
                         stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                    </svg>
                    Delete Workout
                </button>

                <button
                    disabled={!context.wasChangeMade} onClick={handleSave}
                    className={`inline-flex items-center px-4 py-2 text-white text-sm font-medium rounded-md fade-animation ${context.wasChangeMade ? 'bg-regal-blue hover:bg-blue-700' : 'bg-gray-400 cursor-not-allowed'}`}
                >
                    <svg xmlns="http://www.w3.org/2000/svg" className="mr-2 h-5 w-5" fill="none" viewBox="0 0 24 24"
                         stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="3" d="M5 13l4 4L19 7"/>
                    </svg>
                    Save
                </button>
            </div>
        </>
    );
}
export default EditorOptions;