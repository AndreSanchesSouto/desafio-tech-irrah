import { createBrowserRouter, Navigate } from "react-router-dom";
import Auth from "./pages/auth";
import BCB from "./pages";
import Chats from "./pages/chats";
import UserChat from "./components/userChat";
import { Settings } from "./pages/settings";
import NotFound from "./pages/notFound";
import Payments from "./pages/payments";

export const routes = createBrowserRouter([
    {
        path: '/',
        element: <Navigate to="/auth" replace />
    },
    {
        path: '/auth',
        element: <Auth />
    },
    {
        path: '/',
        element: <BCB />,
        children: [
            {
                path: '/chats',
                element: <Chats />,
                children: [{
                    path: '/chats/:id',
                    element: <UserChat />
                }]
            },
            {
                path: '/settings',
                element: <Settings />
            },
            {
                path: '/payments',
                element: <Payments />
            }
        ]
    },
    {
        path: '*',
        element: <NotFound />
    },
])