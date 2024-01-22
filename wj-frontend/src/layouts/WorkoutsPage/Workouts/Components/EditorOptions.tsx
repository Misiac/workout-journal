import {useContext, useState} from "react";
import {WorkoutExplorerContext} from "../../WorkoutExplorerContext.tsx";
import {useOktaAuth} from "@okta/okta-react";

export const EditorOptions = () => {

    const {authState} = useOktaAuth();

    const [isModalOpen, setIsModalOpen] = useState(false);

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
        context.setSelectedWorkoutId(0);
        context.setWorkoutName('');
        context.setWorkoutDate('');
        context.deleteTrigger.trigger();
    };

    const handleModalOpen = () => {
        setIsModalOpen(true);
    };

    const handleModalClose = () => {
        setIsModalOpen(false);
    };

    const handleConfirmDelete = () => {
        setIsModalOpen(false);

        deleteWorkout();

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
                    disabled={!context.wasChangeMade}
                    className={`inline-flex items-center px-4 py-2 text-white text-sm font-medium rounded-md fade-animation ${context.wasChangeMade ? 'bg-regal-blue hover:bg-blue-700' : 'bg-gray-400 cursor-not-allowed'}`}
                >
                    <svg xmlns="http://www.w3.org/2000/svg" className="mr-2 h-5 w-5" fill="none" viewBox="0 0 24 24"
                         stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="3" d="M5 13l4 4L19 7"/>
                    </svg>
                    Save
                </button>

                {/*Modal*/}
                {isModalOpen && (
                    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50"
                         aria-labelledby="modal-title"
                         role="dialog"
                         aria-modal="true">
                        <div
                            className="inline-block transform overflow-hidden rounded-lg border bg-white text-left align-bottom shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg sm:align-middle">
                            <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                                <div className="sm:flex sm:items-start">
                                    <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                        <h3 className="text-lg font-medium leading-6 text-gray-900"
                                            id="modal-title">
                                            Are you sure you want to delete this workout?
                                        </h3>
                                    </div>
                                </div>
                            </div>
                            <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                <button onClick={handleConfirmDelete} type="button"
                                        className="inline-flex w-full justify-center rounded-md border border-transparent bg-red-500 px-4 py-2 text-base font-medium text-white shadow-sm hover:bg-red-700 sm:ml-3 sm:w-auto sm:text-sm">
                                    Yes
                                </button>
                                <button onClick={handleModalClose} type="button"
                                        className="mt-3 inline-flex w-full justify-center rounded-md border border-gray-300 px-4 py-2 text-base font-medium text-white shadow-sm bg-regal-blue hover:bg-blue-700 sm:mt-0 sm:w-auto sm:text-sm">
                                    No
                                </button>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </>
    );
}
export default EditorOptions;