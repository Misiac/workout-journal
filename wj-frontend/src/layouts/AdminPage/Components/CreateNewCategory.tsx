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
        } else if (selectedOption == 'Musclegroup') {
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
        <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8 flex items-center justify-center">
            <div className="mr-auto">
                <img src={img2} alt="Exercise" className="w-250 h-256 object-cover"/>
            </div>
            <div className="ml-auto ">
                <h1 className="text-3xl font-bold tracking-tight text-gray-900 ">Create new category</h1>

                <form className="w-full max-w-lg py-5">
                    <div className="flex flex-wrap -mx-3">
                        <div className="w-full md:w-1/2 px-3 mb-4 md:mb-0">
                            <label className="block uppercase tracking-wide text-gray-700 text-m font-bold mb-2"
                                   htmlFor="grid-first-name">
                                Name
                            </label>
                            <input
                                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
                                id="grid-first-name" type="text" placeholder="Category"
                                onChange={e => setName(e.target.value)} value={name}/>

                            {/* Radio Buttons */}
                            <div className="flex mb-4">
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
                                <label className="inline-flex items-center ml-6">
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