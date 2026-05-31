# Physio Rehab Center (PRC) — Management Information System

### Tugas Besar Pemrograman Berorientasi Objek & Manajemen Basis Data — Semester 2 Teknik Informatika

Aplikasi manajemen operasional klinik fisioterapi terintegrasi yang dibangun menggunakan **Java SE (Swing GUI)** dan **MySQL/MariaDB**. Sistem ini menerapkan arsitektur *Pay-per-Visit* untuk menjamin konsistensi keuangan, resep kefarmasian yang presisi, serta pelacakan perkembangan klinis pasien secara *real-time*.

---

## 🚀 Fitur Utama Sistem (5 Modul Terintegrasi & 4 Aktor RBAC)

Sistem Informasi *Physio Rehab Center* (PRC) mencakup **5 modul utama** yang diimplementasikan secara aman melalui pembatasan hak akses lintas **4 aktor utama** (*Role-Based Access Control*):

### 📁 5 Modul Utama Aplikasi
1. **Manajemen Pasien:** Mengelola registrasi, data demografis, riwayat medis, dan data asuransi pasien.
2. **Manajemen Terapis:** Mengelola profil kompetensi terapis, spesialisasi, manajemen jadwal shift, dan akun otorisasi sistem.
3. **Manajemen Sesi Terapi:** Mengatur penjadwalan dinamis kunjungan, pengisian rekam medis digital, serta pencatatan skala nyeri (sebelum & sesudah intervensi).
4. **Manajemen Resep & Item Medis:** Mengelola inventaris stok alat/bahan medis klinik serta penerbitan resep per sesi terapi pasien.
5. **Sistem Billing & Asuransi:** Melakukan kalkulasi penagihan otomatis, manajemen detail item finansial, laporan transaksi, dan pemrosesan klaim asuransi.

---

### 👥 Hak Akses Aktor Sistem (Privilege CRUD)
* **Admin:** Memiliki akses penuh pada **Modul Manajemen Pasien** dan fungsi alokasi slot waktu kunjungan pada **Modul Sesi Terapi** (`jadwal_terapi`).
* **Terapis:** Memiliki akses operasional pada **Modul Sesi Terapi** untuk menginput data klinis sensitif (skala nyeri sebelum/sesudah sesi, diagnosis, tindakan) ke `rekam_medis` serta merancang `program_terapi`.
* **Staf Farmasi:** Memiliki akses penuh pada **Modul Manajemen Resep & Item Medis** untuk memantau resep masuk (`resep_item_medis`) dan memperbarui stok fisik (`item_medis`).
* **Kasir:** Memiliki hak akses eksklusif pada **Modul Billing & Asuransi** untuk memvalidasi ringkasan nota (`tagihan`), mengelola rincian biaya (`detail_tagihan`), dan mengeksekusi pengajuan (`klaim_asuransi`).

## 🛠️ Konseptual OOP & Basis Data yang Diterapkan

* **Encapsulation:** Pembatasan akses langsung variabel model melalui implementasi *private attributes* serta *getter & setter* yang ketat di tingkat objek Java.
* **Inheritance & Polymorphism:** Kelas `Terapis` diturunkan secara langsung dari kelas induk `Pengguna` melalui generalisasi *Foreign Key* di database, didukung oleh fleksibilitas pemanggilan metode otentikasi.
* **Database Normalization:** Desain database efisien yang memenuhi standar **Third Normal Form (3NF)** guna menekan redundansi data hingga minimal, menggunakan *junction table* untuk memecah relasi *Many-to-Many*.

---

## 🗄️ Arsitektur Data Berbasis SQL (12 Entitas Sinkron ERD)

Seluruh struktur basis data sebanyak **12 tabel utama (ditambah 1 tabel penghubung tambahan)** telah berhasil dikonfigurasi dan di-deploy pada DBMS MySQL dengan urutan dependensi *Foreign Key* sebagai berikut:

1.  `pengguna` (Akses Login & Otorisasi Sesi)
2.  `pasien` (Data Master Identitas Medis)
3.  `terapis` (Data Spesialisasi Tenaga Kesehatan)
4.  `item_medis` (Inventaris Farmasi & Alat Kesehatan)
5.  `asuransi` (Data Limit Penjaminan Kesehatan Pasien)
6.  `program_terapi` (Rencana Pemulihan Jangka Panjang)
7.  `program_terapi_terapis` (*Junction Table* Kolaborasi Tim Terapis)
8.  `jadwal_terapi` (Transaksi Slot Kunjungan Sesi Pasien)
9.  `rekam_medis` (Evaluasi Klinis & Skala Nyeri Sesi 1:1)
10. `resep_item_medis` (*Junction Table* Pengeluaran Obat/Alat Medis)
11. `tagihan` (Billing Finansial Utama Kasir 1:1 terhadap Sesi)
12. `detail_tagihan` (*Junction Table* Transaksional Rincian Item Finansial)
13. `klaim_asuransi` (Jembatan Proses Pengajuan Biaya ke Provider Asuransi)

---

## 👥 Anggota Kelompok (Role-Based Core Responsibilities)

* **Cut Ekarasiana (Ketua):** System Analyst, Database Administrator, & Core Backend Developer (`database.sql`, `Koneksi.java`, Keamanan OOP).
* **Rahma Santika (Anggota 1):** Business Process Analyst & Core Developer untuk Modul *Patient & Scheduling* (Pendaftaran Pasien & Jadwal Terapi).
* **Pebi (Anggota 2):** System Workflow Specialist & Core Developer untuk Modul *Clinical Record* (Rekam Medis & Evaluasi Perkembangan Pasien).
* **Risma Ayu (Anggota 3):** Frontend Designer & Core Developer untuk Modul *Billing & Financial* (Kalkulasi Tagihan, Detail Item, & Dokumen Laporan).
