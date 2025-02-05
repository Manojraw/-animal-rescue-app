package com.animelrescu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class DuckDuckGoSearchApp extends JFrame {

    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton; // Declaration
    File imageFile = new File("C:\\Users\\manoj\\Downloads\\Gemini_Generated_Image_uilzssuilzssuilz.jpeg");
    ImageIcon logo = new ImageIcon(imageFile.getAbsolutePath());

    public DuckDuckGoSearchApp() {
        setTitle("About Us");

        setSize(600, 400);
        setLayout(new BorderLayout());
        setIconImage(logo.getImage());
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(40);

        searchButton = new JButton("Search"); // Initialization is KEY!

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        // Search Action
        searchButton.addActionListener(new ActionListener() { // Now it's initialized!
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                if (!query.isEmpty()) {
                    if (isValidURL(query) || isValidDomainName(query)) {
                        new HtmlViewer(query); // Open in HtmlViewer
                    } else {
                        searchDuckDuckGo(query); // Perform DuckDuckGo search
                    }
                } else {
                    JOptionPane.showMessageDialog(DuckDuckGoSearchApp.this, "Please enter a search term.",
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void searchDuckDuckGo(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String urlString = "https://api.duckduckgo.com/?q=" + encodedQuery
                    + "&format=json&no_html=1&skip_disambig=1";

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Process JSON Response
                    try {
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        JSONArray relatedTopics = jsonResponse.getJSONArray("RelatedTopics");

                        StringBuilder results = new StringBuilder();
                        for (int i = 0; i < relatedTopics.length(); i++) {
                            JSONObject topic = relatedTopics.getJSONObject(i);
                            if (topic.has("Text") && topic.has("FirstURL")) {
                                results.append(topic.getString("Text")).append("\n");
                                results.append(topic.getString("FirstURL")).append("\n\n");
                            }
                        }
                        if (results.length() > 0) {
                            resultArea.setText(results.toString());
                        } else {
                            resultArea.setText("No results found.");
                        }
                    } catch (JSONException e) {
                        resultArea.setText("Error parsing JSON response.");
                    }
                }
            } else {
                resultArea.setText("Error: Could not fetch search results. HTTP " + responseCode);
            }
        } catch (IOException e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidDomainName(String domain) {
        return domain.matches("^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

}
