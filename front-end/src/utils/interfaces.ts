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
    id: string;
    recipientId: string;
    recipientName: string;
    lastMessageContent: string;
    lastMessageTime: string;
    number: number;
}

export default interface MessageChat {
    id: string;
    status: string;
    senderId: string;
    message: string;
    timestamp: string;
    estimatedDelivery: string;
    cost: number;
    currentBalance: number;
}