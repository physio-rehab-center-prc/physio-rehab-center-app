package controller;

import data.DataStore;
import model.*;
import java.time.LocalDate;
import java.util.List;

public class PasienController {
    private DataStore ds;
    public PasienController(DataStore ds) { this.ds = ds; }

    public List<Pasien> getAllPasien() { return ds.getAllPasien(); }
    public Pasien getPasienById(String id) { return ds.getPasienById(id); }
    public int getTotalPasienAktif() { return ds.getTotalPasienAktif(); }

    public Pasien daftarPasien(String nama, String email, String password, String hp,
                                LocalDate lahir, String gender, String alamat,
                                String diagnosis, String asuransi) {
        String id = "P" + String.format("%03d", ds.getAllPasien().size() + 1);
        Pasien p = new Pasien(id, nama, email, password, hp, lahir, gender, alamat, diagnosis, asuransi);
        ds.addUser(p);
        return p;
    }

    public boolean updatePasien(String id, String nama, String hp, String alamat, String alergi) {
        Pasien p = ds.getPasienById(id);
        if (p != null) {
            p.setNama(nama);
            p.setNomorHP(hp);
            p.setAlamat(alamat);
            p.setAlergi(alergi);
            return true;
        }
        return false;
    }

    public List<RekamMedis> getRekamMedis(String pasienId) {
        return ds.getRekamMedisByPasien(pasienId);
    }

    public RekamMedis tambahRekamMedis(String pasienId, String terapisId, String sesiId,
                                        int sesiKe, String s, String o, String a, String p, int vas) {
        String id = ds.generateIdRekam();
        RekamMedis rm = new RekamMedis(id, pasienId, terapisId, sesiId, LocalDate.now(), sesiKe);
        rm.isiSOAP(s, o, a, p);
        rm.setSkalaNyeriVAS(vas);
        ds.addRekamMedis(rm);
        return rm;
    }
}
