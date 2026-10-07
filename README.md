# 🌋 Gempa Tracker - Aplikasi Pemantau Gempa BMKG

**Gempa Tracker** adalah aplikasi Android berbasis **Jetpack Compose** yang digunakan untuk memantau aktivitas gempa bumi terkini di Indonesia secara *real-time* menggunakan data resmi dari **BMKG (Badan Meteorologi, Klimatologi, dan Geofisika)**.

---

Link Drive (Vid & APK Debug) : https://drive.google.com/drive/folders/1BkWiPseH2u6vPTS8ApmllY9vae0beEAG?usp=sharing

## 📸 Screenshots

| Home Screen | Detail Screen |
| :---: | :---: |
| <img width="349" height="772" alt="home_screen" src="https://github.com/user-attachments/assets/bc24766b-1906-49d6-b76b-102f9e4aa56b" /> | <img width="354" height="774" alt="detail_screen" src="https://github.com/user-attachments/assets/12a99ee8-7993-4135-9c8c-70fd42042dad" /> |



---

## ✨ Daftar Fitur Utama

- 📡 **Real-time Earthquake Monitoring**: Menampilkan daftar gempa bumi terkini yang bersumber langsung dari API resmi BMKG.
- 🚨 **Banner Gempa Terbaru**: Menyorot kejadian gempa paling akhir dengan tampilan khusus magnitudo, lokasi, kedalaman, dan waktu kejadian.
- 🔍 **Pencarian Reaktif berdasarkan Wilayah**: Fitur pencarian interaktif untuk memfilter daftar gempa berdasarkan nama wilayah/lokasi secara *real-time*.
- 🎨 **Indikator Tingkat Keparahan (Severity Color Coding)**:
  - 🟢 **Hijau (< 5.0 SR)**: Ringan
  - 🟠 **Oranye (5.0 - 5.9 SR)**: Sedang
  - 🔴 **Merah (≥ 6.0 SR)**: Kuat / Berpotensi Bahaya
- 📊 **Detail Informasi Gempa Komprehensif**: Menampilkan koordinat episentrum, lintang/bujur, kedalaman, potensi tsunami, serta laporan wilayah yang merasakan dampak (skala MMI).
- 🔄 **UI State Handling (Loading, Success, Error)**: Penanganan state aplikasi yang responsif dilengkapi indikator pemuatan data dan tombol *retry* jika koneksi gagal.

---

## 🏛️ Arsitektur Aplikasi

Aplikasi ini dibangun menggunakan arsitektur modern Android berstandar **MVVM (Model-View-ViewModel)** yang dipadukan dengan **Repository Pattern** dan **Unidirectional Data Flow (UDF)**.

```
       ┌────────────────────────┐
       │   BMKG TEWS API Service │
       └───────────┬────────────┘
                   │
                   ▼
       ┌────────────────────────┐
       │    GempaRepository     │ (Single Source of Truth)
       └───────────┬────────────┘
                   │
                   ▼
       ┌────────────────────────┐
       │     GempaViewModel     │ (State Management via StateFlow)
       └───────────┬────────────┘
                   │  UiState (Loading | Success | Error)
                   ▼
┌──────────────────────────────────────┐
│  Jetpack Compose UI (Screens/Views)  │
│  - HomeScreen                        │
│  - DetailScreen                      │
└──────────────────────────────────────┘
```

### Component Breakdown:

1. **Model**:
   - `GempaResponse`, `InfoGempaData`, dan `GempaItem`: Data model yang memetakan data JSON dari BMKG menggunakan Gson annotations (`@SerializedName`).
2. **Repository**:
   - `GempaRepository`: Bertindak sebagai *Single Source of Truth* yang mengisolasi sumber data API dari komponen UI/ViewModel.
3. **ViewModel & Reaktif StateFlow**:
   - `GempaViewModel`: Mengelola *business logic* dan *state ui*.
   - Menggunakan `StateFlow` dan operator `combine` untuk menggabungkan `_rawState` (data dari API) dan `_searchQuery` (pencarian pengguna) secara reaktif dan *thread-safe*.
4. **UiState via Sealed Interface**:
   - `UiState`: Sealed interface untuk merepresentasikan status UI secara eksplisit:
     - `UiState.Loading`: Menampilkan indikator loading.
     - `UiState.Success(val data: List<GempaItem>)`: Menampilkan daftar data gempa.
     - `UiState.Error(val message: String)`: Menampilkan pesan kesalahan beserta tombol muat ulang (*retry*).
5. **View (UI Layer)**:
   - Dikembangkan sepenuhnya dengan **Jetpack Compose** dan **Material 3**, memanfaatkan `NavHostController` untuk navigasi antar layar (`HomeScreen` ke `DetailScreen`).

---

## 🌐 Dokumentasi API

Data aplikasi diperoleh dari API Publik **BMKG ( TEWS - Tsunami Early Warning System)**.

- **Base URL**: `https://data.bmkg.go.id/`
- **Endpoint**: `DataMKG/TEWS/gempaterkini.json`
- **Full URL**: `https://data.bmkg.go.id/DataMKG/TEWS/gempaterkini.json`
- **HTTP Method**: `GET`
- **Format Response**: `JSON`

### Contoh Struktur Data Response (JSON):
```json
{
  "Infogempa": {
    "gempa": [
      {
        "Tanggal": "06 Okt 2026",
        "Jam": "20:11:23 WIB",
        "DateTime": "2026-10-06T13:11:23+00:00",
        "Coordinates": "4.94,118.77",
        "Lintang": "4.94 LU",
        "Bujur": "118.77 BT",
        "Magnitude": "5.3",
        "Kedalaman": "10 km",
        "Wilayah": "219 km TimurLaut TARAKAN-KALTARA",
        "Potensi": "Tidak berpotensi tsunami",
        "Dirasakan": "-"
      }
    ]
  }
}
```

---

## 🛠️ Spesifikasi Teknis & Library

| Kategori | Teknologi / Library | Versi / Keterangan |
| :--- | :--- | :--- |
| **Bahasa Pemrograman** | Kotlin | 2.0+ |
| **Minimum SDK** | API 26 (Android 8.0 Oreo) | - |
| **Target SDK** | API 37 | Android 15+ |
| **UI Toolkit** | Jetpack Compose (Material 3) | Declarative UI |
| **Asynchronous & Flow** | Kotlin Coroutines & Flow | `StateFlow`, `combine`, `viewModelScope` |
| **Networking** | Retrofit 2 | `com.squareup.retrofit2:retrofit:2.11.0` |
| **JSON Parser** | Converter Gson | `com.squareup.retrofit2:converter-gson:2.11.0` |
| **Navigation** | Navigation Compose | `androidx.navigation:navigation-compose:2.8.0` |
| **Architecture Components** | Lifecycle ViewModel Compose | `androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5` |

---

## 🚀 Cara Menjalankan Project

1. **Clone repository ini**:
   ```bash
   git clone https://github.com/username/gempatracker.git
   ```
2. **Buka project di Android Studio**:
   - Pilih `File > Open...` lalu arahkan ke folder project.
3. **Sync Gradle**:
   - Biarkan Android Studio mengunduh seluruh dependensi yang diperlukan.
4. **Jalankan Aplikasi**:
   - Hubungkan perangkat Android fisik atau aktifkan Emulator Android.
   - Tekan tombol **Run (`Shift + F10`)**.
