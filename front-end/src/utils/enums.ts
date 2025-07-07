export const Role = {
    ADMINER: "adminer",
    COMMON: "common"
} as const;

export function setRole(status: string | null): string {
    switch (status?.toLocaleLowerCase()) {
        case Role.ADMINER:  return "Administrador";
        case Role.COMMON:   return "Usuário";
        default:            return "Desconecido";
    }
}

export const MessageState = {
    QUEUED:"queued",          // Mensagem na fila
    PROCESSING:"processing",  // Em processamento
    SENT:"sent",              // Enviada
    DELIVERED:"delivered",    // Entregue
    READ:"read",              // Lida
    FAILED:"failed"           // Falha no envio
} as const

export function setMessageStatus(status: string | null): string {
    switch (status?.toLocaleLowerCase()) {
        case MessageState.QUEUED:       return "Na fila";
        case MessageState.PROCESSING:   return "Processando";
        case MessageState.SENT:         return "Enviada";
        case MessageState.DELIVERED:    return "Entregue";
        case MessageState.READ:         return "Lida";
        case MessageState.FAILED:       return "Falha";
        default:                        return "Desconecido";
    }
}
