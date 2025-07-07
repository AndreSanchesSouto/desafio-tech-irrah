import { User } from "lucide-react";
import { NavLink } from "react-router-dom";
import type ResponseGenericChat from "../../utils/interfaces";

export default function Chatcard({...props}: ResponseGenericChat) {

    return (
        <NavLink 
        className={({ isActive }) =>
            isActive ? 
        'text-violet-800 bg-violet-200 flex hover:rounded-sm gap-2 p-2 items-center cursor-pointer' : 
        'bg-neutral-50 hover:text-violet-600 hover:bg-violet-50 w-full flex hover:rounded-sm gap-2 p-2 items-center cursor-pointer'
        }
            to={`/chats/${props.id}`}
        >
            <div>
                <User />
            </div>
            <div className="flex flex-col">
                <p className="font-medium">{props.recipientName}</p>
                <p className="text-xs">{props.id}</p>
            </div>
        </NavLink>
    )
} 