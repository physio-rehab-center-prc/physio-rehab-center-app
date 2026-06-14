
package view.panel;


import controller.*;
import model.Terapis;
import util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AnalitikPanel extends JPanel {
    private SesiController sesiCtrl;
    private PasienController pasienCtrl;
    private TerapisController terapisCtrl;
    private TagihanController tagihanCtrl;

    public AnalitikPanel(SesiController sc, PasienController pc, TerapisController tc, TagihanController tgc) {
        this.sesiCtrl = sc; this.pasienCtrl = pc;
        this.terapisCtrl = tc; this.tagihanCtrl = tgc;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18, 22, 18, 22));

        content.add(buildTopStats());
        content.add(Box.createVerticalStrut(14));
        content.add(buildCharts());
        content.add(Box.createVerticalStrut(14));
        content.add(buildTerapisPerforma());

        add(UIHelper.createScrollPane(content), BorderLayout.CENTER);
    }

    private JPanel buildTopStats() {
        JPanel row = new JPanel(new GridLayout(1, 3, 12, 0));
        row.setBackground(UIHelper.BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        row.add(buildAnCard("142", "Total Sesi Bulan Ini", "▲ 18 vs bulan lalu"));
        row.add(buildAnCard("6.4", "Rata-rata Sesi/Hari", "Weekday saja"));
        row.add(buildAnCard("4.8 ⭐", "Kepuasan Pasien", "Dari 38 ulasan"));
        return row;
    }

    private JPanel buildAnCard(String val, String lbl, String sub) {
        JPanel c = UIHelper.createCardWithPadding(16);
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.add(UIHelper.label(lbl, UIHelper.FONT_REG_11, UIHelper.TEXT3));
        c.add(Box.createVerticalStrut(6));
        c.add(UIHelper.label(val, UIHelper.FONT_BOLD_20, UIHelper.TEXT));
        c.add(UIHelper.label(sub, UIHelper.FONT_REG_11, UIHelper.TEXT3));
        return c;
    }

    private JPanel buildCharts() {
        JPanel row = new JPanel(new GridLayout(1, 2, 14, 0));
        row.setBackground(UIHelper.BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        // Distribusi diagnosis
        JPanel distCard = UIHelper.createCard();
        distCard.setLayout(new BorderLayout());
        JPanel dh = UIHelper.createCardHeader("Distribusi Diagnosis");
        dh.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIHelper.BORDER),
            new EmptyBorder(10, 14, 10, 14)));
        distCard.add(dh, BorderLayout.NORTH);

        JPanel dBody = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        dBody.setBackground(UIHelper.SURFACE);
        dBody.add(buildDonut());

        JPanel legend = new JPanel();
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
        legend.setBackground(UIHelper.SURFACE);
        String[][] diag = {
            {"Low Back Pain", "35%", String.valueOf(UIHelper.TEAL.getRGB())},
            {"Knee Rehab",    "21%", String.valueOf(UIHelper.BLUE.getRGB())},
            {"Stroke Rehab",  "15%", String.valueOf(UIHelper.PURPLE.getRGB())},
            {"Sports Injury", "11%", String.valueOf(UIHelper.AMBER.getRGB())}
        };
        for (String[] d : diag) {
            JPanel li = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
            li.setBackground(UIHelper.SURFACE);
            JLabel dot = new JLabel("●");
            dot.setForeground(new Color(Integer.parseInt(d[2])));
            dot.setFont(UIHelper.FONT_BOLD_12);
            li.add(dot);
            li.add(UIHelper.label(d[0] + " — " + d[1], UIHelper.FONT_REG_12, UIHelper.TEXT2));
            legend.add(li);
        }
        dBody.add(legend);
        distCard.add(dBody, BorderLayout.CENTER);
        row.add(distCard);

        // Grafik batang
        JPanel chartCard = UIHelper.createCard();
        chartCard.setLayout(new BorderLayout());
        JPanel ch2 = UIHelper.createCardHeader("Pendapatan 6 Bulan Terakhir");
        ch2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIHelper.BORDER),
            new EmptyBorder(10, 14, 10, 14)));
        chartCard.add(ch2, BorderLayout.NORTH);
        chartCard.add(buildBarChart(), BorderLayout.CENTER);
        row.add(chartCard);

        return row;
    }

    private JPanel buildDonut() {
        return new JPanel() {
            { setBackground(UIHelper.SURFACE); setPreferredSize(new Dimension(110, 110)); }
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int[] degs = {126, 76, 54, 40};
                Color[] cols = {UIHelper.TEAL, UIHelper.BLUE, UIHelper.PURPLE, UIHelper.AMBER};
                int start = 0;
                for (int i = 0; i < degs.length; i++) {
                    g2.setColor(cols[i]);
                    g2.fillArc(5, 5, 100, 100, start, degs[i]);
                    start += degs[i];
                }
                g2.setColor(UIHelper.SURFACE);
                g2.fillOval(28, 28, 54, 54);
                g2.setFont(UIHelper.FONT_BOLD_11);
                g2.setColor(UIHelper.TEXT);
                g2.drawString("48 Px", 32, 59);
            }
        };
    }

    private JPanel buildBarChart() {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                String[] months = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun"};
                int[] vals = {60, 70, 55, 80, 85, 90};
                int bw = 30, gap = 12, startX = 20, botY = getHeight() - 30;
                for (int i = 0; i < vals.length; i++) {
                    int h = (int) (vals[i] / 100.0 * (getHeight() - 50));
                    int x = startX + i * (bw + gap);
                    g2.setColor(i == vals.length - 1 ? UIHelper.BLUE : UIHelper.TEAL);
                    g2.fillRoundRect(x, botY - h, bw, h, 6, 6);
                    g2.setColor(UIHelper.TEXT3);
                    g2.setFont(UIHelper.FONT_REG_11);
                    g2.drawString(months[i], x + 3, botY + 16);
                }
            }
        };
        panel.setBackground(UIHelper.SURFACE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JPanel buildTerapisPerforma() {
        JPanel card = UIHelper.createCard();
        card.setLayout(new BorderLayout());
        JPanel h = UIHelper.createCardHeader("Performa Terapis Bulan Ini");
        h.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIHelper.BORDER),
            new EmptyBorder(10, 14, 10, 14)));
        card.add(h, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIHelper.SURFACE);
        body.setBorder(new EmptyBorder(12, 16, 16, 16));

        List<Terapis> list = terapisCtrl.getAllTerapis();
        String[] sesiCount = {"42 sesi", "55 sesi", "35 sesi"};
        int[] pct = {76, 100, 64};
        for (int i = 0; i < Math.min(list.size(), 3); i++) {
            body.add(UIHelper.createProgressBar(list.get(i).getNama() + " — " + sesiCount[i], pct[i], UIHelper.TEAL));
            body.add(Box.createVerticalStrut(8));
        }
        card.add(body, BorderLayout.CENTER);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return card;
    }
}
