package view;

import controller.AuthController;
import model.User;
import util.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;


public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JLabel statusLabel;
    private AuthController authController;

    public LoginFrame() {
        authController = new AuthController();
        initUI();
    }

    private void initUI() {
        setTitle("PhysioHub - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 580);
        setMinimumSize(new Dimension(800, 520));
        setLocationRelativeTo(null);
        setResizable(true);

        
        JPanel root = new JPanel(new GridLayout(1, 2));
        root.setBackground(UIHelper.TEAL_DARK);
        setContentPane(root);

        JPanel leftPanel = createBrandingPanel();
        root.add(leftPanel);

        JPanel rightPanel = createFormPanel();
        root.add(rightPanel);

        setVisible(true);
    }

    private JPanel createBrandingPanel() {
        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, UIHelper.TEAL_DARK,
                        getWidth(), getHeight(), new Color(0x0F9B8E));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);

        // Logo
        JPanel logoPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255,255,255,40));
                g2.fillOval(0, 0, 90, 90);
                g2.setColor(new Color(255,255,255,80));
                g2.fillOval(10, 10, 70, 70);
                // Salib plus
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(30, 45, 60, 45);
                g2.drawLine(45, 30, 45, 60);
                // Pin lokasi outline
                g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(255,255,255,150));
                g2.drawOval(35, 32, 20, 20);
                g2.drawLine(37, 50, 45, 65);
                g2.drawLine(53, 50, 45, 65);
            }
        };
        logoPanel.setOpaque(false);
        logoPanel.setPreferredSize(new Dimension(90, 90));
        logoPanel.setMaximumSize(new Dimension(90, 90));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("PhysioHub");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Manajemen Terapis Fisioterapi");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(new Color(255, 255, 255, 180));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        String[] fiturList = {
            
        };

        JPanel fiturPanel = new JPanel();
        fiturPanel.setLayout(new BoxLayout(fiturPanel, BoxLayout.Y_AXIS));
        fiturPanel.setOpaque(false);
        fiturPanel.setBorder(new EmptyBorder(20, 10, 0, 10));
        fiturPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (String f : fiturList) {
            JLabel fl = new JLabel(f);
            fl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            fl.setForeground(new Color(255,255,255,200));
            fl.setAlignmentX(Component.LEFT_ALIGNMENT);
            fl.setBorder(new EmptyBorder(3, 0, 3, 0));
            fiturPanel.add(fl);
        }

        inner.add(logoPanel);
        inner.add(Box.createVerticalStrut(12));
        inner.add(title);
        inner.add(Box.createVerticalStrut(6));
        inner.add(sub);
        inner.add(fiturPanel);

        // Copyright
        JLabel copy = new JLabel("© 2026 PhysioHub — Sistem Manajemen Fisioterapi");
        copy.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        copy.setForeground(new Color(255,255,255,100));
        copy.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(Box.createVerticalStrut(30));
        inner.add(copy);

        panel.add(inner);
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UIHelper.SURFACE);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(UIHelper.SURFACE);
        form.setPreferredSize(new Dimension(340, 400));

        JLabel welcome = UIHelper.label("Selamat Datang!", UIHelper.FONT_BOLD_20, UIHelper.TEXT);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = UIHelper.label("Masuk ke akun Anda untuk melanjutkan.", UIHelper.FONT_REG_13, UIHelper.TEXT3);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(welcome);
        form.add(Box.createVerticalStrut(6));
        form.add(desc);
        form.add(Box.createVerticalStrut(28));

        // Email
        JLabel emailLbl = UIHelper.label("Email", UIHelper.FONT_BOLD_12, UIHelper.TEXT2);
        emailLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField = new JTextField();
        emailField.setFont(UIHelper.FONT_REG_13);
        emailField.setForeground(UIHelper.TEXT);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(UIHelper.BORDER, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        

        // Password
        JLabel passLbl = UIHelper.label("Password", UIHelper.FONT_BOLD_12, UIHelper.TEXT2);
        passLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField = new JPasswordField();
        passwordField.setFont(UIHelper.FONT_REG_13);
        passwordField.setForeground(UIHelper.TEXT);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(UIHelper.BORDER, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        

        // Login Button
        loginBtn = new JButton("Masuk ke PhysioHub") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, UIHelper.TEAL, getWidth(), 0, UIHelper.MINT);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        loginBtn.setFont(UIHelper.FONT_BOLD_14);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(UIHelper.FONT_REG_12);
        statusLabel.setForeground(UIHelper.CORAL);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Akun demo hint
        JPanel demoPanel = new JPanel();
        demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));
        demoPanel.setBackground(UIHelper.TEAL_LIGHT);
        demoPanel.setBorder(BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(UIHelper.BORDER, 1, true),
            new EmptyBorder(10, 12, 10, 12)
        ));
        demoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        demoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        
        form.add(emailLbl); form.add(Box.createVerticalStrut(4));
        form.add(emailField); form.add(Box.createVerticalStrut(14));
        form.add(passLbl); form.add(Box.createVerticalStrut(4));
        form.add(passwordField); form.add(Box.createVerticalStrut(18));
        form.add(loginBtn); form.add(Box.createVerticalStrut(8));
        form.add(statusLabel); form.add(Box.createVerticalStrut(20));
        form.add(demoPanel);

        panel.add(form);

        
        loginBtn.addActionListener(this::doLogin);
        passwordField.addActionListener(this::doLogin);
        emailField.addActionListener(e -> passwordField.requestFocus());

        return panel;
    }

    private void doLogin(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Email dan password tidak boleh kosong!");
            return;
        }

        loginBtn.setEnabled(false);
        loginBtn.setText("Memverifikasi...");
        statusLabel.setText(" ");

        SwingWorker<User, Void> worker = new SwingWorker<>() {
            protected User doInBackground() throws Exception {
                Thread.sleep(400); // simulasi loading
                return authController.login(email, password);
            }
            protected void done() {
                try {
                    User user = get();
                    if (user != null) {
                        dispose();
                        openDashboard(user);
                    } else {
                        statusLabel.setText("Email atau password salah!");
                        loginBtn.setEnabled(true);
                        loginBtn.setText("Masuk ke PhysioHub");
                    }
                } catch (Exception ex) {
                    statusLabel.setText("Terjadi kesalahan: " + ex.getMessage());
                    loginBtn.setEnabled(true);
                    loginBtn.setText("Masuk ke PhysioHub");
                }
            }
        };
        worker.execute();
    }

    private void openDashboard(User user) {
        switch (user.getRole()) {
            case "ADMIN":   new AdminFrame(user); break;
            case "TERAPIS": new AdminFrame(user); break; 
            case "PASIEN":  new PasienFrame(user); break;
        }
    }
}
