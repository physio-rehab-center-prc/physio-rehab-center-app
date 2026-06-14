
package view.panel;

import controller.PasienController;
import controller.TagihanController;
import model.Pasien;
import model.Tagihan;
import util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TagihanPanel extends JPanel {
    private TagihanController tagihanCtrl;
    private PasienController pasienCtrl;
    private DefaultTableModel model;

    public TagihanPanel(TagihanController tc, PasienController pc) {
        this.tagihanCtrl = tc;
        this.pasienCtrl = pc;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18, 22, 18, 22));
        content.add(buildStats(), BorderLayout.NORTH);
        content.add(buildTable(), BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JPanel buildStats() {
        JPanel row = new JPanel(new GridLayout(1, 3, 12, 0));
        row.setBackground(UIHelper.BG);
        row.setBorder(new EmptyBorder(0, 0, 14, 0));

        List<Tagihan> list = tagihanCtrl.getAllTagihan();
        double lunas = list.stream().filter(t -> t.getStatus() == Tagihan.StatusTagihan.LUNAS)
            .mapToDouble(Tagihan::getJumlah).sum();
        double menunggu = list.stream().filter(t -> t.getStatus() == Tagihan.StatusTagihan.MENUNGGU)
            .mapToDouble(Tagihan::getJumlah).sum();
        double jatuh = list.stream().filter(t -> t.getStatus() == Tagihan.StatusTagihan.JATUH_TEMPO)
            .mapToDouble(Tagihan::getJumlah).sum();

        row.add(buildStat("💳", "Rp " + String.format("%,.0f", lunas), "Terbayar Bulan Ini", UIHelper.GREEN_LIGHT));
        row.add(buildStat("⏳", "Rp " + String.format("%,.0f", menunggu), "Menunggu", UIHelper.AMBER_LIGHT));
        row.add(buildStat("❌", "Rp " + String.format("%,.0f", jatuh), "Jatuh Tempo", UIHelper.CORAL_LIGHT));
        return row;
    }

    private JPanel buildStat(String ic, String val, String lbl, Color bg) {
        JPanel c = UIHelper.createCard();
        c.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12));
        JLabel icon = UIHelper.label(ic, new Font("Segoe UI Emoji", Font.PLAIN, 20), UIHelper.TEXT);
        icon.setOpaque(true);
        icon.setBackground(bg);
        icon.setPreferredSize(new Dimension(40, 40));
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel inf = new JPanel();
        inf.setLayout(new BoxLayout(inf, BoxLayout.Y_AXIS));
        inf.setBackground(UIHelper.SURFACE);
        inf.add(UIHelper.label(val, UIHelper.FONT_BOLD_14, UIHelper.TEXT));
        inf.add(UIHelper.label(lbl, UIHelper.FONT_REG_11, UIHelper.TEXT3));
        c.add(icon);
        c.add(inf);
        return c;
    }

    private JScrollPane buildTable() {
        String[] cols = {"No. Tagihan", "Pasien", "Layanan", "Jumlah", "Metode", "Status"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(UIHelper.FONT_REG_13);
        table.setBackground(UIHelper.SURFACE);
        table.setGridColor(UIHelper.BORDER);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(UIHelper.TEAL_LIGHT);
        table.getTableHeader().setFont(UIHelper.FONT_BOLD_11);
        table.getTableHeader().setBackground(UIHelper.BG);

        table.getColumnModel().getColumn(5).setCellRenderer((t, v, s, f, r, c) -> {
            String st = v != null ? v.toString() : "";
            switch (st) {
                case "Lunas":       return UIHelper.badgeGreen(st);
                case "Jatuh Tempo": return UIHelper.badgeCoral(st);
                default:            return UIHelper.badgeAmber(st);
            }
        });

        List<Tagihan> list = tagihanCtrl.getAllTagihan();
        for (Tagihan tg : list) {
            Pasien p = pasienCtrl.getPasienById(tg.getPasienId());
            model.addRow(new Object[]{
                tg.getId(),
                p != null ? p.getNama() : tg.getPasienId(),
                tg.getNamaLayanan(),
                tg.formatRupiah(),
                tg.getMetodePembayaran() != null ? tg.getMetodePembayaran() : "-",
                tg.getStatusLabel()
            });
        }

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int r = table.getSelectedRow();
                    if (r >= 0) {
                        String id = model.getValueAt(r, 0).toString();
                        if (UIHelper.showConfirm(TagihanPanel.this, "Konfirmasi pembayaran tagihan " + id + "?")) {
                            tagihanCtrl.bayar(id, "Manual");
                            model.setValueAt("Lunas", r, 5);
                            UIHelper.showSuccess(TagihanPanel.this, "Tagihan " + id + " ditandai lunas!");
                        }
                    }
                }
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(UIHelper.BORDER, 1));
        sp.getViewport().setBackground(UIHelper.SURFACE);
        return sp;
    }
}