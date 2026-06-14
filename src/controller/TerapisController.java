package controller;
import data.DataStore;
import model.Terapis;
import java.util.List;

public class TerapisController {
    private DataStore ds;
    public TerapisController(DataStore ds) { this.ds = ds; }
    public List<Terapis> getAllTerapis() { return ds.getAllTerapis(); }
    public Terapis getTerapisById(String id) { return ds.getTerapisById(id); }
    public boolean setStatus(String id, Terapis.StatusTerapis status) {
        Terapis t = ds.getTerapisById(id);
        if (t != null) { t.setStatus(status); return true; }
        return false;
    }
}
