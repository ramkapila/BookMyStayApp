import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 *
 * This class demonstrates booking request handling using a Queue
 * to maintain First-Come-First-Served (FIFO) order.
 *
 * No inventory updates are performed here.
 *
 * @author Ram
 * @version 5.0
 */

// -------------------- Reservation Class --------------------
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}

// -------------------- Booking Queue --------------------
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue (FIFO) ---");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }

    // Peek next request (no removal)
    public Reservation peekNext() {
        return queue.peek();
    }
}

// -------------------- Main Class --------------------
public class uc5 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 5.0");
        System.out.println("=====================================");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate booking requests (arrival order)
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayQueue();

        // Show next request to be processed
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.peekNext();
        if (next != null) {
            next.display();
        }

        System.out.println("\n(Note: No inventory changes at this stage)");
    }
}