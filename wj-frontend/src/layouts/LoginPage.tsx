import LoginWidget from "../Auth/LoginWidget";
import {oktaConfig} from "../lib/oktaConfig";
import WaveFooter from "./Navigation/WaveFooter";
import React from "react";
import logo from "../resources/logo.png"

export const LoginPage = () => {
    return (
        <>
            <div className='flex items-center justify-center h-screen'>
                <div className='items-center'>
                    <div className='flex items-center justify-center'>
                        <img src={logo} width={396} alt="logo"/>
                    </div>
                    <LoginWidget config={oktaConfig}/>
                </div>
            </div>
            <WaveFooter/>
        </>

    );
}
export default LoginPage;