# 🏥 Physio Rehab Center (PRC) — Management Information System

Tugas Besar Pemrograman Berorientasi Objek & Manajemen Basis Data — Semester 2 Teknik Informatika.

Aplikasi manajemen operasional klinik fisioterapi terintegrasi yang dibangun menggunakan **Java SE (Swing GUI)** dan **MySQL/MariaDB**. Sistem ini menerapkan arsitektur *Pay-per-Visit* untuk menjamin konsistensi keuangan dan pelacakan perkembangan klinis pasien secara real-time.

---

## 🚀 Fitur Utama Sistem (Ruang Lingkup RBAC)
Sistem ini membagi hak akses ke dalam **4 aktor utama** dengan batasan fungsi (*privilege*) CRUD berbeda demi menjaga keamanan data medis:
- **Admin**: Mengelola data master pasien dan mengatur slot waktu kunjungan (`JADWAL_TERAPI`).
- **Terapis**: Menginput data klinis sensitif (skala nyeri, diagnosis, tindakan) ke `REKAM_MEDIS` serta mengeluarkan resep.
- **Staf Farmasi**: Memantau resep dan mengelola pembaruan stok barang medis secara real-time (`ITEM_MEDIS`).
- **Kasir**: Mengakses data invoice otomatis (`TAGIHAN`) dan memproses pembayaran pasien.

---

## 🛠️ Konseptual OOP & Basis Data yang Diterapkan
- **Encapsulation**: Pembatasan akses langsung variabel model melalui implementasi *private attributes* serta *getter & setter*.
- **Inheritance**: Struktur kelas `Terapis` diturunkan secara langsung dari kelas induk `Pengguna` melalui generalisasi *Foreign Key* di database.
- **Database Normalization**: Desain database efisien yang memenuhi standar Bentuk Normal Third Normal Form (3NF) guna menekan redundansi data hingga 0%.

---

## 🗄️ Arsitektur Data Berbasis SQL (Telah Di-deploy)
Seluruh struktur basis data sebanyak 10 tabel terintegrasi telah berhasil dikonfigurasi pada DBMS MySQL, meliputi:
1. `pengguna` (Akses Login)
2. `pasien` (Data Master Medis)
3. `terapis` (Data Spesialisasi)
4. `item_medis` (Inventaris Farmasi)
5. `asuransi` (Penjaminan)
6. `jadwal_terapi` (Transaksi Kunjungan)
7. `program_terapi` (Rencana Pemulihan)
8. `rekam_medis` (Evaluasi Klinis 1:1)
9. `resep_item_medis` (Junction Table)
10. `tagihan` (Billing Kasir 1:1)

---

## 👥 Anggota Kelompok
- **Eka** — *System Analyst, Database Administrator, & Core Developer*
