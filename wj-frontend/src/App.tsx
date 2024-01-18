import "./App.css";
import {Route, useLocation, Navigate, useNavigate, Routes} from "react-router";
import {oktaConfig} from "./lib/oktaConfig";
import {OktaAuth, toRelativeUrl} from "@okta/okta-auth-js";
import {Security} from "@okta/okta-react";
import React from "react";

import Navbar from "./layouts/Navigation/Navbar";
import LoginPage from "./layouts/LoginPage";
import WorkoutsPage from "./layouts/WorkoutsPage/WorkoutsPage";
import AdminPage from "./layouts/AdminPage/AdminPage";


const oktaAuth = new OktaAuth(oktaConfig);


export const App = () => {
    let navigate = useNavigate();
    const location = useLocation();
    const isLoginPage = location.pathname === '/login';
    const customAuthHandler = () => {
        navigate("/login");
    };
    const restoreOriginalUri = async (_oktaAuth: any, originalUri: any) => {
        navigate(
            toRelativeUrl(originalUri || "/", window.location.origin)
        );
    };

    return (
        <div>
            <Security
                oktaAuth={oktaAuth}
                restoreOriginalUri={restoreOriginalUri}
                onAuthRequired={customAuthHandler}>
                {!isLoginPage && <Navbar/>}
                <Routes>

                    <Route path="/" element={<Navigate to="/login"/>}/>

                    <Route path="/login" element={<LoginPage/>}/>

                    <Route path="/workouts" element={<WorkoutsPage/>}/>

                    <Route path="/admin" element={<AdminPage/>}/>
                </Routes>
            </Security>
        </div>
    );
};