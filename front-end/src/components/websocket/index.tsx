import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useEffect } from 'react';

export function WebSocketStatusListener() {
    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/ws');
        const stompClient = new Client({
            webSocketFactory: () => socket,
            debug: str => console.log(str),
            reconnectDelay: 5000,
        });

        stompClient.onConnect = () => {
            console.log('Connected to WebSocket');
            stompClient.subscribe('/topic/messages', (message) => {
                const body = JSON.parse(message.body);
                console.log('Status update:', body);
                // Aqui você pode atualizar o estado do React para exibir no frontend
            });
        };

        stompClient.activate();

        return () => {
            stompClient.deactivate();
        };
    }, []);

    return <></>;
}
