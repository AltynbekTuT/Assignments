package com.example.moderation.implementor;

public class CheckResult {
    private final boolean approved;
    private final String reason;

    public CheckResult(boolean approved, String reason) {
        this.approved = approved;
        this.reason = reason;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getReason() {
        return reason;
    }
}