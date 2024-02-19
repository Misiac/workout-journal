import {useOktaAuth} from "@okta/okta-react";
import {NavLink} from "react-router-dom";
import {LogOut} from "lucide-react";
import {useNavigate} from 'react-router-dom';

export const Navbar = () => {
    const {oktaAuth, authState} = useOktaAuth();
    const navigate = useNavigate();

    const handleLogout = async () => {
        await oktaAuth.closeSession();
        localStorage.removeItem('okta-token-storage');
        await oktaAuth.authStateManager.updateAuthState();
        navigate('/login');
    };

    return (
        <div className="min-h-full bg-regal-blue">
            <nav className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                <div className="flex h-16 items-center justify-between">
                    <div className="flex items-center">
                        <div className="md:block">
                            <div className="ml-10 flex items-baseline space-x-4">
                                <NavLink to='/workouts'
                                         className="rounded-md px-3 py-2 font-medium text-white text-md hover:text-regal-blue hover:bg-white"
                                         aria-current="page">Workout</NavLink>
                                <a className="pointer-events-none rounded-md px-3 py-2 text-sm font-medium text-white opacity-50 hover:text-regal-blue hover:bg-white">Diet</a>
                                <a className="pointer-events-none rounded-md px-3 py-2 text-sm font-medium text-white opacity-50 hover:text-regal-blue hover:bg-white">Sleep</a>
                                {authState?.isAuthenticated && authState?.accessToken?.claims.userType === 'admin' &&
                                    <NavLink to='/admin'
                                             className="rounded-md px-3 py-2 text-sm font-medium text-white hover:text-regal-blue hover:bg-white">Admin
                                    </NavLink>
                                }
                            </div>
                        </div>
                    </div>
                    <div className="md:block">
                        <div className="ml-4 flex items-center md:ml-6">
                            <button type="button" onClick={handleLogout}
                                    className="relative rounded-full p-1 text-white hover:ring-1 hover:ring-white hover:ring-offset-1 focus:outline-none">
                                <span className="sr-only">View notifications</span>
                                <LogOut/>
                            </button>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    );
}
export default Navbar