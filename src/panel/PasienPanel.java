package panel;

import controller.*;
import model.*;
import util.UIHelper;
import view.AdminFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import view.AdminFrame;


class PasienPanelInternal extends JPanel {
    PasienController pasienCtrl; TerapisController terapisCtrl; SesiController sesiCtrl; AdminFrame frame;
    DefaultTableModel model;

    PasienPanelInternal(PasienController pc, TerapisController tc, SesiController sc, AdminFrame f) {
        this.pasienCtrl=pc; this.terapisCtrl=tc; this.sesiCtrl=sc; this.frame=f;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    void build() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18,22,18,22));
        content.add(buildToolbar(), BorderLayout.NORTH);
        content.add(buildTable(), BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    JPanel buildToolbar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        bar.setBackground(UIHelper.BG);
        bar.setBorder(new EmptyBorder(0,0,12,0));
        JComboBox<String> filter = UIHelper.createComboBox(new String[]{"Semua Status","Aktif","Baru","Selesai"});
        JButton btn = UIHelper.createButtonPrimary("+ Pasien Baru");
        btn.addActionListener(e -> showFormPasien());
        bar.add(UIHelper.label("Filter:", UIHelper.FONT_BOLD_12, UIHelper.TEXT2));
        bar.add(filter); bar.add(Box.createHorizontalStrut(10)); bar.add(btn);
        return bar;
    }

    JScrollPane buildTable() {
        String[] cols = {"#","Nama Pasien","Usia","Diagnosis","Terapis","Sesi","Status"};
        model = new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = new JTable(model);
        table.setRowHeight(36); table.setFont(UIHelper.FONT_REG_13);
        table.setBackground(UIHelper.SURFACE); table.setGridColor(UIHelper.BORDER);
        table.setShowVerticalLines(false); table.setSelectionBackground(UIHelper.TEAL_LIGHT);
        table.getTableHeader().setFont(UIHelper.FONT_BOLD_11);
        table.getTableHeader().setForeground(UIHelper.TEXT3);
        table.getTableHeader().setBackground(UIHelper.BG);
        table.getColumnModel().getColumn(6).setCellRenderer((t,v,s,f,r,c) -> UIHelper.statusBadgePasien(v!=null?v.toString():""));
        refresh(table);
        table.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent e){
                if(e.getClickCount()==2){ int r=table.getSelectedRow(); if(r>=0) showDetail(model.getValueAt(r,0).toString()); }
            }
        });
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(UIHelper.BORDER,1));
        sp.getViewport().setBackground(UIHelper.SURFACE);
        return sp;
    }

    void refresh(JTable table) {
        model.setRowCount(0);
        List<Pasien> list = pasienCtrl.getAllPasien();
        for(int i=0;i<list.size();i++){
            Pasien p=list.get(i);
            Terapis t=terapisCtrl.getTerapisById(p.getTerapisId());
            model.addRow(new Object[]{p.getId(),p.getNama(),p.getUsia()+" thn",p.getDiagnosis(),
                t!=null?t.getNama():"—",p.getTotalSesiSelesai()+"/"+p.getTotalSesiTarget(),p.getStatusLabel()});
        }
    }

    void showDetail(String pasienId) {
        Pasien p = pasienCtrl.getPasienById(pasienId);
        if(p==null) return;
        JDialog dlg = new JDialog((Frame)SwingUtilities.getWindowAncestor(this),"Detail Pasien: "+p.getNama(),true);
        dlg.setSize(480,420); dlg.setLocationRelativeTo(this);
        JPanel panel = new JPanel(); panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(UIHelper.SURFACE); panel.setBorder(new EmptyBorder(20,24,20,24));
        String[][] info = {{"Nama",p.getNama()},{"Email",p.getEmail()},{"No HP",p.getNomorHP()},
            {"Usia",p.getUsia()+" tahun"},{"Jenis Kelamin",p.getJenisKelamin()},{"Alamat",p.getAlamat()},
            {"Diagnosis",p.getDiagnosis()},{"Asuransi",p.getAsuransi()},{"Alergi",p.getAlergi()!=null?p.getAlergi():"-"},
            {"Total Sesi",p.getTotalSesiSelesai()+"/"+p.getTotalSesiTarget()},{"Status",p.getStatusLabel()}};
        for(String[] row:info){
            JPanel r=new JPanel(new BorderLayout(10,0)); r.setBackground(UIHelper.SURFACE);
            r.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
            r.add(UIHelper.label(row[0]+":",UIHelper.FONT_BOLD_12,UIHelper.TEXT2),BorderLayout.WEST);
            r.add(UIHelper.label(row[1],UIHelper.FONT_REG_12,UIHelper.TEXT),BorderLayout.CENTER);
            panel.add(r); panel.add(Box.createVerticalStrut(4));
        }
        // Progress
        panel.add(Box.createVerticalStrut(8));
        panel.add(UIHelper.createProgressBar("Kekuatan",p.getProgresKekuatan(),UIHelper.TEAL));
        panel.add(Box.createVerticalStrut(4));
        panel.add(UIHelper.createProgressBar("Fleksibilitas",p.getProgresFleksibilitas(),UIHelper.TEAL));
        JButton close=UIHelper.createButtonPrimary("Tutup"); close.addActionListener(e->dlg.dispose());
        JPanel btnRow=new JPanel(new FlowLayout(FlowLayout.RIGHT)); btnRow.setBackground(UIHelper.SURFACE);
        btnRow.add(close); panel.add(Box.createVerticalStrut(12)); panel.add(btnRow);
        dlg.add(new JScrollPane(panel)); dlg.setVisible(true);
    }

    void showFormPasien() {
        JDialog dlg = new JDialog((Frame)SwingUtilities.getWindowAncestor(this),"Daftar Pasien Baru",true);
        dlg.setSize(460,400); dlg.setLocationRelativeTo(this);
        JPanel panel = new JPanel(); panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(UIHelper.SURFACE); panel.setBorder(new EmptyBorder(18,22,18,22));
        JTextField tfNama=UIHelper.createTextField("Nama lengkap");
        JTextField tfEmail=UIHelper.createTextField("email@domain.com");
        JTextField tfHP=UIHelper.createTextField("08XX-XXXX-XXXX");
        JTextField tfAlamat=UIHelper.createTextField("Alamat lengkap");
        JTextField tfDiagnosis=UIHelper.createTextField("Diagnosis");
        JComboBox<String> cbAsuransi=UIHelper.createComboBox(new String[]{"Umum","BPJS Kesehatan","Asuransi Swasta"});
        String[][] fields={{"Nama",null},{"Email",null},{"No HP",null},{"Alamat",null},{"Diagnosis",null},{"Asuransi",null}};
        JComponent[] inputs={tfNama,tfEmail,tfHP,tfAlamat,tfDiagnosis,cbAsuransi};
        for(int i=0;i<inputs.length;i++){
            JPanel row=new JPanel(new BorderLayout(8,0)); row.setBackground(UIHelper.SURFACE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE,44));
            JLabel lbl=UIHelper.label(fields[i][0],UIHelper.FONT_BOLD_12,UIHelper.TEXT2);
            lbl.setPreferredSize(new Dimension(80,20));
            inputs[i].setMaximumSize(new Dimension(Integer.MAX_VALUE,32));
            row.add(lbl,BorderLayout.WEST); row.add(inputs[i],BorderLayout.CENTER);
            panel.add(row); panel.add(Box.createVerticalStrut(8));
        }
        JPanel btnRow=new JPanel(new FlowLayout(FlowLayout.RIGHT)); btnRow.setBackground(UIHelper.SURFACE);
        JButton cancel=UIHelper.createButtonOutline("Batal");
        JButton save=UIHelper.createButtonPrimary("✓  Simpan");
        cancel.addActionListener(e->dlg.dispose());
        save.addActionListener(e->{
            try{
                pasienCtrl.daftarPasien(tfNama.getText(),tfEmail.getText(),"pasien123",
                    tfHP.getText(),java.time.LocalDate.of(1990,1,1),"Laki-laki",
                    tfAlamat.getText(),tfDiagnosis.getText(),(String)cbAsuransi.getSelectedItem());
                dlg.dispose(); UIHelper.showSuccess(this,"Pasien berhasil didaftarkan!");
            }catch(Exception ex){ UIHelper.showError(this,"Gagal: "+ex.getMessage()); }
        });
        btnRow.add(cancel); btnRow.add(save);
        panel.add(btnRow); dlg.add(panel); dlg.setVisible(true);
    }
}

public class PasienPanel extends PasienPanelInternal {
    public PasienPanel(PasienController pc, TerapisController tc, SesiController sc, AdminFrame f) {
        super(pc,tc,sc,f);
    }
}
