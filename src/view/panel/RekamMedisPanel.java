package view.panel;

import controller.*;
import data.DataStore;
import model.*;
import util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

// ===================== REKAM MEDIS PANEL =====================
public class RekamMedisPanel extends JPanel {
    PasienController pasienCtrl; SesiController sesiCtrl; User currentUser;
    JPanel detailArea;

    public RekamMedisPanel(PasienController pc, SesiController sc, User user) {
        this.pasienCtrl=pc; this.sesiCtrl=sc; this.currentUser=user;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    void build() {
        JPanel content = new JPanel(new BorderLayout(14,0));
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18,22,18,22));

        // Kiri - daftar pasien
        JPanel leftPanel = UIHelper.createCard();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(220,0));
        JPanel lh = UIHelper.createCardHeader("Pilih Pasien");
        lh.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER),new EmptyBorder(10,14,10,14)));
        leftPanel.add(lh, BorderLayout.NORTH);

        JPanel plist = new JPanel(); plist.setLayout(new BoxLayout(plist,BoxLayout.Y_AXIS));
        plist.setBackground(UIHelper.SURFACE); plist.setBorder(new EmptyBorder(8,10,8,10));

        List<Pasien> pasienList = pasienCtrl.getAllPasien();
        detailArea = new JPanel(new CardLayout());
        detailArea.setBackground(UIHelper.BG);
        detailArea.add(buildPlaceholder(), "empty");

        for (int i=0; i<pasienList.size(); i++) {
            Pasien p = pasienList.get(i);
            JPanel prow = new JPanel(new BorderLayout(8,0));
            prow.setBackground(UIHelper.SURFACE);
            prow.setBorder(new EmptyBorder(8,8,8,8));
            prow.setMaximumSize(new Dimension(Integer.MAX_VALUE,44));
            prow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            prow.add(UIHelper.createAvatar(UIHelper.getInisial(p.getNama()),UIHelper.getAvatarColor(i),28),BorderLayout.WEST);
            JPanel pi = new JPanel(); pi.setLayout(new BoxLayout(pi,BoxLayout.Y_AXIS)); pi.setBackground(UIHelper.SURFACE);
            pi.setBorder(new EmptyBorder(0,6,0,0));
            pi.add(UIHelper.label(p.getNama(),UIHelper.FONT_BOLD_12,UIHelper.TEXT));
            List<RekamMedis> rm = pasienCtrl.getRekamMedis(p.getId());
            pi.add(UIHelper.label(rm.size()+" rekam medis",UIHelper.FONT_REG_11,UIHelper.TEXT3));
            prow.add(pi, BorderLayout.CENTER);

            JPanel detail = buildDetailPasien(p, rm);
            String cardId = "rm-" + p.getId();
            detailArea.add(detail, cardId);
            prow.addMouseListener(new java.awt.event.MouseAdapter(){
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    ((CardLayout)detailArea.getLayout()).show(detailArea, cardId);
                    prow.setBackground(UIHelper.TEAL_LIGHT);
                }
                public void mouseEntered(java.awt.event.MouseEvent e){ prow.setBackground(UIHelper.BG); }
                public void mouseExited(java.awt.event.MouseEvent e){ prow.setBackground(UIHelper.SURFACE); }
            });
            plist.add(prow); plist.add(Box.createVerticalStrut(2));
        }
        leftPanel.add(new JScrollPane(plist), BorderLayout.CENTER);
        content.add(leftPanel, BorderLayout.WEST);
        content.add(detailArea, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    JPanel buildPlaceholder() {
        JPanel p = new JPanel(new GridBagLayout()); p.setBackground(UIHelper.BG);
        p.add(UIHelper.label("← Pilih pasien untuk melihat rekam medis", UIHelper.FONT_REG_13, UIHelper.TEXT3));
        return p;
    }

    JPanel buildDetailPasien(Pasien p, List<RekamMedis> rmList) {
        JPanel panel = new JPanel(); panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(UIHelper.BG);

        JPanel header = UIHelper.createCard(); header.setLayout(new FlowLayout(FlowLayout.LEFT,12,10));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE,60));
        header.add(UIHelper.createAvatar(UIHelper.getInisial(p.getNama()),UIHelper.TEAL,38));
        JPanel hi=new JPanel(); hi.setLayout(new BoxLayout(hi,BoxLayout.Y_AXIS)); hi.setBackground(UIHelper.SURFACE);
        hi.add(UIHelper.label(p.getNama(),UIHelper.FONT_BOLD_14,UIHelper.TEXT));
        hi.add(UIHelper.label(p.getDiagnosis()+" · "+p.getUsia()+" thn",UIHelper.FONT_REG_12,UIHelper.TEXT3));
        header.add(hi); panel.add(header); panel.add(Box.createVerticalStrut(10));

        for (RekamMedis rm : rmList) {
            JPanel card = UIHelper.createCardWithPadding(14);
            card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));

            JPanel rowTop = new JPanel(new BorderLayout()); rowTop.setBackground(UIHelper.SURFACE);
            rowTop.add(UIHelper.label("Sesi ke-"+rm.getSesiKe()+" — "+rm.getTanggal(),UIHelper.FONT_BOLD_13,UIHelper.TEXT),BorderLayout.WEST);
            rowTop.add(UIHelper.badgeGreen("Selesai"),BorderLayout.EAST);
            card.add(rowTop); card.add(Box.createVerticalStrut(10));

            JPanel soap = new JPanel(new GridLayout(2,2,10,8)); soap.setBackground(UIHelper.SURFACE);
            soap.add(buildSOAPBox("S — Subjektif",rm.getSubjektif(),UIHelper.TEAL));
            soap.add(buildSOAPBox("O — Objektif",rm.getObjektif(),UIHelper.BLUE));
            soap.add(buildSOAPBox("A — Asesmen",rm.getAsesmen(),UIHelper.AMBER));
            soap.add(buildSOAPBox("P — Planning",rm.getPlanning(),UIHelper.PURPLE));
            card.add(soap);
            if(rm.getSkalaNyeriVAS()>0){
                card.add(Box.createVerticalStrut(8));
                JPanel vas=new JPanel(new FlowLayout(FlowLayout.LEFT,6,0)); vas.setBackground(UIHelper.SURFACE);
                vas.add(UIHelper.label("Skala Nyeri VAS:",UIHelper.FONT_BOLD_11,UIHelper.TEXT3));
                vas.add(UIHelper.label(rm.getSkalaNyeriVAS()+"/10",UIHelper.FONT_BOLD_12,UIHelper.CORAL));
                card.add(vas);
            }
            panel.add(card); panel.add(Box.createVerticalStrut(10));
        }
        if(rmList.isEmpty()) panel.add(UIHelper.label("Belum ada rekam medis.",UIHelper.FONT_REG_13,UIHelper.TEXT3));
        JScrollPane sp = UIHelper.createScrollPane(panel); sp.setBackground(UIHelper.BG);
        JPanel wrapper = new JPanel(new BorderLayout()); wrapper.setBackground(UIHelper.BG); wrapper.add(sp);
        return wrapper;
    }

    JPanel buildSOAPBox(String title, String content, Color color) {
        JPanel box = new JPanel(); box.setLayout(new BoxLayout(box,BoxLayout.Y_AXIS));
        box.setBackground(new Color(color.getRed(),color.getGreen(),color.getBlue(),15));
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(color.getRed(),color.getGreen(),color.getBlue(),50),1,true),
            new EmptyBorder(8,10,8,10)));
        JLabel ttl=UIHelper.label(title,UIHelper.FONT_BOLD_11,color); ttl.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(ttl); box.add(Box.createVerticalStrut(4));
        JTextArea ta=new JTextArea(content!=null?content:"-"); ta.setLineWrap(true); ta.setWrapStyleWord(true);
        ta.setFont(UIHelper.FONT_REG_11); ta.setForeground(UIHelper.TEXT2);
        ta.setBackground(new Color(0,0,0,0)); ta.setEditable(false); ta.setOpaque(false);
        ta.setBorder(null); ta.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(ta);
        return box;
    }
}
