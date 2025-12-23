import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private JButton loginButton, registerButton;

    public HomePage() {
        setTitle("Cafeteria AIUB");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel welcomeLabel = new JLabel("Cafeteria AIUB");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 35));
        Color greenColor = new Color(34, 139, 34); 
        welcomeLabel.setForeground(greenColor);
        welcomeLabel.setBounds(270, 160, 350, 40);
        add(welcomeLabel);

        JLabel mottoLabel = new JLabel("Skip the line, savor your time");
        mottoLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        mottoLabel.setForeground(greenColor);
        mottoLabel.setBounds(260, 200, 300, 30);
        add(mottoLabel);

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
            dispose();
            new Login();
        });
        add(loginButton);
        registerButton = new JButton("Register");
        registerButton.setBounds(430, 430, 120, 40);
        registerButton.setFont(buttonFont);
        registerButton.setBackground(new Color(241, 225, 198));
        registerButton.setForeground(new Color(47, 58, 69)); 
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createBevelBorder(0));
        registerButton.setPreferredSize(new Dimension(120, 40));

        
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(225, 202, 157));  
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(241, 225, 198));  
            }
        });

        registerButton.addActionListener(e -> {
            dispose();
            new Registration();
        });
        add(registerButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
