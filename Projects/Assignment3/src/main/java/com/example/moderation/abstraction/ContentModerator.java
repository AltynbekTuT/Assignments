package com.example.moderation.abstraction;

import com.example.moderation.exception.ModerationException;
import com.example.moderation.implementor.ContentChecker;

public abstract class ContentModerator {
    protected ContentChecker checker;

    public ContentModerator(ContentChecker checker) {
        this.checker = checker;
    }

    public void setChecker(ContentChecker checker) {
        this.checker = checker;
    }

    public abstract boolean processPost(String content, String userId) throws ModerationException;
}