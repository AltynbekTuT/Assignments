package com.example.moderation.implementor;

import com.example.moderation.exception.ModerationException;

public interface ContentChecker {
    /**
     * Проверяет контент на соответствие правилам.
     *
     * @param content 
     * @param userId  
     * @return 
     * @throws ModerationException 
     */
    CheckResult checkContent(String content, String userId) throws ModerationException;
}