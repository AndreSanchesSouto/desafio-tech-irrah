import { createBrowserRouter } from "react-router-dom";
import Auth from "./pages/auth";
import BCB from "./pages";
import Chats from "./pages/chats";
import UserChat from "./components/userChat";

export const routes = createBrowserRouter([
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
            }
        ]
    }
])