import React from "react";
import Stats from "./Stats/Stats";
import {useOktaAuth} from "@okta/okta-react";
import Workouts from "./Workouts/Workouts";

export const WorkoutsPage = () => {

    const {authState} = useOktaAuth();
    console.log(authState?.accessToken)
    return (
        <>
            <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                <Stats/>
                <hr/>
                <Workouts/>
            </div>
        </>
    );
}

export default WorkoutsPage;