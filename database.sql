-- ==========================================
-- 1. KLASTER MASTER DATA & KEAMANAN
-- ==========================================

-- Tabel Pengguna (Otentikasi Login & Multi-Role)
CREATE TABLE pengguna (
    id_pengguna INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    role ENUM('Admin', 'Terapis', 'Farmasi', 'Kasir') NOT NULL,
    status_aktif BOOLEAN DEFAULT TRUE,
    last_login DATETIME NULL
);

-- Tabel Pasien (Data Master Medis)
CREATE TABLE pasien (
    id_pasien INT AUTO_INCREMENT PRIMARY KEY,
    nik VARCHAR(16) UNIQUE NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    tanggal_lahir DATE NOT NULL,
    jenis_kelamin ENUM('Laki-laki', 'Perempuan') NOT NULL,
    alamat TEXT,
    no_telepon VARCHAR(15),
    email VARCHAR(100),
    golongan_darah VARCHAR(3),
    alergi TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabel Terapis (Inheritance dari Pengguna secara Logis)
CREATE TABLE terapis (
    id_terapis INT PRIMARY KEY,
    nama_lengkap VARCHAR(100) NOT NULL,
    no_str VARCHAR(50) UNIQUE NOT NULL,
    spesialisasi VARCHAR(100),
    no_telepon VARCHAR(15),
    email VARCHAR(100),
    status_aktif BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_terapis) REFERENCES pengguna(id_pengguna) ON DELETE CASCADE
);

-- Tabel Item Medis (Inventaris Alat/Obat Farmasi)
CREATE TABLE item_medis (
    id_item_medis INT AUTO_INCREMENT PRIMARY KEY,
    nama_item VARCHAR(100) NOT NULL,
    kategori VARCHAR(50),
    satuan VARCHAR(20),
    harga_satuan DECIMAL(10,2) NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    deskripsi TEXT
);

-- Tabel Asuransi (Data Master Penjaminan)
CREATE TABLE asuransi (
    id_asuransi INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien INT,
    nama_provider VARCHAR(100) NOT NULL,
    no_polis VARCHAR(50) NOT NULL,
    limit_tahunan DECIMAL(12,2) NOT NULL,
    sisa_limit DECIMAL(12,2) NOT NULL,
    masa_berlaku DATE NOT NULL,
    status ENUM('Aktif', 'Tidak Aktif') DEFAULT 'Aktif',
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien)
);

-- ==========================================
-- 2. KLASTER OPERASIONAL & KLINIS
-- ==========================================

-- Tabel Jadwal Terapi (Penjadwalan Kunjungan Sesi)
CREATE TABLE jadwal_terapi (
    id_jadwal INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien INT NOT NULL,
    id_terapis INT NOT NULL,
    tanggal_waktu DATETIME NOT NULL,
    status ENUM('Dijadwalkan', 'Berlangsung', 'Selesai', 'Batal') DEFAULT 'Dijadwalkan',
    tipe_sesi VARCHAR(50),
    durasi_menit INT NOT NULL DEFAULT 60,
    catatan_jadwal TEXT,
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien),
    FOREIGN KEY (id_terapis) REFERENCES terapis(id_terapis)
);

-- Tabel Program Terapi (Rencana Jangka Panjang)
CREATE TABLE program_terapi (
    id_program INT AUTO_INCREMENT PRIMARY KEY,
    id_pasien INT NOT NULL,
    id_terapis INT NOT NULL,
    nama_program VARCHAR(100) NOT NULL,
    deskripsi TEXT,
    tanggal_mulai DATE NOT NULL,
    tanggal_selesai DATE,
    status ENUM('Aktif', 'Selesai', 'Dihentikan') DEFAULT 'Aktif',
    target_pemulihan TEXT,
    FOREIGN KEY (id_pasien) REFERENCES pasien(id_pasien),
    FOREIGN KEY (id_terapis) REFERENCES terapis(id_terapis)
);

