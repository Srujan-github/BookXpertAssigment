# 📱 BookXpertAssigment Android Project

## 📝 Overview

This is an Android application built using **Jetpack Compose/XML**, **MVVM architecture**, and **Hilt for Dependency Injection**. The app fetches data from a remote API, stores it in a Room database, and follows an **offline-first approach**.

## 🚀 Features

- Fetches data from a **remote API** using Retrofit
- Stores data locally using **Room Database**
- Offline-first approach with **cached data**
- Uses **Jetpack Compose/XML** for UI
- Implements **Hilt** for Dependency Injection
- Follows **MVVM architecture** for clean code structure
- Handles **permissions dynamically**

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose / XML
- **Architecture**: MVVM
- **Networking**: Retrofit + OkHttp
- **Dependency Injection**: Hilt
- **Local Database**: Room
- **Async Operations**: Coroutines + Flow
- **Version Control**: Git


## 📂 Project Structure

```
android-project/
│── app/
│   ├── data/            # Data layer (Retrofit API, Room Database, Repositories)
│   ├── domain/          # Business logic (Use cases, Models)
│   ├── presentation/    # UI layer (Compose Screens, ViewModels)
│   ├── di/              # Dependency Injection (Hilt Modules)
│   ├── common/           # Utility classes and helpers
│   ├── MainActivity.kt  # Entry point of the app
│   └── ...
└── build.gradle         # Project dependencies
```
 

## Demo
https://github.com/user-attachments/assets/d5ed77fc-a792-4372-a7d5-5a73e24c9c28


## 📜 License

This project is licensed under the **MIT License**.

---

Made with ❤️ by Sai Srujan

