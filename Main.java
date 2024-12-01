import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {
    public static final String FILE_PATH = "database/items.txt";
    private static ArrayList<String[]> items;

    public static void main(String[] args) {
        // Initialize item list and load from file
        items = new ArrayList<>();
        loadItemsFromFile();

        // Frame setup
        JFrame frame = new JFrame("Inventory System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table setup
        String[] columnNames = {"Item Name", "Quantity"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        // Populate table with loaded items
        for (String[] item : items) {
            tableModel.addRow(item);
        }

        // Buttons
        JButton addButton = new JButton("Add Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton updateButton = new JButton("Update Item");

        // Add item logic
        addButton.addActionListener(e -> AddItem.openAddItemDialog(frame, tableModel, items));

        // Delete item logic
        deleteButton.addActionListener(e -> DeleteItem.deleteItem(frame, table, tableModel, items));

        // Update item logic
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String name = (String) tableModel.getValueAt(selectedRow, 0);
                String quantity = (String) tableModel.getValueAt(selectedRow, 1);
                UpdateItem.openUpdateItemDialog(frame, tableModel, items, selectedRow, name, quantity);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row to update!");
            }
        });

        // Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        frame.getContentPane().add(tableScroll, "Center");
        frame.getContentPane().add(buttonPanel, "South");

        frame.setVisible(true);
    }

    // Load items from file
    private static void loadItemsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();  // Ensure directory exists
            try {
                file.createNewFile();  // Create file if it doesn't exist
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating file: " + e.getMessage());
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] item = line.split("\\|");
                items.add(item);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage());
        }
    }
}
