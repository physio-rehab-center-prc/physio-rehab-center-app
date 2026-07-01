package view.panel;

import controller.*;
import model.*;
import util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// ===================== LATIHAN PANEL =====================
class LatihanPanelBase extends JPanel {
    SesiController sesiCtrl; PasienController pasienCtrl;
    public LatihanPanelBase(SesiController sc, PasienController pc) {
        this.sesiCtrl=sc; this.pasienCtrl=pc;
        setBackground(UIHelper.BG); setLayout(new BorderLayout()); build();
    }
    void build() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIHelper.BG); content.setBorder(new EmptyBorder(18,22,18,22));
        content.add(buildGrid());
        add(content, BorderLayout.CENTER);
    }
    JScrollPane buildGrid() {
        data.DataStore ds = data.DataStore.getInstance();
        List<ProgramLatihan> list = ds.getAllProgram();
        JPanel grid = new JPanel(new GridLayout(0,3,14,14)); grid.setBackground(UIHelper.BG);
        Color[][] colors={{UIHelper.TEAL_DARK,UIHelper.TEAL_LIGHT,UIHelper.TEAL},
            {UIHelper.BLUE,UIHelper.BLUE_LIGHT,UIHelper.BLUE},
            {UIHelper.PURPLE,UIHelper.PURPLE_LIGHT,UIHelper.PURPLE},
            {UIHelper.AMBER,UIHelper.AMBER_LIGHT,UIHelper.AMBER},
            {UIHelper.CORAL,UIHelper.CORAL_LIGHT,UIHelper.CORAL}};
        for(int i=0;i<list.size();i++) {
            ProgramLatihan p=list.get(i); Color[]c=colors[i%colors.length];
            grid.add(buildCard(p,c[0],c[1],c[2]));
        }
        return UIHelper.createScrollPane(grid);
    }
    JPanel buildCard(ProgramLatihan p, Color hdrColor, Color bgColor, Color valColor) {
        JPanel card = UIHelper.createCard(); card.setLayout(new BorderLayout());
        JPanel hdr = new JPanel(); hdr.setLayout(new BoxLayout(hdr,BoxLayout.Y_AXIS));
        hdr.setBackground(bgColor); hdr.setBorder(new EmptyBorder(14,14,14,14));
        JLabel em=UIHelper.label(p.getEmoji(),new Font("Segoe UI Emoji",Font.PLAIN,24),hdrColor);
        em.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel nm=UIHelper.label(p.getNama(),UIHelper.FONT_BOLD_14,hdrColor);
        nm.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel kt=UIHelper.label(p.getKategori()+" · "+p.getDurasiMenit()+" menit",UIHelper.FONT_REG_11,UIHelper.TEXT3);
        kt.setAlignmentX(Component.LEFT_ALIGNMENT);
        hdr.add(em); hdr.add(Box.createVerticalStrut(4)); hdr.add(nm); hdr.add(kt);
        card.add(hdr, BorderLayout.NORTH);
        JPanel body = new JPanel(); body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
        body.setBackground(UIHelper.SURFACE); body.setBorder(new EmptyBorder(10,14,14,14));
        List<String> daftar=p.getDaftarLatihan();
        for(int i=0;i<Math.min(daftar.size(),5);i++){
            JPanel row=new JPanel(new FlowLayout(FlowLayout.LEFT,6,1)); row.setBackground(UIHelper.SURFACE);
            JLabel num=new JLabel(String.valueOf(i+1));
            num.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10)); num.setForeground(Color.WHITE); num.setOpaque(false);
            JPanel circle=new JPanel(){protected void paintComponent(Graphics g){
                Graphics2D g2=(Graphics2D)g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(valColor); g2.fillOval(0,0,getWidth(),getHeight()); }};
            circle.setLayout(new GridBagLayout()); circle.setPreferredSize(new Dimension(18,18));
            circle.setOpaque(false); num.setForeground(Color.WHITE);
            circle.add(num); row.add(circle);
            row.add(UIHelper.label(daftar.get(i),UIHelper.FONT_REG_12,UIHelper.TEXT2));
            body.add(row);
        }
        body.add(Box.createVerticalStrut(8));
        JButton assign=UIHelper.createButtonSmall("Assign ke Pasien",bgColor,valColor);
        assign.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        assign.setAlignmentX(Component.LEFT_ALIGNMENT);
        assign.addActionListener(e->UIHelper.showSuccess(this,"Program '"+p.getNama()+"' berhasil di-assign!"));
        body.add(assign);
        card.add(body,BorderLayout.CENTER);
        return card;
    }
}

