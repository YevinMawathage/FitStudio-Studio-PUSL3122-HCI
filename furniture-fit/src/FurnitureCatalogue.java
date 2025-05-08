import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FurnitureCatalogue {

    private JFrame frame;

    public FurnitureCatalogue() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());  // Apply dark theme
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Furniture Catalogue");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 800);
        frame.getContentPane().setBackground(new Color(40, 40, 40));  // Dark background for the main frame

        // Navigation Bar Panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(Color.WHITE);  // White background for navbar
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Home Icon Label
        ImageIcon homeIcon = new ImageIcon("furniture-fit/images/home.png");
        Image homeImage = homeIcon.getImage();
        Image newHomeImage = homeImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        homeIcon = new ImageIcon(newHomeImage);
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

        // Catalogue Panel with Cards
        JPanel cataloguePanel = new JPanel();
        cataloguePanel.setLayout(new GridLayout(2, 4, 5, 5));  // Grid layout with space between cards
        cataloguePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cataloguePanel.setBackground(new Color(40, 40, 40)); // Dark background for catalogue panel

        // Furniture Items
        String[] items = {
                "Modern Leather Sofa",
                "Industrial Steel Dining Table",
                "Minimalist Coffee Table",
                "Elegant Queen Bed",
                "Vintage Wooden Chair",
                "Luxury Recliner",
                "Compact Side Table",
                "Stylish TV Stand"
        };

        String[] sizes = {
                "3-seater",
                "6 x 4",
                "42 x 24",
                "Queen Size",
                "Single",
                "Recliner",
                "18 x 18",
                "60 x 20"
        };

        String[] imagePaths = {
                "furniture-fit/images/furnitures/sofa.jpg",
                "furniture-fit/images/furnitures/dining_table.jpg",
                "furniture-fit/images/furnitures/coffee_table.jpg",
                "furniture-fit/images/furnitures/queen_bed.jpg",
                "furniture-fit/images/furnitures/wooden_chair.jpg",
                "furniture-fit/images/furnitures/recliner.jpg",
                "furniture-fit/images/furnitures/side_table.jpg",
                "furniture-fit/images/furnitures/tv_stand.jpg"
        };

        String[] descriptions = {
                "A sleek and comfortable modern leather sofa, perfect for any contemporary living room.",
                "An industrial steel dining table designed for large family gatherings, combining durability with style.",
                "A minimalist coffee table with a glass top, ideal for small spaces and modern interiors.",
                "An elegant queen bed crafted with solid wood, offering comfort and sophistication for your bedroom.",
                "A vintage wooden chair with intricate detailing, adding a rustic charm to any room.",
                "A luxury recliner chair with adjustable positions, perfect for relaxation after a long day.",
                "A compact and stylish side table, perfect for small spaces while adding a touch of elegance.",
                "A modern TV stand with sleek storage options, offering both functionality and a stylish focal point."
        };

        // Iterate through items and create the cards dynamically
        for (int i = 0; i < items.length; i++) {
            ImageIcon itemIcon = new ImageIcon(imagePaths[i]);
            Image itemImage = itemIcon.getImage();
            Image newItemImage = itemImage.getScaledInstance(250, 180, Image.SCALE_SMOOTH); // Resize image
            itemIcon = new ImageIcon(newItemImage);

            JPanel itemCard = new JPanel();
            itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.Y_AXIS));
            itemCard.setBackground(new Color(40, 40, 40));  // Dark background for card
            itemCard.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2, true));  // Card border with rounded corners
            itemCard.setPreferredSize(new Dimension(250, 350)); // Ensuring uniform card size

            // Add the item image
            JLabel imageLabel = new JLabel(itemIcon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageLabel.setPreferredSize(new Dimension(250, 180));  // Set image size
            itemCard.add(imageLabel);

            // Add the item name
            JLabel nameLabel = new JLabel(items[i]);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemCard.add(nameLabel);

            // Add the item size
            JLabel sizeLabel = new JLabel(sizes[i]);
            sizeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            sizeLabel.setForeground(Color.LIGHT_GRAY);
            sizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemCard.add(sizeLabel);

            // Add the item description
            JLabel descriptionLabel = new JLabel(descriptions[i]);
            descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            descriptionLabel.setForeground(Color.LIGHT_GRAY);
            descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            descriptionLabel.setPreferredSize(new Dimension(250, 50));  // Space for description text
            itemCard.add(descriptionLabel);

            cataloguePanel.add(itemCard);
        }

        frame.add(cataloguePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FurnitureCatalogue());
    }
}
