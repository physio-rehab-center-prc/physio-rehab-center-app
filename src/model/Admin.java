package model;


public class Admin extends User {
    private String jabatan;

    public Admin(String id, String nama, String email, String password, String nomorHP, String jabatan) {
        super(id, nama, email, password, nomorHP, "ADMIN");
        this.jabatan = jabatan;
    }

    @Override
    public String getInfo() {
        return "Admin: " + nama + " | Jabatan: " + jabatan;
    }

    @Override
    public String getDashboardTitle() {
        return "Dashboard Administrator - PhysioHub";
    }

    public String getJabatan() { return jabatan; }
    public void setJabatan(String jabatan) { this.jabatan = jabatan; }
}
