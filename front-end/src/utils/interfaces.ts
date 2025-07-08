export interface Auth {
    name?: string;
    document: string;
}

export interface ActualUser {
    id: string;
    name: string;
    role: string;
    documentId: string;
    documentType: string;
    balance?: string;
    number?: string;
    planType?: string;
    active: true;
}


export default interface ResponseGenericChat {
    senderId: string;
    message: string;
    id: string;
    recipientId: string;
    recipientName: string;
    lastMessageContent: string;
    lastMessageTime: string;
    number: number;
    status: string;
}

export interface MessageChat {
    id: string;
    status: string;
    senderId: string;
    message: string;
    timestamp: string;
    estimatedDelivery: string;
    cost: number;
    currentBalance: number;
}

export interface Payment {
    id: string;
    type: string;
    price: number;
    createdAt: string;
}