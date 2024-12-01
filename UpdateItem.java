import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UpdateItem {
    public static void openUpdateItemDialog(JFrame frame, DefaultTableModel tableModel, ArrayList<String[]> items, int rowIndex, String currentName, String currentQuantity) {
        JDialog dialog = new JDialog(frame, "Update Item", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Item Name:");
        JTextField nameField = new JTextField(currentName, 15);

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(currentQuantity, 5);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String quantity = quantityField.getText();

            if (!name.isEmpty() && !quantity.isEmpty()) {
                // Update the item in the list and table
                items.set(rowIndex, new String[]{name, quantity});
                tableModel.setValueAt(name, rowIndex, 0);
                tableModel.setValueAt(quantity, rowIndex, 1);
                saveItemsToFile(items);  // Save to file after updating
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please enter both name and quantity!");
            }
        });

        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(quantityLabel);
        dialog.add(quantityField);
        dialog.add(saveButton);

        dialog.setVisible(true);
    }

    public static void saveItemsToFile(ArrayList<String[]> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.FILE_PATH))) {
            for (String[] item : items) {
                writer.write(item[0] + "|" + item[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage());
        }
    }
}
