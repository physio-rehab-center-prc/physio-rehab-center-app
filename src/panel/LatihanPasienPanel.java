package panel;
import controller.*;
import model.*;
import util.UIHelper;
import view.PasienFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
class LatihanPasienPanelBase extends JPanel {
    Pasien pasien; SesiController sesiCtrl;
    public LatihanPasienPanelBase(Pasien p,SesiController sc){this.pasien=p;this.sesiCtrl=sc;setBackground(UIHelper.BG);setLayout(new BorderLayout());build();}
    void build(){
        JPanel content=new JPanel(); content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setBackground(UIHelper.BG); content.setBorder(new EmptyBorder(18,22,18,22));

        JLabel title=UIHelper.label("Program Latihan Anda — "+pasien.getDiagnosis(),UIHelper.FONT_BOLD_14,UIHelper.TEXT);
        content.add(title); content.add(Box.createVerticalStrut(12));

        data.DataStore ds=data.DataStore.getInstance();
        List<model.ProgramLatihan> all=ds.getAllProgram();
        // Tampilkan semua program
        for(int i=0;i<all.size();i++){
            model.ProgramLatihan prog=all.get(i);
            content.add(buildProgramCard(prog, i)); content.add(Box.createVerticalStrut(12));
        }
        add(UIHelper.createScrollPane(content),BorderLayout.CENTER);
    }
    JPanel buildProgramCard(model.ProgramLatihan prog, int idx){
        Color[][]colors={{UIHelper.TEAL_DARK,UIHelper.TEAL_LIGHT,UIHelper.TEAL},
            {UIHelper.BLUE,UIHelper.BLUE_LIGHT,UIHelper.BLUE},
            {UIHelper.PURPLE,UIHelper.PURPLE_LIGHT,UIHelper.PURPLE},
            {UIHelper.AMBER,UIHelper.AMBER_LIGHT,UIHelper.AMBER},
            {UIHelper.CORAL,UIHelper.CORAL_LIGHT,UIHelper.CORAL}};
        Color[]c=colors[idx%colors.length];
        JPanel card=UIHelper.createCard(); card.setLayout(new BorderLayout());
        JPanel hdr=new JPanel(new FlowLayout(FlowLayout.LEFT,14,12)); hdr.setBackground(c[1]);
        hdr.setBorder(BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER));
        JLabel em=UIHelper.label(prog.getEmoji(),new Font("Segoe UI Emoji",Font.PLAIN,22),c[0]);
        JPanel hi=new JPanel(); hi.setLayout(new BoxLayout(hi,BoxLayout.Y_AXIS)); hi.setBackground(c[1]);
        hi.add(UIHelper.label(prog.getNama(),UIHelper.FONT_BOLD_14,c[0]));
        hi.add(UIHelper.label(prog.getKategori()+" · "+prog.getDurasiMenit()+" menit · Kesulitan: "+prog.getTingkatKesulitan(),UIHelper.FONT_REG_11,UIHelper.TEXT3));
        hdr.add(em); hdr.add(hi); card.add(hdr,BorderLayout.NORTH);

        JPanel body=new JPanel(new GridLayout(1,2,14,0)); body.setBackground(UIHelper.SURFACE); body.setBorder(new EmptyBorder(12,14,14,14));

        JPanel daftarPanel=new JPanel(); daftarPanel.setLayout(new BoxLayout(daftarPanel,BoxLayout.Y_AXIS)); daftarPanel.setBackground(UIHelper.SURFACE);
        daftarPanel.add(UIHelper.label("Daftar Latihan:",UIHelper.FONT_BOLD_11,UIHelper.TEXT3));
        daftarPanel.add(Box.createVerticalStrut(6));
        List<String> dl=prog.getDaftarLatihan();
        for(int i=0;i<dl.size();i++){
            JPanel row=new JPanel(new FlowLayout(FlowLayout.LEFT,6,2)); row.setBackground(UIHelper.SURFACE);
            JLabel num=UIHelper.label(""+(i+1)+".",UIHelper.FONT_BOLD_11,c[2]);
            num.setPreferredSize(new Dimension(18,16));
            row.add(num); row.add(UIHelper.label(dl.get(i),UIHelper.FONT_REG_12,UIHelper.TEXT2));
            daftarPanel.add(row);
        }
        body.add(daftarPanel);

        JPanel infoPanel=new JPanel(); infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS)); infoPanel.setBackground(UIHelper.SURFACE);
        infoPanel.add(UIHelper.label("Tujuan Program:",UIHelper.FONT_BOLD_11,UIHelper.TEXT3));
        infoPanel.add(Box.createVerticalStrut(6));
        JTextArea taTujuan=new JTextArea(prog.getTujuan()!=null?prog.getTujuan():"-");
        taTujuan.setLineWrap(true); taTujuan.setWrapStyleWord(true);
        taTujuan.setFont(UIHelper.FONT_REG_12); taTujuan.setForeground(UIHelper.TEXT2);
        taTujuan.setEditable(false); taTujuan.setOpaque(false); taTujuan.setBorder(null);
        infoPanel.add(taTujuan);
        infoPanel.add(Box.createVerticalStrut(14));
        JButton btnMulai=UIHelper.createButtonSmall("▶  Mulai Latihan Hari Ini",c[1],c[0]);
        btnMulai.setMaximumSize(new Dimension(Integer.MAX_VALUE,32));
        btnMulai.addActionListener(e->UIHelper.showSuccess(this,"Program '"+prog.getNama()+"' dimulai!\nSelamat berlatih, "+pasien.getNama().split(" ")[0]+"! 💪"));
        infoPanel.add(btnMulai);
        body.add(infoPanel);
        card.add(body,BorderLayout.CENTER);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
        return card;
    }
}
public class LatihanPasienPanel extends LatihanPasienPanelBase {
    public LatihanPasienPanel(Pasien p,SesiController sc){super(p,sc);}
}