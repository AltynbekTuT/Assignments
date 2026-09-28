package com.example.moderation.implementor;

import com.example.moderation.exception.ModerationException;

public class OpenAiModerationChecker implements ContentChecker {
    @Override
    public CheckResult checkContent(String content, String userId) throws ModerationException {
        if (content == null) {
            throw new ModerationException("OpenAI: Content cannot be null");
        }
        if (content.toLowerCase().contains("hate")) {
            return new CheckResult(false, "OpenAI: Violates safety policy (hate speech)");
        }
        return new CheckResult(true, "OpenAI: Approved by AI model");
    }
}