package com.example.moderation;

import com.example.moderation.abstraction.FlagForReviewModerator;
import com.example.moderation.abstraction.StrictModerator;
import com.example.moderation.exception.ModerationException;
import com.example.moderation.implementor.CheckResult;
import com.example.moderation.implementor.ContentChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BridgeTest {
    private ContentChecker mockChecker;

    @BeforeEach
    void setUp() {
        mockChecker = mock(ContentChecker.class);
    }

    @Test
    @DisplayName("StrictModerator rejects post when checker returns approved=false")
    void testStrictModeratorRejection() throws ModerationException {
        when(mockChecker.checkContent("bad text", "u1"))
                .thenReturn(new CheckResult(false, "Spam detected"));

        StrictModerator moderator = new StrictModerator(mockChecker);
        boolean result = moderator.processPost("bad text", "u1");

        assertFalse(result);
        verify(mockChecker, times(1)).checkContent("bad text", "u1");
    }

    @Test
    @DisplayName("FlagForReviewModerator approves post even when flagged")
    void testFlagForReviewModerator() throws ModerationException {
        when(mockChecker.checkContent("suspicious text", "u1"))
                .thenReturn(new CheckResult(false, "Flagged"));

        FlagForReviewModerator moderator = new FlagForReviewModerator(mockChecker);
        boolean result = moderator.processPost("suspicious text", "u1");

        assertTrue(result); // Флаг поднят, но пост не заблокирован полностью
        verify(mockChecker, times(1)).checkContent("suspicious text", "u1");
    }
}