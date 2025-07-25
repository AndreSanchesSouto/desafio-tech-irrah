import { AlertCircle, SendHorizonal } from "lucide-react";
import { useState } from "react";
import api from "../../utils/api";
import { API_URL, getHeaders } from "../../utils/configs";
import { useParams } from "react-router-dom";
import { WebSocketStatusListener } from "../websocket";
import Messages from "../messages";
import { getUser } from "../../utils/getUser";
import { Role } from "../../utils/enums";

export default function UserChat() {
    const [message, setMessage] = useState("");
    const { id } = useParams()
    const [urgent, setUrgent] = useState(false)
    const[tempMessages, setTempMessages] = useState<string[]>([])

    function handleSendMessage(event: React.FormEvent) {
        event.preventDefault()
        setTempMessages(prev => [...prev, message])
        const data = {
            conversationId: id,
            content: message,
            priority: urgent ? "URGENT" : "NORMAL",
        };

        api.post(`${API_URL}/messages`, data, getHeaders())
    }

    return (
        <div className="h-full w-full flex flex-col bg-neutral-50">
            <div className="flex justify-end items-end h-full overflow-auto relative">
                <Messages tempMessages={tempMessages}/>
            </div>
            <div className="mb-3 bg-transparent">
                <div className={`w-full flex justify-center items-end h-full pb-1
                    ${getUser()?.role === Role.ADMINER ? 'px-16' : 'pr-16 pl-3'}
                    `}>
                    <form id="textMessage" className="flex items-center relative w-full"
                        onSubmit={handleSendMessage}
                    >
                        <input className="bg-violet-200 text-lg flex w-full px-5 py-1 rounded-lg placeholder:text-violet-400 text-violet-900"
                            type="text"
                            placeholder="Digite sua mensagem..."
                            onChange={(e) => setMessage(e.target.value)}
                        />
                        <div className={`absolute right-3 cursor-pointer transition
                            ${urgent ? 'text-violet-800 scale-105' : 'hover:text-violet-600'}
                        `}
                            onClick={() => setUrgent(!urgent)}
                        >
                            <AlertCircle strokeWidth={2} />
                        </div>
                    </form>
                </div>
                <button form="textMessage" className="absolute bottom-3 right-3 h-12 w-12 items-center flex justify-center rounded-full bg-violet-500 text-neutral-100 hover:cursor-pointer hover:bg-violet-400 hover:text-white transition-all">
                    <SendHorizonal />
                </button>
            </div>
            <WebSocketStatusListener />
        </div>
    )
} 