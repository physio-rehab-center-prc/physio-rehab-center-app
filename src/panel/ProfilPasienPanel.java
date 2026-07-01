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
class ProfilPasienPanelBase extends JPanel {
    Pasien pasien; PasienController pasienCtrl; PasienFrame frame;
    public ProfilPasienPanelBase(Pasien p,PasienController pc,PasienFrame f){this.pasien=p;this.pasienCtrl=pc;this.frame=f;setBackground(UIHelper.BG);setLayout(new BorderLayout());build();}
    void build(){
        JPanel content=new JPanel(); content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.setBackground(UIHelper.BG); content.setBorder(new EmptyBorder(18,22,18,22));
        content.add(buildHeroBanner()); content.add(Box.createVerticalStrut(14));
        content.add(buildInfoSection()); content.add(Box.createVerticalStrut(14));
        content.add(buildEditSection());
        add(UIHelper.createScrollPane(content),BorderLayout.CENTER);
    }
    JPanel buildHeroBanner(){
        JPanel banner=new JPanel(){
            protected void paintComponent(Graphics g){
                Graphics2D g2=(Graphics2D)g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp=new GradientPaint(0,0,UIHelper.TEAL_DARK,getWidth(),getHeight(),UIHelper.TEAL);
                g2.setPaint(gp); g2.fillRoundRect(0,0,getWidth(),getHeight(),14,14);
            }
        };
        banner.setLayout(new FlowLayout(FlowLayout.LEFT,20,16));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        JLabel av=UIHelper.createAvatar(UIHelper.getInisial(pasien.getNama()),new Color(255,255,255,60),62);
        av.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,80),2,true));
        JPanel info=new JPanel(); info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS)); info.setOpaque(false);
        info.add(UIHelper.label(pasien.getNama(),new Font("Segoe UI",Font.BOLD,18),Color.WHITE));
        info.add(UIHelper.label("Pasien #"+pasien.getId()+" · Terdaftar "+pasien.getTanggalDaftar(),UIHelper.FONT_REG_12,new Color(255,255,255,180)));
        JPanel badges=new JPanel(new FlowLayout(FlowLayout.LEFT,6,2)); badges.setOpaque(false);
        badges.add(UIHelper.createBadge(pasien.getDiagnosis(),new Color(255,255,255,25),Color.WHITE));
        badges.add(UIHelper.createBadge(pasien.getAsuransi(),new Color(255,255,255,25),Color.WHITE));
        badges.add(UIHelper.createBadge(pasien.getStatusLabel(),new Color(255,255,255,25),Color.WHITE));
        info.add(badges);
        banner.add(av); banner.add(info); return banner;
    }
    JPanel buildInfoSection(){
        JPanel grid=new JPanel(new GridLayout(1,2,14,0)); grid.setBackground(UIHelper.BG);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
        JPanel left=UIHelper.createCardWithPadding(16); left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        left.add(UIHelper.label("Data Pribadi",UIHelper.FONT_BOLD_13,UIHelper.TEXT)); left.add(Box.createVerticalStrut(10));
        String[][]data={{"Tanggal Lahir",pasien.getTanggalLahir().toString()},
            {"Usia",pasien.getUsia()+" tahun"},{"Jenis Kelamin",pasien.getJenisKelamin()},
            {"Golongan Darah",pasien.getGolonganDarah()!=null?pasien.getGolonganDarah():"-"},
            {"No. HP",pasien.getNomorHP()},{"Email",pasien.getEmail()},
            {"Alamat",pasien.getAlamat()},{"Alergi",pasien.getAlergi()!=null?pasien.getAlergi():"-"}};
        for(String[]d:data){addRow(left,d[0],d[1]);}
        JPanel right=UIHelper.createCardWithPadding(16); right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));
        right.add(UIHelper.label("Data Medis",UIHelper.FONT_BOLD_13,UIHelper.TEXT)); right.add(Box.createVerticalStrut(10));
        String[][]med={{"Diagnosis",pasien.getDiagnosis()},{"Asuransi",pasien.getAsuransi()},
            {"No. Asuransi",pasien.getNomorAsuransi()!=null?pasien.getNomorAsuransi():"-"},
            {"Total Sesi Target",String.valueOf(pasien.getTotalSesiTarget())},
            {"Sesi Selesai",String.valueOf(pasien.getTotalSesiSelesai())},
            {"Progres Keseluruhan",pasien.getProgresSesiPersen()+"%"},
            {"Status Pasien",pasien.getStatusLabel()},
            {"Terapis",pasien.getTerapisId()!=null?"Dr. Rina Maharani":"-"}};
        for(String[]d:med){addRow(right,d[0],d[1]);}
        grid.add(left); grid.add(right); return grid;
    }
    void addRow(JPanel parent,String key,String val){
        JPanel row=new JPanel(new BorderLayout(10,0)); row.setBackground(UIHelper.SURFACE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE,26));
        JLabel k=UIHelper.label(key+":",UIHelper.FONT_BOLD_11,UIHelper.TEXT3); k.setPreferredSize(new Dimension(130,16));
        row.add(k,BorderLayout.WEST); row.add(UIHelper.label(val,UIHelper.FONT_REG_12,UIHelper.TEXT),BorderLayout.CENTER);
        parent.add(row); parent.add(Box.createVerticalStrut(5));
    }
    JPanel buildEditSection(){
        JPanel card=UIHelper.createCardWithPadding(16); card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
        card.add(UIHelper.label("Edit Profil Saya",UIHelper.FONT_BOLD_13,UIHelper.TEXT)); card.add(Box.createVerticalStrut(12));
        JPanel form=new JPanel(new GridLayout(2,2,12,8)); form.setBackground(UIHelper.SURFACE);
        JTextField tfNama=UIHelper.createTextField("Nama"); tfNama.setText(pasien.getNama());
        JTextField tfHP=UIHelper.createTextField("No HP"); tfHP.setText(pasien.getNomorHP());
        JTextField tfAlamat=UIHelper.createTextField("Alamat"); tfAlamat.setText(pasien.getAlamat()!=null?pasien.getAlamat():"");
        JTextField tfAlergi=UIHelper.createTextField("Alergi"); tfAlergi.setText(pasien.getAlergi()!=null?pasien.getAlergi():"");
        JPanel r1=new JPanel(new BorderLayout(6,0)); r1.setBackground(UIHelper.SURFACE);
        r1.add(UIHelper.label("Nama:",UIHelper.FONT_BOLD_11,UIHelper.TEXT2),BorderLayout.WEST); r1.add(tfNama,BorderLayout.CENTER);
        JPanel r2=new JPanel(new BorderLayout(6,0)); r2.setBackground(UIHelper.SURFACE);
        r2.add(UIHelper.label("No HP:",UIHelper.FONT_BOLD_11,UIHelper.TEXT2),BorderLayout.WEST); r2.add(tfHP,BorderLayout.CENTER);
        JPanel r3=new JPanel(new BorderLayout(6,0)); r3.setBackground(UIHelper.SURFACE);
        r3.add(UIHelper.label("Alamat:",UIHelper.FONT_BOLD_11,UIHelper.TEXT2),BorderLayout.WEST); r3.add(tfAlamat,BorderLayout.CENTER);
        JPanel r4=new JPanel(new BorderLayout(6,0)); r4.setBackground(UIHelper.SURFACE);
        r4.add(UIHelper.label("Alergi:",UIHelper.FONT_BOLD_11,UIHelper.TEXT2),BorderLayout.WEST); r4.add(tfAlergi,BorderLayout.CENTER);
        form.add(r1); form.add(r2); form.add(r3); form.add(r4);
        card.add(form); card.add(Box.createVerticalStrut(12));
        JButton save=UIHelper.createButtonPrimary("💾  Simpan Perubahan");
        save.setAlignmentX(Component.LEFT_ALIGNMENT);
        save.addActionListener(e->{
            pasienCtrl.updatePasien(pasien.getId(),tfNama.getText(),tfHP.getText(),tfAlamat.getText(),tfAlergi.getText());
            UIHelper.showSuccess(this,"Profil berhasil diperbarui!");
        });
        card.add(save);
        // Ubah password
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createSeparator());
        card.add(Box.createVerticalStrut(12));
        card.add(UIHelper.label("Ubah Password",UIHelper.FONT_BOLD_13,UIHelper.TEXT)); card.add(Box.createVerticalStrut(8));
        JPanel passForm=new JPanel(new GridLayout(1,3,10,0)); passForm.setBackground(UIHelper.SURFACE);
        JPasswordField pfLama=new JPasswordField(); pfLama.setFont(UIHelper.FONT_REG_13);
        JPasswordField pfBaru=new JPasswordField(); pfBaru.setFont(UIHelper.FONT_REG_13);
        JPasswordField pfKonfirm=new JPasswordField(); pfKonfirm.setFont(UIHelper.FONT_REG_13);
        JPanel pp1=new JPanel(new BorderLayout(4,0)); pp1.setBackground(UIHelper.SURFACE);
        pp1.add(UIHelper.label("Password Lama:",UIHelper.FONT_BOLD_11,UIHelper.TEXT2),BorderLayout.NORTH); pp1.add(pfLama,BorderLayout.CENTER);
        JPanel pp2=new JPanel(new BorderLayout(4,0)); pp2.setBackground(UIHelper.SURFACE);
        pp2.add(UIHelper.label("Password Baru:",UIHelper.FONT_BOLD_11,UIHelper.TEXT2),BorderLayout.NORTH); pp2.add(pfBaru,BorderLayout.CENTER);
        JPanel pp3=new JPanel(new BorderLayout(4,0)); pp3.setBackground(UIHelper.SURFACE);
        pp3.add(UIHelper.label("Konfirmasi:",UIHelper.FONT_BOLD_11,UIHelper.TEXT2),BorderLayout.NORTH); pp3.add(pfKonfirm,BorderLayout.CENTER);
        passForm.add(pp1); passForm.add(pp2); passForm.add(pp3);
        card.add(passForm); card.add(Box.createVerticalStrut(10));
        JButton savePass=UIHelper.createButtonOutline("Ubah Password");
        savePass.addActionListener(e->{
            String lama=new String(pfLama.getPassword());
            String baru=new String(pfBaru.getPassword());
            String konfirm=new String(pfKonfirm.getPassword());
            if(!pasien.verifyPassword(lama)){UIHelper.showError(this,"Password lama salah!");return;}
            if(!baru.equals(konfirm)){UIHelper.showError(this,"Password baru tidak cocok!");return;}
            if(baru.length()<6){UIHelper.showError(this,"Password minimal 6 karakter!");return;}
            pasien.setPassword(baru);
            UIHelper.showSuccess(this,"Password berhasil diubah!");
        });
        card.add(savePass);
        return card;
    }
}
public class ProfilPasienPanel extends ProfilPasienPanelBase {
    public ProfilPasienPanel(Pasien p,PasienController pc,PasienFrame f){super(p,pc,f);}
}
