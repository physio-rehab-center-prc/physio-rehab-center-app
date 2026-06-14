package model;

import java.util.ArrayList;
import java.util.List;


public class Terapis extends User {
    private String nomorSTR;      
    private String nomorSIP;      
    private String spesialisasi;
    private String jamKerjaStart;
    private String jamKerjaEnd;
    private double rating;
    private int pengalamanTahun;
    private List<String> sertifikasi;
    private StatusTerapis status;

    public enum StatusTerapis {
        TERSEDIA, SIBUK, ISTIRAHAT, TIDAK_AKTIF
    }

    public Terapis(String id, String nama, String email, String password, String nomorHP,
                   String nomorSTR, String nomorSIP, String spesialisasi, int pengalamanTahun) {
        super(id, nama, email, password, nomorHP, "TERAPIS");
        this.nomorSTR = nomorSTR;
        this.nomorSIP = nomorSIP;
        this.spesialisasi = spesialisasi;
        this.pengalamanTahun = pengalamanTahun;
        this.rating = 0.0;
        this.jamKerjaStart = "07:00";
        this.jamKerjaEnd = "16:00";
        this.status = StatusTerapis.TERSEDIA;
        this.sertifikasi = new ArrayList<>();
    }

    @Override
    public String getInfo() {
        return "Terapis: " + nama + " | Spesialisasi: " + spesialisasi +
               " | STR: " + nomorSTR + " | Rating: " + rating;
    }

    @Override
    public String getDashboardTitle() {
        return "Dashboard Terapis - " + nama;
    }

    public void tambahSertifikasi(String sertif) {
        sertifikasi.add(sertif);
    }

    // Getters & Setters
    public String getNomorSTR() { return nomorSTR; }
    public void setNomorSTR(String nomorSTR) { this.nomorSTR = nomorSTR; }
    public String getNomorSIP() { return nomorSIP; }
    public void setNomorSIP(String nomorSIP) { this.nomorSIP = nomorSIP; }
    public String getSpesialisasi() { return spesialisasi; }
    public void setSpesialisasi(String spesialisasi) { this.spesialisasi = spesialisasi; }
    public String getJamKerjaStart() { return jamKerjaStart; }
    public void setJamKerjaStart(String jamKerjaStart) { this.jamKerjaStart = jamKerjaStart; }
    public String getJamKerjaEnd() { return jamKerjaEnd; }
    public void setJamKerjaEnd(String jamKerjaEnd) { this.jamKerjaEnd = jamKerjaEnd; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public int getPengalamanTahun() { return pengalamanTahun; }
    public void setPengalamanTahun(int tahun) { this.pengalamanTahun = tahun; }
    public List<String> getSertifikasi() { return sertifikasi; }
    public StatusTerapis getStatus() { return status; }
    public void setStatus(StatusTerapis status) { this.status = status; }

    public String getStatusLabel() {
        switch (status) {
            case TERSEDIA: return "Tersedia";
            case SIBUK: return "Sibuk";
            case ISTIRAHAT: return "Istirahat";
            default: return "Tidak Aktif";
        }
    }
}
