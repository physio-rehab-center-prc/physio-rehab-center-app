# 🏥 PhysioRehabCenter (PRC)
Aplikasi desktop manajemen klinik fisioterapi berbasis **Java Swing**.

---

## 👥 Anggota Kelompok
| No | Nama | Peran |
|----|------|-------|
| 1 | Rahma | Model & Data |
| 2 | Pebi | Controller & Logic |
| 3 | Eka | UI Login & Dashboard |
| 4 | Risma | UI Pasien, Jadwal & Rekam Medis |

---

## 📌 Fitur
- Login & autentikasi
- Manajemen data pasien (CRUD)
- Penjadwalan sesi terapi
- Rekam medis pasien
- Dashboard statistik

---

## 🧱 Konsep OOP
| Konsep | Implementasi |
|--------|-------------|
| Encapsulation | Atribut `private` di class `Pasien`, akses via getter/setter |
| Inheritance | `Terapis extends Pengguna` |
| Polymorphism | Override method `getInfo()` |
| Abstraction | `Pengguna` sebagai abstract class |
| Singleton | `DataStore.getInstance()` |

---

## 🔑 Akun Login Demo
| Username | Password |
|----------|----------|
| `rahma` | `admin123` |
| `pebi` | `admin123` |
| `eka` | `admin123` |
| `risma` | `admin123` |

---

## ⚙️ Cara Menjalankan
1. Buka **NetBeans IDE**
2. Buat project Java baru → nonaktifkan "Create Main Class"
3. Buat package: `prc.model`, `prc.controller`, `prc.view`, `prc.main`
4. Salin semua file `.java` ke package masing-masing
5. Set Main Class ke `prc.main.Main`
6. Tekan **F6** untuk menjalankan

---

## 🛠️ Teknologi
- Java SE 8+
- Java Swing (GUI)
- NetBeans IDE
- Penyimpanan In-Memory (ArrayList)
