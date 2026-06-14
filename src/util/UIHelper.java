package util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIHelper {
    // === WARNA TEMA ===
    public static final Color TEAL        = new Color(0x0F9B8E);
    public static final Color TEAL_DARK   = new Color(0x0A6B62);
    public static final Color TEAL_LIGHT  = new Color(0xE0F5F3);
    public static final Color MINT        = new Color(0x4ECDC4);
    public static final Color BLUE        = new Color(0x2E86AB);
    public static final Color BLUE_LIGHT  = new Color(0xE3F2F9);
    public static final Color GREEN       = new Color(0x27AE60);
    public static final Color GREEN_LIGHT = new Color(0xE8F8EE);
    public static final Color AMBER       = new Color(0xF39C12);
    public static final Color AMBER_LIGHT = new Color(0xFEF5E7);
    public static final Color CORAL       = new Color(0xE74C3C);
    public static final Color CORAL_LIGHT = new Color(0xFDECEA);
    public static final Color PURPLE      = new Color(0x8E44AD);
    public static final Color PURPLE_LIGHT= new Color(0xF4ECF7);
    public static final Color BG          = new Color(0xF0FAF9);
    public static final Color SURFACE     = Color.WHITE;
    public static final Color BORDER      = new Color(0xD0EBE8);
    public static final Color TEXT        = new Color(0x1A2E2B);
    public static final Color TEXT2       = new Color(0x4A6B67);
    public static final Color TEXT3       = new Color(0x7A9B97);

    // === FONT ===
    public static final Font FONT_BOLD_20  = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_BOLD_16  = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BOLD_14  = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BOLD_13  = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_BOLD_12  = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_BOLD_11  = new Font("Segoe UI", Font.BOLD, 11);
    public static final Font FONT_REG_14   = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_REG_13   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_REG_12   = new Font("Segoe UI", Font.PLAIN, 12);
    public static Font FONT_BOLD_10() { return new Font("Segoe UI", Font.BOLD, 10); }
    public static final Font FONT_REG_11   = new Font("Segoe UI", Font.PLAIN, 11);

    // === PANEL CARD ===
    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(0, 0, 0, 0)
        ));
        return card;
    }

    public static JPanel createCardWithPadding(int pad) {
        JPanel card = createCard();
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(pad, pad, pad, pad)
        ));
        return card;
    }

    // === HEADER CARD ===
    public static JPanel createCardHeader(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SURFACE);
        header.setBorder(new EmptyBorder(12, 16, 12, 16));
        JLabel lbl = new JLabel(title);
        lbl.setFont(FONT_BOLD_13);
        lbl.setForeground(TEXT);
        header.add(lbl, BorderLayout.WEST);
        return header;
    }

    public static JPanel createCardHeader(String title, JComponent right) {
        JPanel header = createCardHeader(title);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    // === TOMBOL ===
    public static JButton createButtonPrimary(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(TEAL);
        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_BOLD_13);
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(TEAL_DARK); }
            public void mouseExited(MouseEvent e) { btn.setBackground(TEAL); }
        });
        return btn;
    }

    public static JButton createButtonOutline(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(SURFACE);
        btn.setForeground(TEXT2);
        btn.setFont(FONT_BOLD_12);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(6, 12, 6, 12)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(TEAL);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(TEAL, 1, true), new EmptyBorder(6, 12, 6, 12)));
            }
            public void mouseExited(MouseEvent e) {
                btn.setForeground(TEXT2);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER, 1, true), new EmptyBorder(6, 12, 6, 12)));
            }
        });
        return btn;
    }

    public static JButton createButtonSmall(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(FONT_BOLD_11);
        btn.setBorder(new EmptyBorder(4, 10, 4, 10));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JButton createButtonDanger(String text) {
        return createButtonSmall(text, CORAL_LIGHT, CORAL);
    }

    public static JButton createButtonSuccess(String text) {
        return createButtonSmall(text, GREEN_LIGHT, GREEN);
    }

    // === BADGE ===
    public static JLabel createBadge(String text, Color bg, Color fg) {
        JLabel badge = new JLabel(" " + text + " ") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        badge.setFont(FONT_BOLD_11);
        badge.setBackground(bg);
        badge.setForeground(fg);
        badge.setOpaque(false);
        badge.setBorder(new EmptyBorder(2, 6, 2, 6));
        return badge;
    }

    public static JLabel badgeGreen(String text)  { return createBadge(text, GREEN_LIGHT, GREEN); }
    public static JLabel badgeTeal(String text)   { return createBadge(text, TEAL_LIGHT, TEAL_DARK); }
    public static JLabel badgeBlue(String text)   { return createBadge(text, BLUE_LIGHT, BLUE); }
    public static JLabel badgeAmber(String text)  { return createBadge(text, AMBER_LIGHT, AMBER); }
    public static JLabel badgeCoral(String text)  { return createBadge(text, CORAL_LIGHT, CORAL); }
    public static JLabel badgePurple(String text) { return createBadge(text, PURPLE_LIGHT, PURPLE); }

    // === STATUS BADGE ===
    public static JLabel statusBadgeSesi(String status) {
        switch (status) {
            case "Selesai":     return badgeGreen(status);
            case "Berlangsung": return badgeTeal(status);
            case "Menunggu":    return badgeAmber(status);
            case "Dibatalkan":  return badgeCoral(status);
            default:            return badgeBlue(status);
        }
    }

    public static JLabel statusBadgePasien(String status) {
        switch (status) {
            case "Aktif":       return badgeGreen(status);
            case "Selesai":     return badgeTeal(status);
            case "Baru":        return badgeBlue(status);
            case "Tidak Aktif": return badgeCoral(status);
            default:            return badgeBlue(status);
        }
    }

    // === AVATAR ===
    public static JLabel createAvatar(String inisial, Color color, int size) {
        JLabel av = new JLabel(inisial) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, color, getWidth(), getHeight(), color.darker());
                g2.setPaint(gp);
                g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        av.setFont(new Font("Segoe UI", Font.BOLD, size > 40 ? 16 : 11));
        av.setForeground(Color.WHITE);
        av.setHorizontalAlignment(SwingConstants.CENTER);
        av.setPreferredSize(new Dimension(size, size));
        av.setMinimumSize(new Dimension(size, size));
        av.setMaximumSize(new Dimension(size, size));
        av.setOpaque(false);
        return av;
    }

    // === PROGRESS BAR ===
    public static JPanel createProgressBar(String label, int persen, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(SURFACE);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(SURFACE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_REG_12);
        lbl.setForeground(TEXT2);
        JLabel pct = new JLabel(persen + "%");
        pct.setFont(FONT_BOLD_12);
        pct.setForeground(color);
        top.add(lbl, BorderLayout.WEST);
        top.add(pct, BorderLayout.EAST);
        panel.add(top);
        panel.add(Box.createVerticalStrut(4));

        JPanel track = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BORDER);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                int w = (int) (getWidth() * (persen / 100.0));
                GradientPaint gp = new GradientPaint(0, 0, TEAL, w, 0, MINT);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, w, getHeight(), 8, 8);
            }
        };
        track.setPreferredSize(new Dimension(Integer.MAX_VALUE, 8));
        track.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        track.setBackground(new Color(0, 0, 0, 0));
        panel.add(track);
        return panel;
    }

    // === SEPARATOR ===
    public static JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setBackground(BORDER);
        return sep;
    }

    // === LABEL ===
    public static JLabel label(String text, Font font, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(color);
        return lbl;
    }

    // === INPUT FIELD ===
    public static JTextField createTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setFont(FONT_REG_13);
        tf.setForeground(TEXT);
        tf.setBackground(SURFACE);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        return tf;
    }

    public static JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_REG_13);
        cb.setBackground(SURFACE);
        cb.setForeground(TEXT);
        return cb;
    }

    public static JTextArea createTextArea(int rows, int cols) {
        JTextArea ta = new JTextArea(rows, cols);
        ta.setFont(FONT_REG_13);
        ta.setForeground(TEXT);
        ta.setBackground(SURFACE);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(new EmptyBorder(8, 10, 8, 10));
        return ta;
    }

    // === SCROLL PANE ===
    public static JScrollPane createScrollPane(Component comp) {
        JScrollPane sp = new JScrollPane(comp);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.setBackground(BG);
        return sp;
    }

    // === SHOW DIALOG ===
    public static void showSuccess(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean showConfirm(Component parent, String msg) {
        return JOptionPane.showConfirmDialog(parent, msg, "Konfirmasi",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // === WARNA AVATAR ---
    public static Color[] AVATAR_COLORS = {TEAL, BLUE, PURPLE, CORAL, AMBER, GREEN};
    public static Color getAvatarColor(int index) {
        return AVATAR_COLORS[index % AVATAR_COLORS.length];
    }

    // === INISIAL dari nama ===
    public static String getInisial(String nama) {
        if (nama == null || nama.isEmpty()) return "?";
        String[] parts = nama.trim().split("\\s+");
        if (parts.length >= 2) {
            return ("" + parts[0].charAt(0) + parts[parts.length - 1].charAt(0)).toUpperCase();
        }
        return nama.substring(0, Math.min(2, nama.length())).toUpperCase();
    }
}


