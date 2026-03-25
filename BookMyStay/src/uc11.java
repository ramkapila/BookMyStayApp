import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates:
 * - Multi-threading
 * - Shared resources (Queue + Inventory)
 * - Synchronization (critical sections)
 * - Prevention of race conditions
 *
 * @author Ram
 * @version 11.0
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

// -------------------- Thread-Safe Booking Queue --------------------
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    // synchronized add
    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println(Thread.currentThread().getName() +
                " added request: " + r.getGuestName());
    }

    // synchronized remove
    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// -------------------- Thread-Safe Inventory --------------------
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // critical section
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            // simulate delay (to expose race condition if not synchronized)
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inventory.put(roomType, available - 1);

            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

// -------------------- Booking Processor (Thread) --------------------
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryService inventory;

    public BookingProcessor(BookingQueue queue, InventoryService inventory, String name) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // synchronized retrieval
            synchronized (queue) {
                r = queue.getRequest();
            }

            if (r == null) break;

            // allocate room (critical section inside method)
            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(getName() + " ✅ Booked for " + r.getGuestName()
                        + " (" + r.getRoomType() + ")");
            } else {
                System.out.println(getName() + " ❌ No room available for "
                        + r.getGuestName());
            }
        }
    }
}

// -------------------- Main Class --------------------
public class uc11 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 11.0");
        System.out.println("=====================================");

        // Shared resources
        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Add requests (simulating multiple users)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // extra
        queue.addRequest(new Reservation("David", "Double Room"));

        // Create multiple threads
        Thread t1 = new BookingProcessor(queue, inventory, "Thread-1");
        Thread t2 = new BookingProcessor(queue, inventory, "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        inventory.displayInventory();

        System.out.println("\n(All bookings processed safely without conflicts)");
    }
}