import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HelpTutorialsPage {
    private JFrame frame;

    public HelpTutorialsPage() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Apply Flat Dark theme
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Help and Tutorials");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 800);
        frame.getContentPane().setBackground(new Color(255, 255, 255));

        // Navigation Bar Panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(new Color(255, 255, 255));  // Dark background for navbar
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Home Icon Label
        ImageIcon homeIcon = new ImageIcon("furniture-fit/images/home.png");
        Image homeImage = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        homeIcon = new ImageIcon(homeImage);
        JLabel homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Icon Label
        ImageIcon userIcon = new ImageIcon("furniture-fit/images/user.png");
        Image userImage = userIcon.getImage();
        Image newUserImage = userImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(newUserImage);
        JLabel userIconLabel = new JLabel(userIcon);
        userIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        // Help and Tutorials Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 1, 20, 20));  // 3 cards stacked vertically
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // FAQ Card
        JPanel faqCard = createCard("FAQ", new String[]{
                "How do I create custom furniture?",
                "How can I track my order status?",
                "What payment methods are accepted?"
        });
        contentPanel.add(faqCard);

        // Contact Card
        JPanel contactCard = createCard("Contact", new String[]{
                "Email us: support@FitSpace-Studio.com",
                "Call us: +01 123-456-7890"
        });
        contentPanel.add(contactCard);

        // Tutorials Card
        JPanel tutorialsCard = createCard("Tutorials", new String[]{
                "How to Customize Furniture",
                "How to edit user Data",
                "How to Sign in to the App",
        });
        contentPanel.add(tutorialsCard);

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createCard(String title, String[] content) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(new Color(0, 0, 0));  // Dark background for card
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(titleLabel);

        cardPanel.add(Box.createVerticalStrut(10));

        // Content (list of FAQs, Contact info, or Tutorials)
        for (String line : content) {
            JLabel contentLabel = new JLabel(line);
            contentLabel.setForeground(Color.WHITE);
            contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(contentLabel);
            cardPanel.add(Box.createVerticalStrut(5));
        }

        return cardPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HelpTutorialsPage());
    }
}
