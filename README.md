# SnapEm v1

Android app that captures a photo when an incorrect lock-screen PIN is entered — a security awareness / anti-theft utility.

## Overview

Native Kotlin Android application using device admin and accessibility capabilities to detect failed unlock attempts and silently capture images of the person using the device.

## Features

- Lock-screen PIN attempt detection
- Automatic camera capture on failed unlock
- Background service for event monitoring
- Custom splash screen and app branding

## Tech Stack

- **Kotlin**
- **Android SDK** (Gradle Kotlin DSL)
- **Device Admin / Accessibility APIs**

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17+
- Android device or emulator (API 24+)

### Build & run

```bash
./gradlew assembleDebug
```

Install the generated APK on a physical device for full lock-screen functionality.

> **Note:** This app requires sensitive permissions (camera, device admin). Use responsibly and only on devices you own.

## Related

See also: [SnapEm](https://github.com/Kezara666/SnapEm) (Java version)

## Author

Kezara Lakshan — [GitHub](https://github.com/Kezara666)
