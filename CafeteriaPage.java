import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class CafeteriaPage extends JFrame {
    private List<String> cart;
    private String username;
    private String cafeteriaName;

    public CafeteriaPage(String cafeteriaName, String username) {
        this.cafeteriaName = cafeteriaName;
        this.username = username;
        initialize();
    }

    private void initialize() {
        setTitle(cafeteriaName);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        cart = new ArrayList<>();

        JLabel label = new JLabel("Welcome to " + cafeteriaName);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setBounds(250, 50, 300, 30);
        add(label);

        List<String> foodItems = loadFoodItems();
        
        int y = 100;
        for (String foodItem : foodItems) {
            JLabel itemLabel = new JLabel(foodItem);
            itemLabel.setBounds(100, y, 300, 30);
            add(itemLabel);

            JButton addToCartButton = createStyledButton("Add to Cart", new Color(168, 213, 186), new Color(125, 174, 151));
            addToCartButton.setBounds(350, y, 150, 30);
            addToCartButton.addActionListener(e -> {
                cart.add(foodItem);
                JOptionPane.showMessageDialog(this, foodItem + " added to cart!");
            });
            add(addToCartButton);

            y += 50;
        }

        JButton myCartButton = createStyledButton("My Cart", new Color(241, 225, 198), new Color(225, 202, 157));
        myCartButton.setBounds(550, 50, 100, 30);
        myCartButton.addActionListener(e -> {
            new MyCartPage(username, cart, cafeteriaName);
        });
        add(myCartButton);

        JButton backButton = createStyledButton("Back", new Color(241, 225, 198), new Color(225, 202, 157));
        backButton.setBounds(50, 50, 100, 30);
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard(username);
        });
        add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<String> loadFoodItems() {
        List<String> items = new ArrayList<>();
        String filename = cafeteriaName.toLowerCase().replace(" ", "_") + "_food.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length >= 1) {
                    items.add(parts[0] +" - " + parts[1] + " Tk");
                }
            }
        } catch (IOException e) {
            items.add("Error loading menu!");
        }
        return items;
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
