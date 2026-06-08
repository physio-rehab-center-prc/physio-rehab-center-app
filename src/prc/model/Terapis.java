/**
 * PhysioRehabCenter — Tugas Besar Pemrograman Berorientasi Objek
 * Anggota Kelompok: Rahma | Pebi | Eka | Risma
 */
package prc.model;

// INHERITANCE - Terapis extends Pengguna
public class Terapis extends Pengguna {
    private int idTerapis;
    private String namaLengkap;
    private String noStr;
    private String spesialisasi;
    private String noTelepon;
    private String email;
    private String statusKerja; // AKTIF, CUTI, NONAKTIF

    public Terapis(int idTerapis, String namaLengkap, String noStr,
                   String spesialisasi, String noTelepon, String email,
                   String username, String password) {
        super(username, password, "TERAPIS");
        this.idTerapis = idTerapis;
        this.namaLengkap = namaLengkap;
        this.noStr = noStr;
        this.spesialisasi = spesialisasi;
        this.noTelepon = noTelepon;
        this.email = email;
        this.statusKerja = "AKTIF";
    }

    // POLYMORPHISM - override method getInfo() dari Pengguna
    @Override
    public String getInfo() {
        return "Terapis: " + namaLengkap + " | Spesialisasi: " + spesialisasi + " | STR: " + noStr;
    }

    // Getters & Setters
    public int getIdTerapis() { return idTerapis; }
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    public String getNoStr() { return noStr; }
    public String getSpesialisasi() { return spesialisasi; }
    public void setSpesialisasi(String spesialisasi) { this.spesialisasi = spesialisasi; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatusKerja() { return statusKerja; }
    public void setStatusKerja(String statusKerja) { this.statusKerja = statusKerja; }

    @Override
    public String toString() {
        return namaLengkap + " (" + spesialisasi + ")";
    }
}
