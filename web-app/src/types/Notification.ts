export interface Notification {
    id: number;
    message: string;
    type: string;
    user: {
        id: number;
        username: string;
        password: string;
        fullname: string;
        dateOfBirth: string;
        address: string;
        email: string;
        phone: string;
        createdAt: string | null;
        updatedAt: string | null;
        blocked: boolean;
    };
}
