# Physio Rehab Center (PRC) — Management Information System

### Tugas Besar Pemrograman Berorientasi Objek & Manajemen Basis Data — Semester 2 Teknik Informatika

Aplikasi manajemen operasional klinik fisioterapi terintegrasi yang dibangun menggunakan **Java SE (Swing GUI)** dan **MySQL/MariaDB**. Sistem ini menerapkan arsitektur *Pay-per-Visit* untuk menjamin konsistensi keuangan, resep kefarmasian yang presisi, serta pelacakan perkembangan klinis pasien secara *real-time*.

---

## 🚀 Fitur Utama Sistem (Ruang Lingkup RBAC)

Sistem ini membagi hak akses ke dalam 4 aktor utama dengan batasan fungsi (*privilege*) CRUD berbeda demi menjaga keamanan enkapsulasi data medis (*Role-Based Access Control*):
* **Admin:** Mengelola data master pasien, administrasi penjaminan, dan mengatur slot waktu kunjungan (`jadwal_terapi`).
* **Terapis:** Menginput data klinis sensitif (skala nyeri sebelum/sesudah sesi, diagnosis, tindakan) ke `rekam_medis`, merancang `program_terapi`, serta mengeluarkan resep elektronik.
* **Staf Farmasi:** Memantau permintaan resep (`resep_item_medis`) dan mengelola pembaruan stok komoditas medis secara *real-time* (`item_medis`).
* **Kasir:** Mengakses ringkasan *invoice* otomatis (`tagihan`), mengelola rincian transaksi (`detail_tagihan`), dan memproses klaim penjaminan (`klaim_asuransi`).

---

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
