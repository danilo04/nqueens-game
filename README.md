# N-Queens Game 👑

An interactive Android chess puzzle game built with Jetpack Compose where players solve the classic N-Queens problem by placing queens on a chessboard so that no two queens attack each other.

## 🎯 About

The N-Queens puzzle is a classic computer science problem where the challenge is to place N chess queens on an N×N chessboard so that no two queens can attack each other. This means no two queens can be:
- On the same row
- On the same column  
- On the same diagonal

This mobile game implementation allows players to:
- Choose board sizes from 4×4 to 12×12
- Interactively place and remove queens
- Get visual feedback for invalid placements
- Track their solving progress and times

## ✨ Features

- **Multiple Board Sizes**: Play on boards from 4×4 up to 12×12
- **Interactive Gameplay**: Tap to place/remove queens with intuitive controls
- **Visual Feedback**: Real-time validation with error highlighting
- **Modern UI**: Beautiful Material Design 3 interface with dynamic theming
- **Celebrations**: Confetti animations when you solve puzzles
- **Clean Architecture**: Well-structured codebase following Android best practices

## 🛠️ Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Clean Architecture
- **Dependency Injection**: Hilt
- **Database**: Room (for future leaderboards)
- **Navigation**: Navigation Compose
- **Testing**: JUnit, Mockito, Espresso
- **Build System**: Gradle with Kotlin DSL
- **Code Quality**: Detekt + Ktlint

## 🚀 Building the Project

### Prerequisites

- Android Studio Koala Feature Drop | 2023.3.2 or later
- JDK 17 or higher
- Android SDK API 24+ (minimum) / API 35 (target)

### Using Android Studio

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd nqueens-game
   ```

2. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project directory and select it

3. **Sync Project**:
   - Android Studio will automatically prompt to sync the project
   - If not, click "Sync Now" in the notification bar

4. **Build the project**:
   - Select `Build` → `Make Project` from the menu
   - Or use the keyboard shortcut `Ctrl+F9` (Windows/Linux) or `Cmd+F9` (Mac)

5. **Run the app**:
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift+F10`

### Using Gradle (Command Line)

1. **Clone and navigate to the project**:
   ```bash
   git clone <repository-url>
   cd nqueens-game
   ```

2. **Build the debug APK**:
   ```bash
   ./gradlew assembleDebug
   ```

3. **Install on connected device**:
   ```bash
   ./gradlew installDebug
   ```

4. **Build release APK** (requires signing configuration):
   ```bash
   ./gradlew assembleRelease
   ```

5. **Run tests**:
   ```bash
   # Unit tests
   ./gradlew test
   
   # Instrumented tests (requires connected device/emulator)
   ./gradlew connectedAndroidTest
   ```

### Available Gradle Tasks

- `./gradlew assembleDebug` - Build debug APK
- `./gradlew assembleRelease` - Build release APK
- `./gradlew test` - Run unit tests
- `./gradlew connectedAndroidTest` - Run instrumented tests
- `./gradlew lint` - Run Android lint checks
- `./gradlew clean` - Clean build artifacts

## 📋 Code Quality

This project maintains high code quality standards using automated tools and pre-commit hooks. For detailed information about our code quality setup, linting rules, and development guidelines, please see our [Code Quality Documentation](CODE_QUALITY.md).

### Quick Quality Check Commands

```bash
# Run all code quality checks
./code-quality.sh

# Auto-format code
./code-quality.sh format

# Run only Ktlint
./code-quality.sh ktlint

# Run only Detekt
./code-quality.sh detekt
```

### Setting Up Pre-commit Hooks

To automatically run code quality checks before each commit:

```bash
./setup-pre-commit.sh
```

## 🎮 How to Play

1. **Start a New Game**: Enter your name and select the number of queens (board size)
2. **Place Queens**: Tap on empty squares to place queens
3. **Remove Queens**: Tap on placed queens to remove them
4. **Visual Feedback**: Invalid placements will be highlighted in red
5. **Win Condition**: Successfully place all queens so none can attack each other
6. **Celebration**: Enjoy the confetti when you solve the puzzle!