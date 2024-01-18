import React, {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import TinyExercise from "../../../models/ExerciseTiny";
import img3 from "../../../resources/3.png"

export const ManageExerciseBindings: React.FC<{
    reloadTrigger: boolean
}> = (props) => {

    const {authState} = useOktaAuth();

    const [displayInfo, setDisplayInfo] = useState(false)
    const [responseInfo, setResponseInfo] = useState('')
    const [wasRequestSuccessful, setWasRequestSuccessful] = useState(false)
    const [currentBindOption, setCurrentBindOption] = useState('Unbind')
    const [currentCategoryOption, setCurrentCategoryOption] = useState('')

    const [exerciseOptions, setExerciseOptions] = useState<TinyExercise[]>([]);
    const [selectedExercise, setSelectedExercise] = useState('');

    const [isChecked, setIsChecked] = useState(false)

    const [name, setName] = useState('')

    const handleCheckboxChange = () => {
        setIsChecked(!isChecked)
        if (!isChecked) {
            setCurrentBindOption('Bind')
        } else {
            setCurrentBindOption('Unbind')
        }
    }
    useEffect(() => {
        const fetchData = async () => {
            const url = 'http://localhost:8080/api/exercises/tiny';
            const requestOptions = {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                }
            };

            try {
                const response = await fetch(url, requestOptions);

                if (!response.ok) {
                    throw new Error('Something went wrong!');
                }

                const data = await response.json();
                setExerciseOptions(data);
            } catch (error) {
                console.error('Error fetching exercise data', error);
            }
        };

        fetchData();
    }, [authState, props.reloadTrigger]);

    async function submitRequest() {
        const selectedExerciseId = exerciseOptions.find(exercise => exercise.name === selectedExercise)?.id;

        if (currentCategoryOption === '' || selectedExercise === '' || name === '' || selectedExerciseId === undefined) {
            setResponseInfo("Select the desired options")
            setDisplayInfo(true);
            return;
        }

        let url = 'http://localhost:8080/api/admin/exercise';
        url = url.concat(`/${selectedExerciseId}`)
        if (currentCategoryOption == 'Equipment') {
            url = url.concat('/equipment-categories')
        } else if (currentCategoryOption == 'Musclegroup') {
            url = url.concat('/muscle-categories')
        }

        url = url.concat('/', encodeURIComponent(name), '');

        if (currentCategoryOption == 'Musclegroup' && currentBindOption == 'Bind') {
            url = url.concat(`?isPrimary=${isChecked}`);
        }

        const requestOptions = {
            method: currentBindOption === 'Bind' ? 'PATCH' : 'DELETE',
            headers: {
                Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                'Content-Type': 'application/json'
            }
        };

        const response = await fetch(url, requestOptions)
        const responseText = await response.text();
        if (!response.ok) {
            setWasRequestSuccessful(false)
        } else {
            setWasRequestSuccessful(true);
            setName('');

        }
        setResponseInfo(responseText);
        setDisplayInfo(true);

    }

    return (
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8 flex items-center justify-center">
            <div className="w-full max-w-lg pr-4">
                <h1 className="text-3xl font-bold tracking-tight text-gray-900">Manage exercise bindings</h1>

                <label className='flex cursor-pointer select-none items-center py-6'>
                    <span className="px-3 font-bold">UNBIND</span>
                    <div className='relative'>
                        <input
                            type='checkbox'
                            checked={isChecked}
                            onChange={handleCheckboxChange}
                            className='sr-only'
                        />
                        <div
                            className={`box block h-8 w-14 rounded-full ${
                                isChecked ? 'bg-regal-blue' : 'bg-black'
                            }`}
                        ></div>
                        <div
                            className={`absolute left-1 top-1 flex h-6 w-6 items-center justify-center rounded-full bg-white transition ${
                                isChecked ? 'translate-x-full' : ''
                            }`}
                        ></div>
                    </div>
                    <span className="px-3 font-bold">BIND</span>
                </label>


                <div className="flex mb-4">
                    <label className="inline-flex items-center">
                        <input
                            type="radio"
                            className="form-radio"
                            value="Equipment category"
                            checked={currentCategoryOption === 'Equipment'}
                            onChange={() => setCurrentCategoryOption('Equipment')}
                        />
                        <span className="ml-2 font-bold">Equipment category</span>
                    </label>
                    <label className="inline-flex items-center ml-6">
                        <input
                            type="radio"
                            className="form-radio"
                            value="Muscle group category"
                            checked={currentCategoryOption === 'Musclegroup'}
                            onChange={() => setCurrentCategoryOption('Musclegroup')}
                        />
                        <span className="ml-2 font-bold">Musclegroup category</span>
                    </label>
                </div>
                <div className="w-full max-w-lg ">
                    <label htmlFor="exercises" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Select
                        an option</label>
                    <select id="exercises"
                            className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                            value={selectedExercise}
                            onChange={(e) => setSelectedExercise(e.target.value)}
                            defaultValue={1}>
                        <option value="" disabled>
                            Choose an exercise
                        </option>
                        {exerciseOptions.map((exercise) => (
                            <option key={exercise.id} value={exercise.name}>
                                {exercise.name}
                            </option>
                        ))}
                    </select>
                </div>


                <form className="w-full max-w-lg py-5">
                    <div className="flex flex-wrap -mx-3">
                        <div className="w-full md:w-1/2 px-3 mb-4 md:mb-0">
                            <label className="block uppercase tracking-wide text-gray-700 text-m font-bold mb-2"
                                   htmlFor="grid-first-name">
                                Category name
                            </label>
                            <input
                                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
                                id="grid-first-name" type="text" placeholder="Category"
                                onChange={e => setName(e.target.value)} value={name}/>
                            {currentCategoryOption === 'Musclegroup' && currentBindOption === 'Bind' &&
                                <>

                                    <input
                                        className="mt-2 ml-2"
                                        id="muscle-group-checkbox"
                                        type="checkbox"
                                    />
                                    <label className="text-gray-700 text-xs ml-1"
                                           htmlFor="muscle-group-checkbox">Primary?</label>
                                </>
                            }


                        </div>
                    </div>

                    {displayInfo && wasRequestSuccessful &&
                        <div className="bg-green-100 border border-gray-200 text-green-700 px-4 py-3 rounded relative"
                             role="alert">
                            <span className="block sm:inline">{responseInfo}</span>
                        </div>

                    }
                    {displayInfo && !wasRequestSuccessful &&
                        <div className="bg-red-100 border border-gray-200 text-red-700 px-4 py-3 rounded relative"
                             role="alert">
                            <span className="block sm:inline">{responseInfo}</span>
                        </div>
                    }

                    <div className="flex flex-wrap -mx-3">
                        <div className="w-full md:w-1/2 px-3 py-3 mb-6 md:mb-0">
                            {/* Create Button */}
                            <button
                                className="bg-regal-blue text-white py-2 px-4 rounded focus:outline-none focus:shadow-outline-blue hover:bg-blue-700"
                                type="button" onClick={() => submitRequest()}
                            >
                                {currentBindOption === '' && 'Create'}
                                {currentBindOption && currentBindOption}
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div className="ml-auto">
                <img src={img3} alt="Exercise" className="w-250 h-256 object-cover"/>
            </div>
        </div>
    )

}
export default ManageExerciseBindings;