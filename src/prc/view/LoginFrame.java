package prc.view;

import prc.controller.DataStore;
import prc.model.Terapis;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblStatus;

    public LoginFrame() {
        setTitle("Physio Rehab Center (PRC) - Login");
        setSize(420, 380); // Ukuran frame yang ideal dan proporsional
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 102, 153));

        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 153));
        headerPanel.setPreferredSize(new Dimension(420, 95));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel lblJudul = new JLabel("🏥 Physio Rehab Center");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Sistem Informasi Manajemen Klinik Fisioterapi");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(200, 230, 255));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(Box.createVerticalGlue());
        headerPanel.add(lblJudul);
        headerPanel.add(Box.createVerticalStrut(6));
        headerPanel.add(lblSub);
        headerPanel.add(Box.createVerticalGlue());

        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.gridx = 0;
        gbc.weightx = 1.0; 

        
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 0;
        gbc.insets = new Insets(6, 0, 3, 0);
        formPanel.add(lblUser, gbc);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(300, 35)); 
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(txtUsername, gbc);

        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(300, 35)); 
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 4, 0);
        formPanel.add(txtPassword, gbc);

        
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblStatus.setForeground(Color.RED);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(lblStatus, gbc);

        
        btnLogin = new JButton("MASUK");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(0, 102, 153));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(300, 40));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 12, 0);
        formPanel.add(btnLogin, gbc);

        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        
        btnLogin.addActionListener(e -> prosesLogin());
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) prosesLogin();
            }
        });
    }

    private void prosesLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblStatus.setText("⚠ Username dan password tidak boleh kosong!");
            return;
        }

        // 1. COBA LOGIN SEBAGAI TERAPIS DULUAN
        Terapis terapis = DataStore.getInstance().loginTerapis(username, password);

        if (terapis != null) {
            lblStatus.setForeground(new Color(0, 150, 0));
            lblStatus.setText("✓ Login berhasil! Selamat datang, " + terapis.getNamaLengkap());
            
            String roleDinamis = "Terapis";
            if (username.equalsIgnoreCase("cuteka")) {
                roleDinamis = "Admin";
            }

            DashboardFrame dashboard = new DashboardFrame(roleDinamis);
            dashboard.setVisible(true);
            this.dispose();
            return;
        }

        
        String roleDinamis = "";
        String namaLengkapDemo = "";
        boolean loginDemoSukses = false;

        if (username.equals("rahma") && password.equals("farmasi123")) {
            roleDinamis = "Farmasi";
            namaLengkapDemo = "Rahma (Farmasi Klinik)";
            loginDemoSukses = true;
        } else if (username.equals("pebi") && password.equals("kasir123")) {
            roleDinamis = "Kasir";
            namaLengkapDemo = "Pebi (Kasir Keuangan)";
            loginDemoSukses = true;
        }

        if (loginDemoSukses) {
            lblStatus.setForeground(new Color(0, 150, 0));
            lblStatus.setText("✓ Login berhasil! Selamat datang, " + namaLengkapDemo);

            DashboardFrame dashboard = new DashboardFrame(roleDinamis);
            dashboard.setVisible(true);
            this.dispose();
        } else {
            lblStatus.setForeground(Color.RED);
            lblStatus.setText("✗ Username atau password salah!");
            txtPassword.setText("");
        }
    }
}