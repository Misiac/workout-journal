import Stats from "./Stats/Stats";
import {useOktaAuth} from "@okta/okta-react";
import Workouts from "./Workouts/Workouts";
import {useState} from "react";

export const WorkoutsPage = () => {

    const {authState} = useOktaAuth();
    const [reloadStats, setReloadStats] = useState(0);

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
                <Stats reloadStats={reloadStats}/>
                <hr/>
                <Workouts reloadStats={reloadStats} setReloadStats={setReloadStats}/>
            </div>
        </>
    );
}

export default WorkoutsPage;