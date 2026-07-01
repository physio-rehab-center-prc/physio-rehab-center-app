package model;


public abstract class User {
    protected String id;
    protected String nama;
    protected String email;
    protected String password;
    protected String nomorHP;
    protected String role; 

    public User(String id, String nama, String email, String password, String nomorHP, String role) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.nomorHP = nomorHP;
        this.role = role;
    }

    
    public abstract String getInfo();
    public abstract String getDashboardTitle();

    
    public String getId() { return id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNomorHP() { return nomorHP; }
    public void setNomorHP(String nomorHP) { this.nomorHP = nomorHP; }
    public String getRole() { return role; }

    public boolean verifyPassword(String input) {
        return this.password.equals(input);
    }

    @Override
    public String toString() {
        return "[" + role + "] " + nama + " (" + email + ")";
    }
}