-- Tabel Rekam Medis (Catatan Evaluasi Klinis per Sesi - Relasi 1:1)
CREATE TABLE rekam_medis (
    id_rekam_medis INT AUTO_INCREMENT PRIMARY KEY,
    id_jadwal INT UNIQUE NOT NULL, -- UNIQUE mengunci relasi 1:1 dengan jadwal
    tanggal_sesi DATETIME NOT NULL,
    skala_nyeri_sebelum INT CHECK (skala_nyeri_sebelum BETWEEN 0 AND 10),
    skala_nyeri_sesudah INT CHECK (skala_nyeri_sesudah BETWEEN 0 AND 10),
    diagnosis TEXT NOT NULL,
    tindakan_terapi TEXT NOT NULL,
    hasil_evaluasi TEXT,
    catatan_klinis TEXT,
    perlu_lanjutan BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (id_jadwal) REFERENCES jadwal_terapi(id_jadwal)
);

-- Tabel Resep Item Medis (Tabel Jembatan / Junction Table)
CREATE TABLE resep_item_medis (
    id_resep INT AUTO_INCREMENT PRIMARY KEY,
    id_rekam_medis INT NOT NULL,
    id_item_medis INT NOT NULL,
    jumlah INT NOT NULL CHECK (jumlah > 0),
    dosis VARCHAR(50),
    instruksi_penggunaan TEXT,
    tanggal_resep DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_rekam_medis) REFERENCES rekam_medis(id_rekam_medis),
    FOREIGN KEY (id_item_medis) REFERENCES item_medis(id_item_medis)
);

-- ==========================================
-- 3. KLASTER FINANSIAL & TRANSAKSI
-- ==========================================

-- Tabel Tagihan (Billing Kasir - Relasi 1:1 dengan Jadwal)
CREATE TABLE tagihan (
    id_tagihan INT AUTO_INCREMENT PRIMARY KEY,
    id_jadwal INT UNIQUE NOT NULL, -- UNIQUE mengunci relasi 1:1 dengan jadwal
    total_biaya DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    biaya_sesi DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    biaya_item_medis DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    status_bayar ENUM('Belum Bayar', 'Lunas', 'Gagal Klaim') DEFAULT 'Belum Bayar',
    metode_bayar ENUM('Tunai', 'Transfer', 'Asuransi') NOT NULL,
    tanggal_tagihan DATETIME DEFAULT CURRENT_TIMESTAMP,
    tanggal_tunas DATETIME NULL,
    FOREIGN KEY (id_jadwal) REFERENCES jadwal_terapi(id_jadwal)
);

-- Tabel Klaim Asuransi (Penjaminan Finansial)
CREATE TABLE klaim_asuransi (
    id_claim INT AUTO_INCREMENT PRIMARY KEY,
    id_tagihan INT NOT NULL,
    id_asuransi INT NOT NULL,
    jumlah_klaim DECIMAL(12,2) NOT NULL,
    status_klaim ENUM('Diproses', 'Disetujui', 'Ditolak') DEFAULT 'Diproses',
    no_referensi VARCHAR(50),
    tanggal_klaim DATETIME DEFAULT CURRENT_TIMESTAMP,
    tanggal_diproses DATETIME NULL,
    FOREIGN KEY (id_tagihan) REFERENCES tagihan(id_tagihan),
    FOREIGN KEY (id_asuransi) REFERENCES asuransi(id_asuransi)
);

-- ==========================================
-- 4. DATA AWAL (DUMMY DATA) UNTUK UJI COBA LOGIN
-- ==========================================
INSERT INTO pengguna (username, password_hash, nama_lengkap, role) VALUES 
('admin_eka', 'admin123', 'Eka Administrator', 'Admin'),
('terapis_budi', 'terapis123', 'Dr. Budi Setiawan', 'Terapis'),
('farmasi_siti', 'farmasi123', 'Siti Apoteker', 'Farmasi'),
('kasir_andi', 'kasir123', 'Andi Kasir', 'Kasir');

-- Daftarkan dr. Budi ke tabel terapis secara otomatis agar relasi akun valid
INSERT INTO terapis (id_terapis, nama_lengkap, no_str, spesialisasi, no_telepon) 
VALUES (2, 'Dr. Budi Setiawan', 'STR-123456789-2026', 'Fisioterapi Pasca-Stroke', '08123456789');
