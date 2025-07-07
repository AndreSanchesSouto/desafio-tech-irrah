import type { StateCreator } from "zustand";

export interface CurrentUser {
  id: string | null;
  name: string | null;
  documentId: string | null;
  documentType: string | null;
  balance: string | null;
  number: string | null;
  planType: string | null;
  active: boolean | null;
}

export interface UserSlice {
  currentUser: CurrentUser;
  setUser: (payload: Partial<CurrentUser>) => void;
}

export const createUserSlice: StateCreator<UserSlice> = (set) => ({
  currentUser: {
    id: null,
    name: null,
    documentId: null,
    documentType: null,
    balance: null,
    number: null,
    planType: null,
    active: null
  },
  setUser: (payload) =>
    set((state) => ({
      currentUser: { 
        ...state.currentUser, 
        ...payload 
      }
    })),
});