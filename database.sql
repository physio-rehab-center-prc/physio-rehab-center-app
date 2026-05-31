-- ========================================================
-- DATABASE SYSTEM: PHYSIO REHAB CENTER (PRC)
-- SINKRONISASI: ERD FINAL & LAPORAN KELOMPOK 7
-- ========================================================

-- 1. Tabel PENGGUNA (Otorisasi Akses & RBAC)
CREATE TABLE pengguna (
    id_pengguna INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Terapis', 'Farmasi', 'Kasir') NOT NULL,
    status_aktif BOOLEAN DEFAULT TRUE,
    last_login DATETIME
);

-- 2. Tabel PASIEN (Master Data Pasien)
CREATE TABLE pasien (
    id_pasien INT AUTO_INCREMENT PRIMARY KEY,
    nik VARCHAR(16) NOT NULL UNIQUE,
    nama_lengkap VARCHAR(100) NOT NULL,
    tanggal_lahir DATE NOT NULL,
    jenis_kelamin ENUM('L', 'P') NOT NULL,
    alamat TEXT,
    no_telepon VARCHAR(15),
    email VARCHAR(100),
    golongan_darah VARCHAR(2),
    alergi TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabel TERAPIS (Master Data Tenaga Kesehatan)
CREATE TABLE terapis (
    id_terapis INT AUTO_INCREMENT PRIMARY KEY,
    nama_lengkap VARCHAR(100) NOT NULL,
    no_str VARCHAR(50) NOT NULL UNIQUE,
    spesialisasi VARCHAR(100),
    no_telepon VARCHAR(15),
    email VARCHAR(100),
    status_aktif BOOLEAN DEFAULT TRUE
);

-- 4. Tabel ITEM MEDIS (Inventaris Alat & Bahan Farmasi)
CREATE TABLE item_medis (
    id_item_medis INT AUTO_INCREMENT PRIMARY KEY,
    nama_item VARCHAR(100) NOT NULL,
    kategori VARCHAR(50),
    satuan VARCHAR(20),
    harga_satuan DECIMAL(10,2) NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    deskripsi TEXT
);

-- 5. Tabel ASURANSI (Data Penjamin/Polis Pasien)
CREATE TABLE asuransi (
    id_asuransi INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien INT,
    nama_provider VARCHAR(100) NOT NULL,
    no_polis VARCHAR(50) NOT NULL,
    limit_tahunan DECIMAL(12,2) NOT NULL,
    sisa_limit DECIMAL(12,2) NOT NULL,
    masa_berlaku DATE,
    status VARCHAR(20) DEFAULT 'Aktif',
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien) ON DELETE CASCADE
);

-- 6. Tabel PROGRAM TERAPI (Rencana Pemulihan Klinis)
CREATE TABLE program_terapi (
    id_program INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien INT,
    nama_program VARCHAR(100) NOT NULL,
    deskripsi TEXT,
    tanggal_mulai DATE,
    tanggal_selesai DATE,
    status VARCHAR(20),
    target_pemulihan TEXT,
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien) ON DELETE CASCADE
);

-- 7. Tabel Junction: PROGRAM TERAPI TERAPIS (Relasi Banyak-ke-Banyak)
CREATE TABLE program_terapi_terapis (
    id_program INT,
    id_terapis INT,
    PRIMARY KEY (id_program, id_terapis),
    FOREIGN KEY (id_program) REFERENCES program_terapi(id_program) ON DELETE CASCADE,
    FOREIGN KEY (id_terapis) REFERENCES terapis(id_terapis) ON DELETE CASCADE
);

-- 8. Tabel JADWAL TERAPI (Sesi Kunjungan Pasien)
CREATE TABLE jadwal_terapi (
    id_jadwal INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien INT NOT NULL,
    id_terapis INT NOT NULL,
    id_program INT,
    tanggal_waktu DATETIME NOT NULL,
    status ENUM('Dijadwalkan', 'Selesai', 'Dibatalkan') DEFAULT 'Dijadwalkan',
    tipe_sesi VARCHAR(50),
    durasi_menit INT,
    catatan TEXT,
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien) ON DELETE CASCADE,
    FOREIGN KEY (id_terapis) REFERENCES terapis(id_terapis) ON DELETE CASCADE,
    FOREIGN KEY (id_program) REFERENCES program_terapi(id_program) ON DELETE SET NULL
);

-- 9. Tabel REKAM MEDIS (Hasil Evaluasi Klinis Sesi)
CREATE TABLE rekam_medis (
    id_rekam_medis INT AUTO_INCREMENT PRIMARY KEY,
    id_jadwal INT UNIQUE,
    tanggal_sesi DATETIME NOT NULL,
    skala_nyeri_sebelum INT,
    skala_nyeri_sesudah INT,
    diagnosis TEXT,
    tindakan_terapi TEXT,
    hasil_evaluasi TEXT,
    catatan_klinis TEXT,
    FOREIGN KEY (id_jadwal) REFERENCES jadwal_terapi(id_jadwal) ON DELETE CASCADE
);

-- 10. Tabel Junction: RESEP ITEM MEDIS (Relasi Banyak-ke-Banyak Farmasi)
CREATE TABLE resep_item_medis (
    id_resep INT AUTO_INCREMENT PRIMARY KEY,
    id_rekam_medis INT,
    id_item_medis INT,
    jumlah INT NOT NULL DEFAULT 1,
    dosis_penggunaan VARCHAR(50),
    instruksi_penggunaan TEXT,
    tanggal_pemberian DATETIME DEFAULT CURRENT
