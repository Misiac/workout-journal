import Navbar from "../Navbar";
import React from "react";
import CreateExercise from "./Components/CreateExercise";
import CreateNewCategory from "./Components/CreateNewCategory";
import ManageExerciseBindings from "./Components/ManageExerciseBindings";


export const AdminPage = () => {
    return (
        <>
            <Navbar/>


            <header className="bg-white shadow">
                <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                    <CreateExercise/>
                    <hr/>
                    <CreateNewCategory/>
                    <hr/>
                    <ManageExerciseBindings/>
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