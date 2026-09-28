package com.example.moderation.implementor;

import com.example.moderation.exception.ModerationException;
import com.example.moderation.legacy.EngineStatus;
import com.example.moderation.legacy.LegacyRegexFilterEngine;

import java.nio.charset.StandardCharsets;

public class RegexEngineAdapter implements ContentChecker {
    private final LegacyRegexFilterEngine legacyEngine;

    public RegexEngineAdapter(LegacyRegexFilterEngine legacyEngine) {
        this.legacyEngine = legacyEngine;
    }

    @Override
    public CheckResult checkContent(String content, String userId) throws ModerationException {
        if (content == null) {
            throw new ModerationException("Adapter: Content cannot be null");
        }

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        EngineStatus status;

        try {
            // Вызов несовместимого метода с другими параметрами и типами
            status = legacyEngine.scanBuffer(bytes, bytes.length, true);
        } catch (Exception e) {
            throw new ModerationException("Adapter: Unexpected legacy invocation error", e);
        }

        // Перехват и трансляция кодов ошибок в ModerationException
        if (status.getStatusCode() < 0) {
            throw new ModerationException("Legacy Engine Failure [" + status.getStatusCode() + "]: " + status.getDetailMessage());
        }

        boolean isApproved = (status.getStatusCode() == 0);
        return new CheckResult(isApproved, "LegacyEngine: " + status.getDetailMessage());
    }
}