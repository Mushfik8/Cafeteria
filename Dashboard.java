import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private String username;

    public Dashboard(String username) {
        this.username = username;
        initialize();
    }

    private void initialize() {
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        
        JLabel dashboardLabel = new JLabel("Dashboard - Select a Cafeteria");
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dashboardLabel.setBounds(250, 50, 300, 30);
        add(dashboardLabel);

        JLabel image1Label = createImageLabel("path/to/image1.jpg", 50, 120);
        JLabel image2Label = createImageLabel("path/to/image2.jpg", 300, 120);
        JLabel image3Label = createImageLabel("path/to/image3.jpg", 550, 120);
        JLabel image4Label = createImageLabel("path/to/image4.jpg", 50, 320);
        JLabel image5Label = createImageLabel("path/to/image5.jpg", 300, 320);
        JLabel image6Label = createImageLabel("path/to/image6.jpg", 550, 320);

        add(image1Label);
        add(image2Label);
        add(image3Label);
        add(image4Label);
        add(image5Label);
        add(image6Label);

        JButton cafeteria1Button = createCafeteriaButton("Cafe Saniya's", 50, 200);
        JButton cafeteria2Button = createCafeteriaButton("Rooftop Cafe", 300, 200);
        JButton cafeteria3Button = createCafeteriaButton("Main Canteen", 550, 200);
        JButton cafeteria4Button = createCafeteriaButton("Bioscope Lane", 50, 400);
        JButton cafeteria5Button = createCafeteriaButton("Cafeteria 5", 300, 400);
        JButton cafeteria6Button = createCafeteriaButton("Cafeteria 6", 550, 400);

        add(cafeteria1Button);
        add(cafeteria2Button);
        add(cafeteria3Button);
        add(cafeteria4Button);
        add(cafeteria5Button);
        add(cafeteria6Button);

        JButton backButton = createStyledButton("Back", new Color(241, 225, 198), new Color(225, 202, 157));
        backButton.setBounds(650, 20, 120, 30);
        backButton.addActionListener(e -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
            dispose();
        });
        add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createImageLabel(String imagePath, int x, int y) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel label = new JLabel(imageIcon);
        label.setBounds(x, y, 200, 100);
        return label;
    }

    private JButton createCafeteriaButton(String text, int x, int y) {
        JButton button = createStyledButton(text, new Color(168, 213, 186), new Color(125, 174, 151));
        button.setBounds(x, y, 200, 100);
        button.addActionListener(e -> {
            CafeteriaPage cafeteriaPage = new CafeteriaPage(text, username);
            cafeteriaPage.setVisible(true);
            dispose();
        });
        return button;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createBevelBorder(0));
        button.setFont(new Font("Arial", Font.BOLD, 14));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

   
}
