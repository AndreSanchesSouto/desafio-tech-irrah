package com.irrah.back_end.enums;

public enum PlanType {
    PREPAID("prepaid"),
    POSTPAID("postpaid");

    private String planType;

    PlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanType() {
        return this.planType;
    }
}
