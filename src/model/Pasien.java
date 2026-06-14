package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Pasien extends User {
    private LocalDate tanggalLahir;
    private String jenisKelamin;
    private String alamat;
    private String diagnosis;
    private String asuransi;       
    private String nomorAsuransi;
    private String golonganDarah;
    private String alergi;
    private String catatanMedis;
    private String terapisId;       
    private int totalSesiTarget;
    private int totalSesiSelesai;
    private StatusPasien statusPasien;
    private LocalDate tanggalDaftar;
    private List<String> riwayatPenyakit;
    private int progresKekuatan;
    private int progresFleksibilitas;
    private int progresNyeri;
    private int progresKemandirian;

    public enum StatusPasien {
        AKTIF, SELESAI, TIDAK_AKTIF, BARU
    }

    public Pasien(String id, String nama, String email, String password, String nomorHP,
                  LocalDate tanggalLahir, String jenisKelamin, String alamat,
                  String diagnosis, String asuransi) {
        super(id, nama, email, password, nomorHP, "PASIEN");
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.diagnosis = diagnosis;
        this.asuransi = asuransi;
        this.statusPasien = StatusPasien.BARU;
        this.tanggalDaftar = LocalDate.now();
        this.riwayatPenyakit = new ArrayList<>();
        this.totalSesiTarget = 0;
        this.totalSesiSelesai = 0;
        this.progresKekuatan = 0;
        this.progresFleksibilitas = 0;
        this.progresNyeri = 0;
        this.progresKemandirian = 0;
    }

    @Override
    public String getInfo() {
        return "Pasien: " + nama + " | Diagnosis: " + diagnosis +
               " | Sesi: " + totalSesiSelesai + "/" + totalSesiTarget;
    }

    @Override
    public String getDashboardTitle() {
        return "Dashboard Pasien - " + nama;
    }

    public int getUsia() {
        return LocalDate.now().getYear() - tanggalLahir.getYear();
    }

    public double getPresentaseProgres() {
        if (totalSesiTarget == 0) return 0;
        return (double) totalSesiSelesai / totalSesiTarget * 100;
    }

    public int getProgresSesiPersen() {
        if (totalSesiTarget == 0) return 0;
        return (int) ((double) totalSesiSelesai / totalSesiTarget * 100);
    }

    public void tambahRiwayatPenyakit(String riwayat) {
        riwayatPenyakit.add(riwayat);
    }

    public void sesiSelesai() {
        if (totalSesiSelesai < totalSesiTarget) {
            totalSesiSelesai++;
            updateProgres();
            if (totalSesiSelesai >= totalSesiTarget) {
                statusPasien = StatusPasien.SELESAI;
            } else if (statusPasien == StatusPasien.BARU) {
                statusPasien = StatusPasien.AKTIF;
            }
        }
    }

    private void updateProgres() {
        int persen = getProgresSesiPersen();
        progresKekuatan = Math.min(100, persen + 20);
        progresFleksibilitas = Math.min(100, persen + 5);
        progresNyeri = Math.min(100, persen + 15);
        progresKemandirian = Math.min(100, persen + 30);
    }

    
    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getAsuransi() { return asuransi; }
    public void setAsuransi(String asuransi) { this.asuransi = asuransi; }
    public String getNomorAsuransi() { return nomorAsuransi; }
    public void setNomorAsuransi(String nomorAsuransi) { this.nomorAsuransi = nomorAsuransi; }
    public String getGolonganDarah() { return golonganDarah; }
    public void setGolonganDarah(String golonganDarah) { this.golonganDarah = golonganDarah; }
    public String getAlergi() { return alergi; }
    public void setAlergi(String alergi) { this.alergi = alergi; }
    public String getCatatanMedis() { return catatanMedis; }
    public void setCatatanMedis(String catatanMedis) { this.catatanMedis = catatanMedis; }
    public String getTerapisId() { return terapisId; }
    public void setTerapisId(String terapisId) { this.terapisId = terapisId; }
    public int getTotalSesiTarget() { return totalSesiTarget; }
    public void setTotalSesiTarget(int totalSesiTarget) { this.totalSesiTarget = totalSesiTarget; }
    public int getTotalSesiSelesai() { return totalSesiSelesai; }
    public void setTotalSesiSelesai(int n) { this.totalSesiSelesai = n; }
    public StatusPasien getStatusPasien() { return statusPasien; }
    public void setStatusPasien(StatusPasien statusPasien) { this.statusPasien = statusPasien; }
    public LocalDate getTanggalDaftar() { return tanggalDaftar; }
    public List<String> getRiwayatPenyakit() { return riwayatPenyakit; }
    public int getProgresKekuatan() { return progresKekuatan; }
    public void setProgresKekuatan(int v) { this.progresKekuatan = v; }
    public int getProgresFleksibilitas() { return progresFleksibilitas; }
    public void setProgresFleksibilitas(int v) { this.progresFleksibilitas = v; }
    public int getProgresNyeri() { return progresNyeri; }
    public void setProgresNyeri(int v) { this.progresNyeri = v; }
    public int getProgresKemandirian() { return progresKemandirian; }
    public void setProgresKemandirian(int v) { this.progresKemandirian = v; }

    public String getStatusLabel() {
        switch (statusPasien) {
            case AKTIF: return "Aktif";
            case SELESAI: return "Selesai";
            case TIDAK_AKTIF: return "Tidak Aktif";
            default: return "Baru";
        }
    }
}
