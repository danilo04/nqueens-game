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

## Architecture

This project follows a modular package structure organized by feature and layer separation:

### Package Structure

```
com.nqueens.game/
├── 📱 MainActivity.kt                    # Entry point
├── 🚀 ChessGamesApplication.kt          # Application class
├── 🧭 ChessGamesNavHost.kt              # Navigation setup
├── 📄 Screens.kt                        # Screen definitions
├── 
├── 🎯 core/                             # Shared core components
│   ├── board/                           # Chess board logic
│   │   ├── domain/                      # Board business logic
│   │   └── ui/                          # Reusable board UI components
│   ├── data/                            # Data layer
│   │   ├── database/                    # Room database setup
│   │   ├── di/                          # Data dependency injection
│   │   └── repositories/                # Data repositories
│   ├── design/                          # Design system
│   │   ├── components/                  # Reusable UI components
│   │   └── theme/                       # Material Design theme
│   ├── icons/                           # Custom icons and graphics
│   └── utils/                           # Utility classes
│       ├── di/                          # Utility dependency injection
│       ├── haptic/                      # Haptic feedback
│       ├── sound/                       # Sound effects
│       └── ui/                          # UI utilities
└── 
└── 🎮 features/                         # Feature modules
    ├── mainmenu/                        # Main menu feature
    │   └── ui/                          # Menu screens and components
    ├── nqueens/                         # N-Queens game feature
    │   ├── domain/                      # Game business logic
    │   └── ui/                          # Game screens and components
    └── leaderboards/                    # Leaderboards feature (future)
        └── ui/                          # Leaderboard screens
```

### Architecture Layers

- **🎯 Core Module**: Contains shared business logic, UI components, and utilities
- **🎮 Features Module**: Feature-specific implementations following MVVM pattern
- **📊 Data Layer**: Repository pattern with Room database for persistence
- **🎨 Presentation Layer**: Jetpack Compose UI with ViewModels
- **💉 Dependency Injection**: Hilt modules organized by feature and layer

Even though the modules are separated by just packages, they can be extracted into their own modules when the app grows. 

### The `board` core module

#### Domain Logic
To implement board games, this module offers UI and domain logic classes that can be used by different games. The `ChessBoard` class offers an implementation of an n-based chess board
that can be used by any games. Notice that the `Board` interface has reactive positions by keeping `StateFlow` for each board position. Each position on the board is a `Spot`
and can be an `EmptySpot` or a `PieceSpot`, representing whether the spot on the board has no piece or a piece respectively. Pieces are defined by the `Piece` interface which can 
have a color and return the possible moves from a board position depending on the kind of piece. 

To implement the domain logic for chess-like games (e.g. N-Queens), the core `board` module offers the interface `BoardGame`. Games can be initialized (for chess, it would put the 
initial pieces for white and black in positions), reset the game, insert, and remove pieces. The `BoardGame` also introduces a `GameState` which can be used to identify 
different states during the game (e.g. when the N-Queens game is solved). 

#### UI Logic

The `board` module also offers UI elements that can be implemented and reused by chess-like games. The `BoardUiState` is a UI state that offers UI decorations on top of the domain
board `Spot`. The module also offers the composable `BoardView` which is a reusable composable for chess-like games. Notice that the Board UI uses `BoardUiState` which hoists
state for each of the board cells. This design makes the `BoardView` flexible to be used for different kinds of games. 

With this design, we present a clear separation of concerns, where the implementation of `BoardGame` implements all the domain logic of the game and the implementation of `BoardUiState` implements the UI logic.

### The `nqueens` module

The `nqueens` module implements the chess-like game `N-Queens Puzzle`. The key classes for the implementation are `NQueensBoardGame` which implements the domain logic and `NQueensBoardUiState` which implements the UI logic. 

In the `NQueensBoardGame` class, the method `insertPiece` blocks the game state if two or more queens threaten each other. This class decides whether the game is `BLOCKED` or the game was solved. The game also updates the pieces on a `ChessBoard` by setting or removing a piece. 

The `NQueensBoardUiState` class implements the UI logic through the method `tapOnCell`. In this class we decorate the `StateFlow` spots with more UI information and return a decorated `StateFlow<CellState>`. Notice that in this class is where we implement different UI and UX feedback such as putting the board cells in red when queens threaten each other, adding a sound when a piece is put on a board and haptic feedback when the game is blocked. 


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