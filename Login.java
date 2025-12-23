import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Login extends JFrame {
    private JTextField studentIdField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton, adminLoginButton;

    public Login() {
        setTitle("Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(230, 280, 120, 40);
        add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(430, 280, 120, 40);
        add(studentIdField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(230, 340, 120, 40);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(430, 340, 120, 40);
        add(passwordField);

        Font largeFont = new Font("Arial", Font.BOLD, 18); 
        Color greenColor = new Color(34, 139, 34); 

        studentIdLabel.setFont(largeFont);
        studentIdLabel.setForeground(greenColor);
        passwordLabel.setFont(largeFont);
        passwordLabel.setForeground(greenColor);

        loginButton = new JButton("Login");
        loginButton.setBounds(230, 430, 120, 40);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        loginButton.setFont(buttonFont);
        loginButton.setBackground(new Color(168, 213, 186));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createBevelBorder(0)); 
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFont(buttonFont);

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(125, 174, 151));  
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(168, 213, 186));  
            }
        });

        loginButton.addActionListener(e -> {
            String studentId = studentIdField.getText();
            String password = new String(passwordField.getPassword());
            if (authenticate(studentId, password)) {
                Dashboard dashboard = new Dashboard(studentId);
                dashboard.setVisible(true);
                dispose(); // Dispose Login frame after Dashboard is shown
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Student ID or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(loginButton);

        
        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setBounds(650, 20, 120, 30);
        adminLoginButton.setBackground(new Color(241, 225, 198)); 
        adminLoginButton.setForeground(new Color(47, 58, 69)); 
        adminLoginButton.setFont(buttonFont);
        adminLoginButton.setFocusPainted(false); 
        adminLoginButton.setBorder(BorderFactory.createBevelBorder(0)); 
        adminLoginButton.setPreferredSize(new Dimension(120, 40));

        adminLoginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                adminLoginButton.setBackground(new Color(225, 202, 157));  
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                adminLoginButton.setBackground(new Color(241, 225, 198));  
            }
        });

        adminLoginButton.addActionListener(e -> {
            AdminLogin adminLogin = new AdminLogin(); 
            adminLogin.setVisible(true); 
            dispose(); 
        });
        add(adminLoginButton);

        backButton = new JButton("Back");
        backButton.setBounds(430, 430, 120, 40);
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(241, 225, 198)); 
        backButton.setForeground(new Color(47, 58, 69)); 
        backButton.setFocusPainted(false); 
        backButton.setBorder(BorderFactory.createBevelBorder(0));
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFont(buttonFont);

       
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

    private boolean authenticate(String studentId, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[3].equals(studentId) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getStudentId() {
        return studentIdField.getText();
    }
}
