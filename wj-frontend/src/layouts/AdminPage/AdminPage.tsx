import Navbar from "../Navbar";
import React, {useState} from "react";
import CreateExercise from "./Components/CreateExercise";
import CreateNewCategory from "./Components/CreateNewCategory";
import ManageExerciseBindings from "./Components/ManageExerciseBindings";
import {useOktaAuth} from "@okta/okta-react";


export const AdminPage = () => {
    const {authState} = useOktaAuth();
    const [reloadTrigger, setReloadTrigger] = useState(false);

    if (authState?.accessToken?.claims.userType !== 'admin') {
        return (
            <>
                <Navbar/>
                <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                    <h1 className="text-3xl font-bold tracking-tight text-gray-900">You are not authorized to view this
                        page</h1>
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
                    <a href="https://www.vecteezy.com/free-vector/gym-man">Gym Man Vectors by Vecteezy</a>
                </div>
            </header>
            <main>

            </main>


        </>
    );
}
export default AdminPage;