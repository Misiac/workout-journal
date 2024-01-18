import {useNavigate} from 'react-router-dom';
import {useOktaAuth} from '@okta/okta-react';
import OktaSignInWidget from './OktaSignInWidget';
import ProcessingSpinner from '../layouts/Utils/ProcessingSpinner';
import {useEffect} from "react";

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
    }, [authState]);

    const onError = (err) => {
        console.error('Sign in error: ', err);
    };

    if (!authState) {
        return <ProcessingSpinner/>;
    }


    return <OktaSignInWidget config={config} onSuccess={onSuccess} onError={onError}/>;
};

export default LoginWidget;
