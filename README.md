# NFL Photo Sharing App 📸

A modern Android application that allows NFL fans to share their game-day memories and experiences through photos. Built with Clean Architecture and Modern Android Development practices.

## Architecture Overview

### Clean Architecture

This project implements Clean Architecture to ensure:

- **Separation of Concerns**: Clear boundaries between data, domain, and presentation layers
- **Testability**: Easy to test each component in isolation
- **Maintainability**: Changes in one layer don't affect others
- **Scalability**: New features can be added without modifying existing code

### Modular Architecture

The app is structured into the following modules:

```plaintext
app/
├── data/                 # Data Layer
│   ├── api/             # Network API interfaces
│   ├── local/           # Local database
│   ├── repositories/    # Repository implementations
│   ├── di/              # Data layer dependency injection
│   └── models/          # Data models and mappers
│
├── domain/              # Domain Layer
│   ├── models/          # Domain entities
│   ├── repositories/    # Repository interfaces
│   ├── usecases/        # Business logic use cases
│   └── di/              # Domain layer dependency injection
│
├── app/              # Presentation Layer
│   ├── designsystem/   # UI Design system
│   │   ├── component/   # Custom UI components
│   │   ├── theme/       # App theme and styling
│   ├── feature/        # Feature implemented
│   ├── navigation/     # Navigation components
│
```

## Current Features

- **Login**: Secure login system for users
- **Logout**: Secure login system for users
- **Home Feed**: Browse shared NFL memories
- **Add Memory**: Capture and share new game-day moments
- **Profile**: Personal user space with shared memories

## Technical Stack

- **UI**: Jetpack Compose with Material3 Design
- **Navigation**: Compose Navigation
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt (Dependency Injection)
- **Async**: Kotlin Coroutines + Flow
- **Local Storage**: Preferences DataStore


## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on emulator or device
