package com.animelrescu;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class AnimalRescueApp {
    private JFrame frame;
    private JButton rescueButton;
    private JButton anotherButton;
    private JButton thirdButton;

    File imageFile = new File("C:\\Users\\manoj\\Downloads\\Gemini_Generated_Image_uilzssuilzssuilz.jpeg");
    ImageIcon logo = new ImageIcon(imageFile.getAbsolutePath());

    public AnimalRescueApp() {
        initializeUI();

    }

    private void initializeUI() {
        frame = new JFrame("Animal Rescue Application");

        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(logo.getImage());
        new manue(frame);
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        System.out.println(frameHeight);
        System.out.println(frameWidth);

        JPanel SEARCH = new JPanel();
        SEARCH.setBackground(new Color(0, 0, 0, 90));
        SEARCH.setBounds(0, 0, 780, 90);
        AnimalFrame f = new AnimalFrame();

        JLabel titleLabel = new JLabel("Welcome to Animal Rescue");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(Color.WHITE); // Dark green
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        SEARCH.add(titleLabel);
        JPanel search = new JPanel();
        search.setLayout(null);
        search.setSize(50, 20);
        search.setBackground(new Color(0, 0, 0, 160));
        search.setBounds(290, 200, 400, 100);
        f.display(search);

        ImageIcon backgroundPanel = new ImageIcon("C:\\Users\\manoj\\OneDrive\\Documents\\Pictures\\image.png");

        JLabel background = new JLabel();
        frame.add(background);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();

                Image img = backgroundPanel.getImage();

                // Calculate new dimensions while maintaining aspect ratio
                int imgWidth = backgroundPanel.getIconWidth();
                int imgHeight = backgroundPanel.getIconHeight();
                double frameAspect = (double) frameWidth / frameHeight;
                double imgAspect = (double) imgWidth / imgHeight;

                int newWidth, newHeight;
                if (imgAspect > frameAspect) {
                    // Image is wider than frame; fit height and overflow width
                    newHeight = frameHeight;
                    newWidth = (int) (newHeight * imgAspect);
                } else {
                    // Image is taller than frame; fit width and overflow height
                    newWidth = frameWidth;
                    newHeight = (int) (newWidth / imgAspect);
                }

                Image tempImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                background.setIcon(new ImageIcon(tempImg));
                background.setBounds(0, 0, frameWidth, frameHeight);
                SEARCH.setBounds(0, 0, frameWidth, 90);
                background.add(SEARCH);
                background.add(search);
                frame.add(background);

            }
        });

        // fraim
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();
                int x = (frameWidth - search.getWidth()) / 2;
                int y = (frameHeight - search.getHeight()) / 2; // Update the button's position when the frame is
                                                                // resized
                search.setLocation(x, y);
            }
        });

        rescueButton = new JButton("Rescue");
        anotherButton = new JButton("Emergency Hotline");
        thirdButton = new JButton("donetion");
        rescueButton.setBackground(Color.BLUE);
        anotherButton.setBackground(Color.BLUE);
        thirdButton.setBackground(Color.BLUE);

        // Set button text color to white for better contrast
        rescueButton.setForeground(Color.black);
        anotherButton.setForeground(Color.black);
        thirdButton.setForeground(Color.black);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(rescueButton);
        buttonPanel.add(anotherButton);
        buttonPanel.add(thirdButton);
        emrjencytable r = new emrjencytable();
        adoption_detail p = new adoption_detail();
        donetion t = new donetion();

        rescueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p.anemaledetail();
            }

        });
        anotherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                r.animaleanzio();
            }

        });
        thirdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.p();
            }

        });
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

}
