import React, {useState} from "react";
import {AdminCreateExerciseRequest, MuscleGroupRequest} from "../../../models/AdminCreateExerciseModel";
import {useOktaAuth} from "@okta/okta-react";
import img1 from "../../../resources/1.png"

export const CreateExercise: React.FC<{
    reloadTrigger: boolean
    setReloadTrigger: any
}> = (props) => {

    const {authState} = useOktaAuth();

    const [displayInfo, setDisplayInfo] = useState(false)
    const [responseInfo, setResponseInfo] = useState('')
    const [wasRequestSuccessful, setWasRequestSuccessful] = useState(false)

    const [name, setName] = useState('')

    const [eqCat1, setEqCat1] = useState('')
    const [eqCat2, setEqCat2] = useState('')

    const [muscleCat1, setMuscleCat1] = useState('')
    const [muscleCat2, setMuscleCat2] = useState('')
    const [isPrimary1, setIsPrimary1] = useState(false)
    const [isPrimary2, setIsPrimary2] = useState(false)

    function handleIsPrimary1Change() {
        setIsPrimary1(!isPrimary1)
    }

    function handleIsPrimary2Change() {
        setIsPrimary2(!isPrimary2)
    }

    async function handleCreateButton() {


        const request = new AdminCreateExerciseRequest(name, [eqCat1, eqCat2], [
            new MuscleGroupRequest(muscleCat1, isPrimary1), new MuscleGroupRequest(muscleCat2, isPrimary2)
        ])

        const url = 'http://localhost:8080/api/admin/exercise'
        const requestOptions = {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request)
        };

        const response = await fetch(url, requestOptions)
        const responseText = await response.text();
        if (!response.ok) {
            setWasRequestSuccessful(false)
        } else {
            setWasRequestSuccessful(true);

            props.setReloadTrigger(!props.reloadTrigger);

            setName('');
            setEqCat1('');
            setEqCat2('');
            setMuscleCat1('');
            setMuscleCat2('');
            setIsPrimary1(false);
            setIsPrimary2(false);

        }

        setResponseInfo(responseText);
        setDisplayInfo(true);

    }

    return (
        <div className="mx-auto flex max-w-7xl items-center justify-center px-4 py-6 sm:px-6 lg:px-8">

            <div className="w-full max-w-lg pr-4">
                <h1 className="text-3xl font-bold tracking-tight text-gray-900">Create new exercise</h1>

                <form className="w-full max-w-lg py-5">

                    <div className="-mx-3 mb-4 flex flex-wrap">
                        <div className="mb-4 w-full px-3 md:mb-0 md:w-1/2">
                            <label className="mb-2 block font-bold uppercase tracking-wide text-gray-700 text-m"
                                   htmlFor="grid-first-name">
                                Name
                            </label>
                            <input
                                className="mb-3 block w-full appearance-none rounded border border-gray-200 bg-gray-200 px-4 py-3 leading-tight text-gray-700 focus:bg-white focus:outline-none"
                                id="grid-first-name" type="text" placeholder="Lat Raise"
                                onChange={e => setName(e.target.value)} value={name}/>
                        </div>
                    </div>

                    <div className="-mx-3 mb-6 flex flex-wrap">
                        <div className="mb-6 w-full px-3 md:mb-0 md:w-1/2">
                            <label className="mb-2 block text-xs font-bold uppercase tracking-wide text-gray-700"
                                   htmlFor="grid-city">
                                Equipment categories
                            </label>
                            <input
                                className="block w-full appearance-none rounded border border-gray-200 bg-gray-200 px-4 py-3 leading-tight text-gray-700 focus:bg-white focus:outline-none"
                                id="grid-city" type="text" placeholder="1" onChange={e => setEqCat1(e.target.value)}
                                value={eqCat1}/>
                        </div>

                        <div className="relative w-full px-3 md:mb-0 md:w-1/2">
                            <label className="mb-2 block text-xs font-bold uppercase tracking-wide text-gray-700"
                                   htmlFor="grid-city">
                                Muscle group categories
                            </label>
                            <input
                                className="block w-full appearance-none rounded border border-gray-200 bg-gray-200 px-4 py-3 leading-tight text-gray-700 focus:bg-white focus:outline-none"
                                id="grid-city" type="text" placeholder="1" onChange={e => setMuscleCat1(e.target.value)}
                                value={muscleCat1}/>

                            {/* Checkbox for Muscle group categories */}
                            <input
                                className="mt-2 ml-2"
                                id="muscle-group-checkbox-exercise"
                                type="checkbox"
                                onChange={() => handleIsPrimary1Change()}
                            />
                            <label className="ml-1 text-xs text-gray-700"
                                   htmlFor="muscle-group-checkbox-exercise">Primary?</label>
                        </div>
                    </div>

                    <div className="-mx-3 mb-6 flex flex-wrap">
                        <div className="mb-6 w-full px-3 md:mb-0 md:w-1/2">
                            <input
                                className="block w-full appearance-none rounded border border-gray-200 bg-gray-200 px-4 py-3 leading-tight text-gray-700 focus:bg-white focus:outline-none"
                                id="grid-city" type="text" placeholder="2" onChange={e => setEqCat2(e.target.value)}
                                value={eqCat2}/>
                        </div>

                        <div className="relative mb-6 w-full px-3 md:mb-0 md:w-1/2">
                            <input
                                className="block w-full appearance-none rounded border border-gray-200 bg-gray-200 px-4 py-3 leading-tight text-gray-700 focus:bg-white focus:outline-none"
                                id="grid-city" type="text" placeholder="2" onChange={e => setMuscleCat2(e.target.value)}
                                value={muscleCat2}/>

                            {/* Checkbox for Additional Category 2 */}
                            <input
                                className="mt-2 ml-2"
                                id="additional-category-checkbox"
                                type="checkbox"
                                onChange={() => handleIsPrimary2Change()}
                            />
                            <label className="ml-1 text-xs text-gray-700"
                                   htmlFor="additional-category-checkbox">Primary?</label>
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
                                type="button" onClick={() => handleCreateButton()}
                            >
                                Create
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div className="ml-auto">
                <img src={img1} alt="Exercise" className="object-cover w-250 h-256"/>
            </div>
        </div>
    )
}

export default CreateExercise;
