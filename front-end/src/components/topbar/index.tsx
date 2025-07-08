import { CircleDollarSign, LogOut, MessageCircle, Settings, User } from "lucide-react";
import { getUser } from "../../utils/getUser";
import { useState } from "react";
import { NavLink, useParams } from "react-router-dom";

export default function Topbar() {
    const [showButton, setShowButton] = useState(false);
    const { id } = useParams();

    function handleLogout() {
        localStorage.clear()
        window.location.href = '/auth'
    }

    return (
        <div className="bg-violet-500 w-full px-2 z-10">
            <div className={`text-white font-semibold text-sm w-full py-1 flex ${id ? 'justify-between' : 'justify-end'}  items-center`}>
                {id &&
                    <NavLink to="/chats">
                        Sair do chat
                    </NavLink>}
                <div className="rounded-2xl border flex gap-2 py-[1px] w-max px-3 items-center cursor-pointer relative"
                    onClick={() => setShowButton(!showButton)}
                >
                    {getUser()?.name}
                    <User />
                </div>
                {showButton &&
                    <div className="text-violet-500 h-auto justify-start absolute flex flex-col bg-neutral-50 right-2 top-11 border-4 border-violet-500 rounded-md cursor-pointer">
                        <NavLink className="flex gap-1 items-center px-3 hover:bg-violet-100 py-1"
                            to={'/chats'}
                            onClick={() => setShowButton(false)}
                        >
                            <MessageCircle />
                            <p className="text-violet-500">Chats</p>
                        </NavLink>
                        <NavLink className="flex gap-1 items-center px-3 hover:bg-violet-100 py-1"
                            to={'/settings'}
                            onClick={() => setShowButton(false)}
                        >
                            <Settings />
                            <p className="text-violet-500">Configurações</p>
                        </NavLink>
                        <NavLink className="flex gap-1 items-center px-3 hover:bg-violet-100  py-1"
                            to={'/payments'}
                            onClick={() => setShowButton(false)}
                        >
                            <CircleDollarSign />
                            <p className="text-violet-500">Pagamentos</p>
                        </NavLink>
                        <button className="flex gap-1 items-center px-3 hover:bg-violet-100  py-1"
                            onClick={handleLogout}
                        >
                            <LogOut />
                            Sair
                        </button>
                    </div>
                }
            </div>
        </div>
    )
}