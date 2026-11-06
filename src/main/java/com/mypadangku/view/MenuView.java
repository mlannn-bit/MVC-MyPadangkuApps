
package com.mypadangku.view;

import com.mypadangku.controller.MenuController;
import com.mypadangku.errors.MenuException;
import com.mypadangku.model.Menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuView extends JFrame {

    // Warna aplikasi
    private static final Color MAROON = new Color(0x7B1E26);
    private static final Color MAROON_DARK = new Color(0x5A1219);
    private static final Color GOLD = new Color(0xC9A227);
    private static final Color GOLD_SOFT = new Color(0xF5ECCB);
    private static final Color BACKGROUND = new Color(0xFAF7F2);
    private static final Color BORDER_COLOR = new Color(0xE3DCD0);
    private static final Color TEXT_DARK = new Color(0x2B2B2B);
    private static final Color TEXT_MUTED = new Color(0x7A746B);
    private static final Color ZEBRA = new Color(0xFCFAF6);
    private static final Color DANGER = new Color(0xB3261E);

    private static final Font FONT_BASE =
            new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BOLD =
            new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_LABEL =
            new Font("Segoe UI", Font.BOLD, 12);

    private static final String ID_PLACEHOLDER = "Otomatis";

    private final MenuController controller;

    // Komponen form
    private final JTextField txtId = new JTextField();
    private final JTextField txtNama = new JTextField();
    private final JTextField txtHarga = new JTextField();

    private final JComboBox<String> cmbKategori =
            new JComboBox<>(new String[]{"Makanan", "Minuman"});

    private final JTextField txtStok = new JTextField();

    // Tombol
    private final RoundedButton btnTambah =
            new RoundedButton("Tambah Menu", RoundedButton.Style.PRIMARY);

    private final RoundedButton btnUbah =
            new RoundedButton("Ubah Menu", RoundedButton.Style.GOLD);

    private final RoundedButton btnHapus =
            new RoundedButton("Hapus Menu", RoundedButton.Style.DANGER);

    private final RoundedButton btnReset =
            new RoundedButton("Reset Form", RoundedButton.Style.NEUTRAL);

    // Tabel
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JLabel lblStatus = new JLabel();

    private List<Menu> currentData = new ArrayList<>();
    private boolean updatingTable = false;

    public MenuView(MenuController controller) {
        this.controller = controller;

        setTitle("MyPadangKu - Kelola Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 620));
        setSize(1120, 680);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(
                new Object[]{
                    "ID Menu",
                    "Nama Menu",
                    "Harga",
                    "Kategori",
                    "Stok Siap Jual"
                }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildContent(), BorderLayout.CENTER);
        root.add(buildStatusBar(), BorderLayout.SOUTH);

        setContentPane(root);

        initFormState();
        registerListeners();
        muatDataDariController();
    }

    // ==========================================
    // HEADER
    // ==========================================

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MAROON);
        header.setBorder(new EmptyBorder(16, 28, 14, 28));

        JLabel lblApp = new JLabel("MyPadangKu");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblApp.setForeground(GOLD);

        JLabel lblTitle = new JLabel("Kelola Menu");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblDesc = new JLabel(
                "Manajemen daftar makanan dan minuman"
        );
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDesc.setForeground(new Color(0xE9D9DB));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        text.add(lblApp);
        text.add(Box.createVerticalStrut(2));
        text.add(lblTitle);
        text.add(Box.createVerticalStrut(2));
        text.add(lblDesc);

        header.add(text, BorderLayout.WEST);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(header, BorderLayout.CENTER);

        JPanel goldLine = new JPanel();
        goldLine.setBackground(GOLD);
        goldLine.setPreferredSize(new Dimension(0, 3));

        wrapper.add(goldLine, BorderLayout.SOUTH);

        return wrapper;
    }

    // ==========================================
    // KONTEN UTAMA
    // ==========================================

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(20, 0));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(20, 28, 12, 28));

        content.add(buildFormCard(), BorderLayout.WEST);
        content.add(buildTableCard(), BorderLayout.CENTER);

        return content;
    }

    // ==========================================
    // FORM MENU
    // ==========================================

    private JPanel buildFormCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 14));
        card.setPreferredSize(new Dimension(330, 0));

        JLabel title = new JLabel("Form Menu");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(MAROON);

        card.add(title, BorderLayout.NORTH);

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 4, 0);

        int row = 0;

        row = addField(fields, gbc, row, "ID Menu", txtId);
        row = addField(fields, gbc, row, "Nama Menu", txtNama);
        row = addField(fields, gbc, row, "Harga (Rp)", txtHarga);
        row = addField(fields, gbc, row, "Kategori", cmbKategori);

        addField(fields, gbc, row, "Stok Siap Jual", txtStok);

        gbc.gridy = 100;
        gbc.weighty = 1;
        fields.add(Box.createVerticalGlue(), gbc);

        card.add(fields, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(2, 2, 10, 10));
        buttons.setOpaque(false);

        buttons.add(btnTambah);
        buttons.add(btnUbah);
        buttons.add(btnHapus);
        buttons.add(btnReset);

        card.add(buttons, BorderLayout.SOUTH);

        return card;
    }

    private int addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent input) {

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);

        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 4, 0);
        panel.add(lbl, gbc);

        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 14, 0);
        panel.add(input, gbc);

        return row;
    }

    // ==========================================
    // TABEL MENU
    // ==========================================

    private JPanel buildTableCard() {
        JPanel card = createCard();
        card.setLayout(new BorderLayout(0, 14));

        JLabel title = new JLabel("Daftar Menu");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(MAROON);

        card.add(title, BorderLayout.NORTH);

        configureTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(BORDER_COLOR));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BACKGROUND);
        bar.setBorder(new EmptyBorder(0, 28, 12, 28));

        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStatus.setForeground(TEXT_MUTED);

        bar.add(lblStatus, BorderLayout.WEST);

        return bar;
    }

    // ==========================================
    // STYLING
    // ==========================================

    private JPanel createCard() {
        JPanel card = new JPanel();

        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(18, 18, 18, 18)
        ));

        return card;
    }

    private void initFormState() {
        styleTextField(txtId);
        styleTextField(txtNama);
        styleTextField(txtHarga);
        styleTextField(txtStok);

        txtId.setEditable(false);
        txtId.setFocusable(false);
        txtId.setBackground(new Color(0xF1EDE6));
        txtId.setForeground(TEXT_MUTED);
        txtId.setText(ID_PLACEHOLDER);

        cmbKategori.setFont(FONT_BASE);
        cmbKategori.setBackground(Color.WHITE);
        cmbKategori.setPreferredSize(new Dimension(0, 36));

        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }

    private void styleTextField(JTextField field) {
        field.setFont(FONT_BASE);
        field.setForeground(TEXT_DARK);
        field.setCaretColor(MAROON);
        field.setPreferredSize(new Dimension(0, 36));
        field.setBorder(fieldBorder(BORDER_COLOR));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.isEditable()) {
                    field.setBorder(fieldBorder(GOLD));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(fieldBorder(BORDER_COLOR));
            }
        });
    }

    private javax.swing.border.Border fieldBorder(Color color) {
        return BorderFactory.createCompoundBorder(
                new LineBorder(color, 1, true),
                new EmptyBorder(4, 10, 4, 10)
        );
    }

    private void configureTable() {
        table.setFont(FONT_BASE);
        table.setForeground(TEXT_DARK);
        table.setRowHeight(34);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(0xEFEAE0));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(GOLD_SOFT);
        table.setSelectionForeground(MAROON_DARK);

        JTableHeader th = table.getTableHeader();
        th.setReorderingAllowed(false);
        th.setPreferredSize(new Dimension(0, 38));

        th.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel,
                    boolean foc, int r, int c) {

                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        t, v, false, false, r, c
                );

                lbl.setBackground(MAROON);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(FONT_BOLD);
                lbl.setBorder(new EmptyBorder(0, 12, 0, 12));

                lbl.setHorizontalAlignment(
                        c == 2 || c == 4 ? SwingConstants.RIGHT
                        : c == 0 || c == 3 ? SwingConstants.CENTER
                        : SwingConstants.LEFT
                );

                return lbl;
            }
        });

        int[] align = {
            SwingConstants.CENTER,
            SwingConstants.LEFT,
            SwingConstants.RIGHT,
            SwingConstants.CENTER,
            SwingConstants.RIGHT
        };

        for (int i = 0; i < align.length; i++) {
            final int a = align[i];

            table.getColumnModel().getColumn(i)
                    .setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(
                        JTable t, Object v, boolean sel,
                        boolean foc, int r, int c) {

                    JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                            t, v, sel, false, r, c
                    );

                    lbl.setHorizontalAlignment(a);
                    lbl.setBorder(new EmptyBorder(0, 12, 0, 12));

                    if (!sel) {
                        lbl.setBackground(
                                r % 2 == 0 ? Color.WHITE : ZEBRA
                        );
                    }

                    return lbl;
                }
            });
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(240);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
    }

    // ==========================================
    // EVENT LISTENER
    // ==========================================

    private void registerListeners() {
        btnTambah.addActionListener(e -> aksiTambah());
        btnUbah.addActionListener(e -> aksiUbah());
        btnHapus.addActionListener(e -> aksiHapus());
        btnReset.addActionListener(e -> resetForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting() || updatingTable) {
                return;
            }

            int row = table.getSelectedRow();

            if (row >= 0 && row < currentData.size()) {
                isiForm(currentData.get(row));
            }
        });
    }

    // ==========================================
    // TAMBAH MENU
    // ==========================================

    private void aksiTambah() {
        Menu menu = bacaFormSebagaiMenu(null);

        if (menu == null) {
            return;
        }

        try {
            controller.tambahMenu(menu);

            showInfo("Menu berhasil ditambahkan.");

            resetForm();
            muatDataDariController();

        } catch (MenuException ex) {
            showWarning(ex.getMessage());

        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // ==========================================
    // UBAH MENU
    // ==========================================

    private void aksiUbah() {
        Menu selected = getSelectedMenu();

        if (selected == null) {
            showWarning("Pilih menu pada tabel terlebih dahulu.");
            return;
        }

        Menu menu = bacaFormSebagaiMenu(selected.getIdMenu());

        if (menu == null) {
            return;
        }

        try {
            controller.ubahMenu(menu);

            showInfo("Menu berhasil diubah.");

            resetForm();
            muatDataDariController();

        } catch (MenuException ex) {
            showWarning(ex.getMessage());

        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // ==========================================
    // HAPUS MENU
    // ==========================================

    private void aksiHapus() {
        Menu selected = getSelectedMenu();

        if (selected == null) {
            showWarning("Pilih menu pada tabel terlebih dahulu.");
            return;
        }

        int ok = JOptionPane.showConfirmDialog(
                this,
                "Hapus menu \"" + selected.getNamaMenu()
                        + "\"?\nTindakan ini tidak dapat dibatalkan.",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (ok != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            controller.hapusMenu(selected.getIdMenu());

            showInfo("Menu berhasil dihapus.");

            resetForm();
            muatDataDariController();

        } catch (MenuException ex) {
            showWarning(ex.getMessage());

        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    // ==========================================
    // MEMUAT DATA DARI DATABASE
    // ==========================================

    private void muatDataDariController() {
        try {
            List<Menu> data = controller.getAllMenu();
            tampilkanData(data);

        } catch (SQLException ex) {
            showDatabaseError(ex);
        }
    }

    private void tampilkanData(List<Menu> data) {
        updatingTable = true;

        try {
            currentData = new ArrayList<>(data);
            tableModel.setRowCount(0);

            NumberFormat rupiah =
                    NumberFormat.getIntegerInstance(new Locale("id", "ID"));

            for (Menu m : currentData) {
                tableModel.addRow(new Object[]{
                    m.getIdMenu(),
                    m.getNamaMenu(),
                    "Rp " + rupiah.format(m.getHarga()),
                    m.getKategori(),
                    m.getStok()
                });
            }

            lblStatus.setText(
                    "Total menu: " + currentData.size()
            );

        } finally {
            updatingTable = false;
        }
    }

    // ==========================================
    // HELPER FORM
    // ==========================================

    private Menu bacaFormSebagaiMenu(Integer idMenu) {
        String nama = txtNama.getText().trim();
        String kategori = (String) cmbKategori.getSelectedItem();

        int harga;
        int stok;

        try {
            harga = Integer.parseInt(txtHarga.getText().trim());

        } catch (NumberFormatException ex) {
            showWarning("Harga harus berupa angka bulat, misalnya 25000.");
            txtHarga.requestFocusInWindow();
            return null;
        }

        try {
            stok = Integer.parseInt(txtStok.getText().trim());

        } catch (NumberFormatException ex) {
            showWarning("Stok harus berupa angka bulat, misalnya 40.");
            txtStok.requestFocusInWindow();
            return null;
        }

        if (idMenu == null) {
            return new Menu(nama, harga, kategori, stok);
        }

        return new Menu(idMenu, nama, harga, kategori, stok);
    }

    private Menu getSelectedMenu() {
        int row = table.getSelectedRow();

        if (row >= 0 && row < currentData.size()) {
            return currentData.get(row);
        }

        return null;
    }

    private void isiForm(Menu m) {
        txtId.setText(String.valueOf(m.getIdMenu()));
        txtNama.setText(m.getNamaMenu());
        txtHarga.setText(String.valueOf(m.getHarga()));
        cmbKategori.setSelectedItem(m.getKategori());
        txtStok.setText(String.valueOf(m.getStok()));

        btnTambah.setEnabled(false);
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
    }

    private void resetForm() {
        txtId.setText(ID_PLACEHOLDER);
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");

        cmbKategori.setSelectedIndex(0);

        table.clearSelection();

        btnTambah.setEnabled(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);

        txtNama.requestFocusInWindow();
    }

    // ==========================================
    // DIALOG
    // ==========================================

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(
                this,
                msg,
                "MyPadangKu",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showWarning(String msg) {
        JOptionPane.showMessageDialog(
                this,
                msg,
                "Perhatian",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showDatabaseError(SQLException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Terjadi kesalahan pada database.\n" + ex.getMessage(),
                "Gagal",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ==========================================
    // TOMBOL KUSTOM
    // ==========================================

    private static class RoundedButton extends JButton {

        enum Style {
            PRIMARY, GOLD, DANGER, NEUTRAL
        }

        private final Style style;
        private boolean hover = false;

        RoundedButton(String text, Style style) {
            super(text);

            this.style = style;

            setFont(FONT_BOLD);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(120, 38));

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );

            Color bg;
            Color fg;
            Color border = null;

            switch (style) {
                case PRIMARY:
                    bg = hover ? MAROON_DARK : MAROON;
                    fg = Color.WHITE;
                    break;

                case GOLD:
                    bg = hover ? new Color(0xB38F1F) : GOLD;
                    fg = Color.WHITE;
                    break;

                case DANGER:
                    bg = hover ? new Color(0xFBEAE8) : Color.WHITE;
                    fg = DANGER;
                    border = DANGER;
                    break;

                default:
                    bg = hover
                            ? new Color(0xE6E0D4)
                            : new Color(0xEFEAE0);
                    fg = TEXT_DARK;
                    break;
            }

            if (!isEnabled()) {
                bg = new Color(0xEDEAE4);
                fg = new Color(0xA9A398);
                border = null;
            }

            g2.setColor(bg);
            g2.fillRoundRect(
                    0, 0,
                    getWidth() - 1,
                    getHeight() - 1,
                    10, 10
            );

            if (border != null) {
                g2.setColor(border);
                g2.drawRoundRect(
                        0, 0,
                        getWidth() - 1,
                        getHeight() - 1,
                        10, 10
                );
            }

            g2.setColor(fg);
            g2.setFont(getFont());

            FontMetrics fm = g2.getFontMetrics();

            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() - fm.getHeight()) / 2
                    + fm.getAscent();

            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }
}
