package com.irrah.back_end.enums;

public enum Role {
    ADMINER("adminer"),
    COMMON("common");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
