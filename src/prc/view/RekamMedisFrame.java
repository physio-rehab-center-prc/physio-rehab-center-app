/**
 * PhysioRehabCenter — Tugas Besar Pemrograman Berorientasi Objek
 * Anggota Kelompok: Rahma | Pebi | Eka | Risma
 */
package prc.view;

import prc.controller.DataStore;
import prc.model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class RekamMedisFrame extends JFrame {

    private JTable tabel;
    private DefaultTableModel modelTabel;
    private JComboBox<JadwalTerapi> cmbJadwal;
    private JTextField txtTanggal, txtDiagnosis, txtTindakan, txtHasil, txtCatatan;
    private JSpinner spnNyeriSebelum, spnNyeriSesudah;

    public RekamMedisFrame() {
        setTitle("PRC - Rekam Medis");
        setSize(950, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        muatDataTabel();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 248, 252));

        JLabel lblJudul = new JLabel("📋 Rekam Medis Pasien");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblJudul.setForeground(new Color(153, 76, 0));

        // === FORM ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Form Input Rekam Medis"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.anchor = GridBagConstraints.WEST;

        cmbJadwal = new JComboBox<>();
        for (JadwalTerapi j : DataStore.getInstance().getDaftarJadwal()) {
            cmbJadwal.addItem(j);
        }

        txtTanggal = new JTextField("2026-06-01 09:00", 15);
        txtDiagnosis = new JTextField(20);
        txtTindakan = new JTextField(20);
        txtHasil = new JTextField(20);
        txtCatatan = new JTextField(20);
        spnNyeriSebelum = new JSpinner(new SpinnerNumberModel(5, 0, 10, 1));
        spnNyeriSesudah = new JSpinner(new SpinnerNumberModel(2, 0, 10, 1));

        // Baris 1
        g.gridx=0; g.gridy=0; formPanel.add(new JLabel("Jadwal Sesi:"), g);
        g.gridx=1; g.gridwidth=3; formPanel.add(cmbJadwal, g); g.gridwidth=1;

        // Baris 2
        g.gridx=0; g.gridy=1; formPanel.add(new JLabel("Tanggal Sesi:"), g);
        g.gridx=1; formPanel.add(txtTanggal, g);
        g.gridx=2; formPanel.add(new JLabel("Skala Nyeri Sebelum (0-10):"), g);
        g.gridx=3; formPanel.add(spnNyeriSebelum, g);

        // Baris 3
        g.gridx=0; g.gridy=2; formPanel.add(new JLabel("Skala Nyeri Sesudah (0-10):"), g);
        g.gridx=1; formPanel.add(spnNyeriSesudah, g);

        // Baris 4
        g.gridx=0; g.gridy=3; formPanel.add(new JLabel("Diagnosis:"), g);
        g.gridx=1; g.gridwidth=3; g.fill=GridBagConstraints.HORIZONTAL;
        formPanel.add(txtDiagnosis, g);
        g.gridwidth=1; g.fill=GridBagConstraints.NONE;

        // Baris 5
        g.gridx=0; g.gridy=4; formPanel.add(new JLabel("Tindakan Terapi:"), g);
        g.gridx=1; g.gridwidth=3; g.fill=GridBagConstraints.HORIZONTAL;
        formPanel.add(txtTindakan, g);
        g.gridwidth=1; g.fill=GridBagConstraints.NONE;

        // Baris 6
        g.gridx=0; g.gridy=5; formPanel.add(new JLabel("Hasil Evaluasi:"), g);
        g.gridx=1; g.gridwidth=3; g.fill=GridBagConstraints.HORIZONTAL;
        formPanel.add(txtHasil, g);
        g.gridwidth=1; g.fill=GridBagConstraints.NONE;

        // Baris 7
        g.gridx=0; g.gridy=6; formPanel.add(new JLabel("Catatan Klinis:"), g);
        g.gridx=1; g.gridwidth=3; g.fill=GridBagConstraints.HORIZONTAL;
        formPanel.add(txtCatatan, g);
        g.gridwidth=1; g.fill=GridBagConstraints.NONE;

        // Tombol
        JButton btnSimpan = new JButton("💾 Simpan Rekam Medis");
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSimpan.setBackground(new Color(153, 76, 0));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setBorderPainted(false);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(e -> simpanRekamMedis());

        JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tombolPanel.setBackground(Color.WHITE);
        tombolPanel.add(btnSimpan);
        g.gridx=0; g.gridy=7; g.gridwidth=4; g.fill=GridBagConstraints.HORIZONTAL;
        formPanel.add(tombolPanel, g);

        // === TABEL ===
        String[] kolom = {"ID", "Pasien", "Terapis", "Tanggal", "Nyeri Sblm", "Nyeri Ssdh", "Penurunan", "Status Perkembangan"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabel = new JTable(modelTabel);
        tabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabel.setRowHeight(24);
        tabel.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabel.getTableHeader().setBackground(new Color(153, 76, 0));
        tabel.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Daftar Rekam Medis"));
        scrollPane.setPreferredSize(new Dimension(950, 180));

        mainPanel.add(lblJudul, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void muatDataTabel() {
        modelTabel.setRowCount(0);
        for (RekamMedis rm : DataStore.getInstance().getDaftarRekamMedis()) {
            modelTabel.addRow(new Object[]{
                rm.getIdRekamMedis(),
                rm.getJadwal().getPasien().getNamaLengkap(),
                rm.getJadwal().getTerapis().getNamaLengkap(),
                rm.getTanggalSesi(),
                rm.getSkalaNyeriSebelum(),
                rm.getSkalaNyeriSesudah(),
                rm.getPenurunanNyeri(),
                rm.getStatusPerkembangan()
            });
        }
    }

    private void simpanRekamMedis() {
        JadwalTerapi jadwal = (JadwalTerapi) cmbJadwal.getSelectedItem();
        String tanggal = txtTanggal.getText().trim();
        int nyeriSebelum = (int) spnNyeriSebelum.getValue();
        int nyeriSesudah = (int) spnNyeriSesudah.getValue();
        String diagnosis = txtDiagnosis.getText().trim();
        String tindakan = txtTindakan.getText().trim();
        String hasil = txtHasil.getText().trim();
        String catatan = txtCatatan.getText().trim();

        if (diagnosis.isEmpty() || tindakan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Diagnosis dan Tindakan Terapi wajib diisi!");
            return;
        }

        DataStore.getInstance().tambahRekamMedis(jadwal, tanggal, nyeriSebelum, nyeriSesudah,
                diagnosis, tindakan, hasil, catatan);
        muatDataTabel();
        JOptionPane.showMessageDialog(this,
                "✓ Rekam medis berhasil disimpan!\nPenurunan nyeri: " +
                (nyeriSebelum - nyeriSesudah) + " poin",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }
}
