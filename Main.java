import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.view.AddItem;
import src.model.DeleteItem;
import src.model.UpdateItem;
import src.model.LoadItem;

public class Main {
    private static ArrayList<String[]> items; // List to store items
    public static final String FILE_PATH = "src/database/items.txt"; // File path

    public static void main(String[] args) {
        // Initialize item list
        items = new ArrayList<>();

        // Load items from file
        LoadItem.loadItemsFromFile(items);

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
}
