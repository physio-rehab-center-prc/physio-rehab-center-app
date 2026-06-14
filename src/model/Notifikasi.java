package model;

import java.time.LocalDateTime;


public class Notifikasi {
    private String id;
    private String userId;
    private String judul;
    private String pesan;
    private String emoji;
    private LocalDateTime waktu;
    private boolean sudahDibaca;
    private TipeNotifikasi tipe;

    public enum TipeNotifikasi {
        PENGINGAT_SESI, TAGIHAN, INFO, PERINGATAN, SUKSES
    }

    public Notifikasi(String id, String userId, String judul, String pesan,
                      String emoji, TipeNotifikasi tipe) {
        this.id = id;
        this.userId = userId;
        this.judul = judul;
        this.pesan = pesan;
        this.emoji = emoji;
        this.waktu = LocalDateTime.now();
        this.sudahDibaca = false;
        this.tipe = tipe;
    }

    public void tandaiDibaca() { this.sudahDibaca = true; }

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getJudul() { return judul; }
    public String getPesan() { return pesan; }
    public String getEmoji() { return emoji; }
    public LocalDateTime getWaktu() { return waktu; }
    public boolean isSudahDibaca() { return sudahDibaca; }
    public TipeNotifikasi getTipe() { return tipe; }
    public void setWaktu(LocalDateTime waktu) { this.waktu = waktu; }

    public String getWaktuLabel() {
        LocalDateTime now = LocalDateTime.now();
        long menit = java.time.Duration.between(waktu, now).toMinutes();
        if (menit < 60) return menit + " mnt lalu";
        long jam = menit / 60;
        if (jam < 24) return jam + " jam lalu";
        return (jam / 24) + " hari lalu";
    }
}
