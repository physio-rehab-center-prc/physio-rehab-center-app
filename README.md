🏥
Physio Rehab Center
Sistem Manajemen Pusat Rehabilitasi Fisioterapi ·

## 👥 Anggota Kelompok


---

## 📋 Deskripsi Proyek

PhysioRehabCenter adalah aplikasi desktop berbasis **Java Swing** untuk mengelola operasional klinik fisioterapi secara digital. Aplikasi ini menggantikan pencatatan manual dengan sistem terkomputerisasi yang mencakup:

- ✅ **Autentikasi** — Login berbasis username & password
- ✅ **Manajemen Pasien** — CRUD lengkap data pasien
- ✅ **Penjadwalan Terapi** — Pembuatan dan pemantauan jadwal sesi
- ✅ **Rekam Medis** — Pencatatan diagnosis, tindakan, dan hasil terapi
- ✅ **Dashboard** — Statistik ringkas dan navigasi menu

---

## 🗂️ Struktur Proyek

```
PhysioRehabCenter/
└── src/
    └── prc/
        ├── model/                  # Layer Data (Model)
        │   ├── Pengguna.java       ← Abstract class (Abstraction + Inheritance)
        │   ├── Terapis.java        ← extends Pengguna (Polymorphism)
        │   ├── Pasien.java         ← Encapsulation dengan private + getter/setter
        │   ├── JadwalTerapi.java   ← Data jadwal sesi terapi
        │   └── RekamMedis.java     ← Data rekam medis pasien
        ├── controller/             # Layer Logika (Controller)
        │   └── DataStore.java      ← Singleton, penyimpanan data sementara
        ├── view/                   # Layer Tampilan (View)
        │   ├── LoginFrame.java     ← Halaman autentikasi
        │   ├── DashboardFrame.java ← Menu utama & statistik
        │   ├── PasienFrame.java    ← CRUD data pasien
        │   ├── JadwalFrame.java    ← Manajemen jadwal terapi
        │   └── RekamMedisFrame.java← Input & tampil rekam medis
        └── main/
            └── Main.java           ← Entry point aplikasi
```

---

## 🧱 Konsep OOP yang Diimplementasikan

| Konsep OOP      | Implementasi dalam Kode |
|-----------------|-------------------------|
| **Encapsulation** | Semua atribut di class `Pasien`, `Terapis`, dll. bersifat `private`. Akses hanya melalui getter/setter. |
| **Inheritance**   | `Terapis extends Pengguna` — Terapis mewarisi properti dan method dari abstract class Pengguna. |
| **Polymorphism**  | Method `getInfo()` dideklarasikan abstract di `Pengguna`, kemudian di-*override* di `Terapis` dengan perilaku berbeda. |
| **Abstraction**   | `Pengguna` adalah abstract class yang mendefinisikan kontrak tanpa implementasi penuh — subclass wajib mengimplementasikan `getInfo()`. |
| **Singleton**     | `DataStore` menggunakan pola Singleton sehingga hanya ada satu instance data di seluruh siklus hidup aplikasi. |

---

## 🔑 Akun Login Demo

| Username | Password   | Nama Lengkap      | Spesialisasi     |
|----------|------------|-------------------|-----------------|
| `rahma`  | `admin123` | Rahma Aulia       | Ortopedi         |
| `pebi`   | `admin123` | Pebi Saputra      | Pediatri         |
| `eka`    | `admin123` | Eka Putri         | Neurologi        |
| `risma`  | `admin123` | Risma Wulandari   | Kardiovaskular   |

---

## ⚙️ Cara Menjalankan di NetBeans

1. Buka **NetBeans IDE**
2. **File → New Project → Java → Java Application → Next**
3. **Project Name:** `PhysioRehabCenter`
4. Hilangkan centang **"Create Main Class"** → Klik **Finish**
5. Klik kanan **Source Packages → New → Java Package**, buat:
   - `prc.model`
   - `prc.controller`
   - `prc.view`
   - `prc.main`
6. Salin semua file `.java` ke package yang sesuai
7. Klik kanan project → **Properties → Run → Main Class:** `prc.main.Main`
8. Tekan **F6** atau klik **▶ Run**

---

## 🧪 Skenario Demo (Langkah-Langkah Presentasi)

Ikuti langkah ini saat demo kepada dosen:

### 1. Login
- Jalankan aplikasi → muncul halaman **Login**
- Masukkan: `rahma` / `admin123` → klik **MASUK**
- ✔️ *Mendemonstrasikan: Encapsulation (password tidak terekspos), Inheritance & Polymorphism (objek Terapis)*

### 2. Dashboard
- Dashboard tampil dengan statistik: jumlah pasien, jadwal, rekam medis
- ✔️ *Mendemonstrasikan: Singleton DataStore — data konsisten di semua frame*

### 3. Manajemen Pasien
- Klik menu **"Manajemen Pasien"**
- Klik **Tambah** → isi form → klik **Simpan**
- Pilih data → klik **Hapus**
- ✔️ *Mendemonstrasikan: CRUD, Encapsulation pada class Pasien*

### 4. Jadwal Terapi
- Klik menu **"Jadwal Terapi"**
- Klik **Tambah Jadwal** → pilih pasien & terapis → Simpan
- ✔️ *Mendemonstrasikan: Relasi antar objek (Pasien ↔ Terapis ↔ JadwalTerapi)*

### 5. Rekam Medis
- Klik menu **"Rekam Medis"**
- Klik **Tambah** → isi diagnosis, tindakan, hasil → Simpan
- ✔️ *Mendemonstrasikan: Komposisi objek (RekamMedis mengandung JadwalTerapi)*

---

## 🏛️ Arsitektur Aplikasi

Aplikasi menggunakan pola **MVC (Model-View-Controller)**:

```
┌─────────────┐     aksi      ┌──────────────────┐     update    ┌───────────────┐
│    VIEW     │ ─────────────▶│   CONTROLLER     │ ─────────────▶│    MODEL      │
│  (Frame UI) │               │  (DataStore)     │               │  (Java Class) │
│             │ ◀─────────────│                  │ ◀─────────────│               │
└─────────────┘    tampilkan  └──────────────────┘     baca      └───────────────┘
```

---

## 📦 Teknologi

- **Bahasa:** Java SE 8+
- **UI Framework:** Java Swing
- **IDE:** NetBeans
- **Penyimpanan:** In-Memory (tanpa database, menggunakan ArrayList)

---

## 📝 Catatan

- Data akan hilang saat aplikasi ditutup (penyimpanan in-memory by design)
- Tidak memerlukan koneksi internet atau database eksternal
- Kompatibel dengan NetBeans 12+ dan JDK 8+
