import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame {

    public WelcomePage() {
        // Set up the frame
        setTitle("Welcome to FitSpace-Studio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 800);
        getContentPane().setBackground(Color.WHITE);  // White background

        // Create the logo at the top (use your actual logo image)
        ImageIcon logoIcon = new ImageIcon("furniture-fit/images/fitspace-studio-logo.png");  // Path to the uploaded image
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Center logo

        // Create the title "FitSpace-Studio"
        JLabel titleLabel = new JLabel("FitSpace-Studio", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);

        // Create the Sign Up and Login buttons
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(Color.BLUE);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpButton.setPreferredSize(new Dimension(200, 50));
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loginButton.setPreferredSize(new Dimension(200, 50));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Action listeners for buttons
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignUpPage(); // Redirect to Sign Up page
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginPage(); // Redirect to Login page
            }
        });

        // Create a panel for the center content with GridBagLayout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        // GridBagConstraints to align elements in the center
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add the components to the centerPanel
        centerPanel.add(logoLabel, gbc);
        gbc.gridy++;
        centerPanel.add(titleLabel, gbc);
        gbc.gridy++;
        centerPanel.add(signUpButton, gbc);
        gbc.gridy++;
        centerPanel.add(loginButton, gbc);

        // Add the center panel to the frame
        add(centerPanel, BorderLayout.CENTER);

        // Set the frame visibility
        setLocationRelativeTo(null);  // Center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage());
    }
}
