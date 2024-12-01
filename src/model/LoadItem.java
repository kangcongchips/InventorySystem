package src.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LoadItem {
    public static final String FILE_PATH = "src/database/items.txt";

    public static void loadItemsFromFile(ArrayList<String[]> items) {
        File file = new File(FILE_PATH);

        // Ensure directory exists
        if (!file.exists()) {
            file.getParentFile().mkdirs();  // Create directories if not exist
            try {
                file.createNewFile();  // Create file if it doesn't exist
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating file: " + e.getMessage());
            }
        }

        // Load items from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] item = line.split("\\|");
                if (item.length == 2) { // Ensure valid format
                    items.add(item);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage());
        }
    }
}
