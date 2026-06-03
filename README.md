# Physio Rehab Center (PRC)
## Petunjuk Setup di NetBeans

---

### STRUKTUR PACKAGE
```
src/
├── prc/model/
│   ├── Pengguna.java        ← Abstract class (Inheritance)
│   ├── Terapis.java         ← extends Pengguna (Polymorphism)
│   ├── Pasien.java
│   ├── JadwalTerapi.java
│   └── RekamMedis.java
├── prc/controller/
│   └── DataStore.java       ← Singleton, menyimpan data sementara
├── prc/view/
│   ├── LoginFrame.java      ← Halaman login
│   ├── DashboardFrame.java  ← Menu utama
│   ├── PasienFrame.java     ← CRUD data pasien
│   ├── JadwalFrame.java     ← Penjadwalan terapi
│   └── RekamMedisFrame.java ← Input rekam medis
└── prc/main/
    └── Main.java            ← Entry point
```

---

### CARA SETUP DI NETBEANS

1. Buka NetBeans → File → New Project
2. Pilih: Java → Java Application → Next
3. Project Name: PhysioRehabCenter
4. Hilangkan centang "Create Main Class" → Finish
5. Klik kanan folder "Source Packages" → New → Java Package
6. Buat package: prc.model, prc.controller, prc.view, prc.main
7. Salin semua file .java ke package yang sesuai
8. Klik kanan project → Properties → Run → Main Class: prc.main.Main
9. Tekan F6 atau klik Run untuk menjalankan

---

### AKUN LOGIN DEMO
| Username | Password  | Nama              | Spesialisasi |
|----------|-----------|-------------------|--------------|
| cuteka   | admin123  | Cut Eka Putri     | Neurologi    |
| rahma    | admin123  | Rahma Santika     | Ortopedi     |
| pebi     | admin123  | Pebi Saputra      | Pediatri     |

---

### FITUR OOP YANG DIIMPLEMENTASIKAN

| Konsep OOP    | Implementasi                                          |
|---------------|-------------------------------------------------------|
| Encapsulation | Semua atribut private + getter/setter                 |
| Inheritance   | Terapis extends Pengguna (abstract class)             |
| Polymorphism  | Method getInfo() di-override di setiap subclass       |
| Abstraction   | Abstract class Pengguna dengan method abstract        |

---

### ALUR PENGUJIAN (untuk laporan)

1. Jalankan aplikasi → muncul halaman Login
2. Masukkan: cuteka / admin123 → klik MASUK
3. Dashboard tampil dengan statistik dan menu
4. Klik "Manajemen Pasien" → tambah pasien baru → simpan
5. Klik "Jadwal Terapi" → buat jadwal baru → simpan
6. Klik "Rekam Medis" → input data klinis → simpan
7. Screenshot setiap langkah untuk laporan
