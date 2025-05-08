import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPage {

    private JFrame frame;
    private DatabaseHelper dbHelper;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpPage::new);
    }

    public SignUpPage() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Initialize database helper for user management
        dbHelper = new DatabaseHelper();
        dbHelper.connectToDatabase("users.db");  // Connect to the user database


        // Create JFrame for sign-up page
        frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 800);

        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon("furniture-fit/images/pexels-lalesh-242827.jpg"); // update path if needed
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        frame.setContentPane(backgroundPanel);

        // Sign Up form-panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(30, 30, 30, 200)); // semi-transparent dark background
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));

        // Logo / Title
        JLabel logoLabel = new JLabel("FitSpace Studio");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(logoLabel);
        contentPanel.add(Box.createVerticalStrut(30));

        // Designer Name
        contentPanel.add(createLabel("Your Name"));
        JTextField designerNameField = createTextField();
        contentPanel.add(designerNameField);
        contentPanel.add(Box.createVerticalStrut(10));

        // Email
        contentPanel.add(createLabel("Email"));
        JTextField emailField = createTextField();
        contentPanel.add(emailField);
        contentPanel.add(Box.createVerticalStrut(10));

        // Phone
        contentPanel.add(createLabel("Phone"));
        JTextField phoneField = createTextField();
        contentPanel.add(phoneField);
        contentPanel.add(Box.createVerticalStrut(10));

        // Password
        contentPanel.add(createLabel("Password"));
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(300, 40)); // Set password field size
        passwordField.setBackground(new Color(50, 50, 50)); // Dark background for password field
        passwordField.setForeground(Color.WHITE);  // White text color
        contentPanel.add(passwordField);
        contentPanel.add(Box.createVerticalStrut(10));

        // Sign Up Button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(180, 40));
        signUpButton.setBackground(new Color(0x007BFF));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String designerName = designerNameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String password = new String(passwordField.getPassword());

                // Insert data into the database
                dbHelper.insertUserData(designerName, email, phone, password);

                // Show success message
                JOptionPane.showMessageDialog(frame, "Sign Up Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        contentPanel.add(signUpButton);
        contentPanel.add(Box.createVerticalStrut(15));

        // Back to Login Button
        JButton backButton = new JButton("Back to Login");
        backButton.setPreferredSize(new Dimension(180, 40));
        backButton.setBackground(new Color(0x555555));
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginPage();
            }
        });
        contentPanel.add(backButton);

        backgroundPanel.add(contentPanel); // center the form on background

        frame.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(15);
        field.setMaximumSize(new Dimension(300, 40));  // Increase the height to 40px
        field.setPreferredSize(new Dimension(300, 40)); // Increase the height to 40px
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        return field;
    }
}
