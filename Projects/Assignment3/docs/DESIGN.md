# Design Rationale Document: Content Moderation System

## 1. Domain Context & Problem Statement
The goal of this project is to implement a robust **Content Moderation Subsystem** designed to filter user-generated text across multi-tenant digital platforms. The system must evaluate submitted text, determine whether it meets community guidelines, and support diverse operational workflows—ranging from automatic post rejection to flagging content for manual human review.

To fulfill these business goals, the system integrates two external third-party content-checking services (Google Perspective API and OpenAI Moderation API) alongside an in-house, legacy binary pattern-matching engine (`LegacyRegexFilterEngine`).

---

## 2. Structural Design Patterns

### 2.1. Bridge Pattern Implementation
To prevent a combinatorial explosion of subclasses, the **Bridge Pattern** was selected to decouple the high-level operational workflows (Abstractions) from the underlying technical checking mechanisms (Implementors).

* **Abstraction (`ContentModerator`):** An abstract class defining the high-level moderation workflow. It contains a reference to the `ContentChecker` interface (the Bridge link).
  * **Refined Abstractions:**
    * `StrictModerator`: Implements immediate rejection logic if content fails verification.
    * `FlagForReviewModerator`: Implements soft-moderation logic, marking suspicious posts for manual review without deleting them.
* **Implementor (`ContentChecker`):** The unified interface declaring the `checkContent(String content, String userId)` method.
  * **Concrete Implementors:** `PerspectiveApiChecker` and `OpenAiModerationChecker`.

**Design Benefit:** Adding a new moderation policy (e.g., `AutomatedEscalationModerator`) or a new checking service (e.g., `AzureContentSafetyChecker`) requires zero changes to existing classes, maintaining an $O(N + M)$ class hierarchy growth instead of $O(N \times M)$.

---

### 2.2. Adapter Pattern Implementation
The legacy inspection tool (`LegacyRegexFilterEngine`) could not be integrated directly into the Bridge structure due to severe interface incompatibilities.

#### Incompatibility Analysis:
1. **Method Signatures & Parameter Types:** The legacy engine requires raw byte arrays (`byte[] rawText`), explicit buffer lengths (`int length`), and boolean flags (`boolean ignoreCase`), whereas the modern system communicates via standard Java `String` objects.
2. **Return Types:** The legacy engine returns a low-level status container (`EngineStatus`) containing integer status codes, whereas modern implementors return a rich `CheckResult` object.
3. **Error Handling Model:** Instead of throwing standard Java exceptions, the legacy engine reports operational failures (e.g., buffer overflows, memory limits) via negative integer status codes (`-1`, `-2`).

#### Solution (`RegexEngineAdapter`):
The `RegexEngineAdapter` implements `ContentChecker` (acting as the Target) while wrapping an instance of `LegacyRegexFilterEngine` (the Adaptee).

* **Data Translation:** Converts incoming Java strings into UTF-8 byte arrays before invoking `scanBuffer()`.
* **Exception Translation:** Intercepts negative integer status codes from `EngineStatus` and translates them into domain-specific `ModerationException` instances, shielding high-level business logic from legacy details.

---

## 3. Dynamic Selection Module (Complexity Requirement)

To optimize latency and external API costs, the system incorporates a runtime selection factory: **`DynamicCheckerFactory`**.

* **Execution Logic:**
  * For short messages (`content.length() < 30`), the factory dynamically selects the fast, local `RegexEngineAdapter` to eliminate network overhead.
  * For longer or more complex text, the factory dynamically routes requests to cloud-based AI checkers (`PerspectiveApiChecker` / `OpenAiModerationChecker`).

This dynamic routing happens transparently at runtime without altering the `ContentModerator` abstraction.

---

## 4. Architectural Trade-offs & Limitations

1. **Memory Overhead in Adapter:** Converting `String` instances to `byte[]` arrays for every legacy scan creates short-lived allocations, which can increase Garbage Collection pressure under ultra-high throughput.
2. **Simplified Legacy Emulation:** To maintain thread safety without complex locking, the legacy engine is treated as stateless within the adapter wrapper.
3. **Fallback Strategy:** Currently, if a primary cloud provider fails, error propagation is strict (`ModerationException`). Future iterations could implement a Circuit Breaker pattern to fall back to the local adapter automatically.