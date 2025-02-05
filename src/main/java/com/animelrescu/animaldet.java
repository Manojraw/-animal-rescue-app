package com.animelrescu;
import javax.swing.*;

import java.awt.Desktop;
import java.awt.event.*;
import java.io.*;
public class animaldet {
   


    public static void main(String[] args) {
        // Create JFrame for the application
        JFrame frame = new JFrame("Animal Rescue Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        
        // Dropdown menu for animal selection
        String[] animals = {"Dog", "Cat", "Rabbit", "Bird"};
        JComboBox<String> animalDropdown = new JComboBox<>(animals);
        
        // Button to open the animal details HTML
        JButton detailsButton = new JButton("Show Details");
        
        // Add action listener to the button
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAnimal = (String) animalDropdown.getSelectedItem();
                // Call method to load the details of the selected animal
                loadAnimalDetails(selectedAnimal);
            }
        });
        
        // Add components to the frame
        JPanel panel = new JPanel();
        panel.add(animalDropdown);
        panel.add(detailsButton);
        frame.add(panel);
        
        frame.setVisible(true);
    }

    // Method to open the animal details in a browser or a WebView (in case of desktop apps)
    public static void loadAnimalDetails(String animal) {
        // Generate the path to the corresponding HTML file
        String filePath = "C:\\Users\\manoj\\Desktop\\New folder\\src\\" + animal + "_details.html";
        
        // Open the HTML file in the default web browser
         try {
            File htmlFile = new File(filePath);
            if (htmlFile.exists()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } else {
                JOptionPane.showMessageDialog(null, "No details found for the selected animal.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error opening the HTML file: " + ex.getMessage());
        }
    }


}
