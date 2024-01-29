import Stats from "./Stats/Stats";
import {useOktaAuth} from "@okta/okta-react";
import Workouts from "./Workouts/Workouts";

export const WorkoutsPage = () => {

    const {authState} = useOktaAuth();
    console.log(authState?.accessToken)

    const postUser = async () => {
        const url = `${import.meta.env.VITE_API_ADDRESS}/api/users/check`;
        const requestOptions = {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                'Content-Type': 'application/json'
            }
        };
        await fetch(url, requestOptions);
    };
    postUser();

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