import Navbar from "../Navbar";
import React, {useState} from "react";
import CreateExercise from "./Components/CreateExercise";
import CreateNewCategory from "./Components/CreateNewCategory";
import ManageExerciseBindings from "./Components/ManageExerciseBindings";


export const AdminPage = () => {
    const [reloadTrigger, setReloadTrigger] = useState(false);
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