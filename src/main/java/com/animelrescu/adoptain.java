package com.animelrescu;

import javax.swing.*;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class adoptain {
    File imageFile = new File("C:\\Users\\manoj\\Downloads\\Gemini_Generated_Image_uilzssuilzssuilz.jpeg");
    ImageIcon logo = new ImageIcon(imageFile.getAbsolutePath());

    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ animaladoptiondb", "root", "Manoj@123");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection connection() {
        return connect();
    }

    // Make table names clickable

    public void showTableData(String tableName, String searchText) {
        JFrame tableFrame = new JFrame("Table Data: " + tableName);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setSize(600, 400);
        tableFrame.setIconImage(logo.getImage());
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
                    query = "SELECT * FROM " + tableName + " WHERE column_name LIKE '%" + searchText + "%'"; // Modify
                                                                                                             // "column_name"
                                                                                                             // with
                                                                                                             // your
                                                                                                             // actual
                                                                                                             // column
                                                                                                             // for
                                                                                                             // search
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
