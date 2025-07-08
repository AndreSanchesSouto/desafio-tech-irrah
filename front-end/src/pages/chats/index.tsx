import { MessageCircle } from "lucide-react";
import Chatcard from "../../components/chatCard";
import { Outlet, useParams } from "react-router-dom";
import { API_URL } from "../../utils/configs";
import useSWR from "swr";
import useAxiosConfiguration from "../../utils/axiosFetcher";
import type ResponseGenericChat from "../../utils/interfaces";
import { getUser } from "../../utils/getUser";
import { Role } from "../../utils/enums";
import { useState } from "react";
import CHAT_SVG from "../../../public/svg/chatMessage.svg"

export default function Chats() {
    const { id } = useParams()
    const fetcher = useAxiosConfiguration();
    const { data: chats } = useSWR<ResponseGenericChat[]>(
        `${API_URL}/chats`,
        fetcher
    );
    const [showChat, setShowChat] = useState(getUser()?.role === Role.ADMINER)

    return (
        <div className="flex relative h-full ">
            {getUser()?.role === Role.ADMINER &&
                showChat &&
                <>
                    <div className="max-w-60 w-full h-full overflow-y-auto bg-neutral-100 border-r-2 border-violet-200 hidden md:flex flex-col">
                        {
                            chats?.map((chat: ResponseGenericChat) => {
                                return (chat &&
                                    <div key={chat.id}
                                    >
                                        <Chatcard {...chat} />
                                    </div>
                                )
                            })
                        }
                    </div>
                    <div className="max-w-60 w-full h-full overflow-y-auto bg-neutral-100 border-r-2 border-violet-200 md:hidden flex flex-col absolute z-10">
                        {
                            chats?.map((chat: ResponseGenericChat) => {
                                return (chat &&
                                    <div key={chat.id}
                                        onClick={() => setShowChat(!showChat)}
                                    >
                                        <Chatcard {...chat} />
                                    </div>
                                )
                            })
                        }
                    </div>
                </>
            }
            {getUser()?.role === Role.ADMINER &&
                <div className="relative z-10">
                    <button
                        className="absolute bottom-3 left-3 h-12 w-12 items-center flex justify-center rounded-full bg-violet-500 text-neutral-100 hover:cursor-pointer hover:bg-violet-400 hover:text-white transition-all"
                        onClick={() => setShowChat(!showChat)}
                    >
                        <MessageCircle size={30} />
                    </button>
                </div>
            }
            <Outlet />
            {!id &&
                <div className="items-center justify-center flex flex-col w-full">
                    <div className="w-[40rem] h-auto ">
                        <img src={CHAT_SVG} alt="" />
                    </div>
                    <p className="text-3xl font-black text-violet-800">SELECIONE UM CHAT PARA COMEÇAR</p>
                </div>
            }
        </div>
    )
} 