import "./App.css";
import {Route, useHistory, useLocation} from "react-router";
import {oktaConfig} from "./lib/oktaConfig";
import {OktaAuth, toRelativeUrl} from "@okta/okta-auth-js";
import {SecureRoute, Security} from "@okta/okta-react";
import React from "react";
import {Redirect} from "react-router-dom";
import WorkoutsPage from "./layouts/WorkoutsPage/WorkoutsPage";
import LoginPage from "./layouts/LoginPage";
import AdminPage from "./layouts/AdminPage/AdminPage";
import Navbar from "./layouts/Navbar";


const oktaAuth = new OktaAuth(oktaConfig);


export const App = () => {
    const history = useHistory();
    const location = useLocation();
    const isLoginPage = location.pathname === '/login';
    const customAuthHandler = () => {
        history.push("/login");
    };
    const restoreOriginalUri = async (_oktaAuth: any, originalUri: any) => {
        history.replace(
            toRelativeUrl(originalUri || "/", window.location.origin)
        );
    };

    return (
        <div className="">
            <Security
                oktaAuth={oktaAuth}
                restoreOriginalUri={restoreOriginalUri}
                onAuthRequired={customAuthHandler}>

                {!isLoginPage && <Navbar/>}

                <Route path="/" exact>
                    <Redirect to="/login"/>
                </Route>

                <Route path="/login">
                    <LoginPage/>
                </Route>

                <SecureRoute path="/workouts">
                    <WorkoutsPage/>
                </SecureRoute>

                <SecureRoute path='/admin'>
                    <AdminPage/>
                </SecureRoute>
            </Security>
        </div>
    );
};