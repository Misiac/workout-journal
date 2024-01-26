import React from 'react';

const NotSupported: React.FC<{
    minWidth: number
}> = (props) => {
    return (
        <div className="flex h-screen flex-col items-center justify-center bg-gray-100">
            <h1 className="mb-4 text-3xl font-bold text-red-500">This screen is not supported</h1>
            <p className="text-xl text-gray-700">{`The minimal width supported is ${props.minWidth}px`}</p>
            <p className="mt-4 text-lg text-gray-600">If you have resized your browser, please refresh the page.</p>
        </div>
    );
}

export default NotSupported;