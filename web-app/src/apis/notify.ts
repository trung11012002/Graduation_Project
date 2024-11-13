import { callApi } from "./callAPI"
import { Notification } from "../types/Notification";

export const initNotification = async (
    data: { username: string }
) => {
    return await callApi<Notification[]>(`notification-service/notification/init`, "get", data)
}

// export const resultInfoPayment = async(
//     data: {
//         scheduleId: number
//         userId: number
//         responseCode: string
//         seats: string[]
//     }
// ) => {
//     console.log(data)
//     return await callApi<any>(`payment-service/result-info`, "post", data)
// }
