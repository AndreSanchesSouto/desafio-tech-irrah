import { createBrowserRouter } from "react-router-dom";
import Login from "./pages/login";
import BCB from "./pages";
import Chats from "./pages/chats";
import UserChat from "./components/userChat";

export const routes = createBrowserRouter([
    {
        path: '/login',
        element: <Login />
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