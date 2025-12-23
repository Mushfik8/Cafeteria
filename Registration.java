import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Registration extends JFrame {
    private JTextField emailField, fullNameField, studentIdField, phoneField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;
    private boolean adminMode;

    public Registration() {
        this(false);
    }

    public Registration(boolean adminMode) {
        this.adminMode = adminMode;
        initialize();
    }

    private void initialize() {
        setTitle(adminMode ? "Admin: Add New User" : "Registration");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(230, 100, 120, 40);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(430, 100, 120, 40);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(230, 160, 120, 40);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(430, 160, 120, 40);
        add(passwordField);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(230, 220, 120, 40);
        add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(430, 220, 120, 40);
        add(fullNameField);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(230, 280, 120, 40);
        add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(430, 280, 120, 40);
        add(studentIdField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(230, 340, 120, 40);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(430, 340, 120, 40);
        add(phoneField);

        Font largeFont = new Font("Arial", Font.BOLD, 18); 
        Color greenColor = new Color(34, 139, 34); 

        studentIdLabel.setFont(largeFont);
        studentIdLabel.setForeground(greenColor);
        passwordLabel.setFont(largeFont);
        passwordLabel.setForeground(greenColor);
        emailLabel.setFont(largeFont);
        emailLabel.setForeground(greenColor);
        fullNameLabel.setFont(largeFont);
        fullNameLabel.setForeground(greenColor);
        phoneLabel.setFont(largeFont);
        phoneLabel.setForeground(greenColor);
        registerButton = createStyledButton(adminMode ? "Add User" : "Register", new Color(168, 213, 186), new Color(125, 174, 151));
        registerButton.setBounds(230, 430, 120, 40);
        registerButton.addActionListener(e -> handleRegistration());
        add(registerButton);

        backButton = createStyledButton("Back", new Color(241, 225, 198), new Color(225, 202, 157));
        backButton.setBounds(430, 430, 120, 40);
        backButton.addActionListener(e -> {
            dispose();
            if (!adminMode) new HomePage();
        });
        add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleRegistration() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText();
        String studentId = studentIdField.getText();
        String phone = phoneField.getText();

        if (!validateInputs(email, password, fullName, studentId, phone)) {
            return;
        }

        if (registerUser(email, password, fullName, studentId, phone)) {
            handleSuccess();
        } else {
            JOptionPane.showMessageDialog(this, "Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInputs(String email, String password, String fullName, 
                                   String studentId, String phone) {
        if (email.isEmpty() || password.isEmpty() || fullName.isEmpty() ||
            studentId.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid Email Address!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidStudentId(studentId)) {
            JOptionPane.showMessageDialog(this, "Invalid AIUB Student ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, "Invalid Bangladeshi phone number!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean isValidStudentId(String studentId) {
        return studentId.matches("\\d{2}-\\d{5}-[1-3]");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("01[3-9]\\d{8}");
    }

    private boolean registerUser(String email, String password, String fullName, 
                                 String studentId, String phone) {
        File file = new File("users.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error creating user data file!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(email + ",")) {
                    return false; 
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading user data!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.join(",",
                email,
                password,
                fullName,
                studentId,
                phone
            ));
            writer.newLine();
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving user data!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void handleSuccess() {
        JOptionPane.showMessageDialog(this,
            adminMode ? "User added successfully!" : "Registration successful!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );

        dispose();

        if (!adminMode) {
            new Login();
        }
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
