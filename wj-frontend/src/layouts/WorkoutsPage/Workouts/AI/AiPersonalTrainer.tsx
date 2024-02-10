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
            <button onClick={handleModalOpen} className="rounded-lg font-semibold ai-btn-grad">
                ✨ AI Personal Trainer
            </button>
{isModalOpen && (
    <div className="fixed inset-0 z-10 flex items-center justify-center overflow-y-auto"
         aria-labelledby="modal-title" role="dialog"
         aria-modal="true">
        <div className="fixed inset-0 bg-black bg-opacity-50 transition-opacity"
             aria-hidden="true"></div>
        <div
             className="relative inline-block max-w-3/4 md:max-w-3/4 lg:max-w-3/4 xl:max-w-2/3 transform overflow-hidden rounded-lg bg-white text-left align-middle shadow-xl transition-all">
            <button onClick={handleModalClose}
                    className="absolute top-0 right-0 m-4 text-gray-500 hover:text-gray-700">
                <X/>
            </button>
            <div className="flex h-full flex-col justify-between bg-white">
                <div className="mb-4 bg-gray-50 py-5 text-center">
                    <h2 className="text-2xl font-bold">AI Personal Trainer</h2>
                </div>
                <div className="flex h-full flex-col items-center justify-between p-10 overflow-y-auto max-h-[700px]">
                    {selectedOption === null && (
                        <>
                            <button
                                className='px-4 py-2 font-semibold text-blue-600'
                                onClick={() => handleOptionSelect('analyzeWorkouts')}>
                                Analyze Workouts
                            </button>
                            <button
                                className='px-4 py-2 font-semibold text-blue-600'
                                onClick={() => handleOptionSelect('generatePlan')}>
                                Generate Personalized Training Plan
                            </button>
                        </>
                    )}
                    {selectedOption === 'analyzeWorkouts' && <AnalyzeWorkouts/>}
                    {selectedOption === 'generatePlan' && <GeneratePlan/>}
                </div>
                <div className="mt-4 bg-gray-50 px-4 py-7 text-center">
                    {selectedOption !== null &&
                        <button onClick={getBack}
                                className="absolute bottom-4 left-5 flex flex-row font-semibold text-blue-600">
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