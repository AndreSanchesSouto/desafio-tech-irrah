import { MessageCircle } from "lucide-react";
import Chatcard from "../../components/chatCard";
import { Outlet } from "react-router-dom";
import View from "../../components/view";
import { API_URL } from "../../utils/configs";
import useSWR from "swr";
import useAxiosConfiguration from "../../utils/axiosFetcher";
import type ResponseGenericChat from "../../utils/interfaces";
import { getUser } from "../../utils/getUser";
import { Role } from "../../utils/enums";

export default function Chats() {
    const fetcher = useAxiosConfiguration();
    const { data: chats } = useSWR<ResponseGenericChat[] >(
        `${API_URL}/chats`,
        fetcher
    );

    return (
        <View>
            <div className="flex relative h-full">
                { getUser()?.role === Role.ADMINER &&
                    <div className="max-w-60 w-full h-full overflow-y-auto bg-neutral-100 border-r-2 border-violet-200">
                        {
                            chats?.map((chat: ResponseGenericChat) => {
                                return (chat &&
                                    <div key={chat.id}>
                                        <Chatcard {...chat} />
                                    </div>
                                )
                            })
                        }
                    </div>
                }
                <div className="relative">
                    <button className="absolute bottom-3 left-3 h-12 w-12 items-center flex justify-center rounded-full bg-violet-500 text-neutral-100 hover:cursor-pointer hover:bg-violet-400 hover:text-white transition-all">
                        <MessageCircle size={30} />
                    </button>
                </div>
                <Outlet />
            </div>
        </View>
    )
} 