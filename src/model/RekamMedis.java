package model;

import java.time.LocalDate;


public class RekamMedis {
    private String id;
    private String pasienId;
    private String terapisId;
    private String sesiId;
    private LocalDate tanggal;
    private int sesiKe;
    // SOAP
    private String subjektif;    
    private String objektif;     
    private String asesmen;      
    private String planning;     
    // Skala Nyeri
    private int skalaNyeriVAS;   
    private String tekananDarah;
    private int nadiPerMenit;

    public RekamMedis(String id, String pasienId, String terapisId, String sesiId,
                      LocalDate tanggal, int sesiKe) {
        this.id = id;
        this.pasienId = pasienId;
        this.terapisId = terapisId;
        this.sesiId = sesiId;
        this.tanggal = tanggal;
        this.sesiKe = sesiKe;
        this.skalaNyeriVAS = 0;
    }

    public void isiSOAP(String subjektif, String objektif, String asesmen, String planning) {
        this.subjektif = subjektif;
        this.objektif = objektif;
        this.asesmen = asesmen;
        this.planning = planning;
    }

    
    public String getId() { return id; }
    public String getPasienId() { return pasienId; }
    public String getTerapisId() { return terapisId; }
    public String getSesiId() { return sesiId; }
    public LocalDate getTanggal() { return tanggal; }
    public int getSesiKe() { return sesiKe; }
    public String getSubjektif() { return subjektif; }
    public void setSubjektif(String subjektif) { this.subjektif = subjektif; }
    public String getObjektif() { return objektif; }
    public void setObjektif(String objektif) { this.objektif = objektif; }
    public String getAsesmen() { return asesmen; }
    public void setAsesmen(String asesmen) { this.asesmen = asesmen; }
    public String getPlanning() { return planning; }
    public void setPlanning(String planning) { this.planning = planning; }
    public int getSkalaNyeriVAS() { return skalaNyeriVAS; }
    public void setSkalaNyeriVAS(int skalaNyeriVAS) { this.skalaNyeriVAS = skalaNyeriVAS; }
    public String getTekananDarah() { return tekananDarah; }
    public void setTekananDarah(String tekananDarah) { this.tekananDarah = tekananDarah; }
    public int getNadiPerMenit() { return nadiPerMenit; }
    public void setNadiPerMenit(int nadiPerMenit) { this.nadiPerMenit = nadiPerMenit; }
}
