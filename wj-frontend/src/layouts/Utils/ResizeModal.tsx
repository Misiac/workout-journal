import React from "react";

export const ResizeModal: React.FC<{
    minWidth: number
}> = (props) => {
    return (
        <div className="fixed inset-0 z-10 flex items-center justify-center bg-black bg-opacity-50"
             aria-labelledby="modal-delete"
             role="dialog"
             aria-modal="true">
            <div className="my-8 inline-block :w-full max-w-lg transform overflow-hidden rounded-lg border bg-white align-middle shadow-xl transition-all">
                <div className="flex items-center justify-center bg-white p-6">
                    <h3 className="text-center text-2xl font-medium leading-6"
                        id="modal-title">
                        {`This app does not support this screen, please resize to at least ${props.minWidth}px`}
                    </h3>
                </div>
            </div>
        </div>
    );
};
export default ResizeModal;