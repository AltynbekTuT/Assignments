package com.example.moderation;

import com.example.moderation.exception.ModerationException;
import com.example.moderation.implementor.CheckResult;
import com.example.moderation.implementor.RegexEngineAdapter;
import com.example.moderation.legacy.EngineStatus;
import com.example.moderation.legacy.LegacyRegexFilterEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdapterTest {

    @Test
    @DisplayName("Adapter normal delegation: converts valid response")
    void testNormalDelegation() throws ModerationException {
        // Создаем Fake-объект legacy-движка с фиксированным ответом без использования Mockito
        LegacyRegexFilterEngine fakeEngine = new LegacyRegexFilterEngine() {
            @Override
            public EngineStatus scanBuffer(byte[] rawText, int length, boolean ignoreCase) {
                return new EngineStatus(0, "CLEAN");
            }
        };

        RegexEngineAdapter adapter = new RegexEngineAdapter(fakeEngine);
        CheckResult result = adapter.checkContent("Hello world", "user123");

        assertTrue(result.isApproved());
    }

    @Test
    @DisplayName("Adapter failure translation: translates negative status code into ModerationException")
    void testFailureTranslation() {
        // Создаем Fake-объект, имитирующий внутреннюю ошибку (код -2)
        LegacyRegexFilterEngine fakeEngine = new LegacyRegexFilterEngine() {
            @Override
            public EngineStatus scanBuffer(byte[] rawText, int length, boolean ignoreCase) {
                return new EngineStatus(-2, "ENGINE_OUT_OF_MEMORY");
            }
        };

        RegexEngineAdapter adapter = new RegexEngineAdapter(fakeEngine);

        ModerationException exception = assertThrows(ModerationException.class, () -> {
            adapter.checkContent("Large content...", "user123");
        });

        assertTrue(exception.getMessage().contains("ENGINE_OUT_OF_MEMORY"));
        assertTrue(exception.getMessage().contains("-2"));
    }
}