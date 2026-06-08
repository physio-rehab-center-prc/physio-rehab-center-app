package prc.controller;

import prc.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  PhysioRehabCenter - Sistem Manajemen Fisioterapi
 * ============================================================
 *  Tugas Besar Pemrograman Berorientasi Objek
 *  
 *  Anggota Kelompok:
 *    1. Rahma       - Model & DataStore
 *    2. Pebi        - Controller & Logic
 *    3. Eka         - View / UI (Login, Dashboard)
 *    4. Risma       - View / UI (Pasien, Jadwal, Rekam Medis)
 * ============================================================
 *
 *  Pola desain: SINGLETON
 *  DataStore adalah satu-satunya sumber data di seluruh aplikasi.
 *  Menyimpan semua data di memori (tanpa database).
 */
public class DataStore {

    // ===== SINGLETON PATTERN =====
    private static DataStore instance;

    private List<Pasien>      daftarPasien      = new ArrayList<>();
    private List<Terapis>     daftarTerapis     = new ArrayList<>();
    private List<JadwalTerapi> daftarJadwal     = new ArrayList<>();
    private List<RekamMedis>  daftarRekamMedis  = new ArrayList<>();

    private int nextIdPasien      = 1;
    private int nextIdJadwal      = 1;
    private int nextIdRekamMedis  = 1;

    private Terapis penggunaSaatIni = null;

    /** Constructor private → tidak bisa di-new dari luar */
    private DataStore() {
        inisialisasiDataDummy();
    }

    /** Mengembalikan satu-satunya instance DataStore */
    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // ===== INISIALISASI DATA AWAL =====
    private void inisialisasiDataDummy() {

        // ----- Data Terapis (4 akun = 4 anggota kelompok) -----
        Terapis t1 = new Terapis(1, "Rahma Aulia",    "STR-001-2024",
                "Ortopedi",  "081234567891", "rahma@prc.com",  "rahma",  "admin123");
        Terapis t2 = new Terapis(2, "Pebi Saputra",   "STR-002-2024",
                "Pediatri",  "081234567892", "pebi@prc.com",   "pebi",   "admin123");
        Terapis t3 = new Terapis(3, "Eka Putri",      "STR-003-2024",
                "Neurologi", "081234567893", "eka@prc.com",    "eka",    "admin123");
        Terapis t4 = new Terapis(4, "Risma Wulandari","STR-004-2024",
                "Kardiovaskular","081234567894","risma@prc.com","risma", "admin123");

        daftarTerapis.add(t1);
        daftarTerapis.add(t2);
        daftarTerapis.add(t3);
        daftarTerapis.add(t4);

        // ----- Data Pasien contoh -----
        tambahPasien("3201010101010001", "Budi Santoso",  "1990-05-15",
                "L", "Jl. Merdeka No.1, Jakarta",       "08111111111",
                "budi@email.com",  "A", "Tidak ada");
        tambahPasien("3201010101010002", "Siti Rahayu",   "1985-08-20",
                "P", "Jl. Sudirman No.5, Bandung",       "08222222222",
                "siti@email.com",  "B", "Penisilin");
        tambahPasien("3201010101010003", "Ahmad Fauzi",   "1995-03-10",
                "L", "Jl. Gatot Subroto No.9, Surabaya", "08333333333",
                "ahmad@email.com", "O", "Tidak ada");

        // ----- Data Jadwal contoh -----
        Pasien p1 = daftarPasien.get(0);
        Pasien p2 = daftarPasien.get(1);

        JadwalTerapi j1 = new JadwalTerapi(nextIdJadwal++, p1, t1,
                "2026-06-01 09:00", "TERAPI_RUTIN", 60, "Terapi pasca stroke");
        JadwalTerapi j2 = new JadwalTerapi(nextIdJadwal++, p2, t2,
                "2026-06-01 10:30", "EVALUASI",     45, "Evaluasi lutut kanan");
        j1.selesaikanSesi();
        daftarJadwal.add(j1);
        daftarJadwal.add(j2);

        // ----- Data Rekam Medis contoh -----
        RekamMedis rm1 = new RekamMedis(nextIdRekamMedis++, j1,
                "2026-06-01 09:00", 7, 4,
                "Hemiplegia sisi kanan pasca stroke",
                "Stimulasi elektrik, latihan ROM aktif-pasif",
                "Pasien mengalami penurunan nyeri signifikan",
                "Lanjutkan terapi minggu depan");
        daftarRekamMedis.add(rm1);
    }

    // ===== PASIEN =====

    /** Membuat Pasien baru dan menambahkannya ke daftar */
    public void tambahPasien(String nik, String nama, String tglLahir,
                              String jk, String alamat, String telp,
                              String email, String golDarah, String alergi) {
        Pasien p = new Pasien(nextIdPasien++, nik, nama, tglLahir,
                jk, alamat, telp, email, golDarah, alergi);
        daftarPasien.add(p);
    }

    public List<Pasien> getDaftarPasien() { return daftarPasien; }

    public void hapusPasien(int idPasien) {
        daftarPasien.removeIf(p -> p.getIdPasien() == idPasien);
    }

    // ===== TERAPIS =====

    public List<Terapis> getDaftarTerapis() { return daftarTerapis; }

    /**
     * Autentikasi login.
     * @return objek Terapis jika berhasil, null jika gagal
     */
    public Terapis loginTerapis(String username, String password) {
        for (Terapis t : daftarTerapis) {
            if (t.getUsername().equals(username) && t.login(password)) {
                penggunaSaatIni = t;
                return t;
            }
        }
        return null;
    }

    // ===== JADWAL =====

    public void tambahJadwal(Pasien pasien, Terapis terapis, String waktu,
                              String tipe, int durasi, String catatan) {
        JadwalTerapi j = new JadwalTerapi(nextIdJadwal++, pasien, terapis,
                waktu, tipe, durasi, catatan);
        daftarJadwal.add(j);
    }

    public List<JadwalTerapi> getDaftarJadwal() { return daftarJadwal; }

    // ===== REKAM MEDIS =====

    public List<RekamMedis> getDaftarRekamMedis() { return daftarRekamMedis; }

    public void tambahRekamMedis(JadwalTerapi jadwal, String tanggal,
                                  int nyeriSebelum, int nyeriSesudah,
                                  String diagnosis, String tindakan,
                                  String hasil, String catatan) {
        RekamMedis rm = new RekamMedis(nextIdRekamMedis++, jadwal, tanggal,
                nyeriSebelum, nyeriSesudah, diagnosis, tindakan, hasil, catatan);
        daftarRekamMedis.add(rm);
    }

    // ===== SESSION =====

    public Terapis getPenggunaSaatIni() { return penggunaSaatIni; }

    public void logout() { penggunaSaatIni = null; }
}
