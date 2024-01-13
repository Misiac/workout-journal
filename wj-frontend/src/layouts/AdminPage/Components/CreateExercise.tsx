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
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8 flex items-center justify-center">

            <div className="w-full max-w-lg pr-4">
                <h1 className="text-3xl font-bold tracking-tight text-gray-900">Create new exercise</h1>

                <form className="w-full max-w-lg py-5">

                    <div className="flex flex-wrap -mx-3 mb-4">
                        <div className="w-full md:w-1/2 px-3 mb-4 md:mb-0">
                            <label className="block uppercase tracking-wide text-gray-700 text-m font-bold mb-2"
                                   htmlFor="grid-first-name">
                                Name
                            </label>
                            <input
                                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
                                id="grid-first-name" type="text" placeholder="Lat Raise"
                                onChange={e => setName(e.target.value)} value={name}/>
                        </div>
                    </div>

                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                   htmlFor="grid-city">
                                Equipment categories
                            </label>
                            <input
                                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white "
                                id="grid-city" type="text" placeholder="1" onChange={e => setEqCat1(e.target.value)}
                                value={eqCat1}/>
                        </div>

                        <div className="w-full md:w-1/2 px-3 md:mb-0 relative">
                            <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                   htmlFor="grid-city">
                                Muscle group categories
                            </label>
                            <input
                                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white "
                                id="grid-city" type="text" placeholder="1" onChange={e => setMuscleCat1(e.target.value)}
                                value={muscleCat1}/>

                            {/* Checkbox for Muscle group categories */}
                            <input
                                className="mt-2 ml-2"
                                id="muscle-group-checkbox-exercise"
                                type="checkbox"
                                onChange={e => handleIsPrimary1Change()}
                            />
                            <label className="text-gray-700 text-xs ml-1"
                                   htmlFor="muscle-group-checkbox-exercise">Primary?</label>
                        </div>
                    </div>

                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                            <input
                                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white"
                                id="grid-city" type="text" placeholder="2" onChange={e => setEqCat2(e.target.value)}
                                value={eqCat2}/>
                        </div>

                        <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0 relative">
                            <input
                                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white"
                                id="grid-city" type="text" placeholder="2" onChange={e => setMuscleCat2(e.target.value)}
                                value={muscleCat2}/>

                            {/* Checkbox for Additional Category 2 */}
                            <input
                                className="mt-2 ml-2"
                                id="additional-category-checkbox"
                                type="checkbox"
                                onChange={e => handleIsPrimary2Change()}
                            />
                            <label className="text-gray-700 text-xs ml-1"
                                   htmlFor="additional-category-checkbox">Primary?</label>
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
                                type="button" onClick={e => handleCreateButton()}
                            >
                                Create
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div className="ml-auto">
                <img src={img1} alt="Exercise" className="w-250 h-256 object-cover"/>
            </div>
        </div>
    )
}

export default CreateExercise;
