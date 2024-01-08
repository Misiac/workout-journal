import React from "react";

export const CreateExercise = () => {
    return (
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
            <h1 className="text-3xl font-bold tracking-tight text-gray-900">Create new exercise</h1>

            <form className="w-full max-w-lg py-5">
                <div className="flex flex-wrap -mx-3 mb-4">
                    <div className="w-full md:w-1/2 px-3 mb-4 md:mb-0">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-first-name">
                            Name
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-red-500 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
                            id="grid-first-name" type="text" placeholder="Lat Raise"/>
                    </div>
                </div>

                <div className="flex flex-wrap -mx-3 mb-6">
                    <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-city">
                            Equipment categories
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-city" type="text" placeholder="1"/>
                    </div>

                    <div className="w-full md:w-1/2 px-3 md:mb-0 relative">
                        <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                               htmlFor="grid-city">
                            Muscle group categories
                        </label>
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-city" type="text" placeholder="1"/>

                        {/* Checkbox for Muscle group categories */}
                        <input
                            className="mt-2 ml-2"
                            id="muscle-group-checkbox"
                            type="checkbox"
                        />
                        <label className="text-gray-700 text-xs ml-1" htmlFor="muscle-group-checkbox">Primary?</label>
                    </div>
                </div>

                <div className="flex flex-wrap -mx-3 mb-6">
                    <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-city" type="text" placeholder="2"/>
                    </div>

                    <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0 relative">
                        <input
                            className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                            id="grid-city" type="text" placeholder="2"/>

                        {/* Checkbox for Additional Category 2 */}
                        <input
                            className="mt-2 ml-2"
                            id="additional-category-checkbox"
                            type="checkbox"
                        />
                        <label className="text-gray-700 text-xs ml-1"
                               htmlFor="additional-category-checkbox">Primary?</label>
                    </div>
                </div>

                <div className="flex flex-wrap -mx-3">
                    <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                        {/* Create Button */}
                        <button
                            className="bg-regal-blue text-white py-2 px-4 rounded focus:outline-none focus:shadow-outline-blue hover:bg-blue-700"
                            type="button"
                        >
                            Create
                        </button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default CreateExercise;
