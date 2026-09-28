package com.example.moderation.implementor;

import com.example.moderation.exception.ModerationException;

public class PerspectiveApiChecker implements ContentChecker {
    @Override
    public CheckResult checkContent(String content, String userId) throws ModerationException {
        if (content == null) {
            throw new ModerationException("PerspectiveAPI: Content cannot be null");
        }
        if (content.toLowerCase().contains("spam")) {
            return new CheckResult(false, "PerspectiveAPI: High toxicity score (spam detected)");
        }
        return new CheckResult(true, "PerspectiveAPI: Content is clean");
    }
}