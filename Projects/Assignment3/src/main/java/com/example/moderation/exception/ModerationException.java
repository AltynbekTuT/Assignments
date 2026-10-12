package com.example.moderation.exception;

public class ModerationException extends Exception {
    public ModerationException(String message) {
        super(message);
    }

    public ModerationException(String message, Throwable cause) {
        super(message, cause);
    }
}