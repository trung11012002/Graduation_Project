import axios, { AxiosResponse, AxiosRequestConfig } from "axios";

const API_URL = "http://localhost:8000/api/v1";
console.log(process.env)
interface ApiCallResponse<T> {
    status?: string;
    msg?: string;
    code: number;
    data: T;
}

// 'application/json' | 'multipart/form-data'

export const callApi = async <T>(
    URL: string,
    method: string = 'get',
    data?: Record<string, any>,
    ContentType: string = 'application/json'
): Promise<ApiCallResponse<T> | undefined> => {
    try {
        const token = localStorage.getItem('token');

        const requestData: AxiosRequestConfig = {
            method,
            url: `${API_URL}/${URL}`,
            headers: {
                'Content-Type': ContentType,
                Auth: `${token}`,
            },
        };

        if (ContentType === "multipart/form-data") {
            const formData = new FormData();
            for (const key in data) {
                formData.append(key, data[key]);
            }
            data = formData
        }

        if (method.toLowerCase() === 'get' && data) {
            requestData.params = data;
        } else {
            requestData.data = data;
        }

        const response: AxiosResponse<ApiCallResponse<T>> = await axios(requestData);

        // console.log(response.data);


        return response.data;


    } catch (error: any) {
        // console.error(error);
        // throw error;
        // if (error.response) {
        //     console.log('Error Response:', error.response);
        //     console.log('Error Status:', error.response.status);
        //     console.log('Error Data:', error.response.data);
        // } else if (error.request) {
        //     console.log('No Response:', error.request);
        // } else {
        //     console.log('Error Message:', error.message);
        // }
        return error.response;
    }
};