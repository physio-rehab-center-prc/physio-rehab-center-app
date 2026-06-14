package controller;

import data.DataStore;
import model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SesiController {
    private DataStore ds;
    public SesiController(DataStore ds) { this.ds = ds; }

    public List<Sesi> getAllSesi() { return ds.getAllSesi(); }
    public List<Sesi> getSesiHariIni() { return ds.getSesiHariIni(); }
    public List<Sesi> getSesiByPasien(String pasienId) { return ds.getSesiByPasien(pasienId); }
    public List<Sesi> getSesiByTerapis(String terapisId) { return ds.getSesiByTerapis(terapisId); }

    public Sesi buatSesi(String pasienId, String terapisId, LocalDate tanggal,
                         LocalTime waktu, int durasi, String ruang, String program) {
        String id = ds.generateIdSesi();
        List<Sesi> existing = ds.getSesiByPasien(pasienId);
        int sesiKe = (int) existing.stream()
            .filter(s -> s.getProgram().equals(program)).count() + 1;
        Sesi sesi = new Sesi(id, pasienId, terapisId, tanggal, waktu, durasi, ruang, program, sesiKe);
        ds.addSesi(sesi);

  
        Notifikasi notif = new Notifikasi(ds.generateIdNotif(), pasienId,
            "Sesi Baru Dijadwalkan",
            "Sesi " + program + " pada " + tanggal + " pukul " + waktu + " di " + ruang,
            "📅", Notifikasi.TipeNotifikasi.PENGINGAT_SESI);
        ds.addNotifikasi(notif);
        return sesi;
    }

    public boolean mulaiSesi(String sesiId) {
        Sesi s = ds.getSesiById(sesiId);
        if (s != null) { s.mulaiSesi(); return true; }
        return false;
    }

    public boolean selesaikanSesi(String sesiId, String soapNotes) {
        Sesi s = ds.getSesiById(sesiId);
        if (s != null) {
            s.selesaikanSesi(soapNotes);
            Pasien p = ds.getPasienById(s.getPasienId());
            if (p != null) p.sesiSelesai();
            // buat tagihan otomatis
            Tagihan tg = new Tagihan(ds.generateIdTagihan(), s.getPasienId(), sesiId,
                "Fisioterapi Sesi ke-" + s.getSesiKe(), 450000);
            ds.addTagihan(tg);
            return true;
        }
        return false;
    }

    public boolean batalkanSesi(String sesiId, String alasan) {
        Sesi s = ds.getSesiById(sesiId);
        if (s != null) { s.batalkanSesi(alasan); return true; }
        return false;
    }

    public int countSesiSelesaiByPasien(String pasienId) {
        return (int) ds.getSesiByPasien(pasienId).stream()
            .filter(s -> s.getStatus() == Sesi.StatusSesi.SELESAI).count();
    }

    public int getTotalSesiHariIni() { return ds.getTotalSesiHariIni(); }
}
