import { MessageCircle } from "lucide-react";
import Chatcard from "../../components/chatCard";
import { Outlet } from "react-router-dom";

export default function Chats() {
    return (
        <div className="flex relative">
            <div className="max-w-60 w-full max-h-screen overflow-y-auto h-screen bg-neutral-100 border-r-2 border-violet-200">
                <Chatcard />
            </div>
            <div className="relative">
                <button className="absolute bottom-3 left-3 h-12 w-12 items-center flex justify-center rounded-full bg-violet-400 text-neutral-100 hover:cursor-pointer hover:bg-violet-300 hover:text-white">
                    <MessageCircle size={30} />
                </button>
            </div>
            <Outlet />
        </div>
    )
} 