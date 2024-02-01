import {useState} from 'react';
import AnalyzeWorkouts from "./Components/AnalyzeWorkouts.tsx";

import {ArrowLeft, X} from "lucide-react";
import GeneratePlan from "./Components/GeneratePlan.tsx";

export const AiPersonalTrainer = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedOption, setSelectedOption] = useState(null);

    const handleModalOpen = () => {
        setIsModalOpen(true);
    };

    const handleModalClose = () => {
        setIsModalOpen(false);
        setSelectedOption(null);
    };

    const handleOptionSelect = (option: any) => {
        setSelectedOption(option);
    };

    const getBack = () => {
        setSelectedOption(null);
    }

    return (
        <>
            <button onClick={handleModalOpen} className="ai-btn-grad rounded-lg font-semibold">
                ✨ AI Personal Trainer
            </button>
            {isModalOpen && (
                <div className="fixed z-10 inset-0 overflow-y-auto flex items-center justify-center"
                     aria-labelledby="modal-title" role="dialog"
                     aria-modal="true">
                    <div className="fixed inset-0 bg-black bg-opacity-50 transition-opacity"
                         aria-hidden="true"></div>
                    <div
                        className="inline-block align-middle bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all w-1/4 h-auto relative">
                        <button onClick={handleModalClose}
                                className="absolute top-0 right-0 m-4 text-gray-500 hover:text-gray-700">
                            <X/>
                        </button>
                        <div className="bg-white flex flex-col justify-between h-full">
                            <div className="text-center mb-4 bg-gray-50 py-5">
                                <h2 className="text-2xl font-bold">AI Personal Trainer</h2>
                            </div>
                            <div className="flex flex-col items-center justify-between h-full py-10">
                                {selectedOption === null && (
                                    <>
                                        <button
                                            className='text-blue-600 font-semibold px-4 py-2'
                                            onClick={() => handleOptionSelect('analyzeWorkouts')}>
                                            Analyze Workouts
                                        </button>
                                        <button
                                            className='text-blue-600 font-semibold px-4 py-2'
                                            onClick={() => handleOptionSelect('generatePlan')}>
                                            Generate Personalized Training Plan
                                        </button>
                                    </>
                                )}
                                {selectedOption === 'analyzeWorkouts' && <AnalyzeWorkouts/>}
                                {selectedOption === 'generatePlan' && <GeneratePlan/>}
                            </div>
                            <div className="mt-4 text-center bg-gray-50 px-4 py-7">
                                {selectedOption !== null &&
                                    <button onClick={getBack}
                                            className=" text-blue-600 font-semibold flex flex-row absolute bottom-4 left-5">
                                        <ArrowLeft/> Back
                                    </button>}
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
}

export default AiPersonalTrainer;