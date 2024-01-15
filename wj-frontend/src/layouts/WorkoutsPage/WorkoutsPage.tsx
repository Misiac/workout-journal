import React from "react";
import Stats from "./Stats/Stats";
import {useOktaAuth} from "@okta/okta-react";

export const WorkoutsPage = () => {

    const {authState} = useOktaAuth();
    console.log(authState?.accessToken)
    return (
        <>
            <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                <h1 className="text-3xl font-bold tracking-tight text-gray-900">Dashboard</h1>
                <Stats/>
                <hr/>
            </div>
        </>
    );
}

export default WorkoutsPage;