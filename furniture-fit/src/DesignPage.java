import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatDarkLaf;

public class DesignPage {

    private JFrame frame;
    private JTextField designNameField;
    private JTextField customerNameField;
    private JTextField roomLengthField;
    private JTextField roomWidthField;
    private JPanel drawingArea;
    private int roomLength = 0;
    private int roomWidth = 0;
    private boolean isDrawing = false;
    private int startX = 0, startY = 0;
    private DatabaseHelper dbHelper;

    public DesignPage() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Initialize SQLite database connection for design data
        dbHelper = new DatabaseHelper();
        dbHelper.connectToDatabase("designs.db");  // Connect to the designs database

        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Create JFrame for the design page
        frame = new JFrame("Design Room");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1080, 800);
        frame.getContentPane().setBackground(new Color(0, 0, 0)); // Dark background color
        frame.setLayout(new BorderLayout());

        // Navigation Bar Panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(new Color(255, 255, 255)); // Darker background for navbar
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

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

        // Central Panel (Main Layout)
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(1, 2, 0, 0)); // 2 columns, horizontal gap of 20

        // Left Column (Text fields and Labels)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.BLACK); // Set the background color of the left panel to black
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Design Name field
        designNameField = createStyledTextField();
        leftPanel.add(createLabel("Design Name"), gbc);
        leftPanel.add(designNameField, gbc);

        // Customer Name field
        customerNameField = createStyledTextField();
        leftPanel.add(createLabel("Customer Name"), gbc);
        leftPanel.add(customerNameField, gbc);

        // Room Length field
        roomLengthField = createStyledTextField();
        leftPanel.add(createLabel("Room Length"), gbc);
        leftPanel.add(roomLengthField, gbc);

        // Room Width field
        roomWidthField = createStyledTextField();
        leftPanel.add(createLabel("Room Width"), gbc);
        leftPanel.add(roomWidthField, gbc);

        // Add the left panel to the central panel
        centralPanel.add(leftPanel);

        // Right Column (Drawing Area)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        // Drawing area for room shape
        drawingArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRoomShape(g);  // This is the method that draws the room shape
            }
        };
        drawingArea.setPreferredSize(new Dimension(300, 300));
        drawingArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        drawingArea.setBackground(Color.WHITE);
        rightPanel.add(drawingArea, BorderLayout.CENTER);

        // Start Designing button
        JButton startDesignButton = new JButton("Start Designing");
        startDesignButton.setPreferredSize(new Dimension(400, 50)); // Adjusting the size of the button
        startDesignButton.setBackground(Color.BLUE); // Blue button color
        startDesignButton.setForeground(Color.WHITE); // White text for contrast
        startDesignButton.addActionListener(e -> {
            // Get input values for design name, customer name, room dimensions
            String designName = designNameField.getText();
            String customerName = customerNameField.getText();
            int roomLength = Integer.parseInt(roomLengthField.getText());
            int roomWidth = Integer.parseInt(roomWidthField.getText());

            // Insert design data into the database
            dbHelper.insertDesignData(designName, customerName, roomLength, roomWidth);

            // Redirect to the OBJViewer page
            frame.dispose();
            new OBJViewer();  // Assuming OBJViewer is the next page
        });
        rightPanel.add(startDesignButton, BorderLayout.SOUTH);

        // Add the right panel to the central panel
        centralPanel.add(rightPanel);

        // Add the central panel to the frame
        frame.add(centralPanel, BorderLayout.CENTER);

        // Position the window in the center of the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Set up mouse listeners for drawing area
        drawingArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                isDrawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
                drawingArea.repaint();  // Simply repaint after drawing ends
            }
        });

        drawingArea.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    roomLength = Math.abs(e.getX() - startX);
                    roomWidth = Math.abs(e.getY() - startY);
                    drawingArea.repaint();  // Update the drawing while dragging
                }
            }
        });
    }

    private void drawRoomShape(Graphics g) {
        try {
            // Get values from room length and width text fields
            String lengthText = roomLengthField.getText();
            String widthText = roomWidthField.getText();

            // Check if the input fields are not empty
            if (lengthText.isEmpty() || widthText.isEmpty()) {
                System.err.println("Room dimensions are empty.");
                return;  // Exit the method if fields are empty
            }

            // Parse room dimensions from text fields
            roomLength = Integer.parseInt(lengthText);
            roomWidth = Integer.parseInt(widthText);

            // Validate the dimensions
            if (roomLength <= 0 || roomWidth <= 0) {
                System.err.println("Room dimensions must be greater than zero.");
                return;  // Exit the method if the dimensions are invalid
            }

            // If valid, draw the room shape
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(76, 175, 80)); // Soft green color for the room shape
            g2d.drawRect(startX, startY, roomLength, roomWidth); // Drawing the rectangle (room shape)
        } catch (NumberFormatException e) {
            System.err.println("Invalid room dimensions. Please enter valid integers.");
        }
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setPreferredSize(new Dimension(300, 33)); // Adjustable width and height
        textField.setBackground(Color.WHITE); // Set the background to white
        textField.setForeground(Color.BLACK); // Set the text color to black
        textField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100))); // Subtle border
        return textField;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setForeground(Color.LIGHT_GRAY); // Light gray text for better readability on dark background
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DesignPage::new);
    }
}
