import { Client } from '@stomp/stompjs';


export function WebSocketStatusListener() {
    const stompClient = new Client({
        brokerURL: 'ws://localhost:8080/ws',
        debug: str => console.log('STOMP DEBUG:', str),
        reconnectDelay: 5000,
        onConnect: () => {
            console.log('✅ Connected to WebSocket');
            stompClient.subscribe('/topic/messages', (message) => {
                const body = JSON.parse(message.body);
                console.log('📥 Mensagem recebida:', body);
            });
        },
        onStompError: (frame) => {
            console.error('💥 STOMP ERROR:', frame.headers['message']);
            console.error(frame.body);
        },
    });

    return <></>;
}
