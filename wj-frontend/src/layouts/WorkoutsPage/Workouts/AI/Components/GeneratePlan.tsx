import {useState} from 'react';
import {AiPlanRequest} from "../../../../../models/AiPlanRequest.ts";
import {useOktaAuth} from "@okta/okta-react";
import ProcessingSpinner from "../../../../Utils/ProcessingSpinner.tsx";

export const GeneratePlan = () => {
    const {authState} = useOktaAuth();
    const [form, setForm] = useState(new AiPlanRequest('Lose weight', 'Beginner', '1', 'Male', '', '', '', '', 'Low', ''));
    const [response, setResponse] = useState('');
    const [wasRequestSent, setWasRequestSent] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    const handleChange = (e: any) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleSubmit = async () => {
        setWasRequestSent(true);

        const url = `${import.meta.env.VITE_API_ADDRESS}/api/ai/plan`;

        try {
            setIsLoading(true);
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(form)
            });

            if (!response.ok) {
                throw new Error('HTTP error ' + response.status);
            }
            setIsLoading(false);

            const data = await response.json();

            setResponse(data.generation);

        } catch (error) {
            console.error('Fetch error:', error);
            setIsLoading(false);
        }
    };

    const downloadAsTxt = () => {
        const element = document.createElement("a");
        const file = new Blob([response], {type: 'text/plain'});
        element.href = URL.createObjectURL(file);
        element.download = "Workout Plan " + new Date().toDateString() + ".txt";
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    }

    const inputClassName = 'flex justify-between gap-x-3 items-center';
    return (
        <div className="py-4">
            {!wasRequestSent ? <>
                    <form>
                        <div className='flex flex-col gap-y-5'>
                            <div className={inputClassName}>
                                <label className="font-bold">Goal:</label>
                                <select name="goal" value={form.goal} onChange={handleChange}
                                        className="mt-1 block w-full rounded border p-2">
                                    <option value="lose weight">Lose weight</option>
                                    <option value="maintenance">Maintenance</option>
                                    <option value="gain muscle">Gain muscle</option>
                                </select>
                            </div>
                            <div className={inputClassName}>
                                <label className="font-bold">Level:</label>
                                <select name="level" value={form.level} onChange={handleChange}
                                        className="mt-1 block w-full rounded border p-2">
                                    <option value="beginner">Beginner</option>
                                    <option value="intermediate">Intermediate</option>
                                    <option value="advanced">Advanced</option>
                                </select>
                            </div>
                            <div className={inputClassName}>
                                <label className="font-bold">Training days Per Week:</label>
                                <select name="daysPerWeek" value={form.daysPerWeek} onChange={handleChange}
                                        className="mt-1 block w-full rounded border p-2">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                            </div>
                            <div className={inputClassName}>
                                <label className="font-bold">Gender:</label>
                                <select name="gender" value={form.gender} onChange={handleChange}
                                        className="mt-1 block w-full rounded border p-2">
                                    <option value="male">Male</option>
                                    <option value="female">Female</option>
                                </select>
                            </div>
                            <div className={inputClassName}>
                                <label className="font-bold">Age:</label>
                                <input type="number" name="age" value={form.age} onChange={handleChange}
                                       className="mt-1 block w-full rounded border p-2"/>
                            </div>
                            <div className={inputClassName}>
                                <label className="font-bold">Weight (kg):</label>
                                <input type="number" name="weight" value={form.weight} onChange={handleChange}
                                       className="mt-1 block w-full rounded border p-2"/>

                            </div>

                            <div className={inputClassName}>
                                <label className="font-bold">Height (cm):</label>
                                <input type="number" name="height" value={form.height} onChange={handleChange}
                                       className="mt-1 block w-full rounded border p-2"/>
                            </div>
                            <div className={inputClassName}>
                                <label className="font-bold">Optional health conditions:</label>
                                <input type="text" name="health" value={form.health} onChange={handleChange}
                                       className="mt-1 block w-full rounded border p-2"/>
                            </div>
                            <div className={inputClassName}>
                                <label className="font-bold">Intensity:</label>
                                <select name="intensity" value={form.intensity} onChange={handleChange}
                                        className="mt-1 block w-full rounded border p-2">
                                    <option value="low">Low</option>
                                    <option value="medium">Medium</option>
                                    <option value="high">High</option>
                                </select>
                            </div>
                            <div className={inputClassName}>
                                <label className="font-bold">Special Goal:</label>
                                <input type="text" name="specialGoal" value={form.specialGoal} onChange={handleChange}
                                       className="mt-1 block w-full rounded border p-2"/>
                            </div>
                        </div>
                    </form>
                    <div className='flex w-full items-center justify-center pt-2'>
                        <button onClick={handleSubmit}
                                className="mt-4 rounded px-4 py-2 text-white transition-colors bg-regal-blue hover:bg-blue-600">
                            Create plan
                        </button>
                    </div>
                </>
                :
                (isLoading ?
                        <div className='flex flex-col items-center'>
                            <ProcessingSpinner/>
                            <span className='font-semibold text-gray-500'>This can take up to 30 seconds</span>
                        </div>
                        :
                        <div className='flex flex-col '>
                            {response.split('\n').map((line, index) => (
                                <span key={index} className='font-semibold text-gray-500'>
                             {line === '' ? '\u00A0' : line}
                                  </span>
                            ))}

                            <div className='flex w-full items-center justify-center pt-2'>
                                <button onClick={downloadAsTxt}
                                        className="mt-4 rounded px-4 py-2 text-white transition-colors bg-regal-blue hover:bg-blue-600">
                                    Download plan
                                </button>
                            </div>
                        </div>

                )
            }
        </div>
    );
}

export default GeneratePlan;