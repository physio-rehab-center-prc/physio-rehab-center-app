package model;

import java.time.LocalDate;


public class Tagihan {
    private String id;
    private String pasienId;
    private String sesiId;
    private String namaLayanan;
    private double jumlah;
    private String metodePembayaran;
    private LocalDate tanggalTagihan;
    private LocalDate tanggalBayar;
    private LocalDate tanggalJatuhTempo;
    private StatusTagihan status;
    private String catatan;

    public enum StatusTagihan {
        MENUNGGU, LUNAS, JATUH_TEMPO, DIBATALKAN
    }

    public Tagihan(String id, String pasienId, String sesiId, String namaLayanan, double jumlah) {
        this.id = id;
        this.pasienId = pasienId;
        this.sesiId = sesiId;
        this.namaLayanan = namaLayanan;
        this.jumlah = jumlah;
        this.tanggalTagihan = LocalDate.now();
        this.tanggalJatuhTempo = LocalDate.now().plusDays(14);
        this.status = StatusTagihan.MENUNGGU;
        this.catatan = "";
    }

    public void bayar(String metode) {
        this.status = StatusTagihan.LUNAS;
        this.metodePembayaran = metode;
        this.tanggalBayar = LocalDate.now();
    }

    public void cekJatuhTempo() {
        if (status == StatusTagihan.MENUNGGU && LocalDate.now().isAfter(tanggalJatuhTempo)) {
            status = StatusTagihan.JATUH_TEMPO;
        }
    }

    public String formatRupiah() {
        return String.format("Rp %,.0f", jumlah);
    }

    
    public String getId() { return id; }
    public String getPasienId() { return pasienId; }
    public String getSesiId() { return sesiId; }
    public String getNamaLayanan() { return namaLayanan; }
    public void setNamaLayanan(String namaLayanan) { this.namaLayanan = namaLayanan; }
    public double getJumlah() { return jumlah; }
    public void setJumlah(double jumlah) { this.jumlah = jumlah; }
    public String getMetodePembayaran() { return metodePembayaran; }
    public void setMetodePembayaran(String metode) { this.metodePembayaran = metode; }
    public LocalDate getTanggalTagihan() { return tanggalTagihan; }
    public LocalDate getTanggalBayar() { return tanggalBayar; }
    public LocalDate getTanggalJatuhTempo() { return tanggalJatuhTempo; }
    public void setTanggalJatuhTempo(LocalDate tgl) { this.tanggalJatuhTempo = tgl; }
    public StatusTagihan getStatus() { return status; }
    public void setStatus(StatusTagihan status) { this.status = status; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }

    public String getStatusLabel() {
        switch (status) {
            case MENUNGGU: return "Menunggu";
            case LUNAS: return "Lunas";
            case JATUH_TEMPO: return "Jatuh Tempo";
            default: return "Dibatalkan";
        }
    }
}
