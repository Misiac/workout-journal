import React, {useState} from "react";
import {useOktaAuth} from "@okta/okta-react";
import img2 from "../../../resources/2.png"

export const CreateNewCategory = () => {

    const {authState} = useOktaAuth();

    const [displayInfo, setDisplayInfo] = useState(false)
    const [responseInfo, setResponseInfo] = useState('')
    const [wasRequestSuccessful, setWasRequestSuccessful] = useState(false)


    const [name, setName] = useState('')
    const [selectedOption, setSelectedOption] = useState('')

    async function handleCreateButton() {

        if (selectedOption === '') {
            setResponseInfo("Select the desired category")
            setDisplayInfo(true);
            return;
        }

        let url = 'http://localhost:8080/api/admin'
        if (selectedOption === 'Equipment') {
            url = url.concat('/equipment-category')
        } else if (selectedOption === 'Musclegroup') {
            url = url.concat('/muscle-category')
        }
        url = url.concat(`?name=${name}`)

        const requestOptions = {
            method: 'POST',
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
            <div className="mr-auto">
                <img src={img2} alt="Exercise" className="object-cover w-250 h-256"/>
            </div>
            <div className="ml-auto">
                <h1 className="text-3xl font-bold tracking-tight text-gray-900">Create new category</h1>

                <form className="w-full max-w-lg py-5">
                    <div className="-mx-3 flex flex-wrap">
                        <div className="mb-4 w-full px-3 md:mb-0 md:w-1/2">
                            <label className="mb-2 block font-bold uppercase tracking-wide text-gray-700 text-m"
                                   htmlFor="grid-first-name">
                                Name
                            </label>
                            <input
                                className="mb-3 block w-full appearance-none rounded border border-gray-200 bg-gray-200 px-4 py-3 leading-tight text-gray-700 focus:bg-white focus:outline-none"
                                id="grid-first-name" type="text" placeholder="Category"
                                onChange={e => setName(e.target.value)} value={name}/>

                            {/* Radio Buttons */}
                            <div className="mb-4 flex">
                                <label className="inline-flex items-center">
                                    <input
                                        type="radio"
                                        className="form-radio"
                                        value="Equipment category"
                                        checked={selectedOption === 'Equipment'}
                                        onChange={() => setSelectedOption('Equipment')}
                                    />
                                    <span className="ml-2 font-bold">Equipment category</span>
                                </label>
                                <label className="ml-6 inline-flex items-center">
                                    <input
                                        type="radio"
                                        className="form-radio"
                                        value="Muscle group category"
                                        checked={selectedOption === 'Musclegroup'}
                                        onChange={() => setSelectedOption('Musclegroup')}
                                    />
                                    <span className="ml-2 font-bold">Musclegroup category</span>
                                </label>
                            </div>
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

        </div>
    )

}
export default CreateNewCategory;