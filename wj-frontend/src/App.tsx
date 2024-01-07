import "./App.css";
import {Route, useHistory} from "react-router";
import {oktaConfig} from "./lib/oktaConfig";
import {OktaAuth, toRelativeUrl} from "@okta/okta-auth-js";
import {LoginCallback, SecureRoute, Security} from "@okta/okta-react";
import LoginWidget from "./Auth/LoginWidget";
import React from "react";
import {Redirect} from "react-router-dom";
import HomePage from "./layouts/HomePage";
import WelcomePage from "./layouts/WelcomePage";
import AdminPage from "./layouts/AdminPage";


const oktaAuth = new OktaAuth(oktaConfig);

export const App = () => {
    const history = useHistory();

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

                <Route path="/" exact>
                    <Redirect to="/welcome"/>
                </Route>

                <Route path="/welcome">
                    <WelcomePage/>
                </Route>

                <SecureRoute path="/home">
                    <HomePage/>
                </SecureRoute>


                <SecureRoute path={'/admin'}>
                    <AdminPage/>
                </SecureRoute>
                <Route
                    path="/login"
                    render={() => <WelcomePage/>}
                />
                <Route path="/login/callback" component={LoginCallback}></Route>


            </Security>
        </div>
    );
};