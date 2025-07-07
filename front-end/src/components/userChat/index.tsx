import { SendHorizonal } from "lucide-react";
import { useState } from "react";
import api from "../../utils/api";
import { API_URL, getHeaders } from "../../utils/configs";
import { useParams } from "react-router-dom";

export default function UserChat() {
    const [message, setMessage] = useState("");
    const { id } = useParams()

    function handleSendMessage() {

        const data = {
            conversationId: id,
            recipientId: "2359f629-5b67-4fbd-8f0d-0be6dc8ff645",
            content: message,
            priority: "normal",
            userPlanType: "POSTPAID"
        };

        api.post(`${API_URL}/messages`, data, getHeaders())
    }

    return (
        <div className="h-full w-full flex flex-col bg-neutral-50">
            <div className="">
                
            </div>
            <div className="mb-3 h-full">
                <div className="w-full flex justify-center items-end h-full px-16 pb-1">
                    <input className="bg-violet-200 text-lg flex w-full px-5 py-1 rounded-lg placeholder:text-violet-400 text-violet-900"
                    type="text" 
                    placeholder="Digite sua mensagem..."
                    onChange={(e) => setMessage(e.target.value)}
                />
                </div>
                <button className="absolute bottom-3 right-3 h-12 w-12 items-center flex justify-center rounded-full bg-violet-500 text-neutral-100 hover:cursor-pointer hover:bg-violet-400 hover:text-white transition-all"
                    onClick={handleSendMessage}
                >
                    <SendHorizonal />
                </button>
            </div>
        </div>
    )
} 