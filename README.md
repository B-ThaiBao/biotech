# 🧬 Biotech

An Android app built with Java in Android Studio, designed for biotechnology education and demonstration. Features include Firebase Authentication, interactive 3D model viewing, joystick-based control, and Augmented Reality (AR) capabilities.

## 🎬 Preview  
https://github.com/user-attachments/assets/6a5d1881-1b30-4b7d-b553-e3ea34b464c2

## ✨ Features
✅ Login with Firebase Authentication

🎮 Joystick-controlled 3D model manipulation

🧬 Display biological 3D models in real time

📷 AR integration using ARCore

📱 Built in Android Studio using native Java

## 🚀 Installation
Follow the steps below to build and run the app:

**1. Clone the repository**
```bash
git clone https://github.com/B-ThaiBao/biotech.git
```
**2. Open in Android Studio**
```
Launch Android Studio
Choose "Open an existing project" and select the cloned folder
```

**3. Connect Firebase**
```
Go to Firebase Console: https://console.firebase.google.com/
Create a project and enable Email/Password Authentication
Download google-services.json and place it in app/ directory
Ensure your build.gradle files are configured for Firebase
```

**4. Build the project**
```
Click Sync Gradle in Android Studio
Build the project (Build > Make Project)
```

**5. Run on a real device**
> ⚠️ ARCore is required, so a physical device with AR support is needed
```
Connect your Android device via USB or Wifi
Press Run (Shift + F10)
```

## 🛠️ Tech Stack
**Language:** Java
**IDE:** Android Studio
**Firebase Authentication:** For secure user login
**AR:** ARCore (via Sceneform or ARCore SDK)
**3D Rendering:** Sceneform or OpenGL-based model viewer
**Joystick Control:** Custom UI using Android View

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

