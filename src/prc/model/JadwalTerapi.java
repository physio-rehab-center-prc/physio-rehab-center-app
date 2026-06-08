/**
 * PhysioRehabCenter — Tugas Besar Pemrograman Berorientasi Objek
 * Anggota Kelompok: Rahma | Pebi | Eka | Risma
 */
package prc.model;

public class JadwalTerapi {
    private int idJadwal;
    private Pasien pasien;
    private Terapis terapis;
    private String tanggalWaktu;
    private String status; // TERJADWAL, BERLANGSUNG, SELESAI, BATAL
    private String tipeSesi; // EVALUASI, TERAPI_RUTIN, KONSULTASI
    private int durasiMenit;
    private String catatanJadwal;

    public JadwalTerapi(int idJadwal, Pasien pasien, Terapis terapis,
                        String tanggalWaktu, String tipeSesi, int durasiMenit,
                        String catatanJadwal) {
        this.idJadwal = idJadwal;
        this.pasien = pasien;
        this.terapis = terapis;
        this.tanggalWaktu = tanggalWaktu;
        this.tipeSesi = tipeSesi;
        this.durasiMenit = durasiMenit;
        this.catatanJadwal = catatanJadwal;
        this.status = "TERJADWAL";
    }

    public void mulaiSesi() {
        this.status = "BERLANGSUNG";
    }

    public void selesaikanSesi() {
        this.status = "SELESAI";
    }

    public void batalkanSesi() {
        this.status = "BATAL";
    }

    // Getters & Setters
    public int getIdJadwal() { return idJadwal; }
    public Pasien getPasien() { return pasien; }
    public void setPasien(Pasien pasien) { this.pasien = pasien; }
    public Terapis getTerapis() { return terapis; }
    public void setTerapis(Terapis terapis) { this.terapis = terapis; }
    public String getTanggalWaktu() { return tanggalWaktu; }
    public void setTanggalWaktu(String tanggalWaktu) { this.tanggalWaktu = tanggalWaktu; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTipeSesi() { return tipeSesi; }
    public void setTipeSesi(String tipeSesi) { this.tipeSesi = tipeSesi; }
    public int getDurasiMenit() { return durasiMenit; }
    public void setDurasiMenit(int durasiMenit) { this.durasiMenit = durasiMenit; }
    public String getCatatanJadwal() { return catatanJadwal; }
    public void setCatatanJadwal(String catatanJadwal) { this.catatanJadwal = catatanJadwal; }

    @Override
    public String toString() {
        return "[" + idJadwal + "] " + pasien.getNamaLengkap() + " → " +
               terapis.getNamaLengkap() + " | " + tanggalWaktu + " | " + status;
    }
}
