# 🤖 Anti-Cosmic – AI Build Instruction Prompt

## Purpose

This document instructs the AI agent to build the **Anti-Cosmic Planet Explorer App** strictly following:

* `gemini.md`
* `branding.md`

Additionally, all development must follow the defined **Git workflow, branching model, and release standards** in this document.

Both documents are the single source of truth for:

* Architecture
* Folder structure
* UI design
* Git management
* Release process

---

# 🔒 Core Instruction

You are building the **Anti-Cosmic** mobile application.

Before generating any code:

1. Carefully read `gemini.md`
2. Carefully read `branding.md`
3. Follow both strictly
4. Follow the Git workflow defined below
5. Do not modify architecture or branding rules
6. Do not introduce alternative workflows

If anything is unclear, request clarification.

---

# 🏗 Architecture Compliance (From gemini.md)

You MUST:

* Use Kotlin Multiplatform (KMP)
* Use MVI architecture
* Follow Clean Architecture layering:

  ```
  presentation → domain → data
  ```
* Keep shared module UI-free
* Keep navigation platform-specific
* Keep image loading platform-specific
* Expose immutable StateFlow
* Maintain unidirectional data flow
* Keep reducer logic pure
* Ensure stores are testable

You MUST NOT:

* Use MVVM
* Mix UI with domain
* Break dependency direction
* Change folder structure defined in `gemini.md`

---

# 🎨 Branding Enforcement (From branding.md)

All UI must strictly follow branding rules.

### Mandatory

* Use exact hex codes
* Follow typography scale
* Maintain 20–28dp corner radius
* Use gradient backgrounds only
* Apply soft shadows
* Motion duration 250–350ms

No deviations allowed.

---

# 📱 Phase 1 Scope

Implement only:

1. Explore / Splash Screen
2. Planet List Screen
3. Planet Detail Screen

Data:

* Local static data only

Planet model must include:

```
id
name
description
imageUrl
```

---

# 🧠 MVI Contract Requirements

Each feature must contain:

```
Intent
State
Effect
Store
```

State must include:

* isLoading
* data
* error

Effects only for:

* Navigation
* One-time events

State must be immutable.

---

# 📂 Folder Structure Compliance

Must match structure in `gemini.md`.

Minimum structure:

```
shared/
  core/
  data/
  domain/
  presentation/

androidApp/ui/
iosApp/Screens/
```

No structural deviation allowed.

---

# 📦 Code Quality Standards

* Production-ready
* No pseudo-code
* No TODO comments
* No magic values
* Use constants
* Clean naming
* Compile-ready Kotlin
* SwiftUI compatible bridging
* Testable business logic

---

# 🔄 Git Management & Development Workflow (MANDATORY)

This project follows an **industry-standard Git workflow**.

---

## 🌿 Branch Strategy

### Permanent Branches

```
main        → Production releases only
feature     → Integration branch
```

---

## 🧩 Task Development Branches

Every task must:

1. Be created from `origin/feature`
2. Follow naming convention:

```
origin/tasks/<small-task-name>
```

Examples:

```
origin/tasks/planet-list-ui
origin/tasks/mvi-store-base
origin/tasks/local-datasource
origin/tasks/branding-theme-setup
```

### Rules

* One branch per small task
* Small, focused changes only
* No large mixed commits

---

## 🔀 Merge Flow

Task workflow:

```
feature → tasks/<task-name>
tasks/<task-name> → feature
```

After task completion:

1. Open PR to `feature`
2. Review
3. Merge into `feature`
4. Delete task branch

---

## 🚀 Release Flow

When ready for release:

```
feature → main
```

Rules:

* Only stable code goes to `main`
* No direct commits to `main`
* Only merge via pull request

---

# 🏷 Versioning Standard (Industry Standard)

Use **Semantic Versioning (SemVer)**:

```
MAJOR.MINOR.PATCH (BUILD_NUMBER)
```

Format:

```
x.y.z(build)
```

Example:

```
1.0.0(1)
1.0.1(2)
1.1.0(10)
2.0.0(25)
```

---

## Version Meaning

| Component    | Meaning                        |
| ------------ | ------------------------------ |
| MAJOR        | Breaking architectural changes |
| MINOR        | New features added             |
| PATCH        | Bug fixes                      |
| BUILD_NUMBER | Incrementing CI/build number   |

---

## Tagging Rules

Every release to `main` must:

1. Create Git tag:

   ```
   v1.0.0
   ```
2. Tag must match versionName (without build number)
3. Tag only from `main`
4. Release notes required

---

# 🔄 Extensibility Constraint

Code must allow future support for:

* SQLDelight
* Ktor remote API
* Favorites
* Search
* Offline-first caching

Do NOT implement them yet.
Structure for easy expansion.

---

# 🧪 Testing Expectations

* Reducers must be unit testable
* Use cases testable
* Repository mockable
* State transitions deterministic

---

# 🚫 Strictly Avoid

* Direct commits to main
* Skipping feature branch
* Large multi-feature task branches
* Architecture drift
* Ignoring branding rules
* Overengineering

---

# 📊 Expected AI Output Format

When generating implementation:

1. Provide file path
2. Provide full file content
3. Respect folder structure
4. Ensure compile-ready code
5. Follow Git workflow when describing steps

---

# 🌌 Brand Vision Reminder

Anti-Cosmic must feel:

* Futuristic
* Cosmic
* Immersive
* Soft-gradient dominant
* Clean
* Slightly playful
* Modern and structured

Every architectural and UI decision must reinforce this identity.

