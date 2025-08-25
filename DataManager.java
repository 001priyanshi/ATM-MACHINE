import java.io.*;
import java.util.HashMap;

public class DataManager {
    private static final String DATA_FILE = "accounts.dat";
    
    public static void saveAccounts(HashMap<Integer, Account> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println("Account data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving account data: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static HashMap<Integer, Account> loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            HashMap<Integer, Account> accounts = (HashMap<Integer, Account>) ois.readObject();
            System.out.println("Account data loaded successfully.");
            return accounts;
        } catch (FileNotFoundException e) {
            System.out.println("No existing account data found. Starting with empty database.");
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading account data: " + e.getMessage());
            System.out.println("Starting with empty database.");
            return new HashMap<>();
        }
    }
    
    public static boolean dataFileExists() {
        File file = new File(DATA_FILE);
        return file.exists();
    }
}