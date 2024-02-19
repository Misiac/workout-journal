import React, {useEffect} from 'react';
import {useOktaAuth} from '@okta/okta-react';
import {toRelativeUrl} from '@okta/okta-auth-js';
import {Outlet} from 'react-router-dom';
import ProcessingSpinner from "../layouts/Utils/ProcessingSpinner.tsx";

export const AuthRequired: React.FC = () => {
    const {oktaAuth, authState} = useOktaAuth();

    useEffect(() => {
        if (!authState) {
            return;
        }

        if (!authState?.isAuthenticated) {
            const originalUri = toRelativeUrl(window.location.href, window.location.origin);
            oktaAuth.setOriginalUri(originalUri);
        }

    }, [oktaAuth, !!authState, authState?.isAuthenticated]);

    if (!authState || !authState?.isAuthenticated) {
        return (<ProcessingSpinner/>);
    }
    return (<Outlet/>);
}
export default AuthRequired;