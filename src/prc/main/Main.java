/**
 * PhysioRehabCenter — Tugas Besar Pemrograman Berorientasi Objek
 * Anggota Kelompok: Rahma | Pebi | Eka | Risma
 */
package prc.main;

import prc.view.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set tampilan menggunakan Look and Feel sistem
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Gunakan default jika gagal
        }

        // Jalankan di Event Dispatch Thread (EDT) - best practice Swing
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
