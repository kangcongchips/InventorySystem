import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DeleteItem {
    public static void deleteItem(JFrame frame, JTable table, DefaultTableModel tableModel, ArrayList<String[]> items) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            items.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            saveItemsToFile(items);  // Save to file after deletion
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete!");
        }
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
