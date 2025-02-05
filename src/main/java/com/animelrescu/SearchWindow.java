package com.animelrescu;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;

import java.util.List;

public class SearchWindow extends JFrame {
    private JTextField searchBar;
    private JButton searchButton;
    File imageFile = new File("C:\\Users\\manoj\\Downloads\\Gemini_Generated_Image_uilzssuilzssuilz.jpeg");
    ImageIcon logo = new ImageIcon(imageFile.getAbsolutePath());
    private AnimalListPanel animalListPanel;

    public SearchWindow() {
        JFrame frame = new JFrame("Search Animals");
        frame.setLayout(new BorderLayout());
        frame.setIconImage(logo.getImage());
        searchBar = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.setVisible(false);
        animalListPanel = new AnimalListPanel(); // Create the AnimalListPanel
        add(animalListPanel, BorderLayout.CENTER);
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                toggleSearchButton();
            }

            public void removeUpdate(DocumentEvent e) {
                toggleSearchButton();
            }

            public void changedUpdate(DocumentEvent e) {
                toggleSearchButton();
            }
        });

        searchButton.addActionListener(e -> searchAnimals());

        JPanel topPanel = new JPanel();
        topPanel.add(searchBar);
        topPanel.add(searchButton);
        animalListPanel = new AnimalListPanel();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(animalListPanel, BorderLayout.CENTER);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void toggleSearchButton() {
        searchButton.setVisible(!searchBar.getText().trim().isEmpty());
    }

    private void searchAnimals() {
        String query = searchBar.getText().trim();
        AnimalApiService apiService = AnimalApiService.create();
        apiService.getAnimals(query).enqueue(new Callback<List<AnimalDTO>>() {
            @Override
            public void onResponse(Call<List<AnimalDTO>> call, Response<List<AnimalDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    animalListPanel.updateAnimalList(response.body()); // Call updateAnimalList with the data
                } else {
                    JOptionPane.showMessageDialog(null, "No animals found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<List<AnimalDTO>> call, Throwable t) {
                JOptionPane.showMessageDialog(null, "Failed to fetch data: " + t.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                t.printStackTrace();
            }
        });
    }
}
