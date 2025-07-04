import { User } from "lucide-react";
import { useNavigate } from "react-router-dom";

export default function Chatcard() {

    const navigate = useNavigate()

    return (
        <div className="w-full flex hover:rounded-sm gap-2 p-2 bg-neutral-50 hover:text-violet-600 hover:bg-violet-50 items-center h-scren"
            onClick={() => navigate(`/chats/1`)}
        >
            <div>
                <User />
            </div>
            <div className="flex flex-col">
                <p className="font-medium">usename</p>
                <p className="text-xs">user_nick</p>
            </div>
        </div>
    )
} 