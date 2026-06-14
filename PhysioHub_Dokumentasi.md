# 🏥 PhysioHub — Sistem Manajemen Fisioterapi

> Tugas Besar Pemrograman Berorientasi Objek (PBO) — Semester 2

---

## 👥 Anggota Tim

| No | Nama | NIM | Role |
|----|------|-----|------|
| 1  | Nama Anggota 1 | 123456001 | Ketua / Backend |
| 2  | Nama Anggota 2 | 123456002 | Frontend / UI |
| 3  | Nama Anggota 3 | 123456003 | Database / Logic |

> **Mata Kuliah:** Pemrograman Berorientasi Objek  
> **Dosen Pengampu:** Nama Dosen  
> **Program Studi:** Informatika  
> **Universitas:** Nama Universitas  
> **Tahun:** 2026

---

## 📌 Deskripsi Sistem

**PhysioHub** adalah aplikasi desktop berbasis Java yang dirancang untuk membantu klinik fisioterapi dalam mengelola seluruh operasional pelayanan secara digital dan efisien.

Sistem ini memiliki **3 role pengguna** dengan fitur yang berbeda-beda:

- 👨‍💼 **Admin** — mengelola seluruh sistem, jadwal, pasien, terapis, tagihan, dan laporan
- 🩺 **Terapis** — melihat jadwal sesi, mengisi rekam medis SOAP, dan memantau progres pasien
- 🧑‍⚕️ **Pasien** — melihat jadwal pribadi, rekam medis, program latihan, dan tagihan

Aplikasi dibangun menggunakan **Java Swing** dengan penyimpanan data secara **in-memory** dan menerapkan prinsip-prinsip **OOP** secara menyeluruh.

---

## ✨ Fitur Utama

### Role Admin / Terapis
- 📊 **Dashboard** — statistik real-time sesi, pasien aktif, pendapatan, dan tingkat kehadiran
- 📅 **Jadwal Sesi** — buat, mulai, selesaikan, dan batalkan sesi terapi
- 👥 **Manajemen Pasien** — daftar pasien, tambah pasien baru, lihat detail dan progres rehabilitasi
- 🩺 **Manajemen Terapis** — profil terapis, STR/SIP, kompetensi, dan status ketersediaan
- 📋 **Rekam Medis** — pencatatan SOAP Notes (Subjektif, Objektif, Asesmen, Planning)
- 🏃 **Program Latihan** — kelola program rehabilitasi dan assign ke pasien
- 🧾 **Tagihan** — buat invoice, konfirmasi pembayaran, laporan keuangan
- 📈 **Analitik** — grafik distribusi diagnosis dan performa terapis
- 🔔 **Notifikasi** — pengingat sesi dan pemberitahuan sistem
- ⚙️ **Pengaturan** — konfigurasi klinik, tarif layanan, dan keamanan akun

### Role Pasien
- 🏠 **Beranda** — ringkasan progres, sesi mendatang, dan tagihan pending
- 📅 **Jadwal Saya** — lihat semua sesi dan ajukan permintaan janji baru
- 📋 **Rekam Medis Saya** — lihat catatan SOAP dari terapis dan grafik progres
- 🏃 **Program Latihan** — panduan latihan lengkap dengan tombol mulai latihan
- 💳 **Tagihan Saya** — ringkasan keuangan dan bayar tagihan dengan berbagai metode
- 🔔 **Notifikasi** — notifikasi personal dan pengumuman klinik
- 👤 **Profil Saya** — edit data pribadi dan ubah password

---

## 🏗️ Arsitektur Sistem

Aplikasi menggunakan pola arsitektur **MVC (Model-View-Controller)**:

```
PhysioHub/
├── model/          → Entitas data (User, Pasien, Terapis, Sesi, dll)
├── view/           → Tampilan antarmuka (LoginFrame, AdminFrame, PasienFrame)
│   └── panel/      → Panel-panel halaman
├── controller/     → Logika bisnis (SesiController, PasienController, dll)
├── data/           → Penyimpanan data (DataStore - Singleton)
└── util/           → Komponen UI reusable (UIHelper)
```

---

## 🔷 Konsep OOP yang Diterapkan

| Konsep | Implementasi |
|--------|-------------|
| **Enkapsulasi** | Semua atribut `private`/`protected` dengan getter & setter |
| **Pewarisan** | `Admin`, `Terapis`, `Pasien` mewarisi class abstrak `User` |
| **Polimorfisme** | Method `getInfo()` dan `getDashboardTitle()` di-*override* tiap subclass |
| **Abstraksi** | `User` adalah `abstract class` dengan method abstrak |
| **Singleton** | `DataStore.getInstance()` — satu instance untuk seluruh aplikasi |

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Keterangan |
|-----------|-----------|
| Java SE 11+ | Bahasa pemrograman utama |
| Java Swing | Framework GUI desktop |
| java.time.* | Pengelolaan tanggal dan waktu |
| LinkedHashMap | Struktur data penyimpanan in-memory |
| MVC Pattern | Pola arsitektur aplikasi |
| Singleton Pattern | Manajemen DataStore |

---

## 🔑 Akun Demo

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@physiohub.com | admin123 |
| Terapis | rina@physiohub.com | terapis123 |
| Pasien | budi@email.com | pasien123 |

---

## 🚀 Cara Menjalankan

1. Pastikan **Java JDK 11+** sudah terinstall
2. Buka project di **NetBeans / IntelliJ IDEA**
3. Set folder `src` sebagai *Sources Root*
4. Jalankan file `Main.java`

---

*© 2026 PhysioHub Team — Program Studi Informatika*
