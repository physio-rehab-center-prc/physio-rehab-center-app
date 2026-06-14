package model;

import java.time.LocalDate;
import java.time.LocalTime;


public class Sesi {
    private String id;
    private String pasienId;
    private String terapisId;
    private LocalDate tanggal;
    private LocalTime waktuMulai;
    private int durasiMenit;
    private String ruang;
    private String program;       
    private int sesiKe;
    private StatusSesi status;
    private String catatan;
    private String soapNotes;   

    public enum StatusSesi {
        TERJADWAL, BERLANGSUNG, SELESAI, DIBATALKAN, MENUNGGU
    }

    public Sesi(String id, String pasienId, String terapisId, LocalDate tanggal,
                LocalTime waktuMulai, int durasiMenit, String ruang, String program, int sesiKe) {
        this.id = id;
        this.pasienId = pasienId;
        this.terapisId = terapisId;
        this.tanggal = tanggal;
        this.waktuMulai = waktuMulai;
        this.durasiMenit = durasiMenit;
        this.ruang = ruang;
        this.program = program;
        this.sesiKe = sesiKe;
        this.status = StatusSesi.TERJADWAL;
        this.catatan = "";
        this.soapNotes = "";
    }

    public LocalTime getWaktuSelesai() {
        return waktuMulai.plusMinutes(durasiMenit);
    }

    public void mulaiSesi() {
        if (status == StatusSesi.TERJADWAL || status == StatusSesi.MENUNGGU) {
            status = StatusSesi.BERLANGSUNG;
        }
    }

    public void selesaikanSesi(String soap) {
        if (status == StatusSesi.BERLANGSUNG) {
            status = StatusSesi.SELESAI;
            this.soapNotes = soap;
        }
    }

    public void batalkanSesi(String alasan) {
        if (status != StatusSesi.SELESAI) {
            status = StatusSesi.DIBATALKAN;
            this.catatan = "Dibatalkan: " + alasan;
        }
    }

    // Getters & Setters
    public String getId() { return id; }
    public String getPasienId() { return pasienId; }
    public String getTerapisId() { return terapisId; }
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public LocalTime getWaktuMulai() { return waktuMulai; }
    public void setWaktuMulai(LocalTime waktuMulai) { this.waktuMulai = waktuMulai; }
    public int getDurasiMenit() { return durasiMenit; }
    public void setDurasiMenit(int durasiMenit) { this.durasiMenit = durasiMenit; }
    public String getRuang() { return ruang; }
    public void setRuang(String ruang) { this.ruang = ruang; }
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    public int getSesiKe() { return sesiKe; }
    public StatusSesi getStatus() { return status; }
    public void setStatus(StatusSesi status) { this.status = status; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
    public String getSoapNotes() { return soapNotes; }
    public void setSoapNotes(String soapNotes) { this.soapNotes = soapNotes; }

    public String getStatusLabel() {
        switch (status) {
            case TERJADWAL: return "Terjadwal";
            case BERLANGSUNG: return "Berlangsung";
            case SELESAI: return "Selesai";
            case DIBATALKAN: return "Dibatalkan";
            default: return "Menunggu";
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s - Sesi ke-%d | %s | %s",
            id, tanggal, waktuMulai, sesiKe, program, getStatusLabel());
    }
}
