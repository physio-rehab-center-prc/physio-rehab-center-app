/**
 * PhysioRehabCenter — Tugas Besar Pemrograman Berorientasi Objek
 * Anggota Kelompok: Rahma | Pebi | Eka | Risma
 */
package prc.view;

import prc.controller.DataStore;
import prc.model.Pasien;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class PasienFrame extends JFrame {

    private JTable tabel;
    private DefaultTableModel modelTabel;
    private JTextField txtNik, txtNama, txtTglLahir, txtAlamat, txtTelp, txtEmail, txtAlergi;
    private JComboBox<String> cmbJK, cmbGolDarah;

    public PasienFrame() {
        setTitle("PRC - Manajemen Pasien");
        setSize(900, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        muatDataTabel();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 248, 252));

        // === JUDUL ===
        JLabel lblJudul = new JLabel("👥 Manajemen Pasien");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblJudul.setForeground(new Color(0, 102, 153));

        // === FORM INPUT ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Form Tambah Pasien Baru"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.anchor = GridBagConstraints.WEST;

        txtNik = new JTextField(15);
        txtNama = new JTextField(15);
        txtTglLahir = new JTextField(10);
        txtAlamat = new JTextField(20);
        txtTelp = new JTextField(13);
        txtEmail = new JTextField(15);
        txtAlergi = new JTextField(15);
        cmbJK = new JComboBox<>(new String[]{"L", "P"});
        cmbGolDarah = new JComboBox<>(new String[]{"A", "B", "AB", "O"});

        // Baris 1
        g.gridx=0; g.gridy=0; formPanel.add(new JLabel("NIK:"), g);
        g.gridx=1; formPanel.add(txtNik, g);
        g.gridx=2; formPanel.add(new JLabel("Nama Lengkap:"), g);
        g.gridx=3; formPanel.add(txtNama, g);
        g.gridx=4; formPanel.add(new JLabel("Jenis Kelamin:"), g);
        g.gridx=5; formPanel.add(cmbJK, g);

        // Baris 2
        g.gridx=0; g.gridy=1; formPanel.add(new JLabel("Tanggal Lahir:"), g);
        g.gridx=1; formPanel.add(txtTglLahir, g);
        JLabel hint = new JLabel("(Format: YYYY-MM-DD)");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        hint.setForeground(Color.GRAY);
        g.gridx=2; formPanel.add(hint, g);
        g.gridx=3; formPanel.add(new JLabel("Gol. Darah:"), g);
        g.gridx=4; formPanel.add(cmbGolDarah, g);

        // Baris 3
        g.gridx=0; g.gridy=2; formPanel.add(new JLabel("No. Telepon:"), g);
        g.gridx=1; formPanel.add(txtTelp, g);
        g.gridx=2; formPanel.add(new JLabel("Email:"), g);
        g.gridx=3; g.gridwidth=2; formPanel.add(txtEmail, g); g.gridwidth=1;

        // Baris 4
        g.gridx=0; g.gridy=3; formPanel.add(new JLabel("Alamat:"), g);
        g.gridx=1; g.gridwidth=3; formPanel.add(txtAlamat, g); g.gridwidth=1;
        g.gridx=4; formPanel.add(new JLabel("Alergi:"), g);
        g.gridx=5; formPanel.add(txtAlergi, g);

        // Tombol
        JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tombolPanel.setBackground(Color.WHITE);

        JButton btnSimpan = new JButton("💾 Simpan Pasien");
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSimpan.setBackground(new Color(0, 102, 153));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setBorderPainted(false);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(e -> simpanPasien());

        JButton btnReset = new JButton("🔄 Reset Form");
        btnReset.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnReset.addActionListener(e -> resetForm());

        tombolPanel.add(btnReset);
        tombolPanel.add(btnSimpan);

        g.gridx=0; g.gridy=4; g.gridwidth=6; g.fill=GridBagConstraints.HORIZONTAL;
        formPanel.add(tombolPanel, g);

        // === TABEL ===
        String[] kolom = {"ID", "NIK", "Nama Lengkap", "Tgl. Lahir", "JK", "No. Telepon", "Email", "Gol. Darah"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabel = new JTable(modelTabel);
        tabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabel.setRowHeight(24);
        tabel.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabel.getTableHeader().setBackground(new Color(0, 102, 153));
        tabel.getTableHeader().setForeground(Color.WHITE);
        tabel.setSelectionBackground(new Color(200, 230, 255));

        JScrollPane scrollPane = new JScrollPane(tabel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Daftar Pasien Terdaftar"));

        // Tombol hapus
        JButton btnHapus = new JButton("🗑 Hapus Pasien Terpilih");
        btnHapus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnHapus.setForeground(Color.RED);
        btnHapus.addActionListener(e -> hapusPasien());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 248, 252));
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(btnHapus, BorderLayout.SOUTH);

        mainPanel.add(lblJudul, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(900, 220));

        add(mainPanel);
    }

    private void muatDataTabel() {
        modelTabel.setRowCount(0);
        List<Pasien> daftar = DataStore.getInstance().getDaftarPasien();
        for (Pasien p : daftar) {
            modelTabel.addRow(new Object[]{
                p.getIdPasien(), p.getNik(), p.getNamaLengkap(),
                p.getTanggalLahir(), p.getJenisKelamin(),
                p.getNoTelepon(), p.getEmail(), p.getGolonganDarah()
            });
        }
    }

    private void simpanPasien() {
        String nik = txtNik.getText().trim();
        String nama = txtNama.getText().trim();
        String tgl = txtTglLahir.getText().trim();
        String alamat = txtAlamat.getText().trim();
        String telp = txtTelp.getText().trim();
        String email = txtEmail.getText().trim();
        String alergi = txtAlergi.getText().trim();
        String jk = (String) cmbJK.getSelectedItem();
        String golDarah = (String) cmbGolDarah.getSelectedItem();

        if (nik.isEmpty() || nama.isEmpty() || tgl.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIK, Nama, dan Tanggal Lahir wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DataStore.getInstance().tambahPasien(nik, nama, tgl, jk, alamat, telp, email, golDarah, alergi);
        muatDataTabel();
        resetForm();
        JOptionPane.showMessageDialog(this, "✓ Pasien " + nama + " berhasil ditambahkan!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    private void hapusPasien() {
        int baris = tabel.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pasien yang ingin dihapus terlebih dahulu.");
            return;
        }
        int id = (int) modelTabel.getValueAt(baris, 0);
        String nama = (String) modelTabel.getValueAt(baris, 2);
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Hapus pasien " + nama + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            DataStore.getInstance().hapusPasien(id);
            muatDataTabel();
        }
    }

    private void resetForm() {
        txtNik.setText(""); txtNama.setText(""); txtTglLahir.setText("");
        txtAlamat.setText(""); txtTelp.setText(""); txtEmail.setText(""); txtAlergi.setText("");
        cmbJK.setSelectedIndex(0); cmbGolDarah.setSelectedIndex(0);
    }
}
