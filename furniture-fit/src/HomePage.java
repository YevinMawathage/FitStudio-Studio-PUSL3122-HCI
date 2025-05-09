import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {

    private JFrame frame;

    public HomePage(String userName) {
        // Set FlatDarkLaf theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 800);
        frame.getContentPane().setBackground(new Color(40, 40, 40));

        // Navigation Bar Panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(new Color(255, 255, 255));
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Home Icon Label
        ImageIcon homeIcon = new ImageIcon("furniture-fit/images/home.png");
        Image homeImage = homeIcon.getImage();
        Image newHomeImage = homeImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        homeIcon = new ImageIcon(newHomeImage);
        JLabel homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        homeIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.dispose(); // Close the current frame
                new HomePage("Yevin"); // Create a new instance of HomePage
            }
        });

        // Logo Label
        ImageIcon logoIcon = new ImageIcon("furniture-fit/images/fitspace-studio-logo.png");
        Image image = logoIcon.getImage();
        Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(newImg);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Icon Label
        ImageIcon userIcon = new ImageIcon("furniture-fit/images/user.png");
        Image userImage = userIcon.getImage();
        Image newUserImage = userImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(newUserImage);
        JLabel userIconLabel = new JLabel(userIcon);
        userIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        userIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frame.dispose(); // Close the current frame
                new ProfilePage(); // Open Profile Page
            }
        });

        // Add components to nav bar panel
        navBarPanel.add(homeIconLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(logoLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(userIconLabel);

        frame.add(navBarPanel, BorderLayout.NORTH);


        // Central Panel
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome " + userName + "!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centralPanel.add(welcomeLabel);

        centralPanel.add(Box.createVerticalStrut(30));

        // Menu Buttons with Icons
        String[] menuItems = {
                "Create Designs",
                "Manage Designs"
        };

        String[] iconPaths = {
                "create_designs.png",
                "manage_ico.png",
                "catalogue_icon.png",
                "projects_icon.png",
                "projects_icon.png",
                "projects_icon.png",
                "projects_icon.png",
                "projects_icon.png"
        };



        for (int i = 0; i < menuItems.length; i++) {
            final int index = i;  // Declare 'index' as final and assign 'i' to it
            ImageIcon buttonIcon = new ImageIcon("images/" + iconPaths[i]);
            Image buttonImage = buttonIcon.getImage();
            Image newButtonImage = buttonImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            buttonIcon = new ImageIcon(newButtonImage);

            JButton button = new JButton(menuItems[i], buttonIcon);
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBackground(new Color(50, 50, 50));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
            button.setOpaque(true);


            // Set a fixed size for width and height to ensure consistency
            button.setPreferredSize(new Dimension(250, 50));
            button.setMaximumSize(new Dimension(250, 50)); // Set exact size for both width and height

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Use 'index' instead of 'i' in the switch
                    switch (menuItems[index]) {  // 'index' is final and accessible
                        case "Create Designs":
                            frame.dispose();
                            new DesignPage();
                            break;
                        case "Manage Designs":
                            frame.dispose();
                            new Manage_Design();
                            break;
                    }
                }
            });

            centralPanel.add(button);
            centralPanel.add(Box.createVerticalStrut(14));  // Space between buttons
        }

        // Add central panel to frame
        frame.add(centralPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage("Yevin"));
    }
}
