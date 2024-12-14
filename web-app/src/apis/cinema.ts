import {callApi} from "./callAPI";

export const changeStatusCinema = async (
    data: {
        id: number
    }
) => {
    return await callApi<any>(`cinema-service/cinema/${data.id}`, "put")
}

export const editCinema = async (
    data: {
        id: number,
        name: string,
        address: string,
        adminId: number,
    }
) => {
    return await callApi<any>(`cinema-service/cinema/edit`, "put",data)
}

