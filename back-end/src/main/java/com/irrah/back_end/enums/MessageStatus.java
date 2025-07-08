package com.irrah.back_end.enums;


public enum MessageStatus {
    QUEUED("queued"),          // Mensagem na fila
    PROCESSING("processing"),  // Em processamento
    SENT("sent"),              // Enviada
    DELIVERED("delivered"),    // Entregue
    READ("read"),              // Lida
    FAILED("failed");          // Falha no envio

    private final String status;

    MessageStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static boolean isFinalStatus(String status) {
        return DELIVERED.getStatus().equals(status) ||
                READ.getStatus().equals(status) ||
                FAILED.getStatus().equals(status);
    }
}