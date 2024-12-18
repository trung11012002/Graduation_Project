import axios from "axios";
import { User } from "../types/user";
import { callApi } from "./callAPI";

// private String username;
//     private String password;
//     private String fullname;
//     private String dateOfBirth;
//     private String address;
//     private String email;
//     private String phone;
// account-service/api/v1/auth/signup
// Post
export const register = async (
    data: {
        email: string | undefined
        password: string | undefined
        phone: string | undefined,
        role: string | undefined,
        address: string | undefined,
        fullname: string | undefined,
        dateOfBirth: string | undefined,
        username: string | undefined,
    }
) => {
    return await callApi<User>('account-service/auth/signup', 'post', data)
}

export const login = async (
    data: any
) => {
    return await callApi<any>("account-service/auth/signin", "post", data)
}

export const loginByToken = async () => {
    const token = localStorage.getItem("tokenAccess")
    if (token) {
        // let data = { token };
        axios.defaults.headers.common['Auth'] = `${token}`;
        try {
            return await callApi<any>("account-service/auth/verify-token", "get")

        } catch (error) {
            console.log(error)
        }
    }

}

export const getAllUser = async () => {
    return await callApi<User[]>('user/get-all', "get")
}

// user-service/api/v1/auth/forgot-password
// tải lên String email
// getmapping

export const forgotPassword = async (
    data: {
        email: string
    }
) => {
    return await callApi<any>("account-service/auth/forgot-password", "post", data)
}