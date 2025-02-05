package com.animelrescu;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class manue {

    manue(Frame frame) {
        // Create JFrame for the application

        // Create a button with three dots (Menu button)
        ImageIcon originalIcon = new ImageIcon(
                "C:\\Users\\manoj\\OneDrive\\Documents\\Pictures\\1954554_hamburger_line_menu_more buttton_three_icon.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JButton menuButton = new JButton(scaledIcon); // Representing three dots
        menuButton.setPreferredSize(new Dimension(10, 40));
        menuButton.setBounds(frame.getWidth() - 70, 10, 40, 40);
        // Create a popup menu (dropdown menu)
        JPopupMenu popupMenu = new JPopupMenu();

        // Add menu items to the popup menu
        JMenuItem aboutUsItem = new JMenuItem("About Us");
        JMenuItem contactUsItem = new JMenuItem("Contact Us");
        JMenuItem adoptionDetailsItem = new JMenuItem("Adoption Details");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem feedbackItem = new JMenuItem("Feedback");

        // Add action listeners to the menu items
        aboutUsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new DuckDuckGoSearchApp());
            }
        });

        contactUsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Contact Us Information");
            }
        });

        adoptionDetailsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                manu3 dog = new manu3(); // Create an instance of the class
                dog.display();

            }
        });

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Help Information");
            }
        });

        feedbackItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Feedback Form");
            }
        });

        // Add the menu items to the popup menu
        popupMenu.add(aboutUsItem);
        popupMenu.add(contactUsItem);
        popupMenu.add(adoptionDetailsItem);
        popupMenu.add(helpItem);
        popupMenu.add(feedbackItem);

        // ActionListener for the menu button to show the popup menu
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(menuButton, 0, menuButton.getHeight());
            }
        });

        // Add the menu button to the panel
        frame.add(menuButton);

        // Add the panel to the frame
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Update the button's position when the frame is resized
                menuButton.setLocation(frame.getWidth() - 70, 10);
            }
        });

        // Set the frame visible

    }
}
