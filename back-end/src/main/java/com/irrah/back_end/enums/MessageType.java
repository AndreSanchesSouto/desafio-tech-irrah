package com.irrah.back_end.enums;


public enum MessageType {
    NORMAL("normal"),
    URGENT("urgent");

    private String messageType;

    MessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getType() {
        return this.messageType;
    }
}
