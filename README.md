# GDG RBU Recruitment Task: Dynamic Content App

This is a submission for the App Developer position in the GDG Ramdeobaba University Recruitment Drive 2025-26.

The project is a simple Android application built with Kotlin and Jetpack Compose that demonstrates the ability to load and display dynamic content from a cloud backend in real-time.

## 🎥 Demo Video
<a href="https://youtube.com/shorts/UylxHZWGNTA?feature=share" target="_blank">
    Watch the App Demo Video Here!
</a>

## ✨ Features Implemented

This app successfully implements all the requirements outlined in the task description.

### Core Features
- **Dynamic Content:** All banners, including text and images, are fetched from a Google Firestore database. There is no hardcoded content in the app.
- **Real-Time Updates:** The app uses a real-time listener (`addSnapshotListener`) to instantly reflect any changes made in the Firestore database without needing an app restart.
- **Promotional Banner UI:** The UI is built around a horizontal pager, creating a swipeable, auto-scrolling carousel that mimics promotional banners in popular apps.

### 🏆 Bonus Features
- **Dynamic Theming:** The background color and text color of each banner are controlled remotely from the Firestore database, demonstrating the ability to dynamically alter the UI's appearance.
- **Clickable Banners:** Banners can have an associated `actionUrl`. Tapping on a banner opens the specified URL in a browser.
- **Image Fullscreen View:** Tapping on an image in a banner opens it in a fullscreen, immersive dialog.
- **Pull-to-Refresh:** Users can swipe down to trigger a manual refresh of the content, a common and intuitive UX pattern.
- **Animated Splash Screen:** The app includes a custom animated splash screen for a polished startup experience.

## 🛠️ Tech Stack & Architecture

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** Follows a basic MVVM pattern (ViewModel for state, Repository for data fetching).
- **Backend:** Google Firestore for real-time data storage.
- **Image Loading:** Coil for asynchronously loading and caching images from URLs.
- **Asynchronous Programming:** Kotlin Coroutines and Flow for managing background tasks and data streams.

## ⚙️ How to Set Up and Run

1.  **Clone the repository:**
    ```bash
    git clone [Your GitHub Repository URL]
    ```
2.  **Firebase Setup:**
    - Create a new project on the [Firebase Console](https://console.firebase.google.com/).
    - Add an Android app to the project with the package name `com.example.gdg_task`.
    - Download the `google-services.json` file and place it in the `app/` directory of the project.
    - Go to the "Firestore Database" section, create a database, and start a collection named `contents`.
    - Add documents to the `contents` collection with the fields `text` (String), `imageUrl` (String), `backgroundColor` (String), and `textColor` (String).
3.  **Build and Run:**
    - Open the project in Android Studio.
    - Let Gradle sync the dependencies.
    - Build and run the app on an emulator or a physical device.
