/**
 * PhysioRehabCenter — Tugas Besar Pemrograman Berorientasi Objek
 * Anggota Kelompok: Rahma | Pebi | Eka | Risma
 */
package prc.model;

// MODEL Pasien - Encapsulation dengan private attributes
public class Pasien {
    private int idPasien;
    private String nik;
    private String namaLengkap;
    private String tanggalLahir;
    private String jenisKelamin; // L / P
    private String alamat;
    private String noTelepon;
    private String email;
    private String golonganDarah;
    private String alergi;

    public Pasien(int idPasien, String nik, String namaLengkap, String tanggalLahir,
                  String jenisKelamin, String alamat, String noTelepon,
                  String email, String golonganDarah, String alergi) {
        this.idPasien = idPasien;
        this.nik = nik;
        this.namaLengkap = namaLengkap;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.email = email;
        this.golonganDarah = golonganDarah;
        this.alergi = alergi;
    }

    public String getInfo() {
        return "Pasien: " + namaLengkap + " | NIK: " + nik + " | Gol. Darah: " + golonganDarah;
    }

    // Getters & Setters
    public int getIdPasien() { return idPasien; }
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }
    public String getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(String tanggalLahir) { this.tanggalLahir = tanggalLahir; }
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGolonganDarah() { return golonganDarah; }
    public void setGolonganDarah(String golonganDarah) { this.golonganDarah = golonganDarah; }
    public String getAlergi() { return alergi; }
    public void setAlergi(String alergi) { this.alergi = alergi; }

    @Override
    public String toString() {
        return namaLengkap + " - " + nik;
    }
}
