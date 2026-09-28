package com.example.moderation.legacy;

/**
 * Несовместимый класс сторонней библиотеки.
 * Нарушает контракт Implementor по сигнатуре, типам аргументов и механизму ошибок.
 */
public class LegacyRegexFilterEngine {
    
    public EngineStatus scanBuffer(byte[] rawText, int length, boolean ignoreCase) {
        if (rawText == null || length <= 0) {
            return new EngineStatus(-1, "INVALID_BUFFER_OR_LENGTH_ERROR");
        }
        if (length > 10000) {
            return new EngineStatus(-2, "CRITICAL_ENGINE_OUT_OF_MEMORY");
        }

        String text = new String(rawText, 0, length);
        if (text.toLowerCase().contains("badword")) {
            return new EngineStatus(1, "PATTERN_MATCHED_BADWORD");
        }
        return new EngineStatus(0, "BUFFER_CLEAN");
    }
}