# Assignment 1: Builder Design Pattern — Email Generator

## Overview
This project implements the **Builder Design Pattern** in Java. It demonstrates how a single construction process managed by a `Director` can produce two distinct representations of an email:
1. **`Email` Object**: An immutable domain model.
2. **`String` Text Preview**: A formatted preview sheet for UI/console output.

## Project Structure
```text
src/
└── email/
    ├── Email.java                # Immutable Product
    ├── EmailBuilder.java         # Builder Interface (Fluent API)
    ├── EmailObjectBuilder.java   # Concrete Builder #1 (Object)
    ├── EmailPreviewBuilder.java  # Concrete Builder #2 (Text Preview)
    ├── EmailDirector.java        # Director managing predefined configurations
    └── Main.java                 # Client code demonstrating usage & validation