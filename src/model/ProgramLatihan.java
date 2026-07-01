package model;

import java.util.ArrayList;
import java.util.List;


public class ProgramLatihan {
    private String id;
    private String nama;
    private String kategori;
    private String deskripsi;
    private int durasiMenit;
    private String tingkatKesulitan; 
    private String emoji;
    private List<String> daftarLatihan;
    private String tujuan;

    public ProgramLatihan(String id, String nama, String kategori, String deskripsi,
                          int durasiMenit, String tingkatKesulitan, String emoji) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.durasiMenit = durasiMenit;
        this.tingkatKesulitan = tingkatKesulitan;
        this.emoji = emoji;
        this.daftarLatihan = new ArrayList<>();
    }

    public void tambahLatihan(String latihan) {
        daftarLatihan.add(latihan);
    }

    
    public String getId() { return id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public int getDurasiMenit() { return durasiMenit; }
    public void setDurasiMenit(int durasiMenit) { this.durasiMenit = durasiMenit; }
    public String getTingkatKesulitan() { return tingkatKesulitan; }
    public void setTingkatKesulitan(String tingkatKesulitan) { this.tingkatKesulitan = tingkatKesulitan; }
    public String getEmoji() { return emoji; }
    public List<String> getDaftarLatihan() { return daftarLatihan; }
    public String getTujuan() { return tujuan; }
    public void setTujuan(String tujuan) { this.tujuan = tujuan; }

    @Override
    public String toString() {
        return emoji + " " + nama + " (" + kategori + ") - " + durasiMenit + " menit";
    }
}
