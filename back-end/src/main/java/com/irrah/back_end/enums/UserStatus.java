package com.irrah.back_end.enums;

public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getUserStatus() {
        return this.status;
    }
}
