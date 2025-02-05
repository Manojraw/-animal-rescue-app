package com.animelrescu;

import javax.swing.*;

import java.io.IOException;

import java.awt.Desktop;

import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;

public class HtmlViewer extends JFrame { // You might not even need JFrame here

    public HtmlViewer(String urlString) {
        try {
            Desktop.getDesktop().browse(new URI(urlString)); // Open in default browser
        } catch (IOException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(this, "Error opening URL: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE); // Show error message
            ex.printStackTrace(); // Print the error for debugging
        }
        // If you still want a frame to open (perhaps to show a message while the
        // browser opens)
        // You can keep the following code:
        setTitle("Opening in Browser..."); // Optional: Set a title
        setSize(200, 100); // Optional: Set a small size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel("Opening in your default browser...");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
        setVisible(true);

    }

    // ... (No fetchWebsiteContent method needed anymore)

}