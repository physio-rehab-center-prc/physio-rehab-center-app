/**
 * PhysioRehabCenter — Tugas Besar Pemrograman Berorientasi Objek
 * Anggota Kelompok: Rahma | Pebi | Eka | Risma
 */
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
        setSize(420, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        // Panel utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 102, 153));

        // Panel header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 153));
        headerPanel.setPreferredSize(new Dimension(420, 90));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel lblJudul = new JLabel("🏥 Physio Rehab Center");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Sistem Informasi Manajemen Klinik Fisioterapi");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(200, 230, 255));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(Box.createVerticalGlue());
        headerPanel.add(lblJudul);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblSub);
        headerPanel.add(Box.createVerticalGlue());

        // Panel form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);

        // Username
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUser, gbc);

        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setPreferredSize(new Dimension(300, 32));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        gbc.gridy = 1;
        formPanel.add(txtUsername, gbc);

        // Password
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 2;
        formPanel.add(lblPass, gbc);

        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setPreferredSize(new Dimension(300, 32));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        gbc.gridy = 3;
        formPanel.add(txtPassword, gbc);

        // Status label
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblStatus.setForeground(Color.RED);
        gbc.gridy = 4;
        formPanel.add(lblStatus, gbc);

        // Tombol Login
        btnLogin = new JButton("MASUK");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBackground(new Color(0, 102, 153));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(300, 38));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        formPanel.add(btnLogin, gbc);

        // Hint akun
        
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Event listener
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

        Terapis terapis = DataStore.getInstance().loginTerapis(username, password);

        if (terapis != null) {
            lblStatus.setForeground(new Color(0, 150, 0));
            lblStatus.setText("✓ Login berhasil! Selamat datang, " + terapis.getNamaLengkap());
            // Buka Dashboard
            DashboardFrame dashboard = new DashboardFrame();
            dashboard.setVisible(true);
            this.dispose();
        } else {
            lblStatus.setForeground(Color.RED);
            lblStatus.setText("✗ Username atau password salah!");
            txtPassword.setText("");
        }
    }
}
