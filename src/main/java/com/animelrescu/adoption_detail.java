package com.animelrescu;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class adoption_detail {
    File imageFile = new File("C:\\Users\\manoj\\Downloads\\Gemini_Generated_Image_uilzssuilzssuilz.jpeg");
    ImageIcon logo = new ImageIcon(imageFile.getAbsolutePath());
    private String lastClickedImage;

    private void saveDetailsToFile(String imagePath, String location, String animalName) {
        String url = "jdbc:mysql://localhost:3306/AnimalRescueDB";
        String user = "root";
        String password = "Manoj@123";

        String query = "INSERT INTO  animaler (image, location, animalname) VALUES (?, ?, ?)";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, password);
                    PreparedStatement stmt = conn.prepareStatement(query)) {

                // Read the image file into a byte array

                stmt.setString(1, imagePath);
                stmt.setString(2, location);
                stmt.setString(3, animalName);
                stmt.executeUpdate();

                System.out.println("Details saved successfully!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void anemaledetail() {
        JButton saveButton = new JButton("Save");

        JPanel adoptionPanel = new JPanel();
        adoptionPanel.setLayout(new BoxLayout(adoptionPanel, BoxLayout.Y_AXIS));
        adoptionPanel.setBackground(new Color(240, 240, 240));
        adoptionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel adoptionLabel = new JLabel("animal rescue Details");
        adoptionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        adoptionLabel.setForeground(new Color(34, 139, 34)); // Dark green
        adoptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adoptionPanel.add(adoptionLabel);

        JTextField inameField = new JTextField("imageField");
        inameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inameField.getPreferredSize().height));
        adoptionPanel.add(inameField);

        JTextField locatonField = new JTextField("locationField");
        locatonField.setMaximumSize(new Dimension(Integer.MAX_VALUE, locatonField.getPreferredSize().height));
        adoptionPanel.add(locatonField);

        JTextField nameField = new JTextField("nameField");
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        adoptionPanel.add(nameField);
        adoptionPanel.add(saveButton);

        JFrame inputFrame = new JFrame("Input Details");
        inputFrame.setIconImage(logo.getImage());
        inputFrame.setSize(300, 200);
        inputFrame.add(adoptionPanel);

        inputFrame.setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastClickedImage = nameField.getText();
                String location = locatonField.getText();
                String animalName = nameField.getText();
                saveDetailsToFile(lastClickedImage, location, animalName);
                JOptionPane.showMessageDialog(inputFrame, "thanks information sucssefully save");
                inputFrame.dispose();
            }
        });
    }

}
