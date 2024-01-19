import React, {useEffect, useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import img3 from "../../../resources/3.png"
import {ExerciseTiny} from "../../../models/ExerciseTiny";

export const ManageExerciseBindings: React.FC<{
    reloadTrigger: boolean
}> = (props) => {

    const {authState} = useOktaAuth();

    const [displayInfo, setDisplayInfo] = useState(false)
    const [responseInfo, setResponseInfo] = useState('')
    const [wasRequestSuccessful, setWasRequestSuccessful] = useState(false)
    const [currentBindOption, setCurrentBindOption] = useState('Unbind')
    const [currentCategoryOption, setCurrentCategoryOption] = useState('')

    const [exerciseOptions, setExerciseOptions] = useState<ExerciseTiny[]>([]);
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
        if (currentCategoryOption === 'Equipment') {
            url = url.concat('/equipment-categories')
        } else if (currentCategoryOption === 'Musclegroup') {
            url = url.concat('/muscle-categories')
        }

        url = url.concat('/', encodeURIComponent(name), '');

        if (currentCategoryOption === 'Musclegroup' && currentBindOption === 'Bind') {
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
        <div className="mx-auto flex max-w-7xl items-center justify-center px-4 py-6 sm:px-6 lg:px-8">
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
                        p
                        <div
                            className={`absolute left-1 top-1 flex h-6 w-6 items-center justify-center rounded-full bg-white transition ${
                                isChecked ? 'translate-x-full' : ''
                            }`}
                        ></div>
                    </div>
                    <span className="px-3 font-bold">BIND</span>
                </label>


                <div className="mb-4 flex">
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
                    <label className="ml-6 inline-flex items-center">
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
                <div className="w-full max-w-lg">
                    <label htmlFor="exercises" className="mb-2 block text-sm font-medium text-gray-900 dark:text-white">Select
                        an option</label>
                    <select id="exercises"
                            className="block w-full rounded-lg border border-gray-300 bg-gray-50 text-sm text-gray-900 p-2.5 focus:border-blue-500 focus:ring-blue-500"
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
                    <div className="-mx-3 flex flex-wrap">
                        <div className="mb-4 w-full px-3 md:mb-0 md:w-1/2">
                            <label className="mb-2 block font-bold uppercase tracking-wide text-gray-700 text-m"
                                   htmlFor="grid-first-name">
                                Category name
                            </label>
                            <input
                                className="mb-3 block w-full appearance-none rounded border border-gray-200 bg-gray-200 px-4 py-3 leading-tight text-gray-700 focus:bg-white focus:outline-none"
                                id="grid-first-name" type="text" placeholder="Category"
                                onChange={e => setName(e.target.value)} value={name}/>
                            {currentCategoryOption === 'Musclegroup' && currentBindOption === 'Bind' &&
                                <>

                                    <input
                                        className="mt-2 ml-2"
                                        id="muscle-group-checkbox"
                                        type="checkbox"
                                    />
                                    <label className="ml-1 text-xs text-gray-700"
                                           htmlFor="muscle-group-checkbox">Primary?</label>
                                </>
                            }
                        </div>
                    </div>

                    {displayInfo && wasRequestSuccessful &&
                        <div className="relative rounded border border-gray-200 bg-green-100 px-4 py-3 text-green-700"
                             role="alert">
                            <span className="block sm:inline">{responseInfo}</span>
                        </div>

                    }
                    {displayInfo && !wasRequestSuccessful &&
                        <div className="relative rounded border border-gray-200 bg-red-100 px-4 py-3 text-red-700"
                             role="alert">
                            <span className="block sm:inline">{responseInfo}</span>
                        </div>
                    }

                    <div className="-mx-3 flex flex-wrap">
                        <div className="mb-6 w-full px-3 py-3 md:mb-0 md:w-1/2">
                            {/* Create Button */}
                            <button
                                className="rounded px-4 py-2 text-white bg-regal-blue hover:bg-blue-700 focus:shadow-outline-blue focus:outline-none"
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
                <img src={img3} alt="Exercise" className="object-cover w-250 h-256"/>
            </div>
        </div>
    )
}
export default ManageExerciseBindings;