import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {


    private DatabaseHelper dbHelper;



    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(LoginPage::new);
    }

    public LoginPage() {

        dbHelper = new DatabaseHelper();
        dbHelper.connectToDatabase("users.db");

        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(1080, 800);
        loginFrame.setLayout(new GridBagLayout());  // Using GridBagLayout for centering

        // Center the frame
        loginFrame.setLocationRelativeTo(null);

        // Create the main panel for layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Left side: Background image panel
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("furniture-fit/images/pexels-kowalievska-1148955.jpg"); // Set your background image here
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        imagePanel.setPreferredSize(new Dimension(540, 800)); // Left side for image
        mainPanel.add(imagePanel, BorderLayout.WEST);

        // Right side: Login form panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 0, 0)); // Dark background for form
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50)); // Padding to center

        // Add logo
        JLabel logoLabel = new JLabel("Furniture");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(logoLabel);

        // Create and center email field
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        JLabel emailLabel = new JLabel("Email Address");
        emailPanel.setBackground(new Color(0, 0, 0));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBackground(new Color(0, 0, 0));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField emailField = new JTextField(15);
        emailField.setPreferredSize(new Dimension(300, 25));  // Reduce height for text field
        emailField.setMaximumSize(new Dimension(300, 25));  // Set maximum height for text field
        emailField.setBackground(Color.WHITE);
        emailField.setForeground(Color.BLACK);
        emailPanel.add(emailLabel);
        emailPanel.add(Box.createVerticalStrut(10));  // Add space between label and text field
        emailPanel.add(emailField);
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the panel to center
        contentPanel.add(emailPanel);

        // Create and center password field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setBackground(new Color(0, 0, 0));
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        JLabel passwordLabel = new JLabel("Your Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(300, 25));  // Reduce height for text field
        passwordField.setMaximumSize(new Dimension(300, 25));  // Set maximum height for text field
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.BLACK);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createVerticalStrut(10));  // Add space between label and text field
        passwordPanel.add(passwordField);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the panel to center
        contentPanel.add(passwordPanel);

        // Add Sign In button (Increased size)
        JButton loginButton = new JButton("Sign in");
        loginButton.setPreferredSize(new Dimension(300, 80));  // Increased width and height
        loginButton.setBackground(new Color(0x007BFF));  // Blue background
        loginButton.setForeground(Color.WHITE);  // White text
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredEmail = emailField.getText();  // Get entered email
                String enteredPassword = new String(passwordField.getPassword());  // Get entered password

                // Validate email and password using the database helper
                boolean isValidUser = dbHelper.validateUserCredentials(enteredEmail, enteredPassword);

                if (isValidUser) {
                    // If credentials match
                    JOptionPane.showMessageDialog(loginFrame, "Login Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loginFrame.dispose();  // Close the login window
                    new HomePage(enteredEmail);  // Open the home page (you should define HomePage)
                } else {
                    // If credentials don't match
                    JOptionPane.showMessageDialog(loginFrame, "Invalid email or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        contentPanel.add(Box.createVerticalStrut(20));  // Add space between password and button
        contentPanel.add(loginButton);

        // Add the content panel to the right side of the main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Use GridBagConstraints to add the main panel into the frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH; // Make it take up available space
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Add the main panel with GridBagConstraints to the frame
        loginFrame.add(mainPanel, gbc);

        // Make the frame visible
        loginFrame.setVisible(true);
    }
}

