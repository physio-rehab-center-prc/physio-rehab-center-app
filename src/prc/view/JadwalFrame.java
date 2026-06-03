package prc.view;

import prc.controller.DataStore;
import prc.model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class JadwalFrame extends JFrame {

    private JTable tabel;
    private DefaultTableModel modelTabel;
    private JComboBox<Pasien> cmbPasien;
    private JComboBox<Terapis> cmbTerapis;
    private JComboBox<String> cmbTipeSesi;
    private JTextField txtWaktu, txtDurasi, txtCatatan;

    public JadwalFrame() {
        setTitle("PRC - Jadwal Terapi");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        muatDataTabel();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 248, 252));

        JLabel lblJudul = new JLabel("📅 Penjadwalan Sesi Terapi");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblJudul.setForeground(new Color(0, 153, 76));

        // === FORM ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Form Tambah Jadwal Terapi"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.anchor = GridBagConstraints.WEST;

        // Isi combobox pasien
        cmbPasien = new JComboBox<>();
        for (Pasien p : DataStore.getInstance().getDaftarPasien()) {
            cmbPasien.addItem(p);
        }

        // Isi combobox terapis
        cmbTerapis = new JComboBox<>();
        for (Terapis t : DataStore.getInstance().getDaftarTerapis()) {
            cmbTerapis.addItem(t);
        }

        cmbTipeSesi = new JComboBox<>(new String[]{"TERAPI_RUTIN", "EVALUASI", "KONSULTASI"});
        txtWaktu = new JTextField("2026-06-15 09:00", 15);
        txtDurasi = new JTextField("60", 5);
        txtCatatan = new JTextField(20);

        // Baris 1
        g.gridx=0; g.gridy=0; formPanel.add(new JLabel("Pasien:"), g);
        g.gridx=1; g.gridwidth=2; formPanel.add(cmbPasien, g); g.gridwidth=1;
        g.gridx=3; formPanel.add(new JLabel("Terapis:"), g);
        g.gridx=4; g.gridwidth=2; formPanel.add(cmbTerapis, g); g.gridwidth=1;

        // Baris 2
        g.gridx=0; g.gridy=1; formPanel.add(new JLabel("Tipe Sesi:"), g);
        g.gridx=1; formPanel.add(cmbTipeSesi, g);
        g.gridx=2; formPanel.add(new JLabel("Waktu (YYYY-MM-DD HH:mm):"), g);
        g.gridx=3; formPanel.add(txtWaktu, g);
        g.gridx=4; formPanel.add(new JLabel("Durasi (menit):"), g);
        g.gridx=5; formPanel.add(txtDurasi, g);

        // Baris 3
        g.gridx=0; g.gridy=2; formPanel.add(new JLabel("Catatan:"), g);
        g.gridx=1; g.gridwidth=5; g.fill=GridBagConstraints.HORIZONTAL;
        formPanel.add(txtCatatan, g);
        g.gridwidth=1; g.fill=GridBagConstraints.NONE;

        // Tombol
        JButton btnSimpan = new JButton("💾 Simpan Jadwal");
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSimpan.setBackground(new Color(0, 153, 76));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setBorderPainted(false);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(e -> simpanJadwal());

        JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tombolPanel.setBackground(Color.WHITE);
        tombolPanel.add(btnSimpan);

        g.gridx=0; g.gridy=3; g.gridwidth=6; g.fill=GridBagConstraints.HORIZONTAL;
        formPanel.add(tombolPanel, g);

        // === TABEL ===
        String[] kolom = {"ID", "Pasien", "Terapis", "Waktu", "Tipe Sesi", "Durasi", "Status"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabel = new JTable(modelTabel);
        tabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabel.setRowHeight(24);
        tabel.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabel.getTableHeader().setBackground(new Color(0, 153, 76));
        tabel.getTableHeader().setForeground(Color.WHITE);
        tabel.setSelectionBackground(new Color(200, 255, 220));

        // Warnai baris berdasarkan status
        tabel.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                String status = (String) modelTabel.getValueAt(row, 6);
                if (!sel) {
                    switch (status) {
                        case "SELESAI": c.setBackground(new Color(230, 255, 230)); break;
                        case "BERLANGSUNG": c.setBackground(new Color(255, 255, 200)); break;
                        case "BATAL": c.setBackground(new Color(255, 230, 230)); break;
                        default: c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Daftar Jadwal Terapi"));

        mainPanel.add(lblJudul, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        scrollPane.setPreferredSize(new Dimension(900, 200));

        add(mainPanel);
    }

    private void muatDataTabel() {
        modelTabel.setRowCount(0);
        for (JadwalTerapi j : DataStore.getInstance().getDaftarJadwal()) {
            modelTabel.addRow(new Object[]{
                j.getIdJadwal(),
                j.getPasien().getNamaLengkap(),
                j.getTerapis().getNamaLengkap(),
                j.getTanggalWaktu(),
                j.getTipeSesi(),
                j.getDurasiMenit() + " menit",
                j.getStatus()
            });
        }
    }

    private void simpanJadwal() {
        Pasien pasien = (Pasien) cmbPasien.getSelectedItem();
        Terapis terapis = (Terapis) cmbTerapis.getSelectedItem();
        String waktu = txtWaktu.getText().trim();
        String tipe = (String) cmbTipeSesi.getSelectedItem();
        String catatan = txtCatatan.getText().trim();

        int durasi;
        try {
            durasi = Integer.parseInt(txtDurasi.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Durasi harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pasien == null || terapis == null || waktu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pasien, Terapis, dan Waktu wajib diisi!");
            return;
        }

        DataStore.getInstance().tambahJadwal(pasien, terapis, waktu, tipe, durasi, catatan);
        muatDataTabel();
        txtCatatan.setText("");
        JOptionPane.showMessageDialog(this,
                "✓ Jadwal terapi untuk " + pasien.getNamaLengkap() + " berhasil dibuat!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }
}
