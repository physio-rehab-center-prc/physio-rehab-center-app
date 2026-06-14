package panel;

import controller.*;
import model.*;
import util.UIHelper;
import view.PasienFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


public class HomePasienPanel extends JPanel {
    private Pasien pasien;
    private SesiController sesiCtrl;
    private TagihanController tagihanCtrl;
    private PasienFrame frame;

    public HomePasienPanel(Pasien pasien, SesiController sc, TagihanController tc, PasienFrame frame) {
        this.pasien = pasien;
        this.sesiCtrl = sc;
        this.tagihanCtrl = tc;
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

        content.add(buildWelcomeBanner());
        content.add(Box.createVerticalStrut(16));
        content.add(buildStatsRow());
        content.add(Box.createVerticalStrut(16));
        content.add(buildMainContent());

        add(UIHelper.createScrollPane(content), BorderLayout.CENTER);
    }

    private JPanel buildWelcomeBanner() {
        JPanel banner = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, UIHelper.TEAL_DARK, getWidth(), getHeight(), UIHelper.TEAL);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                // Dekorasi lingkaran
                g2.setColor(new Color(255,255,255,20));
                g2.fillOval(getWidth()-120, -40, 160, 160);
                g2.fillOval(getWidth()-60, getHeight()-60, 100, 100);
            }
        };
        banner.setLayout(new BorderLayout());
        banner.setBorder(new EmptyBorder(20, 24, 20, 24));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);

        JLabel greeting = UIHelper.label("👋 Halo, " + pasien.getNama().split(" ")[0] + "!",
            new Font("Segoe UI", Font.BOLD, 20), Color.WHITE);
        JLabel sub = UIHelper.label("Semangat dalam proses pemulihan Anda hari ini.",
            UIHelper.FONT_REG_13, new Color(255,255,255,200));
        JLabel diag = UIHelper.label("Diagnosis: " + pasien.getDiagnosis(),
            UIHelper.FONT_REG_12, new Color(255,255,255,160));
        greeting.setAlignmentX(Component.LEFT_ALIGNMENT);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        diag.setAlignmentX(Component.LEFT_ALIGNMENT);
        left.add(greeting);
        left.add(Box.createVerticalStrut(5));
        left.add(sub);
        left.add(Box.createVerticalStrut(4));
        left.add(diag);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);
        right.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JLabel progress = UIHelper.label(pasien.getProgresSesiPersen() + "%",
            new Font("Segoe UI", Font.BOLD, 32), Color.WHITE);
        JLabel progLabel = UIHelper.label("Progres Sesi",
            UIHelper.FONT_REG_12, new Color(255,255,255,180));
        JLabel sesiInfo = UIHelper.label(pasien.getTotalSesiSelesai() + " / " + pasien.getTotalSesiTarget() + " sesi",
            UIHelper.FONT_BOLD_12, new Color(255,255,255,200));
        progress.setAlignmentX(Component.CENTER_ALIGNMENT);
        progLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sesiInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        right.add(progress); right.add(progLabel); right.add(sesiInfo);

        banner.add(left, BorderLayout.CENTER);
        banner.add(right, BorderLayout.EAST);
        return banner;
    }

    private JPanel buildStatsRow() {
        JPanel row = new JPanel(new GridLayout(1, 4, 12, 0));
        row.setBackground(UIHelper.BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));

        List<Sesi> mySesi = sesiCtrl.getSesiByPasien(pasien.getId());
        long sesiSelesai = mySesi.stream().filter(s -> s.getStatus() == Sesi.StatusSesi.SELESAI).count();
        long sesiMendatang = mySesi.stream().filter(s -> s.getStatus() == Sesi.StatusSesi.TERJADWAL ||
            s.getStatus() == Sesi.StatusSesi.BERLANGSUNG || s.getStatus() == Sesi.StatusSesi.MENUNGGU).count();
        List<Tagihan> myTagihan = tagihanCtrl.getTagihanByPasien(pasien.getId());
        long tagihanBelumBayar = myTagihan.stream().filter(t ->
            t.getStatus() == Tagihan.StatusTagihan.MENUNGGU || t.getStatus() == Tagihan.StatusTagihan.JATUH_TEMPO).count();

        row.add(buildStatCard("✅", String.valueOf(sesiSelesai), "Sesi Selesai", UIHelper.GREEN_LIGHT, UIHelper.GREEN));
        row.add(buildStatCard("📅", String.valueOf(sesiMendatang), "Sesi Mendatang", UIHelper.BLUE_LIGHT, UIHelper.BLUE));
        row.add(buildStatCard("💳", String.valueOf(tagihanBelumBayar), "Tagihan Pending", UIHelper.AMBER_LIGHT, UIHelper.AMBER));
        row.add(buildStatCard("🩺", "Dr. Rina", "Terapis Anda", UIHelper.TEAL_LIGHT, UIHelper.TEAL));

        return row;
    }

    private JPanel buildStatCard(String icon, String value, String label, Color bg, Color fg) {
        JPanel card = UIHelper.createCard();
        card.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 14));

        JLabel ic = UIHelper.label(icon, new Font("Segoe UI Emoji", Font.PLAIN, 20), fg);
        ic.setOpaque(true); ic.setBackground(bg);
        ic.setPreferredSize(new Dimension(40, 40));
        ic.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIHelper.SURFACE);
        info.add(UIHelper.label(value, UIHelper.FONT_BOLD_16, UIHelper.TEXT));
        info.add(UIHelper.label(label, UIHelper.FONT_REG_11, UIHelper.TEXT3));

        card.add(ic); card.add(info);
        return card;
    }

    private JPanel buildMainContent() {
        JPanel row = new JPanel(new GridLayout(1, 2, 14, 0));
        row.setBackground(UIHelper.BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        row.add(buildProgresCard());
        row.add(buildSesiBerikutnya());
        return row;
    }

    private JPanel buildProgresCard() {
        JPanel card = UIHelper.createCard();
        card.setLayout(new BorderLayout());

        JPanel header = UIHelper.createCardHeader("📊  Progres Rehabilitasi Saya");
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER), new EmptyBorder(10,14,10,14)));
        card.add(header, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIHelper.SURFACE);
        body.setBorder(new EmptyBorder(14, 16, 16, 16));

        body.add(UIHelper.createProgressBar("Kekuatan Otot", pasien.getProgresKekuatan(), UIHelper.TEAL));
        body.add(Box.createVerticalStrut(10));
        body.add(UIHelper.createProgressBar("Fleksibilitas", pasien.getProgresFleksibilitas(), UIHelper.TEAL));
        body.add(Box.createVerticalStrut(10));
        body.add(UIHelper.createProgressBar("Pengurangan Nyeri (VAS)", pasien.getProgresNyeri(), UIHelper.TEAL));
        body.add(Box.createVerticalStrut(10));
        body.add(UIHelper.createProgressBar("Kemandirian Fungsional", pasien.getProgresKemandirian(), UIHelper.TEAL));
        body.add(Box.createVerticalStrut(14));

        // Tombol lihat detail
        JButton detail = UIHelper.createButtonOutline("Lihat Rekam Medis Lengkap");
        detail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        detail.setAlignmentX(Component.LEFT_ALIGNMENT);
        detail.addActionListener(e -> frame.showPage("rekam-saya"));
        body.add(detail);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildSesiBerikutnya() {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBackground(UIHelper.BG);

        // Sesi mendatang
        JPanel sesiCard = UIHelper.createCard();
        sesiCard.setLayout(new BorderLayout());
        JPanel sh = UIHelper.createCardHeader("📅  Sesi Mendatang");
        JButton lihatSemua = UIHelper.createButtonOutline("Lihat semua →");
        lihatSemua.addActionListener(e -> frame.showPage("jadwal-saya"));
        sh.add(lihatSemua, BorderLayout.EAST);
        sh.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER), new EmptyBorder(10,14,10,14)));
        sesiCard.add(sh, BorderLayout.NORTH);

        JPanel sList = new JPanel();
        sList.setLayout(new BoxLayout(sList, BoxLayout.Y_AXIS));
        sList.setBackground(UIHelper.SURFACE);
        sList.setBorder(new EmptyBorder(10, 12, 12, 12));

        List<Sesi> upcoming = sesiCtrl.getSesiByPasien(pasien.getId());
        boolean any = false;
        for (Sesi s : upcoming) {
            if (s.getStatus() == Sesi.StatusSesi.TERJADWAL || s.getStatus() == Sesi.StatusSesi.MENUNGGU) {
                sList.add(buildSesiMini(s));
                sList.add(Box.createVerticalStrut(6));
                any = true;
            }
        }
        if (!any) sList.add(UIHelper.label("  Tidak ada sesi mendatang.", UIHelper.FONT_REG_13, UIHelper.TEXT3));
        sesiCard.add(sList, BorderLayout.CENTER);
        col.add(sesiCard);
        col.add(Box.createVerticalStrut(14));

        // Tagihan terbaru
        JPanel tagCard = UIHelper.createCard();
        tagCard.setLayout(new BorderLayout());
        JPanel th = UIHelper.createCardHeader("💳  Tagihan Saya");
        JButton lihatTag = UIHelper.createButtonOutline("Semua →");
        lihatTag.addActionListener(e -> frame.showPage("tagihan-saya"));
        th.add(lihatTag, BorderLayout.EAST);
        th.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER), new EmptyBorder(10,14,10,14)));
        tagCard.add(th, BorderLayout.NORTH);

        JPanel tList = new JPanel();
        tList.setLayout(new BoxLayout(tList, BoxLayout.Y_AXIS));
        tList.setBackground(UIHelper.SURFACE);
        tList.setBorder(new EmptyBorder(10, 12, 12, 12));

        List<Tagihan> tagList = tagihanCtrl.getTagihanByPasien(pasien.getId());
        boolean anyTag = false;
        for (Tagihan t : tagList) {
            if (t.getStatus() != Tagihan.StatusTagihan.LUNAS) {
                JPanel trow = new JPanel(new BorderLayout(8,0));
                trow.setBackground(UIHelper.SURFACE);
                trow.setBorder(BorderFactory.createCompoundBorder(
                    new javax.swing.border.LineBorder(UIHelper.BORDER, 1, true), new EmptyBorder(8,10,8,10)));
                trow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
                JPanel info = new JPanel(); info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS)); info.setBackground(UIHelper.SURFACE);
                info.add(UIHelper.label(t.getNamaLayanan(), UIHelper.FONT_BOLD_12, UIHelper.TEXT));
                info.add(UIHelper.label(t.formatRupiah(), UIHelper.FONT_REG_11, UIHelper.TEXT3));
                JLabel stBadge = t.getStatus() == Tagihan.StatusTagihan.JATUH_TEMPO ?
                    UIHelper.badgeCoral("Jatuh Tempo") : UIHelper.badgeAmber("Menunggu");
                trow.add(info, BorderLayout.CENTER);
                trow.add(stBadge, BorderLayout.EAST);
                tList.add(trow); tList.add(Box.createVerticalStrut(5));
                anyTag = true;
            }
        }
        if (!anyTag) tList.add(UIHelper.label("  Semua tagihan telah dibayar ✓", UIHelper.FONT_REG_13, UIHelper.GREEN));
        tagCard.add(tList, BorderLayout.CENTER);
        col.add(tagCard);
        return col;
    }

    private JPanel buildSesiMini(Sesi s) {
        JPanel row = new JPanel(new BorderLayout(8,0));
        row.setBackground(UIHelper.SURFACE);
        row.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(UIHelper.BORDER,1,true), new EmptyBorder(8,10,8,10)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
        JPanel info = new JPanel(); info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS)); info.setBackground(UIHelper.SURFACE);
        info.add(UIHelper.label(s.getProgram() + " · Sesi ke-" + s.getSesiKe(), UIHelper.FONT_BOLD_12, UIHelper.TEXT));
        info.add(UIHelper.label(s.getTanggal() + "  " + s.getWaktuMulai() + "  |  " + s.getRuang(), UIHelper.FONT_REG_11, UIHelper.TEXT3));
        row.add(info, BorderLayout.CENTER);
        row.add(UIHelper.badgeBlue("Terjadwal"), BorderLayout.EAST);
        return row;
    }
}
