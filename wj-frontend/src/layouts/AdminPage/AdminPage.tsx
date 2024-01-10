import Navbar from "../Navbar";
import React, {useState} from "react";
import CreateExercise from "./Components/CreateExercise";
import CreateNewCategory from "./Components/CreateNewCategory";
import ManageExerciseBindings from "./Components/ManageExerciseBindings";
import {useOktaAuth} from "@okta/okta-react";


export const AdminPage = () => {
    const {authState} = useOktaAuth();
    const [reloadTrigger, setReloadTrigger] = useState(false);

    if (authState?.accessToken?.claims.userType !== 'Admin') {
        return (
            <>
                <Navbar/>
                <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                    <h1 className="text-3xl font-bold tracking-tight text-gray-900">You are not authorized to view this page</h1>
                </div>
            </>
        )
    }
    return (
        <>
            <Navbar/>

            <header className="bg-white shadow">
                <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                    <CreateExercise setReloadTrigger={setReloadTrigger} reloadTrigger={reloadTrigger}/>
                    <hr/>
                    <CreateNewCategory/>
                    <hr/>
                    <ManageExerciseBindings reloadTrigger={reloadTrigger}/>
                </div>
            </header>
            <main>
                <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
                </div>
            </main>

        </>
    );
}
export default AdminPage;