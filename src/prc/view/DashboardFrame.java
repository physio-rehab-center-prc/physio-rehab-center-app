/**
 * PhysioRehabCenter — Tugas Besar Pemrograman Berorientasi Objek
 * Anggota Kelompok: Rahma | Pebi | Eka | Risma
 */
package prc.view;

import prc.controller.DataStore;
import prc.model.Terapis;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        Terapis user = DataStore.getInstance().getPenggunaSaatIni();
        setTitle("PRC - Dashboard | " + (user != null ? user.getNamaLengkap() : ""));
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        Terapis user = DataStore.getInstance().getPenggunaSaatIni();

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(245, 248, 252));

        // === HEADER ===
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 102, 153));
        header.setPreferredSize(new Dimension(700, 70));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitle = new JLabel("🏥 Physio Rehab Center");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userInfo.setBackground(new Color(0, 102, 153));
        JLabel lblUser = new JLabel("👤 " + (user != null ? user.getNamaLengkap() : "") +
                " | " + (user != null ? user.getSpesialisasi() : ""));
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setForeground(new Color(200, 230, 255));

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnLogout.setBackground(new Color(220, 50, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            DataStore.getInstance().logout();
            new LoginFrame().setVisible(true);
            dispose();
        });

        userInfo.add(lblUser);
        userInfo.add(btnLogout);
        header.add(lblTitle, BorderLayout.WEST);
        header.add(userInfo, BorderLayout.EAST);

        // === STATISTIK ===
        JPanel statPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statPanel.setBackground(new Color(245, 248, 252));
        statPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        int jmlPasien = DataStore.getInstance().getDaftarPasien().size();
        int jmlJadwal = DataStore.getInstance().getDaftarJadwal().size();
        int jmlRekam = DataStore.getInstance().getDaftarRekamMedis().size();

        statPanel.add(buatKartuStat("👥 Total Pasien", String.valueOf(jmlPasien), new Color(0, 150, 200)));
        statPanel.add(buatKartuStat("📅 Total Jadwal", String.valueOf(jmlJadwal), new Color(0, 180, 100)));
        statPanel.add(buatKartuStat("📋 Rekam Medis", String.valueOf(jmlRekam), new Color(200, 100, 0)));

        // === MENU ===
        JLabel lblMenu = new JLabel("  Menu Utama");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMenu.setForeground(new Color(60, 60, 60));
        lblMenu.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 0));

        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        menuPanel.setBackground(new Color(245, 248, 252));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        menuPanel.add(buatTombolMenu("👥 Manajemen Pasien",
                "Kelola data pasien klinik", new Color(0, 102, 153), e -> {
                    new PasienFrame().setVisible(true);
                }));
        menuPanel.add(buatTombolMenu("📅 Jadwal Terapi",
                "Atur penjadwalan sesi terapi", new Color(0, 153, 76), e -> {
                    new JadwalFrame().setVisible(true);
                }));
        menuPanel.add(buatTombolMenu("📋 Rekam Medis",
                "Lihat catatan klinis pasien", new Color(153, 76, 0), e -> {
                    new RekamMedisFrame().setVisible(true);
                }));
        menuPanel.add(buatTombolMenu("ℹ️ Tentang Sistem",
                "Informasi aplikasi PRC", new Color(100, 100, 100), e -> {
                    JOptionPane.showMessageDialog(this,
                            "Physio Rehab Center (PRC)\n" +
                            "Sistem Informasi Manajemen Klinik Fisioterapi\n\n" +
                            "Tugas Besar PBO - Kelompok 7\n" +
                            "Program Studi Informatika 2026\n\n" +
                            "Versi 1.0.0",
                            "Tentang Sistem", JOptionPane.INFORMATION_MESSAGE);
                }));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 248, 252));
        centerPanel.add(lblMenu, BorderLayout.NORTH);
        centerPanel.add(menuPanel, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(statPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel buatKartuStat(String label, String nilai, Color warna) {
        JPanel kartu = new JPanel(new GridLayout(2, 1));
        kartu.setBackground(Color.WHITE);
        kartu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, warna),
                BorderFactory.createEmptyBorder(10, 15, 10, 10)));

        JLabel lblNilai = new JLabel(nilai);
        lblNilai.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNilai.setForeground(warna);

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLabel.setForeground(Color.GRAY);

        kartu.add(lblNilai);
        kartu.add(lblLabel);
        return kartu;
    }

    private JButton buatTombolMenu(String judul, String deskripsi, Color warna,
                                    java.awt.event.ActionListener listener) {
        JButton btn = new JButton("<html><b>" + judul + "</b><br>" +
                "<font size='2' color='#cccccc'>" + deskripsi + "</font></html>");
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(warna);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.addActionListener(listener);
        return btn;
    }
}
