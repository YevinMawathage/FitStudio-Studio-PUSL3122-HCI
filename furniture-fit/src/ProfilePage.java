import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatDarkLaf;

public class ProfilePage extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JLabel profilePictureLabel;
    private JButton saveButton;
    private JButton homeButton;

    public ProfilePage() {
        try {
            // Set FlatLaf Look and Feel
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 900)); // Increase form size
        setSize(1200, 900); // Increase form size
        setLocationRelativeTo(null);

        // Create components
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        profilePictureLabel = new JLabel();
        saveButton = new JButton("Save");
        homeButton = new JButton("Home");

        // Set button colors to blue
        saveButton.setBackground(Color.BLUE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        homeButton.setBackground(Color.WHITE);
        homeButton.setForeground(Color.BLUE);
        homeButton.setFocusPainted(false);

        // Increase height of text fields
        nameField.setPreferredSize(new Dimension(250, 40));  // Increase size of text fields
        emailField.setPreferredSize(new Dimension(250, 40));  // Increase size of text fields

        // Form Panel with background image
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("furniture-fit/images/pexels-netoo-20871054.jpg").getImage();  // Set the background image path
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        formPanel.setBackground(new Color(100, 100, 100, 150));
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(1200, 900));

        // Add the form panel directly to the frame
        formPanel.add(createFormPanel());

        // Add panels to the frame
        add(formPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfileInfo();
            }
        });

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                new HomePage("Yevin"); // Replace with your home page class
            }
        });

        pack();
        setVisible(true);

        loadProfileInfo();
    }



    private JPanel createFormPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);  // Added consistent padding (top, left, bottom, right)

        // Add profile picture label to grid
        contentPanel.add(profilePictureLabel, gbc);

        // Add Name Label with padding
        gbc.gridy++;
        contentPanel.add(createLabelWithPadding("Name:"), gbc);

        // Add Name TextField with padding
        gbc.gridy++;
        contentPanel.add(createFieldWithPadding(nameField), gbc);

        // Add Email Label with padding
        gbc.gridy++;
        contentPanel.add(createLabelWithPadding("Email:"), gbc);

        // Add Email TextField with padding
        gbc.gridy++;
        contentPanel.add(createFieldWithPadding(emailField), gbc);

        // Add Save Button with padding
        gbc.gridy++;
        contentPanel.add(createButtonWithPadding(saveButton, 235, 40), gbc);  // Adjusted button width and height

        // Add Home Button with padding
        gbc.gridy++;
        contentPanel.add(createButtonWithPadding(homeButton, 235, 40), gbc);  // Adjusted button width and height

        // Add additional space below Home button (space between Home and bottom of the form)
        gbc.gridy++;
        gbc.insets = new Insets(20, 20, 30, 20); // Increased bottom space
        contentPanel.add(new JLabel(), gbc); // Empty label to create extra space

        return contentPanel;
    }

    private JPanel createLabelWithPadding(String text) {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));  // No margin for label itself
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(250, 33));  // Adjusting the size of the label
        labelPanel.add(label);
        return labelPanel;
    }

    private JPanel createFieldWithPadding(JTextField field) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));  // No margin for field itself
        field.setPreferredSize(new Dimension(250, 40));  // Adjusting size and height of the text field
        fieldPanel.add(field);
        return fieldPanel;
    }

    private JPanel createButtonWithPadding(JButton button, int width, int height) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));  // Centered button
        button.setPreferredSize(new Dimension(width, height));  // Adjusted button width and height
        buttonPanel.add(button);
        return buttonPanel;
    }

    private void loadProfileInfo() {
        // You can load real profile data here (e.g., from a database)
        nameField.setText("yevin");
        emailField.setText("yevin@example.com");
    }



    private void saveProfileInfo() {
        // Validate and save the updated profile information
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save the information (e.g., store it in a database or file)
        JOptionPane.showMessageDialog(this, "Profile information saved successfully.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProfilePage::new);
    }
}
