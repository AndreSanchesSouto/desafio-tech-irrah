import useSWR from "swr";
import { API_URL, getHeaders } from "../../utils/configs";
import { useParams } from "react-router-dom";
import useAxiosConfiguration from "../../utils/axiosFetcher";
import type MessageChat from "../../utils/interfaces";
import { getUser } from "../../utils/getUser";
import { useEffect, useRef } from "react";
import { MessageState } from "../../utils/enums";
import { CircleArrowOutUpRight, CircleCheck, CircleUserRound, CircleX, Clock, RefreshCcw } from "lucide-react";
import api from "../../utils/api";

interface MessagesProps {
    tempMessages?: string[];
}

export default function Messages({ tempMessages }: MessagesProps) {
    const fetcher = useAxiosConfiguration();
    const { id: chatId } = useParams()

    const { data: messages } = useSWR<MessageChat[]>(
        `${API_URL}/messages/${chatId}`,
        fetcher,
        {
            refreshInterval: 500
        }
    );

    useEffect(() => {
        if (!messages) return;
        const messagesId = messages
            .filter((message: MessageChat) => message.status === 'delivered' && message.senderId !== getUser()?.id)
            .map((message: MessageChat) => message.id);

        if (messagesId.length > 0) {
            const markAsRead = async () => {
                try {
                    await api.post(
                        `${API_URL}/messages/read`,
                        { messagesId },
                        getHeaders()
                    );
                } catch (error) {
                    console.error("Erro ao marcar mensagens como lidas:", error);
                }
            };

            markAsRead();
        }
    }, [messages]);

    function isMyMessage(messageEntity: MessageChat): boolean {
        return messageEntity.senderId == getUser()?.id
    }

    return (
        <div className="w-full h-full flex flex-col p-1 absolute">
            {
                messages?.map((messageEntity: MessageChat) => (
                    <div key={messageEntity.id}
                        className={`resize-none flex py-2 rounded-md
                            ${isMyMessage(messageEntity) ?
                                ' justify-end pr-5' :
                                ' pl-5'
                            }    
                            `}
                    >
                        <MessageTextarea
                            messageEntity={messageEntity}
                            isMyMessage={isMyMessage}
                        />

                    </div>
                ))
            }
            { tempMessages && tempMessages.length > 0 &&
                tempMessages?.map((tempMessage, index) => (
                    <div key={index}
                        className='resize-none flex py-2 rounded-md justify-end pr-5'>
                        <MessageTextarea
                            tempMessage={tempMessage}
                            isMyMessage={() => true}
                        />

                    </div>
                ))
            }
        </div>
    )
}

interface PropsMessage {
    messageEntity?: MessageChat;
    isMyMessage: (messageEntity: MessageChat) => boolean
    tempMessage?: string;
}

function MessageTextarea({ messageEntity, isMyMessage, tempMessage }: PropsMessage) {
    const textareaRef = useRef<HTMLTextAreaElement>(null);

    useEffect(() => {
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
        }
    }, [messageEntity?.message]);

    return (
        <div className="items-center relative flex">
            <div className="absolute -left-5">
                {tempMessage ? messageStatus(MessageState.QUEUED) : messageStatus(messageEntity?.status)}
            </div>
            <textarea
                ref={textareaRef}
                className={`resize-none py-2 rounded-md max-w-[30rem] px-2 text-justify overflow-hidden
                ${isMyMessage(messageEntity!!)
                        ? 'bg-violet-400/60 justify-end'
                        : 'bg-violet-400'}
                    `}
                disabled
                rows={1}
                value={tempMessage ? tempMessage : messageEntity?.message}
                readOnly
            />
        </div>
    );
}

function messageStatus(status: string | undefined) {
    switch (status?.toLocaleLowerCase()) {
        case MessageState.QUEUED: return <Clock size={14} strokeWidth={3} />
        case MessageState.PROCESSING: return <RefreshCcw size={14} strokeWidth={3} />
        case MessageState.SENT: return <CircleCheck size={14} strokeWidth={3} />
        case MessageState.DELIVERED: return <CircleArrowOutUpRight size={14} strokeWidth={3} />
        case MessageState.READ: return <CircleUserRound size={14} strokeWidth={3} />
        case MessageState.FAILED: return <CircleX size={14} strokeWidth={3} />
        default: return "Desconecido";
    }
}