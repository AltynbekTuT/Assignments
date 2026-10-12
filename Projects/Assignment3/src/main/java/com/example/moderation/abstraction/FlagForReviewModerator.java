package com.example.moderation.abstraction;

import com.example.moderation.exception.ModerationException;
import com.example.moderation.implementor.CheckResult;
import com.example.moderation.implementor.ContentChecker;

public class FlagForReviewModerator extends ContentModerator {

    public FlagForReviewModerator(ContentChecker checker) {
        super(checker);
    }

    @Override
    public boolean processPost(String content, String userId) throws ModerationException {
        CheckResult result = checker.checkContent(content, userId);
        if (!result.isApproved()) {
            System.out.println("Flagged for human review. Reason: " + result.getReason());
        }
        return true; 
    }
}