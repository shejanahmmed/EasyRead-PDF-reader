<div align="center">
  <!-- Note: You can replace the src attribute with the actual path to your logo (e.g., hosted on GitHub or an Imgur link) -->
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="EasyRead Logo" width="80" />

  # EasyRead PDF Reader

  **A modern, lightweight, and high-performance Android PDF reader.**

  [![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg?logo=kotlin)](https://kotlinlang.org)
  [![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-success.svg?logo=android)](https://developer.android.com/jetpack/compose)
  [![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
</div>

## Overview

**EasyRead** is a professional Android PDF reading application built entirely with modern Android development practices. Designed with a clean, "box-first" aesthetic and prioritizing performance, it leverages native Android APIs to deliver a fast and seamless document reading experience without relying on bloated third-party rendering libraries.

## Key Features

- **Native PDF Rendering:** Utilizes Android's native `PdfRenderer` API for smooth, efficient, and memory-conscious document viewing.
- **Modern Dashboard:** A functional, dashboard-style home screen featuring a "Quick Tools" grid (All Files, Favourites, Library, Security Tools) and a quick-access list of recent files.
- **Advanced File Management:** Includes a fully searchable file library and an organized navigation system to easily locate and manage your documents.
- **Adaptive Theming:** Persistent, robust theme management (System / Light / Dark modes) powered by DataStore/SharedPreferences, providing a comfortable reading experience in any environment.
- **Premium UI/UX:** Built natively with Jetpack Compose, featuring custom floating navigation bars, sophisticated modals with background blur effects, and smooth micro-animations.

## Architecture & Tech Stack

EasyRead is built with scalability, maintainability, and performance in mind:

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture:** MVVM (Model-View-ViewModel) with Unidirectional Data Flow (UDF)
- **PDF Engine:** `android.graphics.pdf.PdfRenderer` (Zero heavy third-party dependencies)
- **Build System:** Gradle with Kotlin DSL (`build.gradle.kts`)

## Getting Started

### Prerequisites

- **Android Studio:** Iguana (or newer recommended)
- **JDK:** Version 17
- **Android SDK:** API 24 (Nougat) or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ShejanAhmmed/EasyRead-PDF-reader.git
   ```
2. Open the project in **Android Studio**.
3. Sync the project with Gradle files.
4. Build and run the application on an emulator or a physical Android device.

## Code Structure

- `ui/` - Contains all Jetpack Compose screens, navigation logic, and UI components (Home, Library, Search, etc.).
- `data/` - Repositories and data sources for file handling and preferences.
- `viewmodel/` - ViewModels managing the state and business logic for the UI layer.

## Contributing

Contributions, issues, and feature requests are welcome. Feel free to check the issues page or submit a pull request if you want to contribute to the project.

## License

This project is licensed under the [MIT License](LICENSE) - see the `LICENSE` file for details.
