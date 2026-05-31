package prc.model;

// BASE CLASS - Encapsulation + sebagai parent class (Inheritance)
public abstract class Pengguna {
    private String username;
    private String passwordHash;
    private String role;
    private boolean statusAktif;

    public Pengguna(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.statusAktif = true;
    }

    // Polymorphism - setiap subclass wajib implementasi ini
    public abstract String getInfo();

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isStatusAktif() { return statusAktif; }
    public void setStatusAktif(boolean statusAktif) { this.statusAktif = statusAktif; }

    public boolean login(String inputPassword) {
        return this.passwordHash.equals(inputPassword);
    }
}
