-- ============================================================================
-- DATABASE SCRIPT: PHYSIO REHAB CENTER (PRC) MANAGEMENT INFORMATION SYSTEM
-- Tugas Besar Pemrograman Berorientasi Objek - Kelompok 7
-- ============================================================================

CREATE DATABASE IF NOT EXISTS db_physio_rehab;
USE db_physio_rehab;

-- ----------------------------------------------------------------------------
-- 1. TABEL PENGGUNA (Untuk enkapsulasi Role-Based Access Control di LoginFrame)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pengguna (
    id_pengguna INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    role ENUM('Admin', 'Terapis', 'Farmasi', 'Kasir') NOT NULL,
    status_aktif BOOLEAN DEFAULT TRUE
);

-- ----------------------------------------------------------------------------
-- 2. TABEL PASIEN (Data master rekam identitas pasien)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pasien (
    id_pasien VARCHAR(20) PRIMARY KEY, -- Contoh format: PRC-2026-0001
    nama_pasien VARCHAR(100) NOT NULL,
    tanggal_lahir DATE NOT NULL,
    jenis_kelamin ENUM('Laki-laki', 'Perempuan') NOT NULL,
    nomor_telepon VARCHAR(15) NOT NULL,
    alamat TEXT NOT NULL,
    tanggal_registrasi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ----------------------------------------------------------------------------
-- 3. TABEL TERAPIS (Data spesialisasi tenaga kesehatan fisioterapi)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS terapis (
    id_terapis INT AUTO_INCREMENT PRIMARY KEY,
    id_pengguna INT UNIQUE,
    spesialisasi VARCHAR(100) NOT NULL,
    nomor_str VARCHAR(50) NOT NULL UNIQUE, -- Surat Tanda Registrasi Medis
    FOREIGN KEY (id_pengguna) REFERENCES pengguna(id_pengguna) ON DELETE SET NULL
);

-- ----------------------------------------------------------------------------
-- 4. TABEL JADWAL TERAPI (Mengatur alur pertemuan / antrean klinik)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS jadwal_terapi (
    id_jadwal INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien VARCHAR(20) NOT NULL,
    id_terapis INT NOT NULL,
    tanggal_reservasi DATE NOT NULL,
    jam_mulai TIME NOT NULL,
    status_kunjungan ENUM('Scheduled', 'In Progress', 'Completed', 'Cancelled') DEFAULT 'Scheduled',
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien) ON DELETE CASCADE,
    FOREIGN KEY (id_terapis) REFERENCES terapis(id_terapis) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- 5. TABEL REKAM MEDIS (Clinical Records & Pelacakan Skala Nyeri Real-Time)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS rekam_medis (
    id_rekam_medis INT AUTO_INCREMENT PRIMARY KEY,
    id_jadwal INT NOT NULL UNIQUE, -- Satu kunjungan menghasilkan satu catatan klinis
    keluhan_utama TEXT NOT NULL,
    pemeriksaan_fisik TEXT NOT NULL,
    skala_nyeri_awal INT CHECK (skala_nyeri_awal BETWEEN 0 AND 10),
    tindakan_terapi TEXT NOT NULL,
    skala_nyeri_akhir INT CHECK (skala_nyeri_akhir BETWEEN 0 AND 10),
    catatan_perkembangan TEXT,
    tanggal_periksa TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_jadwal) REFERENCES jadwal_terapi(id_jadwal) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- 6. TABEL GUDANG FARMASI (Manajemen Persediaan Obat & Item Medis)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS obat (
    id_obat INT AUTO_INCREMENT PRIMARY KEY,
    nama_obat VARCHAR(100) NOT NULL,
    jenis_obat VARCHAR(50),
    stok INT NOT NULL DEFAULT 0,
    harga_jual DECIMAL(10,2) NOT NULL,
    satuan VARCHAR(20) NOT NULL -- Contoh: Botol, Strip, Pcs
);

-- ----------------------------------------------------------------------------
-- 7. TABEL RESEP OBAT (Junction antara Rekam Medis dan Item Farmasi)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS resep_obat (
    id_resep INT AUTO_INCREMENT PRIMARY KEY,
    id_rekam_medis INT NOT NULL,
    id_obat INT NOT NULL,
    jumlah INT NOT NULL,
    aturan_pakai VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_rekam_medis) REFERENCES rekam_medis(id_rekam_medis) ON DELETE CASCADE,
    FOREIGN KEY (id_obat) REFERENCES obat(id_obat) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- 8. TABEL BILLING TRANSAKSI (Sistem Kasir Otomatis Berbasis Pay-per-Visit)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS billing (
    id_invoice VARCHAR(30) PRIMARY KEY, -- Contoh format: INV-PRC-20260531-01
    id_jadwal INT NOT NULL UNIQUE,
    biaya_konsultasi DECIMAL(10,2) NOT NULL DEFAULT 150000.00,
    biaya_tindakan DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total_biaya_obat DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    grand_total DECIMAL(10,2) NOT NULL,
    status_pembayaran ENUM('Belum Lunas', 'Lunas') DEFAULT 'Belum Lunas',
    waktu_pembayaran DATETIME DEFAULT NULL,
    FOREIGN KEY (id_jadwal) REFERENCES jadwal_terapi(id_jadwal) ON DELETE CASCADE
);

-- ============================================================================
-- DATA AWAL SIMULASI (SEED DATA) UNTUK TESTING LOGIN & TESTING SYSTEM
-- ============================================================================

-- Masukkan akun-akun simulasi dengan role berbeda
INSERT INTO pengguna (username, password, nama_lengkap, role) VALUES
('admin', '12345', 'Cut Ekarasiana Nome', 'Admin'),
('terapis1', '12345', 'Pebi Rahmawati', 'Terapis'),
('farmasi1', '12345', 'Rahma Santika', 'Farmasi'),
('kasir1', '12345', 'Risma Ayu', 'Kasir');

-- Masukkan relasi untuk detail data Terapis
INSERT INTO terapis (id_pengguna, spesialisasi, nomor_str) VALUES
(2, 'Neuromuscular Physiotherapist', 'STR-123456789-2026');

-- Masukkan inventaris awal obat di farmasi
INSERT INTO obat (nama_obat, jenis_obat, stok, harga_jual, satuan) VALUES
('Gel Ultrasound 250ml', 'Cairan Medis', 25, 45000.00, 'Botol'),
('Kinesio Tape Roll', 'Alat Medis', 40, 35000.00, 'Pcs'),
('Ibuprofen 400mg', 'Analgasik', 100, 12000.00, 'Strip');
