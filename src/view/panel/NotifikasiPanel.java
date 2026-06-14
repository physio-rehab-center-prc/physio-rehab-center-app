package view.panel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import controller.NotifikasiController;
import model.Notifikasi;
import model.User;
import util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class NotifikasiPanel extends JPanel {
    private NotifikasiController notifCtrl;
    private User currentUser;

    public NotifikasiPanel(NotifikasiController nc, User u) {
        this.notifCtrl = nc;
        this.currentUser = u;
        setBackground(UIHelper.BG);
        setLayout(new BorderLayout());
        build();
    }

    private void build() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIHelper.BG);
        content.setBorder(new EmptyBorder(18, 22, 18, 22));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        toolbar.setBackground(UIHelper.BG);
        toolbar.setBorder(new EmptyBorder(0, 0, 12, 0));
        JButton markAll = UIHelper.createButtonOutline("Tandai Semua Dibaca");
        markAll.addActionListener(e -> {
            notifCtrl.tandaiSemuaDibaca(currentUser.getId());
            removeAll();
            build();
            revalidate();
            repaint();
        });
        toolbar.add(markAll);
        content.add(toolbar, BorderLayout.NORTH);
        content.add(buildList(), BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JScrollPane buildList() {
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(UIHelper.BG);

        List<Notifikasi> notifs = notifCtrl.getByUser(currentUser.getId());
        if (notifs.isEmpty()) {
            list.add(UIHelper.label("Tidak ada notifikasi.", UIHelper.FONT_REG_13, UIHelper.TEXT3));
        }
        for (Notifikasi n : notifs) {
            list.add(buildRow(n));
            list.add(Box.createVerticalStrut(6));
        }
        return UIHelper.createScrollPane(list);
    }

    private JPanel buildRow(Notifikasi n) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(n.isSudahDibaca() ? UIHelper.SURFACE : UIHelper.TEAL_LIGHT);
        row.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(n.isSudahDibaca() ? UIHelper.BORDER : UIHelper.TEAL, 1, true),
            new EmptyBorder(10, 12, 10, 12)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));

        JLabel ic = UIHelper.label(n.getEmoji(), new Font("Segoe UI Emoji", Font.PLAIN, 18), UIHelper.TEXT);
        ic.setOpaque(true);
        ic.setBackground(UIHelper.AMBER_LIGHT);
        ic.setPreferredSize(new Dimension(36, 36));
        ic.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.setBackground(row.getBackground());
        txt.add(UIHelper.label(n.getJudul(), UIHelper.FONT_BOLD_13, UIHelper.TEXT));
        txt.add(UIHelper.label(n.getPesan(), UIHelper.FONT_REG_11, UIHelper.TEXT3));

        JLabel time = UIHelper.label(n.getWaktuLabel(), UIHelper.FONT_REG_11, UIHelper.TEXT3);
        row.add(ic, BorderLayout.WEST);
        row.add(txt, BorderLayout.CENTER);
        row.add(time, BorderLayout.EAST);
        row.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { n.tandaiDibaca(); }
        });
        return row;
    }
}