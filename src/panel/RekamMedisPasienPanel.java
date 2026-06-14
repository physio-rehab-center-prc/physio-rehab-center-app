/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
class RekamMedisPasienPanelBase extends JPanel {
    Pasien pasien; SesiController sesiCtrl;
    public RekamMedisPasienPanelBase(Pasien p, SesiController sc) {
        this.pasien=p; this.sesiCtrl=sc;
        setBackground(UIHelper.BG); setLayout(new BorderLayout()); build();
    }
    void build() {
        JPanel content=new JPanel(); content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setBackground(UIHelper.BG); content.setBorder(new EmptyBorder(18,22,18,22));

        // Info pasien header
        JPanel hdr=UIHelper.createCardWithPadding(14); hdr.setLayout(new FlowLayout(FlowLayout.LEFT,12,0));
        hdr.setMaximumSize(new Dimension(Integer.MAX_VALUE,60));
        hdr.add(UIHelper.createAvatar(UIHelper.getInisial(pasien.getNama()),UIHelper.TEAL,42));
        JPanel hi=new JPanel(); hi.setLayout(new BoxLayout(hi,BoxLayout.Y_AXIS)); hi.setBackground(UIHelper.SURFACE);
        hi.setBorder(new EmptyBorder(0,8,0,0));
        hi.add(UIHelper.label(pasien.getNama(),UIHelper.FONT_BOLD_14,UIHelper.TEXT));
        hi.add(UIHelper.label(pasien.getDiagnosis()+" | Sesi "+pasien.getTotalSesiSelesai()+"/"+pasien.getTotalSesiTarget(),UIHelper.FONT_REG_12,UIHelper.TEXT3));
        hdr.add(hi); content.add(hdr); content.add(Box.createVerticalStrut(14));

        // Progres bar
        JPanel progCard=UIHelper.createCardWithPadding(16); progCard.setLayout(new BoxLayout(progCard,BoxLayout.Y_AXIS));
        progCard.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
        progCard.add(UIHelper.label("Progres Rehabilitasi",UIHelper.FONT_BOLD_13,UIHelper.TEXT));
        progCard.add(Box.createVerticalStrut(12));
        progCard.add(UIHelper.createProgressBar("Kekuatan Otot",pasien.getProgresKekuatan(),UIHelper.TEAL));
        progCard.add(Box.createVerticalStrut(8));
        progCard.add(UIHelper.createProgressBar("Fleksibilitas",pasien.getProgresFleksibilitas(),UIHelper.TEAL));
        progCard.add(Box.createVerticalStrut(8));
        progCard.add(UIHelper.createProgressBar("Pengurangan Nyeri (VAS)",pasien.getProgresNyeri(),UIHelper.TEAL));
        progCard.add(Box.createVerticalStrut(8));
        progCard.add(UIHelper.createProgressBar("Kemandirian Fungsional",pasien.getProgresKemandirian(),UIHelper.TEAL));
        content.add(progCard); content.add(Box.createVerticalStrut(14));

        // Riwayat SOAP
        data.DataStore ds=data.DataStore.getInstance();
        List<model.RekamMedis> rmList=ds.getRekamMedisByPasien(pasien.getId());
        JLabel rmTitle=UIHelper.label("Catatan SOAP dari Terapis",UIHelper.FONT_BOLD_13,UIHelper.TEXT);
        content.add(rmTitle); content.add(Box.createVerticalStrut(8));
        if(rmList.isEmpty()){content.add(UIHelper.label("Belum ada rekam medis tersedia.",UIHelper.FONT_REG_13,UIHelper.TEXT3));}
        for(model.RekamMedis rm:rmList){
            JPanel card=UIHelper.createCardWithPadding(14);
            card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
            JPanel top=new JPanel(new BorderLayout()); top.setBackground(UIHelper.SURFACE);
            top.add(UIHelper.label("Sesi ke-"+rm.getSesiKe()+" — "+rm.getTanggal(),UIHelper.FONT_BOLD_13,UIHelper.TEXT),BorderLayout.WEST);
            if(rm.getSkalaNyeriVAS()>0) top.add(UIHelper.label("Nyeri VAS: "+rm.getSkalaNyeriVAS()+"/10",UIHelper.FONT_BOLD_11,UIHelper.CORAL),BorderLayout.EAST);
            card.add(top); card.add(Box.createVerticalStrut(10));
            JPanel soap=new JPanel(new GridLayout(2,2,8,8)); soap.setBackground(UIHelper.SURFACE);
            soap.add(buildBox("S — Subjektif",rm.getSubjektif(),UIHelper.TEAL));
            soap.add(buildBox("O — Objektif",rm.getObjektif(),UIHelper.BLUE));
            soap.add(buildBox("A — Asesmen",rm.getAsesmen(),UIHelper.AMBER));
            soap.add(buildBox("P — Planning",rm.getPlanning(),UIHelper.PURPLE));
            card.add(soap);
            content.add(card); content.add(Box.createVerticalStrut(10));
        }
        add(UIHelper.createScrollPane(content),BorderLayout.CENTER);
    }
    JPanel buildBox(String ttl,String txt,Color c){
        JPanel box=new JPanel(); box.setLayout(new BoxLayout(box,BoxLayout.Y_AXIS));
        box.setBackground(new Color(c.getRed(),c.getGreen(),c.getBlue(),12));
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(c.getRed(),c.getGreen(),c.getBlue(),50),1,true),new EmptyBorder(8,10,8,10)));
        JLabel t=UIHelper.label(ttl,UIHelper.FONT_BOLD_11,c); t.setAlignmentX(Component.LEFT_ALIGNMENT); box.add(t);
        box.add(Box.createVerticalStrut(4));
        JTextArea ta=new JTextArea(txt!=null?txt:"-"); ta.setLineWrap(true); ta.setWrapStyleWord(true);
        ta.setFont(UIHelper.FONT_REG_11); ta.setForeground(UIHelper.TEXT2); ta.setBackground(new Color(0,0,0,0));
        ta.setEditable(false); ta.setOpaque(false); ta.setBorder(null); ta.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(ta); return box;
    }
}
public class RekamMedisPasienPanel extends RekamMedisPasienPanelBase {
    public RekamMedisPasienPanel(Pasien p,SesiController sc){super(p,sc);}
}