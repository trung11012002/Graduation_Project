import React, { ReactNode, createContext, useEffect, useState } from 'react';
import { User } from '../types/user';
import { loginByToken } from '../apis/auth';
import {useNavigate} from "react-router-dom";
// import { useNavigate } from 'react-router-dom';


interface IAuthContext {
    children: ReactNode;
}

interface IAuthContextProvider {
    userState: IUserState
    setUserState: (userState: IUserState) => void
    logout: () => void
    autoLogin: () => void
}

export interface IUserState {
    user: User | null
    tokenAccess: string | null | undefined
    tokenRefresh: string | null | undefined
    isLogin: boolean
}

export const AuthContextProvider = createContext<IAuthContextProvider | undefined>(undefined);


export const AuthContext: React.FC<IAuthContext> = ({ children }) => {

    const userStateDefault: IUserState = {
        isLogin: false,
        user: null,
        tokenAccess: null,
        tokenRefresh: null,
    };
    // const navigate = useNavigate()
    const [userState, setUserState] = useState<IUserState>(userStateDefault);
    console.log("render AuthContext" ,userState);
    const navigate = useNavigate();
    const autoLogin = async () => {
        if (localStorage.getItem('tokenAccess')) {
            const res = await loginByToken()
            console.log("res", res);
            // console.log("data" ,res);
            if (res?.data && res.code === 1000) {
                setUserState?.({
                    isLogin: true,
                    user: res.data,
                    tokenAccess: res.data.token,
                    tokenRefresh: res.data.tokenRefresh
                })
                localStorage.setItem('tokenAccess', res.data.token)
                // res.data.role.name === "SUPER_ADMIN" ?
                // navigate('/super-admin/theater-list')
                // :
                // res.data.role.name === "ADMIN" ?
                //     navigate('/admin/movie-schedule')
                //     :
                //     navigate('/')
            }
            else {
                localStorage.removeItem("tokenAccess");
                navigate('/login');
            }
        }
    };

    const logout = () => {
        localStorage.removeItem("tokenAccess");
        setUserState({
            isLogin: false,
            user: null,
            tokenAccess: null,
            tokenRefresh: null,
        });
    };

    // if(!userState.user?.username){
    //     localStorage.setItem('websocketConnected', "false");
    //     autoLogin();
    // }

    useEffect(() => {
        localStorage.setItem('websocketConnected', "false");
        autoLogin();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    const data = {
        userState,
        setUserState,
        logout,
        autoLogin
    };

    return (
        <AuthContextProvider.Provider value={data}>{children}</AuthContextProvider.Provider>
    );
};
