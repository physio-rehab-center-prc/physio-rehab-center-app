package view.panel;

import controller.*;
import model.*;
import util.UIHelper;
import view.AdminFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TerapisPanel extends JPanel {
    private TerapisController terapisCtrl;
    private SesiController sesiCtrl;
    private AdminFrame frame;

    public TerapisPanel(TerapisController tc, SesiController sc, AdminFrame frame) {
        this.terapisCtrl=tc; this.sesiCtrl=sc; this.frame=frame;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18,22,18,22));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        toolbar.setBackground(UIHelper.BG);
        toolbar.setBorder(new EmptyBorder(0,0,14,0));
        JButton btnAdd = UIHelper.createButtonPrimary("+ Tambah Terapis");
        toolbar.add(btnAdd);
        content.add(toolbar, BorderLayout.NORTH);

        // Grid kartu terapis
        List<Terapis> list = terapisCtrl.getAllTerapis();
        JPanel grid = new JPanel(new GridLayout(0, 3, 14, 14));
        grid.setBackground(UIHelper.BG);
        Color[] headerColors = {UIHelper.TEAL_DARK, UIHelper.BLUE, UIHelper.PURPLE};
        Color[] valColors = {UIHelper.TEAL, UIHelper.BLUE, UIHelper.PURPLE};
        for (int i = 0; i < list.size(); i++) {
            grid.add(buildTerapisCard(list.get(i), headerColors[i % 3], valColors[i % 3]));
        }
        content.add(new JScrollPane(grid), BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JPanel buildTerapisCard(Terapis t, Color headerColor, Color valColor) {
        JPanel card = UIHelper.createCard();
        card.setLayout(new BorderLayout());
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Header berwarna
        JPanel header = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2=(Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp=new GradientPaint(0,0,headerColor,getWidth(),getHeight(),headerColor.brighter());
                g2.setPaint(gp); g2.fillRect(0,0,getWidth(),getHeight());
            }
        };
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setPreferredSize(new Dimension(0, 110));
        header.setBorder(new EmptyBorder(14,0,14,0));

        JLabel av = UIHelper.createAvatar(UIHelper.getInisial(t.getNama()), new Color(255,255,255,100), 52);
        av.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,80), 2, true));
        av.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nama = UIHelper.label(t.getNama(), UIHelper.FONT_BOLD_13, Color.WHITE);
        nama.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel spesialis = UIHelper.label(t.getSpesialisasi(), UIHelper.FONT_REG_11, new Color(255,255,255,200));
        spesialis.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(av); header.add(Box.createVerticalStrut(8)); header.add(nama); header.add(spesialis);
        card.add(header, BorderLayout.NORTH);

        // Body
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIHelper.SURFACE);
        body.setBorder(new EmptyBorder(12,14,14,14));

        // Stats
        JPanel stats = new JPanel(new GridLayout(1,3,4,0));
        stats.setBackground(UIHelper.SURFACE);
        stats.add(buildStatMini("Pasien", "18", valColor));
        stats.add(buildStatMini("Rating", String.format("%.1f★",t.getRating()), valColor));
        stats.add(buildStatMini("Tahun", t.getPengalamanTahun()+"th", valColor));
        body.add(stats);
        body.add(Box.createVerticalStrut(10));

        // Status
        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT,4,0));
        statusRow.setBackground(UIHelper.SURFACE);
        statusRow.add(UIHelper.label("Status:", UIHelper.FONT_BOLD_11, UIHelper.TEXT3));
        Color stColor; Color stBg;
        switch(t.getStatus()){
            case TERSEDIA: stColor=UIHelper.GREEN; stBg=UIHelper.GREEN_LIGHT; break;
            case SIBUK: stColor=UIHelper.TEAL_DARK; stBg=UIHelper.TEAL_LIGHT; break;
            case ISTIRAHAT: stColor=UIHelper.AMBER; stBg=UIHelper.AMBER_LIGHT; break;
            default: stColor=UIHelper.CORAL; stBg=UIHelper.CORAL_LIGHT;
        }
        statusRow.add(UIHelper.createBadge(t.getStatusLabel(), stBg, stColor));
        body.add(statusRow);
        body.add(Box.createVerticalStrut(6));

        // Sertifikasi
        JPanel certRow = new JPanel(new FlowLayout(FlowLayout.LEFT,4,2));
        certRow.setBackground(UIHelper.SURFACE);
        certRow.add(UIHelper.badgeTeal("STR Aktif"));
        for (String cert : t.getSertifikasi()) {
            if (certRow.getComponentCount() < 3)
                certRow.add(UIHelper.badgeBlue(cert.length() > 12 ? cert.substring(0,10)+".." : cert));
        }
        body.add(certRow);

        // Detail button
        JButton detail = UIHelper.createButtonOutline("Lihat Detail");
        detail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        detail.setAlignmentX(Component.LEFT_ALIGNMENT);
        detail.addActionListener(e -> showDetail(t));
        body.add(Box.createVerticalStrut(8)); body.add(detail);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildStatMini(String label, String value, Color color) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(UIHelper.SURFACE);
        JLabel val = UIHelper.label(value, UIHelper.FONT_BOLD_14, color);
        val.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lbl = UIHelper.label(label, UIHelper.FONT_REG_11, UIHelper.TEXT3);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(val); p.add(lbl);
        return p;
    }

    private void showDetail(Terapis t) {
        JDialog dlg = new JDialog((Frame)SwingUtilities.getWindowAncestor(this),"Detail: "+t.getNama(),true);
        dlg.setSize(460,360); dlg.setLocationRelativeTo(this);
        JPanel panel = new JPanel(); panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(UIHelper.SURFACE); panel.setBorder(new EmptyBorder(18,22,18,22));
        String[][] info={{"Nama",t.getNama()},{"Email",t.getEmail()},{"No HP",t.getNomorHP()},
            {"Spesialisasi",t.getSpesialisasi()},{"No STR",t.getNomorSTR()},{"No SIP",t.getNomorSIP()},
            {"Jam Kerja",t.getJamKerjaStart()+" - "+t.getJamKerjaEnd()},{"Rating",t.getRating()+"⭐"},
            {"Pengalaman",t.getPengalamanTahun()+" tahun"},{"Status",t.getStatusLabel()}};
        for(String[] row:info){
            JPanel r=new JPanel(new BorderLayout(10,0)); r.setBackground(UIHelper.SURFACE);
            r.setMaximumSize(new Dimension(Integer.MAX_VALUE,26));
            JLabel k=UIHelper.label(row[0]+":",UIHelper.FONT_BOLD_12,UIHelper.TEXT2);
            k.setPreferredSize(new Dimension(120,18));
            r.add(k,BorderLayout.WEST); r.add(UIHelper.label(row[1],UIHelper.FONT_REG_12,UIHelper.TEXT),BorderLayout.CENTER);
            panel.add(r); panel.add(Box.createVerticalStrut(4));
        }
        JButton close=UIHelper.createButtonPrimary("Tutup"); close.addActionListener(e->dlg.dispose());
        JPanel br=new JPanel(new FlowLayout(FlowLayout.RIGHT)); br.setBackground(UIHelper.SURFACE); br.add(close);
        panel.add(Box.createVerticalStrut(10)); panel.add(br);
        dlg.add(panel); dlg.setVisible(true);
    }
}
