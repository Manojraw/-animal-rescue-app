package com.animelrescu;

import javax.swing.*;
import java.awt.event.*;

// Import FlowLayout or the layout you want to use
import java.awt.*;

public class AnimalFrame {

    public void display(JPanel parentPanel) {
        parentPanel.setLayout(new BorderLayout());

        JLabel clickHereLabel = new JLabel("Click Here");
        clickHereLabel.setBounds(50, 100, 300, 30);
        clickHereLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font size and style
        clickHereLabel.setForeground(Color.BLACK); // Set text color (e.g., black)
        parentPanel.add(clickHereLabel);
        parentPanel.setLayout(new FlowLayout());// VERY IMPORTANT: Set layout for parentPanel

        JButton openButton = new JButton("Open Search Window");
        openButton.setBounds(150, 150, 100, 30);
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchWindow();
            }
        });

        // Use a Box layout to center the button vertically in the SOUTH area

        // Add the button to the panel

        parentPanel.add(openButton); // Add the panel (containing the button) to the parentPanel

        parentPanel.revalidate(); // Important: Refresh the parent panel
        parentPanel.repaint();
    }
}