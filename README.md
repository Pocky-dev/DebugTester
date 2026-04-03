# 🐛 Debug Tester

**App Name:** Debug Tester  
**Package:** `db.tester`  
**Min SDK:** 21 (Android 5.0)  
**Target SDK:** 34 (Android 14)

---

## Fitur

| Fitur | Deskripsi |
|---|---|
| 🌐 Network Test | Ping server & cek tipe koneksi (WiFi/Mobile) |
| 📱 Device Info | Info lengkap hardware, OS, RAM, storage, CPU |
| 📋 Log Viewer | Baca, tulis, dan hapus logcat |
| 🔧 Build Info | Versi, waktu build, package name tampil di home |

---

## Cara Build APK

### Syarat
- Android Studio (Hedgehog atau lebih baru)
- JDK 17
- Android SDK 34

### Langkah

```bash
# Clone repo
git clone https://github.com/USERNAME/DebugTester.git
cd DebugTester

# Build debug APK
./gradlew assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

---

## CI/CD (GitHub Actions)

Setiap push ke `main` atau `master` akan otomatis build APK debug.  
Hasil APK bisa didownload dari tab **Actions → Artifacts**.

---

## Struktur Project

```
DebugTester/
├── app/
│   ├── src/main/
│   │   ├── java/db/tester/
│   │   │   ├── MainActivity.java
│   │   │   ├── NetworkTestActivity.java
│   │   │   ├── DeviceInfoActivity.java
│   │   │   └── LogViewerActivity.java
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   └── values/
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── .github/workflows/build.yml
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## License

MIT License — bebas dipakai dan dimodifikasi.
