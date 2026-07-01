package panel;

import controller.SesiController;
import model.Pasien;
import model.Sesi;
import util.UIHelper;
import view.PasienFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class JadwalPasienPanel extends JPanel {
    Pasien pasien; SesiController sesiCtrl; PasienFrame frame;
    DefaultTableModel model;

    public JadwalPasienPanel(Pasien p, SesiController sc, PasienFrame f) {
        this.pasien=p; this.sesiCtrl=sc; this.frame=f;
        setBackground(UIHelper.BG); setLayout(new BorderLayout()); build();
    }

    void build() {
        JPanel content=new JPanel(new BorderLayout()); content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18,22,18,22));

        JPanel toolbar=new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        toolbar.setBackground(UIHelper.BG); toolbar.setBorder(new EmptyBorder(0,0,14,0));
        JComboBox<String> filter=UIHelper.createComboBox(new String[]{"Semua","Terjadwal","Selesai","Dibatalkan"});
        JButton btnBuat=UIHelper.createButtonPrimary("+ Buat Janji Baru");
        btnBuat.addActionListener(e->showFormBuatJanji());
        toolbar.add(UIHelper.label("Filter:", UIHelper.FONT_BOLD_12, UIHelper.TEXT2));
        toolbar.add(filter); toolbar.add(Box.createHorizontalStrut(10)); toolbar.add(btnBuat);
        content.add(toolbar, BorderLayout.NORTH);

        String[] cols={"Tanggal","Waktu","Sesi ke-","Program","Terapis","Ruang","Durasi","Status"};
        model=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model);
        table.setRowHeight(36); table.setFont(UIHelper.FONT_REG_13);
        table.setBackground(UIHelper.SURFACE); table.setGridColor(UIHelper.BORDER);
        table.setShowVerticalLines(false); table.setSelectionBackground(UIHelper.TEAL_LIGHT);
        table.getTableHeader().setFont(UIHelper.FONT_BOLD_11);
        table.getTableHeader().setForeground(UIHelper.TEXT3);
        table.getTableHeader().setBackground(UIHelper.BG);
        table.getColumnModel().getColumn(7).setCellRenderer((t,v,s,foc,r,c)->UIHelper.statusBadgeSesi(v!=null?v.toString():""));
        refresh();
        JScrollPane sp=new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(UIHelper.BORDER,1));
        sp.getViewport().setBackground(UIHelper.SURFACE);
        content.add(sp, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    void refresh() {
        model.setRowCount(0);
        List<Sesi> list=sesiCtrl.getSesiByPasien(pasien.getId());
        for(Sesi s:list) {
            model.addRow(new Object[]{s.getTanggal(),s.getWaktuMulai(),s.getSesiKe(),
                s.getProgram(),"Dr. Rina Maharani",s.getRuang(),s.getDurasiMenit()+" mnt",s.getStatusLabel()});
        }
    }

    void showFormBuatJanji() {
        JDialog dlg=new JDialog((Frame)SwingUtilities.getWindowAncestor(this),"Buat Janji Sesi",true);
        dlg.setSize(440,380); dlg.setLocationRelativeTo(this);
        JPanel panel=new JPanel(); panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(UIHelper.SURFACE); panel.setBorder(new EmptyBorder(18,22,18,22));

        JTextField tfTanggal=UIHelper.createTextField("YYYY-MM-DD"); tfTanggal.setText(LocalDate.now().plusDays(1).toString());
        JTextField tfWaktu=UIHelper.createTextField("HH:MM"); tfWaktu.setText("09:00");
        JComboBox<String> cbProgram=UIHelper.createComboBox(new String[]{"Low Back Pain","Rehab Lutut","Stroke Rehab","Neck Pain","Sports Injury"});
        JComboBox<String> cbDurasi=UIHelper.createComboBox(new String[]{"30 menit","60 menit","90 menit"});
        cbDurasi.setSelectedIndex(1);
        JTextArea tfCatatan=UIHelper.createTextArea(3,20);

        String[][]fields={{"Tanggal",null},{"Pukul",null},{"Program",null},{"Durasi",null},{"Catatan",null}};
        JComponent[]inputs={tfTanggal,tfWaktu,cbProgram,cbDurasi,new JScrollPane(tfCatatan)};
        for(int i=0;i<inputs.length;i++){
            JPanel row=new JPanel(new BorderLayout(8,0)); row.setBackground(UIHelper.SURFACE);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE,i==4?80:44));
            JLabel lbl=UIHelper.label(fields[i][0],UIHelper.FONT_BOLD_12,UIHelper.TEXT2);
            lbl.setPreferredSize(new Dimension(70,20));
            inputs[i].setMaximumSize(new Dimension(Integer.MAX_VALUE,i==4?70:32));
            row.add(lbl,BorderLayout.WEST); row.add(inputs[i],BorderLayout.CENTER);
            panel.add(row); panel.add(Box.createVerticalStrut(8));
        }

        JPanel btnRow=new JPanel(new FlowLayout(FlowLayout.RIGHT)); btnRow.setBackground(UIHelper.SURFACE);
        JButton cancel=UIHelper.createButtonOutline("Batal");
        JButton save=UIHelper.createButtonPrimary("✓  Ajukan Janji");
        cancel.addActionListener(e->dlg.dispose());
        save.addActionListener(e->{
            try{
                LocalDate tgl=LocalDate.parse(tfTanggal.getText().trim());
                LocalTime jam=LocalTime.parse(tfWaktu.getText().trim());
                String prog=(String)cbProgram.getSelectedItem();
                int dur=Integer.parseInt(((String)cbDurasi.getSelectedItem()).replace(" menit","").trim());
                sesiCtrl.buatSesi(pasien.getId(),"T001",tgl,jam,dur,"Ruang 1",prog);
                refresh(); dlg.dispose();
                UIHelper.showSuccess(this,"Permintaan janji berhasil diajukan!\nTerapis akan mengonfirmasi segera.");
            }catch(Exception ex){UIHelper.showError(this,"Format salah: "+ex.getMessage());}
        });
        btnRow.add(cancel); btnRow.add(save);
        panel.add(btnRow);
        dlg.add(panel); dlg.setVisible(true);
    }
}
