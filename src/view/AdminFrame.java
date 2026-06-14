package view;

import panel.PasienPanel;
import view.panel.DashboardPanel;
import view.panel.RekamMedisPanel;
import view.panel.TerapisPanel;
import view.panel.JadwalPanel;
import view.panel.NotifikasiPanel;
import controller.*;
import data.DataStore;
import model.*;
import util.UIHelper;
import view.panel.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class AdminFrame extends JFrame {
    private User currentUser;
    private JPanel contentArea;
    private CardLayout cardLayout;
    private JLabel pageTitleLabel;

    // Controllers
    private SesiController sesiCtrl;
    private PasienController pasienCtrl;
    private TerapisController terapisCtrl;
    private TagihanController tagihanCtrl;
    private NotifikasiController notifCtrl;

    public AdminFrame(User user) {
        this.currentUser = user;
        initControllers();
        initUI();
    }

    private void initControllers() {
        DataStore ds = DataStore.getInstance();
        sesiCtrl    = new SesiController(ds);
        pasienCtrl  = new PasienController(ds);
        terapisCtrl = new TerapisController(ds);
        tagihanCtrl = new TagihanController(ds);
        notifCtrl   = new NotifikasiController(ds);
    }

    private void initUI() {
        setTitle("PhysioHub - " + currentUser.getDashboardTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 720);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIHelper.BG);
        setContentPane(root);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainArea(), BorderLayout.CENTER);

        // Default halaman
        showPage("dashboard");
        setVisible(true);
    }

    // ===== SIDEBAR =====
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UIHelper.SURFACE);
        sidebar.setPreferredSize(new Dimension(215, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UIHelper.BORDER));

        sidebar.add(createLogoArea());
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(createNavSection("Menu Utama", new String[][]{
            {"dashboard",   "🏠", "Dashboard"},
            {"jadwal",      "📅", "Jadwal Sesi"},
            {"pasien",      "👥", "Pasien"},
            {"terapis",     "🩺", "Terapis"},
        }));
        sidebar.add(createNavSection("Klinis", new String[][]{
            {"rekam",       "📋", "Rekam Medis"},
            {"latihan",     "🏃", "Program Latihan"},
            {"tagihan",     "🧾", "Tagihan"},
        }));
        sidebar.add(createNavSection("Sistem", new String[][]{
            {"analitik",    "📊", "Analitik"},
            {"notifikasi",  "🔔", "Notifikasi"},
            {"pengaturan",  "⚙", "Pengaturan"},
        }));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(createUserCard());

        return sidebar;
    }

    private JPanel createLogoArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 14));
        panel.setBackground(UIHelper.SURFACE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Logo icon
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
        JLabel name = UIHelper.label("PhysioHub", UIHelper.FONT_BOLD_14, UIHelper.TEAL_DARK);
        JLabel sub  = UIHelper.label("Manajemen Terapis", UIHelper.FONT_REG_11, UIHelper.TEXT3);
        txt.add(name); txt.add(sub);

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
            btn.setName(pageId);

            btn.addActionListener(e -> showPage(pageId));
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!btn.getBackground().equals(UIHelper.TEAL_LIGHT)) {
                        btn.setBackground(new Color(0xF0FAF9));
                    }
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!btn.getBackground().equals(UIHelper.TEAL_LIGHT)) {
                        btn.setBackground(UIHelper.SURFACE);
                    }
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

        String inisial = UIHelper.getInisial(currentUser.getNama());
        JLabel av = UIHelper.createAvatar(inisial, UIHelper.TEAL, 32);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UIHelper.TEAL_LIGHT);

        String shortName = currentUser.getNama().length() > 14 ?
            currentUser.getNama().substring(0, 13) + "..." : currentUser.getNama();
        JLabel nameLbl = UIHelper.label(shortName, UIHelper.FONT_BOLD_12, UIHelper.TEAL_DARK);
        JLabel roleLbl = UIHelper.label(currentUser.getRole(), UIHelper.FONT_REG_11, UIHelper.TEXT3);
        info.add(nameLbl); info.add(roleLbl);

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

        // Topbar
        area.add(createTopbar(), BorderLayout.NORTH);

        // Content (CardLayout)
        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setBackground(UIHelper.BG);

        // Tambahkan semua panel halaman
        contentArea.add(new DashboardPanel(currentUser, sesiCtrl, pasienCtrl, this), "dashboard");
        contentArea.add(new JadwalPanel(sesiCtrl, pasienCtrl, terapisCtrl, currentUser, this), "jadwal");
        contentArea.add(new PasienPanel(pasienCtrl, terapisCtrl, sesiCtrl, this), "pasien");
        contentArea.add(new TerapisPanel(terapisCtrl, sesiCtrl, this), "terapis");
        contentArea.add(new RekamMedisPanel(pasienCtrl, sesiCtrl, currentUser), "rekam");
        contentArea.add(new LatihanPanel(sesiCtrl, pasienCtrl), "latihan");
        contentArea.add(new TagihanPanel(tagihanCtrl, pasienCtrl), "tagihan");
        contentArea.add(new AnalitikPanel(sesiCtrl, pasienCtrl, terapisCtrl, tagihanCtrl), "analitik");
        contentArea.add(new NotifikasiPanel(notifCtrl, currentUser), "notifikasi");
        contentArea.add(new PengaturanPanel(currentUser), "pengaturan");

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

        pageTitleLabel = UIHelper.label("Dashboard", UIHelper.FONT_BOLD_16, UIHelper.TEXT);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setBackground(UIHelper.SURFACE);

        JButton newSesiBtn = UIHelper.createButtonPrimary("+ Sesi Baru");
        newSesiBtn.addActionListener(e -> showPage("jadwal"));

        right.add(newSesiBtn);
        bar.add(pageTitleLabel, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    // ===== NAVIGASI =====
    public void showPage(String pageId) {
        cardLayout.show(contentArea, pageId);

        String[] titles = {
            "dashboard:Dashboard","jadwal:Jadwal Sesi","pasien:Data Pasien",
            "terapis:Terapis","rekam:Rekam Medis","latihan:Program Latihan",
            "tagihan:Tagihan & Pembayaran","analitik:Analitik","notifikasi:Notifikasi",
            "pengaturan:Pengaturan"
        };
        for (String t : titles) {
            String[] parts = t.split(":");
            if (parts[0].equals(pageId)) {
                pageTitleLabel.setText(parts[1]);
                break;
            }
        }
    }
}
