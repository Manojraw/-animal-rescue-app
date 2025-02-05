package com.animelrescu;

import javax.swing.*;

import java.awt.event.*;
import java.sql.*;

import java.awt.*;

import java.io.File;

public class manu3 {

    // Changed from JFrame to JPanel
    private JTextField animalNameField;
    private JButton sellButton;
    File imageFile = new File("C:\\Users\\manoj\\Downloads\\Gemini_Generated_Image_uilzssuilzssuilz.jpeg");
    ImageIcon logo = new ImageIcon(imageFile.getAbsolutePath());

    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/AnimalAdoptionDB", "root", "Manoj@123");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void display() {
        // Create JFrame
        JFrame frame = new JFrame("Animal Rescue App");
        frame.getContentPane().setBackground(Color.BLUE);
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setIconImage(logo.getImage());
        // Create JPanel
        JPanel search = new JPanel();

        search.setSize(400, 300);
        search.setBackground(new Color(0, 0, 0, 160));
        search.setBounds(200, 150, 400, 300);
        frame.add(search);

        JLabel titleLabel = new JLabel("search for adoption ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(Color.WHITE); // Dark green
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);// Set text color (e.g., black)
        search.add(titleLabel);
        // TextField for custom search
        animalNameField = new JTextField();
        animalNameField.setBounds(50, 150, 300, 30);
        animalNameField.setVisible(false); // Initially hidden
        search.add(animalNameField);

        // ComboBox for table selection
        JComboBox<String> tableList = new JComboBox<>();
        tableList.addItem("Select Animal");

        // Load table names from database
        Connection conn = connect();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW TABLES");
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    tableList.addItem(tableName); // Add table names to ComboBox
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        tableList.setBounds(50, 100, 300, 30);
        search.add(tableList);

        // Create "Adoption" button
        sellButton = new JButton("Adoption");
        sellButton.setBounds(150, 200, 100, 30);
        search.add(sellButton);

        // Action listener for "Adoption" button
        sellButton.addActionListener(e -> {
            String selectedTable = (String) tableList.getSelectedItem();
            String searchText = animalNameField.getText();

            if ("Select Animal".equals(selectedTable)) {
                JOptionPane.showMessageDialog(frame, "Please select an animal!");
                return;
            }

            if (selectedTable.equals("Other")) {
                animalNameField.setVisible(true); // Show text field for custom input
            } else {
                animalNameField.setVisible(false); // Hide text field for table selection
                adoptain dog = new adoptain();
                dog.showTableData(selectedTable, searchText);
            }
        });

        // Mouse listener for ComboBox
        tableList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedTable = (String) tableList.getSelectedItem();
                if (!"Select Animal".equals(selectedTable) && !"Other".equals(selectedTable)) {
                    animalNameField.setText(""); // Clear search text
                    animalNameField.setVisible(false); // Hide text field
                    adoptain dog = new adoptain();
                    dog.showTableData(selectedTable, ""); // No search for table selection
                }
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }

}
