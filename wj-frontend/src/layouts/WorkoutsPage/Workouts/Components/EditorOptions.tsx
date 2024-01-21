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
            response.json();
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
            <div className='flex items-center justify-center gap-4 h-full w-full'>
                <button onClick={handleModalOpen}
                        className="inline-flex items-center px-4 py-2 bg-red-500 hover:bg-red-600 text-white text-sm font-medium rounded-md">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24"
                         stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                    </svg>

                    Delete Workout
                </button>

                <button
                    className="inline-flex items-center px-4 py-2 bg-regal-blue hover:bg-blue-700 text-white text-sm font-medium rounded-md">
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24"
                         stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="3" d="M5 13l4 4L19 7"/>
                    </svg>

                    Save
                </button>

                {isModalOpen && (
                    <div className="fixed inset-0 flex items-center justify-center" aria-labelledby="modal-title"
                         role="dialog"
                         aria-modal="true">
                        <div
                            className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl border transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                            <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                                <div className="sm:flex sm:items-start">
                                    <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                        <h3 className="text-lg leading-6 font-medium text-gray-900"
                                            id="modal-title">
                                            Are you sure you want to delete this workout?
                                        </h3>
                                    </div>
                                </div>
                            </div>
                            <div className="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                                <button onClick={handleConfirmDelete} type="button"
                                        className="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-red-500 text-base font-medium text-white hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 sm:ml-3 sm:w-auto sm:text-sm">
                                    Yes
                                </button>
                                <button onClick={handleModalClose} type="button"
                                        className="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-regal-blue text-base font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:w-auto sm:text-sm">
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