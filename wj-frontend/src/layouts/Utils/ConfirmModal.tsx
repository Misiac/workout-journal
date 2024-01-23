import React from "react";
import {createRoot} from "react-dom/client";

interface ConfirmModalProps {
    message: string;
    onConfirm: () => void;
    onCancel: () => void;
}

const ConfirmModal: React.FC<ConfirmModalProps> = ({message, onConfirm, onCancel}) => {
    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-10"
             aria-labelledby="modal-delete"
             role="dialog"
             aria-modal="true">
            <div
                className="inline-block transform overflow-hidden rounded-lg border bg-white text-left align-bottom shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg sm:align-middle">
                <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <div className="sm:flex sm:items-start">
                        <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                            <h3 className="text-lg font-medium leading-6 text-gray-900"
                                id="modal-title">
                                {message}
                            </h3>
                        </div>
                    </div>
                </div>
                <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                    <button onClick={onConfirm} type="button"
                            className="inline-flex w-full justify-center rounded-md border border-transparent bg-red-500 px-4 py-2 text-base font-medium text-white shadow-sm hover:bg-red-700 sm:ml-3 sm:w-auto sm:text-sm">
                        Yes
                    </button>
                    <button onClick={onCancel} type="button"
                            className="mt-3 inline-flex w-full justify-center rounded-md border border-gray-300 px-4 py-2 text-base font-medium text-white shadow-sm bg-regal-blue hover:bg-blue-700 sm:mt-0 sm:w-auto sm:text-sm">
                        No
                    </button>
                </div>
            </div>
        </div>
    );
};

export function confirmModal(message: string): Promise<boolean> {
    return new Promise((resolve) => {
        const modalRoot = document.createElement('div');
        const root = createRoot(modalRoot);
        document.body.appendChild(modalRoot);

        const onConfirm = () => {
            resolve(true);
            root.unmount();
            document.body.removeChild(modalRoot);
        };

        const onCancel = () => {
            resolve(false);
            root.unmount();
            document.body.removeChild(modalRoot);
        };


        root.render(<ConfirmModal message={message} onConfirm={onConfirm} onCancel={onCancel}/>);
    });
}