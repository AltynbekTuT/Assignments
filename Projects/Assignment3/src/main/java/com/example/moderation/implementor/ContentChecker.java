package com.example.moderation.implementor;

import com.example.moderation.exception.ModerationException;

public interface ContentChecker {
    /**
     * Проверяет контент на соответствие правилам.
     *
     * @param content Текст сообщения/поста
     * @param userId  Идентификатор пользователя
     * @return Результат проверки CheckResult
     * @throws ModerationException В случае системного сбоя проверки
     */
    CheckResult checkContent(String content, String userId) throws ModerationException;
}