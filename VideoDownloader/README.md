# Video Downloader App

A robust Android application for downloading videos built with Modern Android Development (MAD) practices.

This project demonstrates a scalable structure using Clean Architecture, Jetpack Compose for UI, and Hilt for dependency injection.

## Features
- **Clean Architecture:** Separation of concerns into Domain, Data, and Presentation layers.
- **Jetpack Compose UI:** Modern declarative UI with Material 3 Design (Light/Dark themes).
- **Hilt Dependency Injection:** Simplified dependency management.
- **Coroutines & Flow:** Asynchronous data handling and reactive UI updates.
- **Room Database:** Persistent local storage for tracking download history and status.
- **Simulated Downloading:** Demonstrates UI updating reactively from repository progression.

## Architecture

The project is structured into three main layers:

1. **Domain Layer (`domain`)**
   - Contains core business logic.
   - Models (`VideoInfo`, `DownloadItem`) and Repository Interfaces (`VideoRepository`, `DownloadRepository`).
   - Independent of Android frameworks.

2. **Data Layer (`data`)**
   - Implements the repository interfaces.
   - Manages data sources (Room Database for local storage, simulated remote API for video extraction).
   - Maps between data models (`DownloadEntity`) and domain models (`DownloadItem`).

3. **Presentation Layer (`presentation`)**
   - Jetpack Compose UI components (`MainScreen`).
   - ViewModels (`MainViewModel`) that interact with the Domain layer using Coroutines/Flow.
   - The UI observes StateFlows from the ViewModel to update reactively.

## Tech Stack
- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Hilt (Dagger)**
- **Room Database**
- **Coroutines & Flow**

## Getting Started

### Prerequisites
- Android Studio Iguana (or newer)
- Gradle 8.2+
- JDK 17

### Building the Project
You can build and run the project using Android Studio, or from the command line using Gradle:

```bash
# To assemble the debug APK
./gradlew assembleDebug

# To run tests
./gradlew test
```

## Note on Extraction and Downloading
Currently, the `VideoRepositoryImpl` and `DownloadRepositoryImpl` use simulated delays and mock data to demonstrate the extraction and download progress mechanisms without relying on external third-party scraping libraries or active network connections.
