package prc.model;

public class RekamMedis {
    private int idRekamMedis;
    private JadwalTerapi jadwal;
    private String tanggalSesi;
    private int skalaNyeriSebelum; // 0-10
    private int skalaNyeriSesudah; // 0-10
    private String diagnosis;
    private String tindakanTerapi;
    private String hasilEvaluasi;
    private String catatanKlinis;

    public RekamMedis(int idRekamMedis, JadwalTerapi jadwal, String tanggalSesi,
                      int skalaNyeriSebelum, int skalaNyeriSesudah,
                      String diagnosis, String tindakanTerapi,
                      String hasilEvaluasi, String catatanKlinis) {
        this.idRekamMedis = idRekamMedis;
        this.jadwal = jadwal;
        this.tanggalSesi = tanggalSesi;
        this.skalaNyeriSebelum = skalaNyeriSebelum;
        this.skalaNyeriSesudah = skalaNyeriSesudah;
        this.diagnosis = diagnosis;
        this.tindakanTerapi = tindakanTerapi;
        this.hasilEvaluasi = hasilEvaluasi;
        this.catatanKlinis = catatanKlinis;
    }

    public int getPenurunanNyeri() {
        return skalaNyeriSebelum - skalaNyeriSesudah;
    }

    public String getStatusPerkembangan() {
        int penurunan = getPenurunanNyeri();
        if (penurunan > 3) return "Signifikan";
        else if (penurunan > 0) return "Membaik";
        else if (penurunan == 0) return "Stabil";
        else return "Memburuk";
    }

    // Getters & Setters
    public int getIdRekamMedis() { return idRekamMedis; }
    public JadwalTerapi getJadwal() { return jadwal; }
    public String getTanggalSesi() { return tanggalSesi; }
    public int getSkalaNyeriSebelum() { return skalaNyeriSebelum; }
    public void setSkalaNyeriSebelum(int skalaNyeriSebelum) { this.skalaNyeriSebelum = skalaNyeriSebelum; }
    public int getSkalaNyeriSesudah() { return skalaNyeriSesudah; }
    public void setSkalaNyeriSesudah(int skalaNyeriSesudah) { this.skalaNyeriSesudah = skalaNyeriSesudah; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTindakanTerapi() { return tindakanTerapi; }
    public void setTindakanTerapi(String t) { this.tindakanTerapi = t; }
    public String getHasilEvaluasi() { return hasilEvaluasi; }
    public void setHasilEvaluasi(String h) { this.hasilEvaluasi = h; }
    public String getCatatanKlinis() { return catatanKlinis; }
    public void setCatatanKlinis(String c) { this.catatanKlinis = c; }
}
