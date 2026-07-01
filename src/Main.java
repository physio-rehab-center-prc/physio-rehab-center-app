import view.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Nimbus tidak tersedia, menggunakan default.");
        }

        
        SwingUtilities.invokeLater(() -> {
            System.out.println("==============================================");
            System.out.println("   PhysioRehab - Sistem Manajemen Fisioterapi  ");
            System.out.println("==============================================");
            System.out.println("Menginisialisasi data...");
            // DataStore singleton akan auto-init data sample
            data.DataStore.getInstance();
            System.out.println("Data berhasil dimuat.");
            System.out.println("Membuka halaman login...");
            System.out.println();
            System.out.println("Akun Demo:");
            System.out.println("  Admin   : admin@physiohub.com   / admin123");
            System.out.println("  Terapis : cut@physiohub.com    / terapis123");
            System.out.println("  Pasien  : risma@email.com        / pasien123");
            System.out.println("==============================================");
            new LoginFrame();
        });
    }
}