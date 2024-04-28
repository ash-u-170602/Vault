# Secure Photo Vault

Secure Photo Vault is an Android application designed to securely store sensitive photos and files. Built with Kotlin and leveraging advanced security features such as AES-256 encryption, this app ensures that your private data remains protected. The app includes biometric authentication options, robust logging, and secure sharing capabilities.

## Features

- **Secure Storage**: Photos and files are encrypted using AES-256 encryption before being stored.
- **PIN Authentication**: Access the app with a secure, 6-digit PIN.
- **Biometric Authentication**: Enhance security with biometric authentication options (Face or Fingerprint).
- **Logging**: Maintain detailed logs of photo additions and access, including photo names, sizes, and access timestamps.
- **Photo Management**: Easily add and remove photos, with removed photos returning to their original location.
- **Sharing**: Safely share photos within the app to external platforms.

## Architecture

The app uses the Model-View-ViewModel (MVVM) architecture to ensure a clean separation of concerns and easier manageability. Dependency injection is handled by Dagger-Hilt, and the Room database is used for secure local storage of encrypted files.

## Libraries and Frameworks

- **Navigation Component**: For handling fragment navigation.
- **Dagger Hilt**: For dependency injection.
- **AndroidX Biometric**: To support biometric authentication.
- **Room Database**: For data persistence.
- **Bouncy Castle**: For implementing AES encryption.
- **Glide**: For efficient image loading.
- **Android PDF Viewer**: For displaying PDFs.

## Setup and Installation

1. Clone the repository.
2. Open the project in Android Studio.
3. Build the project and run on an Android device or emulator.

Or you can download the apk using this link https://drive.google.com/file/d/1B2YXcn-FMMVMDzOTpmTEjWS3Q5EVNckY/view?usp=sharing
