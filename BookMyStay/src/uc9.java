import java.util.*;

/**
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates:
 * - Input validation
 * - Custom exceptions
 * - Fail-fast design
 * - Safe system state handling
 *
 * @author Ram
 * @version 9.0
 */

// -------------------- Custom Exceptions --------------------
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

class InsufficientAvailabilityException extends Exception {
    public InsufficientAvailabilityException(String message) {
        super(message);
    }
}

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

// -------------------- Inventory --------------------
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0); // intentionally unavailable
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) throws InsufficientAvailabilityException {
        int current = getAvailability(roomType);

        if (current <= 0) {
            throw new InsufficientAvailabilityException(
                    "No rooms available for: " + roomType);
        }

        inventory.put(roomType, current - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// -------------------- Validator --------------------
class BookingValidator {

    private InventoryService inventory;

    public BookingValidator(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void validate(Reservation r)
            throws InvalidRoomTypeException, InsufficientAvailabilityException {

        // Validate room type
        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidRoomTypeException(
                    "Invalid room type: " + r.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InsufficientAvailabilityException(
                    "No availability for: " + r.getRoomType());
        }
    }
}

// -------------------- Booking Service --------------------
class BookingService {

    private InventoryService inventory;
    private BookingValidator validator;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
        this.validator = new BookingValidator(inventory);
    }

    public void processBooking(Reservation r) {

        System.out.println("\nProcessing booking for " + r.getGuestName());

        try {
            // Fail-fast validation
            validator.validate(r);

            // Safe allocation
            inventory.decrement(r.getRoomType());

            System.out.println("✅ Booking successful for " + r.getRoomType());

        } catch (InvalidRoomTypeException e) {
            System.out.println("❌ ERROR: " + e.getMessage());

        } catch (InsufficientAvailabilityException e) {
            System.out.println("❌ ERROR: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("❌ Unexpected error occurred");
        }
    }
}

// -------------------- Main Class --------------------
public class uc9 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 9.0");
        System.out.println("=====================================");

        // Initialize system
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Test cases
        Reservation r1 = new Reservation("Alice", "Single Room");   // valid
        Reservation r2 = new Reservation("Bob", "Suite Room");      // no availability
        Reservation r3 = new Reservation("Charlie", "Deluxe Room"); // invalid type

        // Process bookings
        bookingService.processBooking(r1);
        bookingService.processBooking(r2);
        bookingService.processBooking(r3);

        // Final inventory
        inventory.displayInventory();

        System.out.println("\n(Note: System continues safely after errors)");
    }
}