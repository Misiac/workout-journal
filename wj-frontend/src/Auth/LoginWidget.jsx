import {Redirect} from "react-router-dom";
import {useOktaAuth} from "@okta/okta-react";
import OktaSignInWidget from "./OktaSignInWidget";
import ProcessingSpinner from "../layouts/Utils/ProcessingSpinner";

const LoginWidget = ({config}) => {
    const {oktaAuth, authState} = useOktaAuth();
    const onSuccess = (tokens) => {
        oktaAuth.handleLoginRedirect(tokens);
    };

    const onError = (err) => {
        console.error('Sign in error:  ', err);
    }
    if (!authState) {
        return <ProcessingSpinner/>
    }

    return authState.isAuthenticated ?
        <Redirect to={{pathname: '/workouts'}}/>
        :

        <OktaSignInWidget config={config} onSuccess={onSuccess} onError={onError}>

        </OktaSignInWidget>
};

export default LoginWidget;