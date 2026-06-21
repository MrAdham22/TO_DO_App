# TO DO App 📝

A clean, modern, and efficient Android Task Management application designed to help users organize their daily schedules. This app features a calendar-integrated view to track tasks day by day with a professional UI and seamless interactions.

## 🚀 Key Features

-   **Calendar-Driven Task View**: Integrated horizontal calendar for easy navigation between dates and viewing tasks for specific days.
-   **Task Management**: Full CRUD operations (Create, Read, Update, Delete) for tasks.
-   **Interactive UI**:
    -   **Swipe-to-Reveal**: Efficient swipe gestures to delete tasks.
    -   **Bottom Sheet Editor**: Intuitive task addition and editing via a bottom sheet fragment.
    -   **Completion Toggle**: Quickly mark tasks as done or pending.
-   **Local Persistence**: All data is stored locally using Room Database, ensuring availability even without an internet connection.
-   **Empty State Management**: Dynamic UI that informs users when no tasks are scheduled for a selected date.

## 🏗️ Architecture & Technical Stack

The project follows standard Android development practices, focusing on modularity and separation of concerns.

### Tech Stack
-   **Language**: Kotlin
-   **UI Framework**: View Binding (for type-safe view access)
-   **Database**: Room Persistence Library (SQLite abstraction)
-   **UI Components**:
    -   RecyclerView (with custom ItemTouchHelper for swipe gestures)
    -   [Kizitonwose Calendar](https://github.com/kizitonwose/Calendar) (for the week-view calendar)
    -   Material Design Components (Bottom Sheets, Floating Action Buttons)

### Architecture Overview
-   **UI Layer**: Fragment-based architecture.
    -   `ListFragment`: The main screen managing the calendar and task list.
    -   `AddTaskFragment`: A `BottomSheetDialogFragment` for creating and editing tasks.
-   **Data Layer**:
    -   **Room Database**: Manages the local SQLite instance.
    -   **DAOs (Data Access Objects)**: Defined in `TaskDao` to handle semantic queries like `getTasksForLocalDay`.
    -   **Entities**: The `Task` data class defines the schema.
-   **Application**: `MyApplication` handles the singleton database initialization.

## 📂 Project Structure

```text
app/src/main/java/
├── com.ndPractice.todoapp/
│   ├── Database/           # Data layer (Entities, DAOs, Database config)
│   ├── add_task_bottom_sheet/ # Task creation/editing feature
│   ├── utils/              # UI ViewHolders and helpers
│   └── MyApplication.kt    # Application class for DB init
└── main/
    ├── fragments/
    │   └── list_fragment/  # Task listing and Calendar integration
    └── MainActivity.kt     # Host activity
```

## 🛠️ Getting Started

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/TODOApp.git
    ```
2.  **Open in Android Studio**:
    -   Open the project folder.
    -   Wait for Gradle Sync to complete.
3.  **Run the app**:
    -   Select an emulator or physical device.
    -   Click "Run".

## 🤝 Contribution

Contributions are welcome! Feel free to open issues or submit pull requests to improve the app's features or architecture.

---
*Created with ❤️ as a professional showcase of modern Android development.*
