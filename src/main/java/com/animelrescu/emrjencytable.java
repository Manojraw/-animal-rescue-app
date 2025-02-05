package com.animelrescu;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class emrjencytable {
    File imageFile = new File("C:\\Users\\manoj\\Downloads\\Gemini_Generated_Image_uilzssuilzssuilz.jpeg");
    ImageIcon logo = new ImageIcon(imageFile.getAbsolutePath());
    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/animalrescuedb", "root", "Manoj@123");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public  void animaleanzio() {
        JFrame frame = new JFrame("Animal Rescue anzios");
        frame.setIconImage(logo.getImage());
        frame.setSize(400, 250);
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        placeComponents(panel);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Select Table:");
        userLabel.setBounds(10, 20, 80, 25);
        userLabel.setForeground(Color.white);
        panel.add(userLabel);

        JComboBox<String> tableList = new JComboBox<>();
        tableList.addItem("Other"); // Add "Other" option

        // Load table names from database
        Connection conn = connect();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW TABLES");
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    tableList.addItem(tableName); // Add table name to JComboBox
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        tableList.setBounds(100, 20, 165, 25);
        panel.add(tableList);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(280, 20, 80, 25);
        panel.add(searchButton);

        JTextField searchField = new JTextField(); // Add search field
        searchField.setBounds(100, 50, 165, 25);
        searchField.setVisible(false); // Hide search field initially
        panel.add(searchField);

        searchButton.addActionListener(e -> {
            String selectedTable = (String) tableList.getSelectedItem();
            String searchText = searchField.getText();

            if (selectedTable.equals("Other")) {
                // Handle "Other" selection (show search text field)
                searchField.setVisible(true);
            } else {
                searchField.setVisible(false); // Hide search field for table selection
                
                emrjencytable dog = new emrjencytable(); // Create an instance of the class
                dog.showTableData(selectedTable, searchText); //
            }
        });

        // Make table names clickable
        tableList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Check for single click
                    String selectedTable = (String) tableList.getSelectedItem();
                    if (!selectedTable.equals("Other")) {
                        searchField.setText(""); // Clear search text
                        searchField.setVisible(false); // Hide search field
                        emrjencytable dog = new emrjencytable(); // Create an instance of the class
                        dog.showTableData(selectedTable, "searchText"); //; // No search for table selection
                    }
                }
            }
        });
    }

    private void showTableData(String tableName, String searchText) {
        JFrame tableFrame = new JFrame("Table Data: " + tableName);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setSize(600, 400);

        // Create a JTable
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        tableFrame.add(scrollPane);

        Connection conn = connect();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                String query;
                if (searchText.isEmpty()) {
                    query = "SELECT * FROM " + tableName;
                } else {
                    // Add search criteria based on searchText
                    query = "SELECT * FROM " + tableName + " WHERE column_name LIKE '%" + searchText + "%'"; // Modify "column_name" with your actual column for search
                }
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();

                // Create a JTable model
                DefaultTableModel tableModel = new DefaultTableModel(); // Declare and initialize tableModel here

                // Get column names
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    tableModel.addColumn(rsmd.getColumnName(i));
                }

                // Add table data
                while (rs.next()) {
                    Object[] rowData = new Object[rsmd.getColumnCount()];
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        rowData[i - 1] = rs.getObject(i);
                    }
                    tableModel.addRow(rowData);
                }

                table.setModel(tableModel);
                tableFrame.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
    