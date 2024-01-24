import React from 'react';

const NotSupported: React.FC<{
    minWidth: number
}> = (props) => {
    return (
        <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
            <h1 className="text-3xl font-bold text-red-500 mb-4">This screen is not supported</h1>
            <p className="text-xl text-gray-700">{`The minimal width supported is ${props.minWidth}px`}</p>
            <p className="text-lg text-gray-600 mt-4">If you have resized your browser, please refresh the page.</p>
        </div>
    );
}

export default NotSupported;