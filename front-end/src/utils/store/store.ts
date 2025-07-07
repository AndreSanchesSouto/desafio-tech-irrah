import { create } from 'zustand';
import { createUserSlice, type UserSlice } from './user';

const useUserStore = create<UserSlice>()((...set) => ({
    ...createUserSlice(...set),
}));

export default useUserStore;