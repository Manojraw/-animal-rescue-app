package com.animelrescu; // Make sure this is the correct package

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import java.util.HashSet;
import java.awt.datatransfer.StringSelection;

public class AnimalListPanel extends JPanel {

    private JTable animalTable;
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;

    public AnimalListPanel() { // Constructor
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        animalTable = new JTable(tableModel);

        animalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Important!
        animalTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Important: Ignore adjusting events
                    boolean isSelected = animalTable.getSelectedRowCount() > 0;
                    popupMenu.setEnabled(isSelected); // Enable/disable popup menu
                }
            }
        });

        // Create popup menu
        popupMenu = new JPopupMenu();
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        popupMenu.add(copyMenuItem);

        copyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard();
            }
        });

        // Add mouse listener to show popup menu
        animalTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // Disable popup menu initially
        popupMenu.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(animalTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        tableModel.addTableModelListener(e -> { // Listener for dynamic column sizing
            if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.UPDATE) {
                SwingUtilities.invokeLater(() -> {
                    int columnCount = animalTable.getColumnCount();
                    for (int i = 0; i < columnCount; i++) {
                        TableColumn column = animalTable.getColumnModel().getColumn(i);
                        int preferredWidth = column.getMinWidth();
                        for (int j = 0; j < animalTable.getRowCount(); j++) {
                            Component cellRenderer = animalTable.prepareRenderer(animalTable.getCellRenderer(j, i), j,
                                    i);
                            preferredWidth = Math.max(preferredWidth, cellRenderer.getPreferredSize().width);
                        }
                        column.setPreferredWidth(preferredWidth);
                    }
                    animalTable.doLayout(); // Force layout recalculation
                });
            }
        });
    }

    private void copyToClipboard() {
        StringBuilder sb = new StringBuilder();
        int numCols = animalTable.getColumnCount();
        // Copy only selected rows

        // Copy header (all columns)
        for (int i = 0; i < numCols; i++) {
            sb.append(animalTable.getColumnName(i)).append("\t");
        }
        sb.append("\n");

        // Copy data (only selected rows)
        int[] selectedRows = animalTable.getSelectedRows(); // Get selected rows
        for (int selectedRow : selectedRows) {
            for (int j = 0; j < numCols; j++) {
                Object cellValue = animalTable.getValueAt(selectedRow, j);
                sb.append(cellValue == null ? "" : cellValue.toString()).append("\t");
            }
            sb.append("\n");
        }

        StringSelection stringSelection = new StringSelection(sb.toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

        JOptionPane.showMessageDialog(this, "Selected table data copied to clipboard.");
    }

    public void updateAnimalList(List<AnimalDTO> animals) { // Method to update the list (called from SearchWindow)
        tableModel.setRowCount(0); // Clear existing data
        tableModel.setColumnCount(0); // Clear existing columns

        if (animals == null || animals.isEmpty()) {
            return; // Handle empty or null list
        }

        java.util.Set<String> allKeys = new HashSet<>();
        for (AnimalDTO animal : animals) {
            if (animal.getTaxonomy() != null) {
                allKeys.addAll(animal.getTaxonomy().keySet());
            }
            if (animal.getCharacteristics() != null) {
                allKeys.addAll(animal.getCharacteristics().keySet());
            }
        }

        tableModel.addColumn("Name"); // Always add name column
        for (String key : allKeys) {
            tableModel.addColumn(key);
        }

        for (AnimalDTO animal : animals) {
            Object[] rowData = new Object[allKeys.size() + 1];
            rowData[0] = animal.getName();

            int columnIndex = 1;

            for (String key : allKeys) {
                Object value = null;

                if (animal.getTaxonomy() != null && animal.getTaxonomy().containsKey(key)) {
                    value = animal.getTaxonomy().get(key);
                } else if (animal.getCharacteristics() != null && animal.getCharacteristics().containsKey(key)) {
                    value = animal.getCharacteristics().get(key);
                }

                rowData[columnIndex] = value;
                columnIndex++;
            }
            tableModel.addRow(rowData);
        }
        SwingUtilities.invokeLater(() -> { // Adjust column widths on initial load
            int columnCount = animalTable.getColumnCount(); // Get the *actual* column count *after* data load

            for (int i = 0; i < columnCount; i++) { // Use the actual column count
                if (i < animalTable.getColumnModel().getColumnCount()) { // Check bounds before getting the column
                    TableColumn column = animalTable.getColumnModel().getColumn(i);
                    int preferredWidth = column.getMinWidth();
                    for (int j = 0; j < animalTable.getRowCount(); j++) {
                        Component cellRenderer = animalTable.prepareRenderer(animalTable.getCellRenderer(j, i), j, i);
                        preferredWidth = Math.max(preferredWidth, cellRenderer.getPreferredSize().width);
                    }
                    column.setPreferredWidth(preferredWidth);
                }
            }
        });
    }
}