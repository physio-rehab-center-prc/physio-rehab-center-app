package view.panel;

import controller.*;
import model.*;
import util.UIHelper;
import view.AdminFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {
    private User currentUser;
    private SesiController sesiCtrl;
    private PasienController pasienCtrl;
    private AdminFrame frame;

    public DashboardPanel(User user, SesiController sc, PasienController pc, AdminFrame frame) {
        this.currentUser = user;
        this.sesiCtrl = sc;
        this.pasienCtrl = pc;
        this.frame = frame;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(20, 22, 20, 22));

        content.add(buildStatsRow());
        content.add(Box.createVerticalStrut(16));
        content.add(buildMiddleRow());

        add(UIHelper.createScrollPane(content), BorderLayout.CENTER);
    }

    private JPanel buildStatsRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 12, 0));
        row.setBackground(UIHelper.BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        List<Sesi> hariIni = sesiCtrl.getSesiHariIni();
        int totalPasien = pasienCtrl.getTotalPasienAktif();
        long selesai = hariIni.stream().filter(s -> s.getStatus() == Sesi.StatusSesi.SELESAI).count();
        int tingkatKehadiran = hariIni.isEmpty() ? 0 : (int)(selesai * 100 / Math.max(1, hariIni.size()));

        row.add(buildStatCard("🏃", String.valueOf(hariIni.size()), "Sesi Hari Ini", "▲ 2 dari kemarin", UIHelper.TEAL_LIGHT, UIHelper.GREEN));
        row.add(buildStatCard("👥", String.valueOf(totalPasien), "Pasien Aktif", "▲ 4 bulan ini", UIHelper.BLUE_LIGHT, UIHelper.GREEN));
        row.add(buildStatCard("✅", tingkatKehadiran + "%", "Tingkat Kehadiran", "▲ 5% bulan ini", UIHelper.GREEN_LIGHT, UIHelper.GREEN));
        row.add(buildStatCard("💰", "Rp 12,4M", "Pendapatan Bulan Ini", "▲ 18% bulan lalu", UIHelper.AMBER_LIGHT, UIHelper.GREEN));
        return row;
    }

    private JPanel buildStatCard(String icon, String value, String label, String change, Color iconBg, Color changeColor) {
        JPanel card = UIHelper.createCard();
        card.setLayout(new FlowLayout(FlowLayout.LEFT, 14, 14));

        JLabel ic = new JLabel(icon);
        ic.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        ic.setOpaque(true);
        ic.setBackground(iconBg);
        ic.setPreferredSize(new Dimension(44, 44));
        ic.setHorizontalAlignment(SwingConstants.CENTER);
        ic.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIHelper.SURFACE);

        JLabel val = UIHelper.label(value, UIHelper.FONT_BOLD_20, UIHelper.TEXT);
        JLabel lbl = UIHelper.label(label, UIHelper.FONT_REG_11, UIHelper.TEXT3);
        JLabel chg = UIHelper.label(change, UIHelper.FONT_BOLD_11, changeColor);
        info.add(val); info.add(lbl); info.add(chg);

        card.add(ic); card.add(info);
        return card;
    }

    private JPanel buildMiddleRow() {
        JPanel row = new JPanel(new GridLayout(1, 2, 14, 0));
        row.setBackground(UIHelper.BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        row.add(buildJadwalCard());
        row.add(buildRightColumn());
        return row;
    }

    private JPanel buildJadwalCard() {
        JPanel card = UIHelper.createCard();
        card.setLayout(new BorderLayout());

        JPanel header = UIHelper.createCardHeader("📅  Jadwal Hari Ini");
        JButton lihat = UIHelper.createButtonOutline("Lihat semua →");
        lihat.addActionListener(e -> frame.showPage("jadwal"));
        header.add(lihat, BorderLayout.EAST);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0, UIHelper.BORDER), new EmptyBorder(10,14,10,14)));
        card.add(header, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(UIHelper.SURFACE);
        list.setBorder(new EmptyBorder(8, 12, 12, 12));

        List<Sesi> sesiList = sesiCtrl.getSesiHariIni();
        if (sesiList.isEmpty()) {
            list.add(UIHelper.label("  Tidak ada sesi hari ini", UIHelper.FONT_REG_13, UIHelper.TEXT3));
        } else {
            for (Sesi s : sesiList) {
                Pasien p = pasienCtrl.getPasienById(s.getPasienId());
                String namaPasien = p != null ? p.getNama() : s.getPasienId();
                list.add(buildSesiRow(s.getWaktuMulai().toString(), namaPasien,
                    s.getProgram() + " · Sesi " + s.getSesiKe(), s.getStatusLabel()));
                list.add(Box.createVerticalStrut(4));
            }
        }
        card.add(list, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildSesiRow(String waktu, String nama, String sub, String statusLabel) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(UIHelper.SURFACE);
        row.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(UIHelper.BORDER, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel timeLbl = UIHelper.label(waktu, UIHelper.FONT_BOLD_12, UIHelper.TEAL_DARK);
        timeLbl.setPreferredSize(new Dimension(52, 20));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIHelper.SURFACE);
        info.add(UIHelper.label(nama, UIHelper.FONT_BOLD_13, UIHelper.TEXT));
        info.add(UIHelper.label(sub, UIHelper.FONT_REG_11, UIHelper.TEXT3));

        row.add(timeLbl, BorderLayout.WEST);
        row.add(info, BorderLayout.CENTER);
        row.add(UIHelper.statusBadgeSesi(statusLabel), BorderLayout.EAST);
        return row;
    }

    private JPanel buildRightColumn() {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(UIHelper.BG);

        // Pasien terbaru
        JPanel pasienCard = UIHelper.createCard();
        pasienCard.setLayout(new BorderLayout());

        JPanel ph = UIHelper.createCardHeader("👥  Pasien Terbaru");
        JButton lihatPasien = UIHelper.createButtonOutline("Semua →");
        lihatPasien.addActionListener(e -> frame.showPage("pasien"));
        ph.add(lihatPasien, BorderLayout.EAST);
        ph.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER), new EmptyBorder(10,14,10,14)));
        pasienCard.add(ph, BorderLayout.NORTH);

        JPanel plist = new JPanel();
        plist.setLayout(new BoxLayout(plist, BoxLayout.Y_AXIS));
        plist.setBackground(UIHelper.SURFACE);
        plist.setBorder(new EmptyBorder(8, 12, 12, 12));

        List<Pasien> pasienList = pasienCtrl.getAllPasien();
        int i = 0;
        for (Pasien p : pasienList) {
            if (i++ >= 5) break;
            plist.add(buildPasienRow(p, i-1));
            plist.add(Box.createVerticalStrut(3));
        }
        pasienCard.add(plist, BorderLayout.CENTER);
        col.add(pasienCard);
        col.add(Box.createVerticalStrut(14));

        // Status terapis ringkasan
        JPanel statCard = UIHelper.createCard();
        statCard.setLayout(new BorderLayout());
        JPanel sh = UIHelper.createCardHeader("🩺  Status Terapis");
        sh.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER), new EmptyBorder(10,14,10,14)));
        statCard.add(sh, BorderLayout.NORTH);

        JPanel slist = new JPanel();
        slist.setLayout(new BoxLayout(slist, BoxLayout.Y_AXIS));
        slist.setBackground(UIHelper.SURFACE);
        slist.setBorder(new EmptyBorder(8, 12, 12, 12));
        slist.add(buildStatusRow("Dr. Cut Eka", "Neurologi Rehab", "Sibuk", UIHelper.TEAL, UIHelper.TEAL_LIGHT));
        slist.add(Box.createVerticalStrut(4));
        slist.add(buildStatusRow("Dr. Frengki", "Muskuloskeletal", "Tersedia", UIHelper.GREEN, UIHelper.GREEN_LIGHT));
        slist.add(Box.createVerticalStrut(4));
        slist.add(buildStatusRow("Dr. Afrisca", "Sports Rehab", "Istirahat", UIHelper.AMBER, UIHelper.AMBER_LIGHT));
        statCard.add(slist, BorderLayout.CENTER);
        col.add(statCard);

        return col;
    }

    private JPanel buildPasienRow(Pasien p, int idx) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(UIHelper.SURFACE);
        row.setBorder(new EmptyBorder(5, 4, 5, 4));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JLabel av = UIHelper.createAvatar(UIHelper.getInisial(p.getNama()),
            UIHelper.getAvatarColor(idx), 30);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIHelper.SURFACE);
        info.setBorder(new EmptyBorder(0, 8, 0, 0));
        info.add(UIHelper.label(p.getNama(), UIHelper.FONT_BOLD_12, UIHelper.TEXT));
        info.add(UIHelper.label(p.getDiagnosis(), UIHelper.FONT_REG_11, UIHelper.TEXT3));

        row.add(av, BorderLayout.WEST);
        row.add(info, BorderLayout.CENTER);
        row.add(UIHelper.statusBadgePasien(p.getStatusLabel()), BorderLayout.EAST);
        return row;
    }

    private JPanel buildStatusRow(String nama, String spesialis, String status, Color fg, Color bg) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(UIHelper.SURFACE);
        row.setBorder(new EmptyBorder(5, 4, 5, 4));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JLabel av = UIHelper.createAvatar(UIHelper.getInisial(nama), fg, 28);
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIHelper.SURFACE);
        info.setBorder(new EmptyBorder(0, 8, 0, 0));
        info.add(UIHelper.label(nama, UIHelper.FONT_BOLD_12, UIHelper.TEXT));
        info.add(UIHelper.label(spesialis, UIHelper.FONT_REG_11, UIHelper.TEXT3));

        row.add(av, BorderLayout.WEST);
        row.add(info, BorderLayout.CENTER);
        row.add(UIHelper.createBadge(status, bg, fg), BorderLayout.EAST);
        return row;
    }
}
