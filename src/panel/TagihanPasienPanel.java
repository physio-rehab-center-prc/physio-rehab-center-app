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
class TagihanPasienPanelBase extends JPanel {
    Pasien pasien; TagihanController tagihanCtrl;
    DefaultTableModel model; JTable table;
    public TagihanPasienPanelBase(Pasien p,TagihanController tc){this.pasien=p;this.tagihanCtrl=tc;setBackground(UIHelper.BG);setLayout(new BorderLayout());build();}
    void build(){
        JPanel content=new JPanel(new BorderLayout()); content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18,22,18,22));
        content.add(buildStats(),BorderLayout.NORTH);
        content.add(buildTable(),BorderLayout.CENTER);
        add(content,BorderLayout.CENTER);
    }
    JPanel buildStats(){
        JPanel row=new JPanel(new GridLayout(1,3,12,0)); row.setBackground(UIHelper.BG);
        row.setBorder(new EmptyBorder(0,0,14,0));
        List<Tagihan> list=tagihanCtrl.getTagihanByPasien(pasien.getId());
        double lunas=list.stream().filter(t->t.getStatus()==Tagihan.StatusTagihan.LUNAS).mapToDouble(Tagihan::getJumlah).sum();
        double pending=list.stream().filter(t->t.getStatus()==Tagihan.StatusTagihan.MENUNGGU).mapToDouble(Tagihan::getJumlah).sum();
        double jatuh=list.stream().filter(t->t.getStatus()==Tagihan.StatusTagihan.JATUH_TEMPO).mapToDouble(Tagihan::getJumlah).sum();
        row.add(buildStat("✅","Rp "+String.format("%,.0f",lunas),"Sudah Dibayar",UIHelper.GREEN_LIGHT,UIHelper.GREEN));
        row.add(buildStat("⏳","Rp "+String.format("%,.0f",pending),"Menunggu Bayar",UIHelper.AMBER_LIGHT,UIHelper.AMBER));
        row.add(buildStat("❗","Rp "+String.format("%,.0f",jatuh),"Jatuh Tempo",UIHelper.CORAL_LIGHT,UIHelper.CORAL));
        return row;
    }
    JPanel buildStat(String ic,String val,String lbl,Color bg,Color fg){
        JPanel c=UIHelper.createCard(); c.setLayout(new FlowLayout(FlowLayout.LEFT,12,12));
        JLabel icon=UIHelper.label(ic,new Font("Segoe UI Emoji",Font.PLAIN,20),fg);
        icon.setOpaque(true); icon.setBackground(bg); icon.setPreferredSize(new Dimension(38,38));
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel inf=new JPanel(); inf.setLayout(new BoxLayout(inf,BoxLayout.Y_AXIS)); inf.setBackground(UIHelper.SURFACE);
        inf.add(UIHelper.label(val,UIHelper.FONT_BOLD_14,UIHelper.TEXT));
        inf.add(UIHelper.label(lbl,UIHelper.FONT_REG_11,UIHelper.TEXT3));
        c.add(icon); c.add(inf); return c;
    }
    JScrollPane buildTable(){
        String[]cols={"No. Tagihan","Layanan","Jumlah","Metode","Tgl Tagihan","Status","Aksi"};
        model=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return c==6;}};
        table=new JTable(model); table.setRowHeight(36); table.setFont(UIHelper.FONT_REG_13);
        table.setBackground(UIHelper.SURFACE); table.setGridColor(UIHelper.BORDER);
        table.setShowVerticalLines(false); table.setSelectionBackground(UIHelper.TEAL_LIGHT);
        table.getTableHeader().setFont(UIHelper.FONT_BOLD_11); table.getTableHeader().setBackground(UIHelper.BG);
        table.getColumnModel().getColumn(5).setCellRenderer((t,v,s,f,r,c)->{
            String st=v!=null?v.toString():"";
            switch(st){case"Lunas":return UIHelper.badgeGreen(st);case"Jatuh Tempo":return UIHelper.badgeCoral(st);default:return UIHelper.badgeAmber(st);}
        });
        // Tombol bayar
        table.getColumnModel().getColumn(6).setCellRenderer((t,v,s,f,r,c)->{
            String st=model.getValueAt(r,5)!=null?model.getValueAt(r,5).toString():"";
            if(st.equals("Lunas")) return UIHelper.label("Lunas ✓",UIHelper.FONT_REG_11,UIHelper.TEXT3);
            JButton btn=UIHelper.createButtonSmall("Bayar Sekarang",UIHelper.TEAL_LIGHT,UIHelper.TEAL_DARK);
            return btn;
        });
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JCheckBox()){
            JButton btn; String tagId;
            {btn=UIHelper.createButtonSmall("Bayar Sekarang",UIHelper.TEAL_LIGHT,UIHelper.TEAL_DARK);
             btn.addActionListener(e->{ showBayar(tagId); fireEditingStopped(); });}
            public Component getTableCellEditorComponent(JTable t,Object v,boolean s,int r,int c){tagId=v!=null?v.toString():"";return btn;}
            public Object getCellEditorValue(){return tagId;}
        });
        refresh();
        JScrollPane sp=new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(UIHelper.BORDER,1));
        sp.getViewport().setBackground(UIHelper.SURFACE);
        return sp;
    }
    void refresh(){
        model.setRowCount(0);
        List<Tagihan> list=tagihanCtrl.getTagihanByPasien(pasien.getId());
        for(Tagihan t:list){
            model.addRow(new Object[]{t.getId(),t.getNamaLayanan(),t.formatRupiah(),
                t.getMetodePembayaran()!=null?t.getMetodePembayaran():"-",t.getTanggalTagihan(),t.getStatusLabel(),t.getId()});
        }
    }
    void showBayar(String tagId){
        String[]metode={"Transfer Bank","QRIS","Cash","BPJS","Kartu Kredit"};
        String pilihan=(String)JOptionPane.showInputDialog(this,"Pilih metode pembayaran:","Bayar Tagihan",
            JOptionPane.QUESTION_MESSAGE,null,metode,metode[0]);
        if(pilihan!=null){
            tagihanCtrl.bayar(tagId,pilihan);
            UIHelper.showSuccess(this,"Pembayaran berhasil dengan "+pilihan+"!\nTagihan "+tagId+" telah lunas.");
            refresh();
        }
    }
}
public class TagihanPasienPanel extends TagihanPasienPanelBase {
    public TagihanPasienPanel(Pasien p,TagihanController tc){super(p,tc);}
}