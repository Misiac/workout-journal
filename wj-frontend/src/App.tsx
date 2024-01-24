import "./App.css";
import {Route, useLocation, Navigate, useNavigate, Routes} from "react-router";
import {oktaConfig} from "./lib/oktaConfig";
import {OktaAuth} from "@okta/okta-auth-js";
import {Security} from "@okta/okta-react";

import Navbar from "./layouts/Navigation/Navbar";
import LoginPage from "./layouts/LoginPage";
import WorkoutsPage from "./layouts/WorkoutsPage/WorkoutsPage";
import AdminPage from "./layouts/AdminPage/AdminPage";
import AuthRequired from "./Auth/AuthRequired.tsx";
import {useEffect, useRef, useState} from "react";
import NotSupported from "./layouts/Utils/NotSupported.tsx";
import ResizeModal from "./layouts/Utils/ResizeModal.tsx";

const oktaAuth = new OktaAuth(oktaConfig);

export const App = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const isLoginPage = location.pathname === '/login';

    const MIN_WIDTH = 1280;
    const wasEnoughAtStart = useRef(window.innerWidth > MIN_WIDTH);

    const [isWidthEnoughNow, setIsWidthEnoughNow] = useState(window.innerWidth > MIN_WIDTH);

    useEffect(() => {
        const handleWindowResize = () => {
            if (window.innerWidth > MIN_WIDTH) {
                setIsWidthEnoughNow(true);
            } else {
                setIsWidthEnoughNow(false);
            }
        };

        window.addEventListener('resize', handleWindowResize);

        return () => {
            window.removeEventListener('resize', handleWindowResize);
        };
    }, []);

    console.log(wasEnoughAtStart)

    return (
        <div>
            <Security
                oktaAuth={oktaAuth}
                restoreOriginalUri={(_oktaAuth, originalUri) => navigate(originalUri || "/")}
                onAuthRequired={() => navigate("/login")}>

                {!isLoginPage && wasEnoughAtStart.current && <Navbar/>}
                {!wasEnoughAtStart.current ? <NotSupported minWidth={MIN_WIDTH}/> :
                    <>
                        <Routes>
                            <Route path="/" element={<Navigate to="/login"/>}/>

                            <Route path="/login" element={<LoginPage/>}/>

                            <Route path="/workouts" element={<AuthRequired/>}>
                                <Route path='' element={<WorkoutsPage/>}/>
                            </Route>

                            <Route path="/admin" element={<AuthRequired/>}>
                                <Route path='' element={<AdminPage/>}/>
                            </Route>

                            <Route path='*' element={<Navigate to='/login'/>}/>
                        </Routes>
                        {(!isWidthEnoughNow ? <ResizeModal minWidth={MIN_WIDTH}/> : null)}
                    </>
                }
            </Security>
        </div>
    );
}
