package com.animelrescu;

import java.util.List;
import java.util.ArrayList;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class App extends JFrame {

    public App() {
    }

    public void rog(JButton paymentButton) {
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a popup message
                JOptionPane.showMessageDialog(null, "Redirecting to Payment Gateway...");

                // Open the URL in the default web browser
                try {
                    String approvalUrl = processPayment(); // Call the processPayment method
                    java.awt.Desktop.getDesktop().browse(new URI(approvalUrl));
                } catch (IOException | URISyntaxException ex) {
                    // Handle exceptions appropriately
                    JOptionPane.showMessageDialog(null, "Error opening payment gateway: " + ex.getMessage());
                }
            }
        });

    }

    private String processPayment() {
        try {
            String ramu = "";
            String clientId = "AdoJG4bQWdTWuUO4bv4KBsA0ZpZYWrDq_9TwtiSik7Z3EWx2n0_Vzd9QMJOsUhQ54YtHGbcxhdKMphmX";
            String clientSecret = "EMYCrNgUoYyvr9HOs7kuuFl69AiYBGeUP47vM9oSTJa7B_Xvtyi-crXNFPd_R5q2A6NxFdlCUArd6MwV";
            APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");

            Amount amount = new Amount();
            amount.setCurrency("USD");
            amount.setTotal("10");

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription("Donation to Animal Rescue");

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");

            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setReturnUrl("http://127.0.0.1:5500/FILE/CHEOUT.html");
            redirectUrls.setCancelUrl("http://127.0.0.1:5500/FILE/CHEOUT.html");

            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setTransactions(transactions);
            payment.setRedirectUrls(redirectUrls);

            Payment createdPayment = payment.create(apiContext);
            for (Links link : createdPayment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    ramu = link.getHref();
                    // Redirect the user to link.getHref() for PayPal approval
                }
            }
            return ramu;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }
}
