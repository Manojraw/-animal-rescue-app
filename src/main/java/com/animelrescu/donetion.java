package com.animelrescu;
import javax.swing.*;



import java.awt.*;
  
    import java.io.File;
   
public class donetion {
    File imageFile = new File("C:\\Users\\manoj\\Downloads\\Gemini_Generated_Image_uilzssuilzssuilz.jpeg");
    ImageIcon logo = new ImageIcon(imageFile.getAbsolutePath());
    public void p() {
        JFrame frame = new JFrame("Animal Rescue Donation Portal");
        frame.setSize(900, 900);
        frame.setIconImage(logo.getImage());
       
        frame.setLayout(null);
        JPanel donationPanel = createDonationPanel();
        frame.add(donationPanel);
        frame.revalidate();
        frame.setVisible(true);
    }
private static JPanel createDonationPanel() {
    JPanel panel= new JPanel();
    panel.setLayout(null);
    panel.setSize(400, 400);
    panel.setBackground(new Color(0,0,0,160));
    panel.setBounds(250,250,400,400);
    
    JLabel R = new JLabel("Donor Name:");
    R.setForeground(Color.white);
    R.setBounds(50, 50, 100, 30);
    panel.add(R);

    JTextField nameField = new JTextField();
     
    nameField.setBounds(200, 50, 150, 30);
    panel.add(nameField);

    JLabel da = new JLabel("Donation Amount:");
    da.setForeground(Color.white);
    da.setBounds(50, 100, 150, 30);
    panel.add(da);

    JTextField amountField = new JTextField();
    amountField.setBounds(200, 100, 150, 30);
    panel.add(amountField);

    JLabel a = new JLabel("Email id:");
    a.setForeground(Color.white);
    a.setBounds(50, 150, 100, 30);
    panel.add(a);

    JTextField emailField = new JTextField();
    emailField.setBounds(200, 150, 150, 30);
    panel.add(emailField);

    JLabel b = new JLabel("Donor Number:");
    b.setForeground(Color.white);
    b.setBounds(50, 200, 150, 30);
    panel.add(b);

    JTextField numberField = new JTextField();
    numberField.setBounds(200, 200, 150, 30);
    panel.add(numberField);

    JButton submitButton = new JButton("Pay Donation");
    
    submitButton.setBounds(150, 300, 150, 30);
    panel.add(submitButton);
       App r=new App();
          r.rog(submitButton);
        return panel;
    }}
    

