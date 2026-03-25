import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 *
 * This class demonstrates centralized room inventory management
 * using a HashMap to maintain consistency and scalability.
 *
 * Version: 3.1 (Refactored version)
 *
 * @author Ram
 * @version 3.1
 */

// Inventory Class (Centralized Management)
class RoomInventory {

    // HashMap to store room type -> available count
    private Map<String, Integer> inventory;

    // Constructor to initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled update)
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
    }
}

// Main Class
public class uc3 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 3.1");
        System.out.println("=====================================");

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Check availability
        System.out.println("\nChecking availability of Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        // Update availability
        System.out.println("\nUpdating Double Room availability to 2...");
        inventory.updateAvailability("Double Room", 2);

        // Display updated inventory
        inventory.displayInventory();
    }
}