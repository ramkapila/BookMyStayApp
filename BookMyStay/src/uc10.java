import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates:
 * - Stack (LIFO) for rollback
 * - Safe cancellation with validation
 * - Inventory restoration
 * - Controlled state mutation
 *
 * @author Ram
 * @version 10.0
 */

// -------------------- Reservation --------------------
class Reservation {
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

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }
}

// -------------------- Inventory --------------------
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// -------------------- Booking Store --------------------
class BookingStore {

    // Stores active bookings
    private Map<String, Reservation> bookings = new HashMap<>();

    public void addBooking(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public boolean exists(String reservationId) {
        return bookings.containsKey(reservationId);
    }

    public Reservation get(String reservationId) {
        return bookings.get(reservationId);
    }

    public void remove(String reservationId) {
        bookings.remove(reservationId);
    }
}

// -------------------- Cancellation Service --------------------
class CancellationService {

    private InventoryService inventory;
    private BookingStore bookingStore;

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(InventoryService inventory, BookingStore bookingStore) {
        this.inventory = inventory;
        this.bookingStore = bookingStore;
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nProcessing cancellation for ID: " + reservationId);

        // Validate existence
        if (!bookingStore.exists(reservationId)) {
            System.out.println("❌ ERROR: Reservation does not exist or already cancelled.");
            return;
        }

        // Get booking
        Reservation r = bookingStore.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increment(r.getRoomType());

        // Remove booking
        bookingStore.remove(reservationId);

        System.out.println("✅ Cancellation successful for " + r.getGuestName());
        System.out.println("Room released: " + r.getRoomType());
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// -------------------- Main Class --------------------
public class uc10 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 10.0");
        System.out.println("=====================================");

        // Initialize services
        InventoryService inventory = new InventoryService();
        BookingStore bookingStore = new BookingStore();
        CancellationService cancelService = new CancellationService(inventory, bookingStore);

        // Simulate confirmed bookings (from UC6)
        Reservation r1 = new Reservation("SR101", "Alice", "Single Room");
        Reservation r2 = new Reservation("DR102", "Bob", "Double Room");

        bookingStore.addBooking(r1);
        bookingStore.addBooking(r2);

        // Perform cancellation
        cancelService.cancelBooking("SR101");  // valid
        cancelService.cancelBooking("SR101");  // invalid (already cancelled)
        cancelService.cancelBooking("XX999");  // invalid (not exist)

        // Show rollback stack
        cancelService.displayRollbackStack();

        // Show updated inventory
        inventory.displayInventory();
    }
}