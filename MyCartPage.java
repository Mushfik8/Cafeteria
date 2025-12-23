import javax.swing.*;
import java.awt.*;
import java.util.List; 
import java.util.ArrayList;
import java.io.*;
import java.time.LocalDateTime;

public class MyCartPage extends JFrame {
    private String username;
    private List<String> cart;
    private String cafeteriaName;

    public MyCartPage(String username, List<String> cart, String cafeteriaName) {
        this.username = username;
        this.cart = cart;
        this.cafeteriaName = cafeteriaName;
        initialize();
    }

    private void initialize() {
        setTitle("My Cart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel cartLabel = new JLabel("My Cart - " + username);
        cartLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cartLabel.setBounds(250, 20, 300, 30);
        add(cartLabel);

        DefaultListModel<String> cartModel = new DefaultListModel<>();
        for (String item : cart) {
            cartModel.addElement(item);
        }
        JList<String> cartList = new JList<>(cartModel);
        JScrollPane scrollPane = new JScrollPane(cartList);
        scrollPane.setBounds(150, 80, 500, 200);
        add(scrollPane);

        JLabel detailsLabel = new JLabel("Order Summary:");
        detailsLabel.setBounds(150, 300, 200, 30);
        add(detailsLabel);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setText("Cafeteria: " + cafeteriaName + "\n"
                          + "Items: " + String.join(", ", cart) + "\n"
                          + "Total Items: " + cart.size());
        detailsArea.setBounds(150, 330, 500, 80);
        add(detailsArea);

        JButton placeOrderButton = createStyledButton("Place Order", new Color(168, 213, 186), new Color(125, 174, 151));
        placeOrderButton.setBounds(300, 450, 200, 40);
        placeOrderButton.addActionListener(e -> saveOrderHistory());
        add(placeOrderButton);

        JButton backButton = createStyledButton("Back", new Color(241, 225, 198), new Color(225, 202, 157));
        backButton.setBounds(650, 20, 120, 30);
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard(username);
        });
        add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveOrderHistory() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("order_history.txt", true))) {
            String timestamp = LocalDateTime.now().toString();
            String orderEntry = String.join(" | ",
                username,
                cafeteriaName,
                String.join(", ", cart),
                timestamp
            );
            
            writer.write(orderEntry);
            writer.newLine();
            
            JOptionPane.showMessageDialog(this, 
                "Order placed successfully!\nEstimated delivery time: 30 minutes",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            cart.clear();
            dispose();
            new Dashboard(username);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Failed to save order!\nPlease try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
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
