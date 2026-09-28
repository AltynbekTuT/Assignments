# Content Moderation System (Bridge & Adapter Design Patterns)

A Java-based content moderation system built to process user-generated text across digital platforms. The system demonstrates the application of the **Bridge** and **Adapter** structural design patterns to decouple moderation strategies from verification engines and seamlessly integrate incompatible legacy filters.

---

## 🏗️ Architecture Overview

The project is structured into three main layers:

1. **Bridge Pattern:** Decouples high-level moderation policies (`StrictModerator`, `FlagForReviewModerator`) from underlying content checkers (`OpenAiModerationChecker`, `PerspectiveApiChecker`).
2. **Adapter Pattern:** Integrates a non-compatible `LegacyRegexFilterEngine` into the unified `ContentChecker` interface. The adapter translates `String` content to raw `byte[]` buffers and maps negative integer status codes into domain-specific `ModerationException` instances[cite: 1].
3. **Dynamic Selection Module:** Features a `DynamicCheckerFactory` that routes requests to local legacy adapters or cloud-based AI checkers at runtime based on input length[cite: 1].

---

## 📁 Project Structure

```text
Assignment3/
├── docs/
│   ├── DESIGN.md
│   └── diagram.png
├── src/
│   ├── main/java/com/example/moderation/
│   │   ├── abstraction/        # Bridge Abstraction (ContentModerator, StrictModerator, FlagForReviewModerator)
│   │   ├── dynamic/            # Runtime selection module (DynamicCheckerFactory)
│   │   ├── exception/          # Custom exceptions (ModerationException)
│   │   ├── implementor/        # Bridge Implementor & Target (ContentChecker, CheckResult, Adapters, AI Checkers)
│   │   └── legacy/             # Non-modifiable legacy engine (LegacyRegexFilterEngine, EngineStatus)
│   └── test/java/com/example/moderation/
│       ├── AdapterTest.java    # Unit tests for Adapter pattern & exception translation
│       └── BridgeTest.java     # Unit tests for Bridge pattern behavior
├── pom.xml                     # Maven project configuration
└── README.md