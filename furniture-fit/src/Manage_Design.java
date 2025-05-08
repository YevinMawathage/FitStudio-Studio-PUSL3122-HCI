import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.ArrayList;

public class Manage_Design {

    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private DatabaseHelper dbHelper;

    public Manage_Design() {
        // Initialize SQLite database connection
        dbHelper = new DatabaseHelper();
        dbHelper.connectToDatabase("designs.db");
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Set the FlatLaf Dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Manage Designs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 800);
        frame.getContentPane().setBackground(new Color(0, 0, 0)); // Dark background for modern feel

        // Navigation Bar Panel (Improved style with modern colors)
        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(new Color(255, 255, 255)); // Darker background for navbar
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Home Icon Label
        ImageIcon homeIcon = new ImageIcon("furniture-fit/images/home.png");
        Image homeImage = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        homeIcon = new ImageIcon(homeImage);
        JLabel homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.dispose();
                new HomePage("Yevin");
            }
        });

        // Logo Label
        ImageIcon logoIcon = new ImageIcon("furniture-fit/images/fitspace-studio-logo.png");
        Image image = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(image);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Icon Label
        ImageIcon userIcon = new ImageIcon("furniture-fit/images/user.png");
        Image userImage = userIcon.getImage();
        Image newUserImage = userImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(newUserImage);
        JLabel userIconLabel = new JLabel(userIcon);
        userIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.dispose();
                new ProfilePage();
            }
        });

        navBarPanel.add(homeIconLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(logoLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(userIconLabel);

        frame.add(navBarPanel, BorderLayout.NORTH);

        // Table Model (Updated with modern style and rounded buttons)
        String[] columnNames = {"Design Name", "Customer Name", "Room Length", "Room Width", "Edit", "Move", "Delete"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        table.setRowHeight(40);  // Set row height for better spacing
        table.setGridColor(Color.GRAY);  // Soft gridlines
        table.setSelectionBackground(new Color(76, 175, 80));  // Highlight color for selection
        table.setSelectionForeground(Color.WHITE);  // Text color on selection

        // Custom Button Renderer and Editor for table actions
        ButtonColumnRenderer buttonRenderer = new ButtonColumnRenderer();
        for (int i = 4; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(buttonRenderer);
            table.getColumnModel().getColumn(i).setCellEditor(new ButtonColumnEditor(new JCheckBox()));
        }

        // Scrollable Table (Vertical Scroll Only)
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Always show vertical scrollbar
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  // Disable horizontal scrollbar
        frame.add(scrollPane, BorderLayout.CENTER);

        // Retrieve data from the database and populate the table
        addDesignDataFromDatabase(tableModel);

        // Position the window in the center of the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addDesignDataFromDatabase(DefaultTableModel tableModel) {
        ArrayList<String[]> designs = dbHelper.getDesignData();
        for (String[] design : designs) {
            String designName = design[0];
            String customerName = design[1];
            String roomLength = design[2];
            String roomWidth = design[3];
            // Add data to the table model
            tableModel.addRow(new Object[]{designName, customerName, roomLength, roomWidth, "Edit", "Move", "Delete"});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Manage_Design::new);
    }

    // Custom button renderer for modern UI buttons in the table
    static class ButtonColumnRenderer extends JButton implements TableCellRenderer {
        public ButtonColumnRenderer() {
            setOpaque(true);
            setBackground(new Color(0, 123, 255)); // Modern button color
            setForeground(Color.WHITE);  // Button text color
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));  // Padding inside buttons
            setFont(new Font("Arial", Font.PLAIN, 12));  // Font size
            setFocusPainted(false);  // Remove focus border
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom button editor for modern UI buttons in the table
    static class ButtonColumnEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonColumnEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                JOptionPane.showMessageDialog(button, label + " clicked");
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
