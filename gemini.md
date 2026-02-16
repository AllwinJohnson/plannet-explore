# 🌍 Anti-Cosmic – Planet Explorer

## System Design Document

---

## 1. Overview

**Anti-Cosmic** is a cross-platform mobile application built using **Kotlin Multiplatform (KMP)**.
The application allows users to explore planets, view images, and read detailed information.

The architecture follows:

* **MVI (Model–View–Intent)**
* **Clean Architecture principles**
* **Unidirectional Data Flow**
* Shared business logic in `commonMain`
* Platform-specific UI (Android / iOS)

---

## 2. Goals

### Functional Goals

* Display list of planets
* View planet details
* Show planet image
* Store planet data locally (Phase 1)
* Scalable to support:

  * Remote API
  * Favorites
  * Search
  * Caching
  * Offline-first behavior

### Non-Functional Goals

* Clear separation of concerns
* High scalability
* Platform independence
* Testable business logic
* Reactive state management

---

## 3. High-Level Architecture

```
                 ┌──────────────────────┐
                 │   Android UI (Compose) │
                 └─────────────┬────────┘
                               │
                 ┌─────────────▼────────┐
                 │   iOS UI (SwiftUI)   │
                 └─────────────┬────────┘
                               │
                 ┌─────────────▼─────────────┐
                 │       Shared Module       │
                 │        (KMP commonMain)   │
                 │                           │
                 │  Presentation (MVI)       │
                 │  Domain (UseCases)        │
                 │  Data (Repository)        │
                 └───────────────────────────┘
```

---

## 4. Module Structure

```
anti-gravity/
│
├── androidApp/
├── iosApp/
└── shared/
     ├── data/
     │    ├── model/
     │    ├── datasource/
     │    └── repository/
     │
     ├── domain/
     │    └── usecase/
     │
     └── presentation/
          ├── intent/
          ├── state/
          ├── effect/
          └── store/
```

---

## 5. Architecture Pattern – MVI

### 5.1 Unidirectional Flow

```
User Action → Intent → Reducer → New State → UI Render
                             ↓
                          Effect (optional)
```

---

### 5.2 Core Components

#### Intent

Represents user actions.

```kotlin
sealed interface PlanetIntent {
    data object LoadPlanets : PlanetIntent
    data class SelectPlanet(val id: String) : PlanetIntent
}
```

---

#### State

Represents immutable UI state.

```kotlin
data class PlanetState(
    val isLoading: Boolean = false,
    val planets: List<Planet> = emptyList(),
    val selectedPlanet: Planet? = null,
    val error: String? = null
)
```

---

#### Effect (Optional)

For one-time events (navigation, toast, etc.).

```kotlin
sealed interface PlanetEffect {
    data class ShowError(val message: String) : PlanetEffect
}
```

---

#### Store / ViewModel

* Processes intents
* Calls use cases
* Emits `StateFlow<PlanetState>`
* Emits `SharedFlow<PlanetEffect>`

---

## 6. Data Layer Design

### Phase 1 – Local Static Data

* Data stored in:

  * Hardcoded list
  * JSON file
* Repository returns in-memory data

```
PlanetRepository
    ↑
LocalPlanetDataSource
```

---

### Phase 2 – Local Database (Optional)

Use:

* SQLDelight (KMP compatible)

```
PlanetRepository
    ↑
SQLDelightDataSource
```

---

### Phase 3 – Remote API (Optional)

```
PlanetRepository
    ↑
RemoteDataSource (Ktor)
    ↑
Local Cache (SQLDelight)
```

Offline-first approach recommended.

---

## 7. Domain Layer

Contains business logic.

### Example Use Case

```kotlin
class GetPlanetsUseCase(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(): List<Planet> {
        return repository.getPlanets()
    }
}
```

---

## 8. Data Models

```kotlin
data class Planet(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
)
```

---

## 9. Image Strategy

Images should NOT be loaded in shared module.

### Approach:

Shared:

* Store image URL or image key

Platform:

* Android → Coil
* iOS → Kingfisher / AsyncImage

This keeps shared module pure.

---

## 10. State Management Strategy

Expose:

```kotlin
val state: StateFlow<PlanetState>
val effects: SharedFlow<PlanetEffect>
```

Do NOT expose:

* MutableStateFlow

UI observes state and reacts.

---

## 11. Navigation Strategy

Navigation handled at platform level.

Shared module:

* Emits `PlanetEffect.NavigateToDetails(id)`

Platform:

* Executes navigation.

---

## 12. Dependency Injection

Options:

* Koin (KMP supported)
* Manual DI (recommended for small app)

For simplicity:

```
AppModule
 ├── PlanetRepository
 ├── GetPlanetsUseCase
 └── PlanetStore
```

---

## 13. Technology Stack

| Layer                 | Technology            |
| --------------------- | --------------------- |
| Language              | Kotlin                |
| Multiplatform         | KMP                   |
| Architecture          | MVI                   |
| Concurrency           | Coroutines + Flow     |
| Serialization         | kotlinx.serialization |
| Database (optional)   | SQLDelight            |
| Networking (optional) | Ktor                  |
| Android UI            | Jetpack Compose       |
| iOS UI                | SwiftUI               |

---

## 14. Scalability Considerations

Future Features Supported:

* Search
* Sorting
* Filters
* Favorites
* Remote sync
* Offline-first caching
* Pagination
* Dark mode
* Localization

MVI + KMP enables these without major refactor.

---

## 15. Testing Strategy

### Unit Testing

* UseCase tests
* Reducer logic tests
* Repository tests (mock datasource)

### Integration Testing

* End-to-end flow testing in shared module

---

## 16. Risks & Mitigation

| Risk                     | Mitigation                      |
| ------------------------ | ------------------------------- |
| Over-engineering         | Keep effects minimal            |
| Complex state growth     | Split feature stores            |
| KMP build complexity     | Keep shared module lean         |
| iOS integration friction | Use clear Flow → Combine bridge |

---

## 17. Future Enhancements

* Shared UI via Compose Multiplatform
* Offline-first caching
* NASA Open API integration
* Animated transitions
* Planet comparison feature
* AI-generated planet facts

---

## 18. Final Architectural Summary

* Clean separation of layers
* Shared business logic
* Platform-specific UI
* Unidirectional data flow
* Immutable state
* Highly scalable

---

# End of Document
