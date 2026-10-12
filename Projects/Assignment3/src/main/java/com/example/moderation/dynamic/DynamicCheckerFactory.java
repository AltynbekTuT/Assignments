package com.example.moderation.dynamic;

import com.example.moderation.implementor.ContentChecker;

public class DynamicCheckerFactory {
    private final ContentChecker cloudChecker;
    private final ContentChecker legacyAdapter;

    public DynamicCheckerFactory(ContentChecker cloudChecker, ContentChecker legacyAdapter) {
        this.cloudChecker = cloudChecker;
        this.legacyAdapter = legacyAdapter;
    }

    public ContentChecker selectChecker(String content) {
        if (content != null && content.length() < 30) {
            return legacyAdapter;
        }
        return cloudChecker;
    }
}