import {useOktaAuth} from "@okta/okta-react";
import {NavLink} from "react-router-dom";

export const Navbar = () => {
    const {oktaAuth, authState} = useOktaAuth();
    const handleLogout = async () => oktaAuth.signOut();

    return (
        <div>
            <div className="min-h-full">
                <nav className="bg-regal-blue">
                    <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
                        <div className="flex h-16 items-center justify-between">
                            <div className="flex items-center">

                                <div className="hidden md:block">
                                    <div className="ml-10 flex items-baseline space-x-4">
                                        <NavLink to='/workouts'
                                           className="text-white rounded-md px-3 py-2 text-md font-medium "
                                           aria-current="page">Workout</NavLink>
                                        <a className="text-gray-400 hover:text-gray-400  rounded-md px-3 py-2 text-sm font-medium">Diet</a>
                                        <a className="text-gray-400 hover:text-gray-400   rounded-md px-3 py-2 text-sm font-medium">Sleep</a>
                                        {authState?.isAuthenticated && authState?.accessToken?.claims.userType === 'admin' &&
                                            <NavLink to='/admin'
                                                     className="text-gray-300  hover:text-white rounded-md px-3 py-2 text-sm font-medium">Admin
                                            </NavLink>
                                        }

                                    </div>
                                </div>
                            </div>
                            <div className="hidden md:block">
                                <div className="ml-4 flex items-center md:ml-6">
                                    <button type="button" onClick={handleLogout}
                                            className="relative rounded-full bg-regal-blue p-1 text-white hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800">
                                        <span className="absolute -inset-1.5"></span>
                                        <span className="sr-only">View notifications</span>

                                        <svg className="h-6 w-6 " fill="none" viewBox="0 0 24 24"
                                             stroke="currentColor">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                                                  d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1"/>
                                        </svg>
                                    </button>

                                </div>
                            </div>
                        </div>
                    </div>
                </nav>

            </div>
        </div>

    );
}
export default Navbar