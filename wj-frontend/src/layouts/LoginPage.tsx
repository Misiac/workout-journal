import LoginWidget from "../Auth/LoginWidget";
import {oktaConfig} from "../lib/oktaConfig";
import WaveFooter from "./Navigation/WaveFooter";
import logo from "../resources/logo.png"

export const LoginPage = () => {
    return (
        <>
            <div className='flex h-screen items-center justify-center fade-animation'>
                <div className='items-center'>
                    <div className='flex w-full items-center justify-center'>
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