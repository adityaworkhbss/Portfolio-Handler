# Portfolio Handler

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Firebase](https://img.shields.io/badge/firebase-ffca28?style=for-the-badge&logo=firebase&logoColor=black)

**Portfolio Handler** is a robust and visually striking Android application that serves as a mobile Content Management System (CMS) / Admin Panel for a dynamic web-based developer portfolio. It enables seamless management of portfolio content on the go, synchronizing data across platforms in real-time.

## 🚀 Features

- **Dashboard & Analytics:** Overview of portfolio statistics and quick management access.
- **Projects Management:** Add, update, and remove projects with detailed descriptions, repository links, and tech stacks.
- **Skills & Experience Tracking:** Easily manage your professional skills and work history.
- **Blog Publishing:** Integrated blog management with "publish/unpublish" toggles and an internal WebView to preview external content accurately.
- **Media & Resume Uploads:** Integrated **Cloudinary** multipart upload service to update profile pictures and upload/download PDF resumes securely directly to and from the device.
- **Social Links:** Manage and update contact and social media connections.
- **Premium Design System:** A cohesive, dark-mode first design utilizing glassmorphism-inspired elements, dynamic micro-animations, and custom typography (Inter font).
- **Push Notifications:** Stay updated with integrated Firebase Cloud Messaging (FCM).

## 🛠 Tech Stack & Libraries

This project leverages modern Android development practices and libraries:

- **Language:** [Kotlin](https://kotlinlang.org/) (JVM Toolchain 17)
- **UI Architecture:** **MVVM** (Model-View-ViewModel) utilizing `ViewModel` and `LiveData`/`StateFlow`.
- **UI Components:** 
  - XML Layouts with **ViewBinding**
  - **Material Design** Components (`BottomSheetDialogFragment` for intuitive CRUD interactions)
  - `RecyclerView` for dynamic lists
- **Navigation:** [Jetpack Navigation Component](https://developer.android.com/guide/navigation) for single-activity architecture (`NavGraph`, `NavController`).
- **Backend & Database:** 
  - [Firebase Firestore](https://firebase.google.com/docs/firestore) for NoSQL, real-time data storage.
  - [Firebase Authentication](https://firebase.google.com/docs/auth) for secure admin access.
  - [Firebase Cloud Messaging (FCM)](https://firebase.google.com/docs/cloud-messaging) for push notifications.
  - [Firebase Analytics](https://firebase.google.com/docs/analytics) for user behavior tracking.
- **Media Hosting:** **Cloudinary** implemented via native `HttpURLConnection` for efficient, multipart file uploads (Images & PDFs).
- **Image Loading:** [Glide](https://github.com/bumptech/glide) for optimized image loading and caching.
- **System Services:** Android `DownloadManager` integration for fetching and saving resumes locally to public storage.

## 📐 Architecture

The application strictly adheres to **Clean Architecture** principles to separate concerns, improve testability, and maintain scalability. The codebase is organized into distinct layers:

1. **`domain/` (Domain Layer):** 
   - Contains core business logic, Entities (Models), and Repository Interfaces (e.g., `UserRepository`). 
   - Completely independent of the Android framework.

2. **`data/` (Data Layer):** 
   - Implements the repository interfaces defined in the domain layer (e.g., `UserRepositoryImpl`).
   - Handles data fetching, mapping, and caching.
   - Contains Data Sources representing remote services (Firestore, Cloudinary).

3. **`presenter/` (Presentation / UI Layer):** 
   - Contains `Activities` (`LoginActivity`, `MainActivity`) and `Fragments`.
   - Houses `ViewModels` that coordinate between the UI and Domain/Data layers.
   - Manages UI state and events.

4. **`adapter/` & `bottomsheets/`:** 
   - Dedicated modules for UI rendering. `RecyclerView` adapters format lists, while `BottomSheetDialogFragments` provide consistent, reusable forms for data entry across the app.

5. **`service/` & `utils/`:**
   - Background services like `FCMService` for push events and helper extensions for the app.

## 🎨 Design System

Portfolio Handler is built with a heavy emphasis on visual excellence:
- **Dark Mode First:** Designed with a sleek, premium dark theme utilizing refined HSL color palettes instead of generic blacks and grays.
- **Glassmorphism:** Subtle translucent overlays and shadows to create depth and hierarchy.
- **Typography:** Uses the modern, clean `Inter` font family natively integrated via XML font resources.

## ⚙️ Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   ```
2. **Open in Android Studio:** Ensure you are using a recent version of Android Studio (supporting Gradle Version Catalogs and Kotlin 1.9+ / Java 17).
3. **Configure Firebase:**
   - Add your `google-services.json` file to the `app/` directory.
   - Ensure Firestore, Auth, and Analytics are enabled in your Firebase Console.
4. **Configure Cloudinary:**
   - Add your Cloudinary API credentials to your project's secure configuration/environment variables to enable image and resume uploads.
5. **Build & Run:** Sync Gradle and run the app on an emulator or physical device running API 24 or higher.

## 📝 License

This project is open-source and available under the [MIT License](LICENSE).
