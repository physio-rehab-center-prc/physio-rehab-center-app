package controller;
import data.DataStore;
import model.Notifikasi;
import java.util.List;

public class NotifikasiController {
    private DataStore ds;
    public NotifikasiController(DataStore ds) { this.ds = ds; }
    public List<Notifikasi> getByUser(String userId) { return ds.getNotifikasiByUser(userId); }
    public long countUnread(String userId) { return ds.countUnreadNotifikasi(userId); }
    public void tandaiSemuaDibaca(String userId) { ds.tandaiSemuaDibaca(userId); }
    public void tambah(Notifikasi n) { ds.addNotifikasi(n); }
}
