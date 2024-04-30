# Vault App

Vault App is an Android application designed to securely store sensitive photos and files. Built with Kotlin and leveraging advanced security features such as AES-256 encryption, this app ensures that your private data remains protected. The app includes biometric authentication options, robust logging, and secure sharing capabilities.

## Features

- **Secure Storage**: Photos and files are encrypted using AES-256 encryption before being stored.
- **PIN Authentication**: Access the app with a secure, 6-digit PIN.
- **Biometric Authentication**: Enhance security with biometric authentication options (Face or Fingerprint).
- **Logging**: Maintain detailed logs of photo additions and access, including photo names, sizes, and access timestamps.
- **Photo Management**: Easily add and remove photos, with removed photos returning to their original location.
- **Sharing**: Safely share photos within the app to external platforms.

## Rough Architecture

### Overview

The Vault App app is built using the Model-View-ViewModel (MVVM) architecture. This architecture promotes a clear separation of concerns and enhances the scalability and maintainability of the application. The key components of this architecture include:

### Components

1. **View**: The View is responsible for rendering the user interface and capturing user interactions. It communicates directly with the ViewModel to present data and execute actions.

2. **ViewModel**: The ViewModel serves as an intermediary between the View and the Model. It handles the business logic and provides data to the View by observing changes. The ViewModel communicates with the Model via repositories to fetch and store data, ensuring that the View remains free of business logic.

3. **Model**: The Model represents the data and business rules of the application. It is divided into two main parts:
    - **Entities**: These are the data structures used by the Room database for storing photos and files securely.
    - **Repositories**: Repositories manage data operations. They provide a clean API for data access to the rest of the application.

### Data Flow

Data flows from the persistent storage (Room database) up to the UI as follows:
- **Room Database**: Securely stores encrypted photos and files. It is accessed via the DAO (Data Access Object) interfaces defined in the Room library.
- **Repositories**: Communicate with the database and other data sources if needed, encapsulating the logic for data retrieval and storage.
- **ViewModel**: Retrieves data from Repositories and transforms it for the View. It also handles user commands, such as adding or removing photos, and applies business logic.
- **View**: Observes ViewModel to get state updates and reacts to user inputs by calling methods in the ViewModel.
- 
### Security Features

- **AES-256 Encryption**: All photos and files are encrypted using AES-256 before being stored in the local database, ensuring data confidentiality.
- **PIN and Biometric Authentication**: The app ensures that access is secured through a PIN and optionally through biometrics, providing a robust authentication mechanism.

### Additional Components

- **Navigation Component**: Manages the app's screens and facilitates a consistent and predictable user experience.
- **Dependency Injection (Dagger-Hilt)**: Simplifies the injection of dependencies into various components of the application, promoting modularity and ease of testing.

This architecture not only ensures that the app remains scalable and maintainable but also keeps it secure and efficient in handling sensitive data.


## Details of Libraries and Frameworks

The Vault App app utilizes several robust libraries and frameworks to ensure secure storage, effective encryption, and reliable authentication. Below are details of these technologies and their roles within the app:

### Secure Storage

- **Room Database**: A part of the Android Jetpack suite, Room provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite. The app uses Room to securely store encrypted files and photos, ensuring data persistence and security.
    - **Version**: `androidx.room:room-runtime:2.6.1`
    - **Compiler**: `androidx.room:room-compiler:2.6.1`
    - **Kotlin Extensions and Coroutines support**: `androidx.room:room-ktx:2.6.1`

### Encryption

- **Bouncy Castle (BC)**: A lightweight cryptography API for Java and Android. BC is used in the app to implement AES-256 encryption. This ensures that all photos and files stored within the app are encrypted to a high-security standard, making data unreadable without proper decryption keys.
    - **Version**: `org.bouncycastle:bcprov-jdk15on:1.69`

### Authentication

- **AndroidX Biometric Library**: Provides biometric authentication, such as fingerprint and face recognition, which is integrated into the app to offer an additional layer of security. This library simplifies the process of implementing biometric hardware features across a wide range of devices.
    - **Version**: `androidx.biometric:biometric:1.1.0`

- **PIN-based Authentication**: Utilizes a custom implementation within the app to manage PIN entry and verification, ensuring that access to the app is gated by a secure, user-defined PIN.

### Dependency Injection

- **Dagger Hilt**: Used for dependency injection, Dagger Hilt helps manage dependencies across the application, making it easier to inject repositories, view models, and other components where needed. This aids in maintainability and scalability of the app.
    - **Version**: `com.google.dagger:hilt-android:2.48`
    - **Compiler**: `com.google.dagger:hilt-compiler:2.48`

### User Interface and Navigation

- **Navigation Component**: Part of Android Jetpack, the Navigation component helps with managing app navigation within a single activity. This component simplifies implementing navigation, providing a consistent and predictable user experience across the app.
    - **Versions**: `androidx.navigation:navigation-fragment-ktx:2.7.7`, `androidx.navigation:navigation-ui-ktx:2.7.7`

These libraries and frameworks collectively contribute to the robust functionality of the Vault App, ensuring that the app is secure, user-friendly, and highly maintainable.

## Decisions or Assumptions Made During Development

During the development of the Vault App, several key decisions and assumptions were made to guide the design and functionality of the app. These are outlined below:

### Security First Approach

- **Assumption**: Users prioritize security for their sensitive photos and files.
- **Decision**: Implement AES-256 encryption for all stored data to ensure high-level security against unauthorized access and data breaches.

### User Authentication

- **Assumption**: Users require a simple yet secure method for accessing their data.
- **Decision**: Implement a dual-layer authentication system that includes a 6-digit PIN for quick access and biometric authentication for added security. This approach balances convenience and security, making the app both user-friendly and secure.

### Storage and Data Handling

- **Assumption**: Users may store large quantities of photos and require efficient data handling.
- **Decision**: Use Room Database for data persistence, which provides robust handling of large datasets with minimal overhead. The integration of Room ensures that data operations are efficient and transaction-safe.

### Dependency Management

- **Assumption**: The application needs to be scalable and maintainable as it evolves.
- **Decision**: Implement Dagger Hilt for dependency injection to manage dependencies cleanly and efficiently across the app. This decision supports easier maintenance and scalability of the application.

### User Interface and Navigation

- **Assumption**: Users expect a fluid and intuitive navigation experience within the app.
- **Decision**: Utilize the Navigation Component from Android Jetpack to manage app navigation. This ensures a consistent and reliable user experience while simplifying the development process.

### Extensibility and Third-Party Integration

- **Assumption**: In the future, users might want to share their encrypted photos securely outside the app.
- **Decision**: Design the app architecture to allow for the integration of sharing capabilities. This includes keeping the architecture modular to facilitate the addition of new features like secure sharing to external platforms.

### Logging for Audit and Diagnostics

- **Assumption**: Keeping track of user activity within the app can aid in diagnostics and provide a secure audit trail.
- **Decision**: Implement detailed logging for photo additions and access within the app. This helps in maintaining a secure and traceable record of all operations, enhancing user trust and app reliability.

These decisions and assumptions have been fundamental in shaping the development of the Vault App, ensuring that the app meets the needs of users while adhering to high standards of security and functionality.


## Setup and Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Build the project and run on an Android device or emulator.

Or you can download the apk using this link https://drive.google.com/file/d/1htUrpWOj_XRTrBJNN39xzaU_c_8WK48h/view?usp=sharing
