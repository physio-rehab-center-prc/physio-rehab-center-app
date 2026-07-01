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
class NotifikasiPasienPanelBase extends JPanel {
    Pasien pasien; NotifikasiController notifCtrl;
    public NotifikasiPasienPanelBase(Pasien p,NotifikasiController nc){this.pasien=p;this.notifCtrl=nc;setBackground(UIHelper.BG);setLayout(new BorderLayout());build();}
    void build(){
        JPanel content=new JPanel(new BorderLayout()); content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18,22,18,22));
        JPanel toolbar=new JPanel(new FlowLayout(FlowLayout.RIGHT)); toolbar.setBackground(UIHelper.BG);
        toolbar.setBorder(new EmptyBorder(0,0,12,0));
        JButton mark=UIHelper.createButtonOutline("Tandai Semua Dibaca");
        mark.addActionListener(e->{notifCtrl.tandaiSemuaDibaca(pasien.getId());removeAll();build();revalidate();repaint();});
        toolbar.add(mark); content.add(toolbar,BorderLayout.NORTH);
        JPanel list=new JPanel(); list.setLayout(new BoxLayout(list,BoxLayout.Y_AXIS)); list.setBackground(UIHelper.BG);
        List<Notifikasi> notifs=notifCtrl.getByUser(pasien.getId());
        if(notifs.isEmpty()) list.add(UIHelper.label("Tidak ada notifikasi.",UIHelper.FONT_REG_13,UIHelper.TEXT3));
        for(Notifikasi n:notifs){list.add(buildRow(n));list.add(Box.createVerticalStrut(6));}
        content.add(UIHelper.createScrollPane(list),BorderLayout.CENTER);
        add(content,BorderLayout.CENTER);
    }
    JPanel buildRow(Notifikasi n){
        JPanel row=new JPanel(new BorderLayout(10,0));
        row.setBackground(n.isSudahDibaca()?UIHelper.SURFACE:UIHelper.TEAL_LIGHT);
        row.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(n.isSudahDibaca()?UIHelper.BORDER:UIHelper.TEAL,1,true),
            new EmptyBorder(10,12,10,12)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE,68));
        JLabel ic=UIHelper.label(n.getEmoji(),new Font("Segoe UI Emoji",Font.PLAIN,18),UIHelper.TEXT);
        ic.setPreferredSize(new Dimension(34,34)); ic.setHorizontalAlignment(SwingConstants.CENTER);
        ic.setOpaque(true); ic.setBackground(UIHelper.AMBER_LIGHT);
        JPanel txt=new JPanel(); txt.setLayout(new BoxLayout(txt,BoxLayout.Y_AXIS));
        txt.setBackground(row.getBackground());
        txt.add(UIHelper.label(n.getJudul(),UIHelper.FONT_BOLD_13,UIHelper.TEXT));
        txt.add(UIHelper.label(n.getPesan(),UIHelper.FONT_REG_11,UIHelper.TEXT3));
        JLabel time=UIHelper.label(n.getWaktuLabel(),UIHelper.FONT_REG_11,UIHelper.TEXT3);
        row.add(ic,BorderLayout.WEST); row.add(txt,BorderLayout.CENTER); row.add(time,BorderLayout.EAST);
        row.addMouseListener(new java.awt.event.MouseAdapter(){public void mouseClicked(java.awt.event.MouseEvent e){n.tandaiDibaca();}});
        return row;
    }
}
public class NotifikasiPasienPanel extends NotifikasiPasienPanelBase {
    public NotifikasiPasienPanel(Pasien p,NotifikasiController nc){super(p,nc);}
}