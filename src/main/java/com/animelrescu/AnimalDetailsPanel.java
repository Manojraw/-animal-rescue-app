package com.animelrescu;

import javax.swing.*;

import javax.swing.ImageIcon;

public class AnimalDetailsPanel extends JPanel {
    private JLabel animalNameLabel;
    private JLabel speciesLabel;
    private JLabel imageLabel;

    public AnimalDetailsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        animalNameLabel = new JLabel("Name:");
        speciesLabel = new JLabel("Species:");
        imageLabel = new JLabel();
        add(animalNameLabel);
        add(speciesLabel);
        add(imageLabel);
    }

    public void setAnimalDetails(String name, String species, ImageIcon image) {
        animalNameLabel.setText("Name: " + name);
        speciesLabel.setText("Species: " + species);
        imageLabel.setIcon(image);
    }
}
