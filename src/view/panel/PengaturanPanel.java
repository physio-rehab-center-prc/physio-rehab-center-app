package view.panel;

import model.User;
import util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PengaturanPanel extends JPanel {
    private User currentUser;

    public PengaturanPanel(User u) {
        this.currentUser = u;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18, 22, 18, 22));

        JPanel grid = new JPanel(new GridLayout(1, 2, 14, 0));
        grid.setBackground(UIHelper.BG);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(UIHelper.BG);
        left.add(buildSection("🏥 Klinik", new String[][]{
            {"Nama Klinik",    "PhysioHub Malang"},
            {"Jam Operasional","07:00 – 20:00"},
            {"Kota",           "Malang, Jawa Timur"}
        }));
        left.add(Box.createVerticalStrut(12));
        left.add(buildToggleSection("🔔 Notifikasi", new String[][]{
            {"Pengingat Sesi H-1",  "Kirim WhatsApp ke pasien", "true"},
            {"Notifikasi Tagihan",  "Email saat jatuh tempo",   "true"},
            {"Laporan Mingguan",    "Email setiap Senin",       "false"}
        }));

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(UIHelper.BG);
        right.add(buildSection("💰 Tarif Layanan", new String[][]{
            {"Fisioterapi Umum",  "Rp 450.000 / sesi"},
            {"Stroke Rehab",      "Rp 750.000 / sesi"},
            {"Paket 10 Sesi",     "Rp 3.800.000 (diskon 15%)"},
            {"Konsultasi",        "Rp 150.000"}
        }));
        right.add(Box.createVerticalStrut(12));
        right.add(buildToggleSection("🔒 Keamanan", new String[][]{
            {"Autentikasi 2 Faktor", "OTP via WhatsApp", "true"},
            {"Backup Otomatis",      "Setiap malam",     "true"}
        }));

        grid.add(left);
        grid.add(right);
        content.add(grid);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRow.setBackground(UIHelper.BG);
        JButton save = UIHelper.createButtonPrimary("💾  Simpan Perubahan");
        save.addActionListener(e -> UIHelper.showSuccess(this, "Pengaturan berhasil disimpan!"));
        btnRow.add(save);
        content.add(Box.createVerticalStrut(12));
        content.add(btnRow);

        add(UIHelper.createScrollPane(content), BorderLayout.CENTER);
    }

    private JPanel buildSection(String title, String[][] rows) {
        JPanel card = UIHelper.createCard();
        card.setLayout(new BorderLayout());
        JPanel h = UIHelper.createCardHeader(title);
        h.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIHelper.BORDER),
            new EmptyBorder(10, 14, 10, 14)));
        card.add(h, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIHelper.SURFACE);
        for (String[] row : rows) {
            JPanel r = new JPanel(new BorderLayout(10, 0));
            r.setBackground(UIHelper.SURFACE);
            r.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIHelper.BORDER),
                new EmptyBorder(10, 14, 10, 14)));
            r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
            r.add(UIHelper.label(row[0], UIHelper.FONT_BOLD_12, UIHelper.TEXT), BorderLayout.WEST);
            r.add(UIHelper.label(row[1], UIHelper.FONT_REG_12, UIHelper.TEXT3), BorderLayout.EAST);
            body.add(r);
        }
        card.add(body, BorderLayout.CENTER);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return card;
    }

    private JPanel buildToggleSection(String title, String[][] rows) {
        JPanel card = UIHelper.createCard();
        card.setLayout(new BorderLayout());
        JPanel h = UIHelper.createCardHeader(title);
        h.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIHelper.BORDER),
            new EmptyBorder(10, 14, 10, 14)));
        card.add(h, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIHelper.SURFACE);

        for (String[] row : rows) {
            JPanel r = new JPanel(new BorderLayout(10, 0));
            r.setBackground(UIHelper.SURFACE);
            r.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIHelper.BORDER),
                new EmptyBorder(10, 14, 10, 14)));
            r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setBackground(UIHelper.SURFACE);
            info.add(UIHelper.label(row[0], UIHelper.FONT_BOLD_12, UIHelper.TEXT));
            info.add(UIHelper.label(row[1], UIHelper.FONT_REG_11, UIHelper.TEXT3));

            boolean on = row[2].equals("true");
            JToggleButton tgl = new JToggleButton(on ? "ON" : "OFF", on);
            tgl.setFont(UIHelper.FONT_BOLD_11);
            tgl.setBackground(on ? UIHelper.TEAL : UIHelper.BORDER);
            tgl.setForeground(on ? Color.WHITE : UIHelper.TEXT3);
            tgl.setBorderPainted(false);
            tgl.setFocusPainted(false);
            tgl.setPreferredSize(new Dimension(54, 24));
            tgl.addActionListener(e -> {
                boolean s = tgl.isSelected();
                tgl.setText(s ? "ON" : "OFF");
                tgl.setBackground(s ? UIHelper.TEAL : UIHelper.BORDER);
                tgl.setForeground(s ? Color.WHITE : UIHelper.TEXT3);
            });
            r.add(info, BorderLayout.CENTER);
            r.add(tgl, BorderLayout.EAST);
            body.add(r);
        }
        card.add(body, BorderLayout.CENTER);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return card;
    }
}