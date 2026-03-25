import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates:
 * - Serialization & Deserialization
 * - File-based persistence
 * - System recovery after restart
 * - Failure-safe loading
 *
 * @author Ram
 * @version 12.0
 */

// -------------------- Reservation --------------------
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// -------------------- Inventory --------------------
class InventoryService implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("\nInventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

// -------------------- Booking History --------------------
class BookingHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }

    public void display() {
        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }
    }
}

// -------------------- Persistence Service --------------------
class PersistenceService {

    private static final String FILE_NAME = "booking_data.ser";

    // Save data
    public void save(InventoryService inventory, BookingHistory history) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            oos.writeObject(history);

            System.out.println("\n✅ Data saved successfully!");

        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // Load data
    public Object[] load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            InventoryService inventory = (InventoryService) ois.readObject();
            BookingHistory history = (BookingHistory) ois.readObject();

            System.out.println("\n✅ Data loaded successfully!");
            return new Object[]{inventory, history};

        } catch (FileNotFoundException e) {
            System.out.println("\n⚠ No saved data found. Starting fresh.");

        } catch (Exception e) {
            System.out.println("\n❌ Error loading data. Starting with safe state.");
        }

        return null;
    }
}

// -------------------- Main Class --------------------
public class uc12 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 12.0");
        System.out.println("=====================================");

        PersistenceService persistence = new PersistenceService();

        InventoryService inventory;
        BookingHistory history;

        // Load existing data (simulate restart)
        Object[] data = persistence.load();

        if (data != null) {
            inventory = (InventoryService) data[0];
            history = (BookingHistory) data[1];
        } else {
            // Fresh start
            inventory = new InventoryService();
            history = new BookingHistory();
        }

        // Simulate new booking
        Reservation r1 = new Reservation("SR201", "Alice", "Single Room");
        history.add(r1);

        // Display current state
        history.display();
        inventory.display();

        // Save state before shutdown
        persistence.save(inventory, history);

        System.out.println("\n(System can restart and recover this state)");
    }
}