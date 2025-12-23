import javax.swing.*;
import java.awt.*;

public class AdminLogin extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton, backButton;

    public AdminLogin() {
        setTitle("Admin Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("Admin ID:");
        userLabel.setBounds(230, 280, 120, 40);
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(430, 280, 120, 40);
        add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(230, 340, 120, 40);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(430, 340, 120, 40);
        add(passField);

        Font largeFont = new Font("Arial", Font.BOLD, 18); 
        Color greenColor = new Color(34, 139, 34); 

        userLabel.setFont(largeFont);
        userLabel.setForeground(greenColor);
        passLabel.setFont(largeFont);
        passLabel.setForeground(greenColor);

        loginButton = new JButton("Login");
        loginButton.setBounds(230, 430, 120, 40);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        loginButton.setFont(buttonFont);
        loginButton.setBackground(new Color(168, 213, 186)); 
        loginButton.setForeground(Color.WHITE); 
        loginButton.setFocusPainted(false); 
        loginButton.setBorder(BorderFactory.createBevelBorder(0)); 
        loginButton.setPreferredSize(new Dimension(120, 40));

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(125, 174, 151)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(168, 213, 186)); 
            }
        });

        loginButton.addActionListener(e -> {
            String userId = userField.getText();
            String password = new String(passField.getPassword());
            if (userId.equals("mushfik") && password.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Admin login successful.");
                AdminDashboard adminDashboard = new AdminDashboard(); 
                adminDashboard.setVisible(true); 
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin ID or Password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(loginButton);

        backButton = new JButton("Back");
        backButton.setBounds(430, 430, 120, 40);
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(241, 225, 198)); 
        backButton.setForeground(new Color(47, 58, 69)); 
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createBevelBorder(0)); 
        backButton.setPreferredSize(new Dimension(120, 40));

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(225, 202, 157));  
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(241, 225, 198));  
            }
        });

        backButton.addActionListener(e -> {
            HomePage homePage = new HomePage(); 
            homePage.setVisible(true); 
            dispose(); 
        });
        add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
