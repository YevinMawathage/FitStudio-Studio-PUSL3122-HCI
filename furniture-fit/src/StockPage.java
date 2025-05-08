import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class StockPage {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public StockPage() {
        // Basic Frame Setup
        frame = new JFrame("Stock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 800);
        frame.getContentPane().setBackground(new Color(20, 20, 20));

        // Navigation Bar Panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(Color.WHITE);
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Home Icon
        ImageIcon homeIcon = new ImageIcon("furniture-fit/images/home.png");
        Image homeImage = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        homeIcon = new ImageIcon(homeImage);
        JLabel homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeIconLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                frame.dispose();
                new HomePage("Yevin");
            }
        });

        // Logo Label
        ImageIcon logoIcon = new ImageIcon("furniture-fit/images/fitspace-studio-logo.png");
        Image image = logoIcon.getImage();
        Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(newImg);
        JLabel logoLabel = new JLabel(logoIcon);

        // User Icon
        ImageIcon userIcon = new ImageIcon("furniture-fit/images/user.png");
        Image userImage = userIcon.getImage();
        Image newUserImage = userImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(newUserImage);
        JLabel userIconLabel = new JLabel(userIcon);
        userIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userIconLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
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

        // Center Panel with Search, Buttons, and Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(20, 20, 20));

        // Top Panel (Search, Sort, Add)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(20, 20, 20));

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        topPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setForeground(Color.WHITE);
        topPanel.add(searchButton);

        JButton sortByName = new JButton("Sort by Name");
        sortByName.setBackground(new Color(0, 120, 215));
        sortByName.setForeground(Color.WHITE);
        topPanel.add(sortByName);

        JButton sortByAmount = new JButton("Sort by Amount");
        sortByAmount.setBackground(new Color(0, 120, 215));
        sortByAmount.setForeground(Color.WHITE);
        topPanel.add(sortByAmount);

        JButton addItemButton = new JButton("Add Item");
        addItemButton.setBackground(new Color(0, 120, 215));
        addItemButton.setForeground(Color.WHITE);
        topPanel.add(addItemButton);

        centerPanel.add(topPanel, BorderLayout.NORTH);

        // Table Setup
        String[] columnNames = {"Item Name", "Length * Width * Height", "Amount", "Edit", "Move", "Delete"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(40); // Increase row height to make it more spacious
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Sample Data
        Object[][] rows = {
                {"Chair - Seat Model S", "12\" x 4\" x 8\"", "-4", "Edit", "Move", "Delete"},
                {"Dubai Elegant Model S", "12\" x 4\" x 8\"", "-4", "Edit", "Move", "Delete"},
                {"Classic Wooden Desk", "60\" x 30\" x 30\"", "10", "Edit", "Move", "Delete"},
                {"Ergonomic Office Chair", "28\" x 28\" x 45\"", "15", "Edit", "Move", "Delete"},
                {"Modern Bookshelf", "36\" x 12\" x 72\"", "8", "Edit", "Move", "Delete"},
                {"Queen Size Bed Frame", "80\" x 60\" x 14\"", "5", "Edit", "Move", "Delete"},
                {"Glass Coffee Table", "48\" x 24\" x 18\"", "12", "Edit", "Move", "Delete"},
                {"Compact Nightstand", "20\" x 16\" x 24\"", "20", "Edit", "Move", "Delete"}
        };

        for (Object[] row : rows) {
            tableModel.addRow(row);
        }

        // Button Panel at the Bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 20, 20));
        //JButton cancelButton = new JButton("Cancel");
        //cancelButton.setBackground(new Color(0, 120, 215));
        //cancelButton.setForeground(Color.WHITE);
        //JButton yesButton = new JButton("Yes");
        //yesButton.setBackground(new Color(0, 120, 215));
        //yesButton.setForeground(Color.WHITE);
        //buttonPanel.add(cancelButton);
        //buttonPanel.add(yesButton);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Functionality for Buttons
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().toLowerCase();
            for (int i = 0; i < table.getRowCount(); i++) {
                boolean visible = table.getValueAt(i, 0).toString().toLowerCase().contains(searchTerm);
                table.setRowHeight(i, visible ? table.getRowHeight() : 0);
            }
        });

        sortByName.addActionListener(e -> {
            Vector<Vector> dataVector = tableModel.getDataVector();
            dataVector.sort((row1, row2) -> row1.get(0).toString().compareToIgnoreCase(row2.get(0).toString()));
            tableModel.fireTableDataChanged();  // This will refresh the table view
        });

        // Sort by Amount
        sortByAmount.addActionListener(e -> {
            Vector<Vector> dataVector = tableModel.getDataVector();
            dataVector.sort((row1, row2) -> {
                int amount1 = Integer.parseInt(row1.get(2).toString());
                int amount2 = Integer.parseInt(row2.get(2).toString());
                return Integer.compare(amount1, amount2);
            });
            tableModel.fireTableDataChanged();  // This will refresh the table view
        });

        addItemButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter item name:");
            String size = JOptionPane.showInputDialog("Enter size (L x W x H):");
            String amount = JOptionPane.showInputDialog("Enter stock amount:");
            if (name != null && size != null && amount != null) {
                tableModel.addRow(new Object[]{name, size, amount, "Edit", "Move", "Delete"});
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StockPage::new);
    }
}
