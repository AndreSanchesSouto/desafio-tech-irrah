import type { ActualUser } from "./interfaces";

export function getUser(): ActualUser | null {
    const localUser = localStorage.getItem('actual_user');
    
    if (!localUser) {
        return null;
    }

    try {
        const user = JSON.parse(localUser) as ActualUser;
        if (user && typeof user === 'object') {
            return user;
        }
        return null;
    } catch (error) {
        console.error('Error parsing user data:', error);
        return null;
    }
}