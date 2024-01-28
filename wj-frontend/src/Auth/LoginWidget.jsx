import {useOktaAuth} from '@okta/okta-react';
import ProcessingSpinner from '../layouts/Utils/ProcessingSpinner';
import {useEffect} from "react";
import OktaSignInWidget from "./OktaSignInWidget";
import {useNavigate} from "react-router";

const LoginWidget = ({config}) => {
    const {oktaAuth, authState} = useOktaAuth();
    const navigate = useNavigate();

    const onSuccess = (tokens) => {
        oktaAuth.handleLoginRedirect(tokens);
    };

    useEffect(() => {
        if (authState?.isAuthenticated) {
            navigate('/workouts');
        }
    }, [authState, navigate]);

    const onError = (err) => {
        console.error('Sign in error: ', err);
    };

    if (!authState) {
        return (
            <div className='mt-10 flex w-full justify-center'>
                <ProcessingSpinner/>
            </div>
        );

    }


    return <OktaSignInWidget config={config} onSuccess={onSuccess} onError={onError}/>;
};

export default LoginWidget;
