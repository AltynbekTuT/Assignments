package com.example.moderation.legacy;

public class EngineStatus {
    private final int statusCode; // 0 = Success, 1 = Spam, -1 = Syntax Error, -2 = Out of Memory
    private final String detailMessage;

    public EngineStatus(int statusCode, String detailMessage) {
        this.statusCode = statusCode;
        this.detailMessage = detailMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}