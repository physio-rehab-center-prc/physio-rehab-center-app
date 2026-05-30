package prc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_physio_rehab";
                String user = "root";
                String password = ""; 
                
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi ke Database db_physio_rehab Berhasil!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal terhubung ke Database: " + e.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return koneksi;
    }
}
