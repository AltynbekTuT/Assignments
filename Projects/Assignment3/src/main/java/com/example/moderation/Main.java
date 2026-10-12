package com.example.moderation;

import com.example.moderation.abstraction.*;
import com.example.moderation.implementor.*;
import com.example.moderation.legacy.*;
import com.example.moderation.dynamic.*;
import com.example.moderation.exception.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Starting Content Moderation System ===");

        try {
            ContentChecker openAi = new OpenAiModerationChecker();
            LegacyRegexFilterEngine legacyEngine = new LegacyRegexFilterEngine();
            ContentChecker legacyAdapter = new RegexEngineAdapter(legacyEngine);

            ContentModerator strictModerator = new StrictModerator(openAi);
            boolean result1 = strictModerator.processPost("Hello world! This is a clean post.", "user123");
            System.out.println("Strict Moderator result: " + result1);

            DynamicCheckerFactory factory = new DynamicCheckerFactory(openAi, legacyAdapter);
            
            String shortText = "Short text";
            ContentChecker selectedChecker = factory.selectChecker(shortText);
            System.out.println("Selected checker for short text: " + selectedChecker.getClass().getSimpleName());

            ContentModerator reviewModerator = new FlagForReviewModerator(selectedChecker);
            boolean result2 = reviewModerator.processPost(shortText, "user456");
            System.out.println("FlagForReview Moderator result: " + result2);

        } catch (ModerationException e) {
            System.err.println("Moderation Exception caught: " + e.getMessage());
        }
    }
}