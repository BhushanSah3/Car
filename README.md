# Car 🚗

This is an Android application written in **Kotlin**, designed to control a car-like device using on-screen buttons.

## 📂 Project Structure

```
Car/
├── app/                     # Main application folder
│   ├── src/                 # Source code
│   ├── res/                 # Resources (layouts, drawables, etc.)
│   ├── AndroidManifest.xml  # Manifest file
├── gradle/                  # Gradle wrapper files
├── build.gradle.kts         # Project build script
├── settings.gradle.kts      # Project settings
└── README.md                # Project documentation
```

## 🚀 Features

- **Remote Control UI**: Move the car forward, backward, left, and right using buttons.
- **Kotlin-based Implementation**: The project has been shifted to Kotlin for better efficiency and maintainability.
- **Simple & Intuitive Design**: Clean and minimalistic UI.

## 📦 Technologies Used

- **Kotlin** (88.6%)
- **Java** (11.4%)
- **Android SDK**
- **XML for UI Layouts**
- **Gradle for Build Management**

## 🛠 Setup Instructions

1. Clone the repository:
   ```sh
   git clone https://github.com/BhushanSah3/Car.git
   ```
2. Open the project in **Android Studio**.
3. Sync the Gradle files.
4. Run the application on an **Android Emulator** or a **physical device**.

## 🔧 Possible Issues & Fixes

- **Missing Drawables Error**:
    - If you encounter `error: resource drawable/ic_arrow_up not found`, make sure the necessary icons are present in `res/drawable/`.
    - You can add them manually or replace `app:srcCompat="@android:drawable/ic_arrow_up_float"` with built-in Android icons.

## 📜 License

This project is licensed under the **MIT License**. Feel free to modify and distribute it as per your needs.

---

### 💡 Contributing

Want to improve this project? Feel free to fork and submit pull requests!

🚀 Happy Coding! 🎉
```