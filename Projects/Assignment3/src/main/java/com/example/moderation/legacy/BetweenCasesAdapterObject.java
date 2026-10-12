package com.example.moderation.legacy;

public class BetweenCasesAdapterObject {
     public EngineStatus scanBuffer(byte[] rawText, int length) {
        if (rawText == null || length <= 0) {
            return new EngineStatus(-1, "INVALID_OR_LENGTH_ERROR");
        }
        if (length > 100) {
            return new EngineStatus(-2, "CRITICAL_ENGINE_OUT_OF_MEMORY");
        }

        String text = new String(rawText, 0, length);
        if (text.toLowerCase().contains("badword")) {
            return new EngineStatus(1, "PATTERN_MATCHED_BADWORD");
        }
        return new EngineStatus(0, "BUFFER_CLEAN");
    }
}
