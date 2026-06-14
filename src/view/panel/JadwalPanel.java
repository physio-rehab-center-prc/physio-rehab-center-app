package view.panel;

import controller.*;
import model.*;
import util.UIHelper;
import view.AdminFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class JadwalPanel extends JPanel {
    private SesiController sesiCtrl;
    private PasienController pasienCtrl;
    private TerapisController terapisCtrl;
    private User currentUser;
    private AdminFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public JadwalPanel(SesiController sc, PasienController pc, TerapisController tc, User user, AdminFrame frame) {
        this.sesiCtrl = sc; this.pasienCtrl = pc; this.terapisCtrl = tc;
        this.currentUser = user; this.frame = frame;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18, 22, 18, 22));

        content.add(buildToolbar(), BorderLayout.NORTH);
        content.add(buildTable(), BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JPanel buildToolbar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        bar.setBackground(UIHelper.BG);
        bar.setBorder(new EmptyBorder(0, 0, 14, 0));

        JComboBox<String> filterStatus = UIHelper.createComboBox(new String[]{
            "Semua Status","Terjadwal","Berlangsung","Selesai","Dibatalkan"});
        filterStatus.addActionListener(e -> refreshTable());

        JButton btnBaru = UIHelper.createButtonPrimary("+ Sesi Baru");
        btnBaru.addActionListener(e -> showFormSesi());

        bar.add(UIHelper.label("Filter:", UIHelper.FONT_BOLD_12, UIHelper.TEXT2));
        bar.add(filterStatus);
        bar.add(Box.createHorizontalStrut(10));
        bar.add(btnBaru);
        return bar;
    }

    private JScrollPane buildTable() {
        String[] cols = {"Waktu","Pasien","Terapis","Program","Sesi ke-","Durasi","Status","Aksi"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return c == 7; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(38);
        table.setFont(UIHelper.FONT_REG_13);
        table.setForeground(UIHelper.TEXT2);
        table.setBackground(UIHelper.SURFACE);
        table.setGridColor(UIHelper.BORDER);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(UIHelper.TEAL_LIGHT);
        table.getTableHeader().setFont(UIHelper.FONT_BOLD_11);
        table.getTableHeader().setForeground(UIHelper.TEXT3);
        table.getTableHeader().setBackground(UIHelper.BG);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER));

        // Lebar kolom
        int[] widths = {110,140,130,150,70,80,100,90};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        // Renderer badge status
        table.getColumnModel().getColumn(6).setCellRenderer((t, val, sel, foc, r, c) -> {
            String status = val != null ? val.toString() : "";
            return UIHelper.statusBadgeSesi(status);
        });

        // Tombol aksi
        table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(this));

        refreshTable();

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(UIHelper.BORDER, 1));
        sp.getViewport().setBackground(UIHelper.SURFACE);
        return sp;
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Sesi> list = sesiCtrl.getAllSesi();
        for (Sesi s : list) {
            Pasien p = pasienCtrl.getPasienById(s.getPasienId());
            Terapis t = terapisCtrl.getTerapisById(s.getTerapisId());
            tableModel.addRow(new Object[]{
                s.getTanggal() + " " + s.getWaktuMulai(),
                p != null ? p.getNama() : s.getPasienId(),
                t != null ? t.getNama() : s.getTerapisId(),
                s.getProgram(), s.getSesiKe(),
                s.getDurasiMenit() + " mnt",
                s.getStatusLabel(), s.getId()
            });
        }
    }

    public void showFormSesi() {
        JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Buat Sesi Baru", true);
        dlg.setSize(500, 460);
        dlg.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIHelper.SURFACE);
        panel.setBorder(new EmptyBorder(20, 24, 20, 24));

        // Pasien
        List<Pasien> pasienList = pasienCtrl.getAllPasien();
        String[] namaPasien = pasienList.stream().map(p -> p.getNama() + " [" + p.getId() + "]").toArray(String[]::new);
        JComboBox<String> cbPasien = UIHelper.createComboBox(namaPasien);

        // Terapis
        List<Terapis> terapisList = terapisCtrl.getAllTerapis();
        String[] namaTerapis = terapisList.stream().map(t -> t.getNama() + " [" + t.getId() + "]").toArray(String[]::new);
        JComboBox<String> cbTerapis = UIHelper.createComboBox(namaTerapis);

        JTextField tfTanggal = UIHelper.createTextField("YYYY-MM-DD");
        tfTanggal.setText(LocalDate.now().toString());
        JTextField tfWaktu = UIHelper.createTextField("HH:MM");
        tfWaktu.setText("09:00");
        JComboBox<String> cbDurasi = UIHelper.createComboBox(new String[]{"30 menit","60 menit","90 menit","120 menit"});
        cbDurasi.setSelectedIndex(1);
        JComboBox<String> cbRuang = UIHelper.createComboBox(new String[]{"Ruang 1","Ruang 2","Ruang 3","Ruang 4"});
        JComboBox<String> cbProgram = UIHelper.createComboBox(new String[]{
            "Low Back Pain","Rehab Lutut","Stroke Rehab","Neck Pain","Sports Injury","Frozen Shoulder"});

        String[][] fields = {{"Pasien",null},{"Terapis",null},{"Tanggal",null},
            {"Waktu",null},{"Durasi",null},{"Ruang",null},{"Program",null}};
        JComponent[] inputs = {cbPasien, cbTerapis, tfTanggal, tfWaktu, cbDurasi, cbRuang, cbProgram};

        for (int i = 0; i < inputs.length; i++) {
            JPanel row = new JPanel(new BorderLayout(8, 0));
            row.setBackground(UIHelper.SURFACE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            JLabel lbl = UIHelper.label(fields[i][0], UIHelper.FONT_BOLD_12, UIHelper.TEXT2);
            lbl.setPreferredSize(new Dimension(80, 20));
            inputs[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
            row.add(lbl, BorderLayout.WEST);
            row.add(inputs[i], BorderLayout.CENTER);
            panel.add(row);
            panel.add(Box.createVerticalStrut(10));
        }

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.setBackground(UIHelper.SURFACE);
        JButton cancel = UIHelper.createButtonOutline("Batal");
        JButton save = UIHelper.createButtonPrimary("✓  Buat Sesi");
        cancel.addActionListener(e -> dlg.dispose());
        save.addActionListener(e -> {
            try {
                int pIdx = cbPasien.getSelectedIndex();
                int tIdx = cbTerapis.getSelectedIndex();
                String pasienId = pasienList.get(pIdx).getId();
                String terapisId = terapisList.get(tIdx).getId();
                LocalDate tgl = LocalDate.parse(tfTanggal.getText().trim());
                LocalTime jam = LocalTime.parse(tfWaktu.getText().trim());
                int dur = Integer.parseInt(((String)cbDurasi.getSelectedItem()).replace(" menit","").trim());
                String ruang = (String) cbRuang.getSelectedItem();
                String prog = (String) cbProgram.getSelectedItem();
                sesiCtrl.buatSesi(pasienId, terapisId, tgl, jam, dur, ruang, prog);
                refreshTable();
                dlg.dispose();
                UIHelper.showSuccess(this, "Sesi berhasil dibuat!");
            } catch (Exception ex) {
                UIHelper.showError(this, "Format salah: " + ex.getMessage());
            }
        });
        btnRow.add(cancel); btnRow.add(save);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnRow);

        dlg.add(panel);
        dlg.setVisible(true);
    }

    public void aksiSesi(String sesiId) {
        Sesi s = sesiCtrl.getAllSesi().stream().filter(x -> x.getId().equals(sesiId)).findFirst().orElse(null);
        if (s == null) return;
        switch (s.getStatus()) {
            case TERJADWAL: case MENUNGGU:
                if (UIHelper.showConfirm(this, "Mulai sesi " + sesiId + "?")) {
                    sesiCtrl.mulaiSesi(sesiId); refreshTable();
                }
                break;
            case BERLANGSUNG:
                String soap = JOptionPane.showInputDialog(this, "Masukkan catatan SOAP:", "Selesaikan Sesi", JOptionPane.PLAIN_MESSAGE);
                if (soap != null) { sesiCtrl.selesaikanSesi(sesiId, soap); refreshTable(); UIHelper.showSuccess(this, "Sesi selesai!"); }
                break;
            case SELESAI:
                UIHelper.showSuccess(this, "Sesi sudah selesai. Lihat rekam medis untuk catatan SOAP.");
                break;
            default:
                UIHelper.showError(this, "Sesi sudah dibatalkan.");
        }
    }

    // Button Renderer & Editor
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
            setText("Aksi");
            setFont(UIHelper.FONT_BOLD_11);
            setBackground(UIHelper.TEAL_LIGHT);
            setForeground(UIHelper.TEAL_DARK);
            setBorder(new EmptyBorder(4, 8, 4, 8));
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
        private JButton btn;
        private JadwalPanel parent;
        private String sesiId;
        public ButtonEditor(JadwalPanel parent) {
            super(new JCheckBox());
            this.parent = parent;
            btn = new JButton("Aksi");
            btn.setFont(UIHelper.FONT_BOLD_11);
            btn.setBackground(UIHelper.TEAL_LIGHT);
            btn.setForeground(UIHelper.TEAL_DARK);
            btn.setBorder(new EmptyBorder(4, 8, 4, 8));
            btn.addActionListener(e -> { parent.aksiSesi(sesiId); fireEditingStopped(); });
        }
        public Component getTableCellEditorComponent(JTable t, Object v, boolean sel, int r, int c) {
            sesiId = v != null ? v.toString() : "";
            return btn;
        }
        public Object getCellEditorValue() { return sesiId; }
    }
}
