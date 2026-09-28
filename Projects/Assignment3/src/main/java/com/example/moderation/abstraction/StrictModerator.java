package com.example.moderation.abstraction;

import com.example.moderation.exception.ModerationException;
import com.example.moderation.implementor.CheckResult;
import com.example.moderation.implementor.ContentChecker;

public class StrictModerator extends ContentModerator {

    public StrictModerator(ContentChecker checker) {
        super(checker);
    }

    @Override
    public boolean processPost(String content, String userId) throws ModerationException {
        CheckResult result = checker.checkContent(content, userId);
        // При строгой модерации любое нарушение приводит к моментальной блокировке
        return result.isApproved();
    }
}