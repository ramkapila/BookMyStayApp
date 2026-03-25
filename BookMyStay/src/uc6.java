import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates:
 * - FIFO request processing using Queue
 * - Unique room assignment using Set
 * - Inventory synchronization using HashMap
 *
 * @author Ram
 * @version 6.0
 */

// -------------------- Reservation --------------------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- Booking Queue --------------------
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// -------------------- Inventory --------------------
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

// -------------------- Booking Service --------------------
class BookingService {

    private InventoryService inventory;

    // Track all allocated room IDs globally
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map roomType -> allocated IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    private int idCounter = 1;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 2).toUpperCase() + idCounter++;
        } while (allocatedRoomIds.contains(id));

        return id;
    }

    // Process booking
    public void processReservation(Reservation r) {

        String type = r.getRoomType();

        System.out.println("\nProcessing request for " + r.getGuestName());

        // Check availability
        if (inventory.getAvailability(type) <= 0) {
            System.out.println("❌ No rooms available for " + type);
            return;
        }

        // Generate unique ID
        String roomId = generateRoomId(type);

        // Add to global set (uniqueness)
        allocatedRoomIds.add(roomId);

        // Map room type to allocated IDs
        roomAllocations.putIfAbsent(type, new HashSet<>());
        roomAllocations.get(type).add(roomId);

        // Decrement inventory (atomic step)
        inventory.decrement(type);

        // Confirm booking
        System.out.println("✅ Booking Confirmed!");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Room Type: " + type);
        System.out.println("Allocated Room ID: " + roomId);
    }

    public void displayAllocations() {
        System.out.println("\n--- Allocated Rooms ---");
        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " -> " + roomAllocations.get(type));
        }
    }
}

// -------------------- Main Class --------------------
public class uc6 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 6.0");
        System.out.println("=====================================");

        // Initialize components
        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Add requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }

        // Show results
        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}