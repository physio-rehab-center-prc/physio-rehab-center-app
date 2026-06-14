package view;

import panel.RekamMedisPasienPanel;
import panel.LatihanPasienPanel;
import panel.ProfilPasienPanel;
import panel.NotifikasiPasienPanel;
import panel.TagihanPasienPanel;
import panel.JadwalPasienPanel;
import panel.HomePasienPanel;
import controller.*;
import data.DataStore;
import model.*;
import util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class PasienFrame extends JFrame {
    private User currentUser;
    private Pasien pasien;
    private JPanel contentArea;
    private CardLayout cardLayout;
    private JLabel pageTitleLabel;

    private SesiController sesiCtrl;
    private TagihanController tagihanCtrl;
    private NotifikasiController notifCtrl;
    private PasienController pasienCtrl;

    public PasienFrame(User user) {
        this.currentUser = user;

        
        if (!(user instanceof Pasien)) {
            JOptionPane.showMessageDialog(null,
                "Error: Akun ini bukan pasien!\nRole: " + user.getRole() + "\nClass: " + user.getClass().getName(),
                "Error Login", JOptionPane.ERROR_MESSAGE);
            new LoginFrame();
            return;
        }

        try {
            this.pasien = (Pasien) user;
            initControllers();
            initUI();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Gagal membuka dashboard pasien:\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initControllers() {
        DataStore ds = DataStore.getInstance();
        sesiCtrl    = new SesiController(ds);
        tagihanCtrl = new TagihanController(ds);
        notifCtrl   = new NotifikasiController(ds);
        pasienCtrl  = new PasienController(ds);
    }

    private void initUI() {
        setTitle("PhysioHub - " + pasien.getDashboardTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 700);
        setMinimumSize(new Dimension(960, 580));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIHelper.BG);
        setContentPane(root);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainArea(), BorderLayout.CENTER);

        showPage("home");
        setVisible(true);
    }

    // ===== SIDEBAR PASIEN =====
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UIHelper.SURFACE);
        sidebar.setPreferredSize(new Dimension(215, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER));

        sidebar.add(createLogoArea());
        sidebar.add(Box.createVerticalStrut(8));

        sidebar.add(createNavSection("Menu Saya", new String[][]{
            {"home",         "🏠", "Beranda"},
            {"jadwal-saya",  "📅", "Jadwal Saya"},
            {"rekam-saya",   "📋", "Rekam Medis Saya"},
            {"latihan-saya", "🏃", "Program Latihan"},
        }));
        sidebar.add(createNavSection("Keuangan", new String[][]{
            {"tagihan-saya", "💳", "Tagihan Saya"},
        }));
        sidebar.add(createNavSection("Akun", new String[][]{
            {"notif-saya",   "🔔", "Notifikasi"},
            {"profil-saya",  "👤", "Profil Saya"},
        }));

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(createUserCard());
        return sidebar;
    }

    private JPanel createLogoArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 14));
        panel.setBackground(UIHelper.SURFACE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel icon = new JLabel("+") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0,0,UIHelper.TEAL,getWidth(),getHeight(),UIHelper.MINT);
                g2.setPaint(gp);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),8,8);
                super.paintComponent(g);
            }
        };
        icon.setFont(new Font("Segoe UI",Font.BOLD,22));
        icon.setForeground(Color.WHITE);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setPreferredSize(new Dimension(36, 36));

        JPanel txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.setBackground(UIHelper.SURFACE);
        txt.add(UIHelper.label("PhysioHub", UIHelper.FONT_BOLD_14, UIHelper.TEAL_DARK));
        txt.add(UIHelper.label("Portal Pasien", UIHelper.FONT_REG_11, UIHelper.TEXT3));

        panel.add(icon); panel.add(txt);
        panel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER));
        return panel;
    }

    private JPanel createNavSection(String title, String[][] items) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(UIHelper.SURFACE);
        section.setBorder(new EmptyBorder(8, 8, 4, 8));

        JLabel lbl = UIHelper.label(title.toUpperCase(), new Font("Segoe UI",Font.BOLD,10), UIHelper.TEXT3);
        lbl.setBorder(new EmptyBorder(4,4,4,4));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(lbl);

        for (String[] item : items) {
            String pageId = item[0], icon = item[1], name = item[2];
            JButton btn = new JButton(icon + "  " + name);
            btn.setFont(UIHelper.FONT_REG_13);
            btn.setForeground(UIHelper.TEXT2);
            btn.setBackground(UIHelper.SURFACE);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(true);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            btn.setBorder(new EmptyBorder(6,10,6,10));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.addActionListener(e -> showPage(pageId));
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    btn.setBackground(new Color(0xF0FAF9));
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!btn.getBackground().equals(UIHelper.TEAL_LIGHT))
                        btn.setBackground(UIHelper.SURFACE);
                }
            });
            section.add(btn);
        }
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return section;
    }

    private JPanel createUserCard() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(UIHelper.TEAL_LIGHT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        panel.setBorder(BorderFactory.createMatteBorder(1,0,0,0,UIHelper.BORDER));

        String inisial = UIHelper.getInisial(pasien.getNama());
        JLabel av = UIHelper.createAvatar(inisial, UIHelper.TEAL, 32);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIHelper.TEAL_LIGHT);
        String shortName = pasien.getNama().length() > 14 ?
            pasien.getNama().substring(0,13) + "..." : pasien.getNama();
        info.add(UIHelper.label(shortName, UIHelper.FONT_BOLD_12, UIHelper.TEAL_DARK));
        info.add(UIHelper.label("Pasien", UIHelper.FONT_REG_11, UIHelper.TEXT3));

        JButton logout = new JButton("Keluar");
        logout.setFont(UIHelper.FONT_BOLD_11);
        logout.setForeground(UIHelper.CORAL);
        logout.setBackground(UIHelper.TEAL_LIGHT);
        logout.setBorderPainted(false);
        logout.setContentAreaFilled(false);
        logout.setFocusPainted(false);
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.addActionListener(e -> {
            if (UIHelper.showConfirm(this, "Yakin ingin keluar?")) {
                dispose();
                new LoginFrame();
            }
        });

        panel.add(av); panel.add(info); panel.add(logout);
        return panel;
    }

    // ===== MAIN AREA =====
    private JPanel createMainArea() {
        JPanel area = new JPanel(new BorderLayout());
        area.setBackground(UIHelper.BG);
        area.add(createTopbar(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(UIHelper.BG);

        // Panel-panel khusus pasien
        contentArea.add(new HomePasienPanel(pasien, sesiCtrl, tagihanCtrl, this), "home");
        contentArea.add(new JadwalPasienPanel(pasien, sesiCtrl, this), "jadwal-saya");
        contentArea.add(new RekamMedisPasienPanel(pasien, sesiCtrl), "rekam-saya");
        contentArea.add(new LatihanPasienPanel(pasien, sesiCtrl), "latihan-saya");
        contentArea.add(new TagihanPasienPanel(pasien, tagihanCtrl), "tagihan-saya");
        contentArea.add(new NotifikasiPasienPanel(pasien, notifCtrl), "notif-saya");
        contentArea.add(new ProfilPasienPanel(pasien, pasienCtrl, this), "profil-saya");

        area.add(UIHelper.createScrollPane(contentArea), BorderLayout.CENTER);
        return area;
    }

    private JPanel createTopbar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(UIHelper.SURFACE);
        bar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,UIHelper.BORDER),
            new EmptyBorder(0,22,0,22)
        ));
        bar.setPreferredSize(new Dimension(0, 56));

        pageTitleLabel = UIHelper.label("Beranda", UIHelper.FONT_BOLD_16, UIHelper.TEXT);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setBackground(UIHelper.SURFACE);

        // Tombol buat janji
        JButton btnJanji = UIHelper.createButtonPrimary("+ Buat Janji");
        btnJanji.addActionListener(e -> showPage("jadwal-saya"));

        // Notifikasi badge
        long unread = DataStore.getInstance().countUnreadNotifikasi(pasien.getId());
        JButton notifBtn = new JButton("🔔" + (unread > 0 ? " (" + unread + ")" : ""));
        notifBtn.setFont(UIHelper.FONT_BOLD_12);
        notifBtn.setForeground(unread > 0 ? UIHelper.CORAL : UIHelper.TEXT2);
        notifBtn.setBackground(UIHelper.SURFACE);
        notifBtn.setBorderPainted(false);
        notifBtn.setContentAreaFilled(false);
        notifBtn.setFocusPainted(false);
        notifBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        notifBtn.addActionListener(e -> showPage("notif-saya"));

        right.add(notifBtn);
        right.add(btnJanji);
        bar.add(pageTitleLabel, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    public void showPage(String pageId) {
        cardLayout.show(contentArea, pageId);
        String[] map = {
            "home:Beranda","jadwal-saya:Jadwal Saya","rekam-saya:Rekam Medis Saya",
            "latihan-saya:Program Latihan","tagihan-saya:Tagihan Saya",
            "notif-saya:Notifikasi","profil-saya:Profil Saya"
        };
        for (String m : map) {
            String[] p = m.split(":");
            if (p[0].equals(pageId)) { pageTitleLabel.setText(p[1]); break; }
        }
    }

    public Pasien getPasien() { return pasien; }
}
