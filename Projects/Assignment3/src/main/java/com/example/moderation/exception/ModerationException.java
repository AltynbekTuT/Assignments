package com.example.moderation.exception;

/**
 * Общее бизнес-исключение подсистемы модерации.
 * Никакие специфичные исключения адаптируемых сторонних библиотек
 * не должны просачиваться за пределы Implementor-слоя.
 */
public class ModerationException extends Exception {
    public ModerationException(String message) {
        super(message);
    }

    public ModerationException(String message, Throwable cause) {
        super(message, cause);
    }
}