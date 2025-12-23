import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class AdminDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private DefaultListModel<String> userListModel;
    private DefaultListModel<String> foodListModel;
    private DefaultListModel<String> orderListModel;
    private JComboBox<String> cafeteriaComboBox;
    private JTextField foodNameField, foodPriceField, foodPhotoField;

    public AdminDashboard() {
        super("Admin Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        tabbedPane = new JTabbedPane();
        
        JPanel userPanel = createUserPanel();
        tabbedPane.addTab("User Management", userPanel);
        
        JPanel foodPanel = createFoodPanel();
        tabbedPane.addTab("Food Management", foodPanel);
        
        JPanel orderPanel = createOrderPanel();
        tabbedPane.addTab("Order Management", orderPanel);
        
        JButton logoutButton = createStyledButton("Logout", new Color(241, 225, 198), new Color(225, 202, 157));
        logoutButton.addActionListener(e -> {
            dispose();
            new HomePage();
        });
        add(logoutButton, BorderLayout.SOUTH);
        
        add(tabbedPane);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("User Management"));
        
       
        userListModel = new DefaultListModel<>();
        loadUsers();
        JList<String> userList = new JList<>(userListModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        
        
        JButton addUserButton = createStyledButton("Add New User", new Color(168, 213, 186), new Color(125, 174, 151));
        addUserButton.addActionListener(e -> {
            new Registration(true);
            loadUsers(); 
        });
        
        JButton deleteUserButton = createStyledButton("Delete Selected User", new Color(241, 225, 198), new Color(225, 202, 157));
        deleteUserButton.addActionListener(e -> deleteUser(userList.getSelectedValue()));
        
        JButton refreshButton = createStyledButton("Refresh List", new Color(168, 213, 186), new Color(125, 174, 151));
        refreshButton.addActionListener(e -> loadUsers());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(refreshButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createFoodPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Food Item Management"));
        
        
        JPanel controlsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        cafeteriaComboBox = new JComboBox<>(new String[]{"Cafe Saniya's", "Rooftop Cafe", "Main Canteen", "Bioscope Lane", "Cafeteria 5", "Cafeteria 6"});
        foodNameField = new JTextField();
        foodPriceField = new JTextField();
        foodPhotoField = new JTextField();
        
        controlsPanel.add(new JLabel("Select Cafeteria:"));
        controlsPanel.add(cafeteriaComboBox);
        controlsPanel.add(new JLabel("Food Name:"));
        controlsPanel.add(foodNameField);
        controlsPanel.add(new JLabel("Price:"));
        controlsPanel.add(foodPriceField);
        controlsPanel.add(new JLabel("Photo Path:"));
        controlsPanel.add(foodPhotoField);
        
        JButton addFoodButton = createStyledButton("Add/Update Food", new Color(168, 213, 186), new Color(125, 174, 151));
        addFoodButton.addActionListener(e -> saveFoodItem());
        
        JButton deleteFoodButton = createStyledButton("Delete Selected Food", new Color(241, 225, 198), new Color(225, 202, 157));
        deleteFoodButton.addActionListener(e -> deleteFoodItem());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addFoodButton);
        buttonPanel.add(deleteFoodButton);
        
        
        foodListModel = new DefaultListModel<>();
        JList<String> foodList = new JList<>(foodListModel);
        JScrollPane foodScroll = new JScrollPane(foodList);
        
        cafeteriaComboBox.addActionListener(e -> loadFoodItems());
        
        panel.add(controlsPanel, BorderLayout.NORTH);
        panel.add(foodScroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Order Management"));
        
        orderListModel = new DefaultListModel<>();
        loadOrders();
        JList<String> orderList = new JList<>(orderListModel);
        JScrollPane scrollPane = new JScrollPane(orderList);
        
        JButton refreshButton = createStyledButton("Refresh Orders", new Color(168, 213, 186), new Color(125, 174, 151));
        refreshButton.addActionListener(e -> loadOrders());
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);
        
        return panel;
    }

    private void loadUsers() {
        userListModel.clear();
        File file = new File("users.txt");
        
        if (!file.exists()) {
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                userListModel.addElement(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    private void deleteUser(String userInfo) {
        if (userInfo == null) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }
    
        String username = userInfo.split(" \\| ")[0];
        ArrayList<String> users = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(username + ",")) {
                    users.add(line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading users.txt!");
            return;
        }
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            for (String user : users) {
                bw.write(user + "\n");
            }
            JOptionPane.showMessageDialog(this, "User deleted successfully!");
            loadUsers(); 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving changes!");
        }
    }    

    private void loadFoodItems() {
        foodListModel.clear();
        String cafeteria = (String) cafeteriaComboBox.getSelectedItem();
        String filename = cafeteria.toLowerCase().replace(" ", "_") + "_food.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                foodListModel.addElement(line);
            }
        } catch (IOException e) {
           
        }
    }

    private void saveFoodItem() {
        String cafeteria = (String) cafeteriaComboBox.getSelectedItem();
        String foodName = foodNameField.getText();
        String price = foodPriceField.getText();
        String photo = foodPhotoField.getText();
    
        if (foodName.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }
    
        String filename = cafeteria.toLowerCase().replace(" ", "_") + "_food.txt";
        ArrayList<String> items = new ArrayList<>();
        boolean exists = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(foodName + ",")) {
                    exists = true;
                    items.add(foodName + "," + price + "," + photo);
                } else {
                    items.add(line);
                }
            }
        } catch (IOException e) {
           
        }
    
        if (!exists) {
            items.add(foodName + "," + price + "," + photo);
        }
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String item : items) {
                bw.write(item + "\n");
            }
            JOptionPane.showMessageDialog(this, "Food item saved successfully!");
            loadFoodItems(); 
            clearFoodFields();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving food item!");
        }
    }    

    private void deleteFoodItem() {
        String selected = foodListModel.getElementAt(foodListModel.size() - 1);
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a food item to delete.");
            return;
        }
    
        String cafeteria = (String) cafeteriaComboBox.getSelectedItem();
        String filename = cafeteria.toLowerCase().replace(" ", "_") + "_food.txt";
        ArrayList<String> items = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals(selected)) {
                    items.add(line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting food item!");
            return;
        }
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String item : items) {
                bw.write(item + "\n");
            }
            JOptionPane.showMessageDialog(this, "Food item deleted successfully!");
            loadFoodItems(); 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving changes!");
        }
    }    

    private void loadOrders() {
        orderListModel.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("order_history.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                orderListModel.addElement(line);
            }
        } catch (IOException e) {
           
        }
    }

    private void clearFoodFields() {
        foodNameField.setText("");
        foodPriceField.setText("");
        foodPhotoField.setText("");
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createBevelBorder(0));
        button.setPreferredSize(new Dimension(120, 40));
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
