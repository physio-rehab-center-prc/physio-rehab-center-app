package controller;
import data.DataStore;
import model.Tagihan;
import java.util.List;

public class TagihanController {
    private DataStore ds;
    public TagihanController(DataStore ds) { this.ds = ds; }
    public List<Tagihan> getAllTagihan() { return ds.getAllTagihan(); }
    public List<Tagihan> getTagihanByPasien(String pasienId) { return ds.getTagihanByPasien(pasienId); }
    public boolean bayar(String id, String metode) {
        Tagihan t = ds.getTagihanById(id);
        if (t != null && t.getStatus() != Tagihan.StatusTagihan.LUNAS) {
            t.bayar(metode); return true;
        }
        return false;
    }
    public double getTotalPendapatan() { return ds.getTotalPendapatanBulanIni(); }
    public long getTagihanMenunggu() { return ds.getTagihanMenunggu(); }
}
