package com.example.moderation.dynamic;

import com.example.moderation.implementor.ContentChecker;

public class DynamicCheckerFactory {
    private final ContentChecker cloudChecker;
    private final ContentChecker legacyAdapter;

    public DynamicCheckerFactory(ContentChecker cloudChecker, ContentChecker legacyAdapter) {
        this.cloudChecker = cloudChecker;
        this.legacyAdapter = legacyAdapter;
    }

    /**
     * Выбирает реализацию динамически в зависимости от длины контента во время исполнения.
     */
    public ContentChecker selectChecker(String content) {
        if (content != null && content.length() < 30) {
            // Для коротких сообщений используется быстрый локальный Адаптер
            return legacyAdapter;
        }
        // Для длинных текстов используется облачный сервис
        return cloudChecker;
    }
}