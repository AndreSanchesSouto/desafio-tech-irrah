import useSWR from "swr";
import { API_URL } from "../../utils/configs";
import { useParams } from "react-router-dom";
import useAxiosConfiguration from "../../utils/axiosFetcher";
import type MessageChat from "../../utils/interfaces";
import { getUser } from "../../utils/getUser";
import { useEffect, useRef } from "react";

export default function Messages() {
    const fetcher = useAxiosConfiguration();
    const { id: chatId } = useParams()

    const { data: chats } = useSWR(
        `${API_URL}/messages/${chatId}`,
        fetcher
    );

    function isMyMessage(messageEntity: MessageChat): boolean {
        return messageEntity.senderId == getUser()?.id
    }

    return (
        <div className="w-full h-full flex flex-col gap-3 p-1">
            {
                chats?.map((messageEntity: MessageChat) => (
                    <div
                    key={messageEntity.id} 
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
        </div>
    )
}

interface PropsMessage {
    messageEntity: MessageChat;
    isMyMessage: (messageEntity: MessageChat) => boolean
}

function MessageTextarea({ messageEntity, isMyMessage } : PropsMessage) {
    const textareaRef = useRef<HTMLTextAreaElement>(null);

    useEffect(() => {
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
            textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
        }
    }, [messageEntity.message]);

    return (
        <textarea
            ref={textareaRef}
            className={`resize-none py-2 rounded-md max-w-[30rem] px-2 text-justify overflow-hidden
            ${isMyMessage(messageEntity)
                ? 'bg-violet-400/60 justify-end'
                : 'bg-violet-400'}
            `}
            disabled
            rows={1}
            value={messageEntity.message}
            readOnly
        />
    );
}