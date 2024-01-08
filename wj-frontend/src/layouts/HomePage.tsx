import React, {useEffect} from "react";
import {useOktaAuth} from "@okta/okta-react";
import Navbar from "./Navbar";


export const HomePage = () => {

    const {authState} = useOktaAuth();

    console.log(authState?.accessToken)

    useEffect(() => {
        const fetchTotalSets = async () => {
            try {
                if (authState && authState.isAuthenticated) {
                    const url = "http://localhost:8080/api/stats/total/sets"
                    const requestOption = {
                        method: 'GET',
                        headers: {
                            Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
                            'Content-Type': 'application/json'
                        }
                    };
                    const messagesResponse = await fetch(url, requestOption);
                    if (!messagesResponse.ok) {
                        throw new Error("Something went wrong!")
                    }
                    const messagesResponseJson = await messagesResponse.json()

                    console.log("xDDD")
                    console.log(messagesResponseJson);

                    // setTotalSets(messagesResponse.headers);
                }
            } catch (error) {
                console.log("xD", error)
            }
        }

        fetchTotalSets();
    }, []);  // Empty dependency array to run the effect only once

    return (
        <>
            <Navbar/>

            <header className="bg-white shadow">
                <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                    <h1 className="text-3xl font-bold tracking-tight text-gray-900">Dashboard</h1>
                </div>
            </header>
            <main>
                <div className="mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
                </div>
            </main>
        </>
    );
}

export default HomePage;