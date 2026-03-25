import java.util.*;

/**
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates:
 * - Storing confirmed bookings using List
 * - Maintaining insertion order (chronological)
 * - Generating reports without modifying data
 *
 * @author Ram
 * @version 8.0
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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// -------------------- Booking History --------------------
class BookingHistory {

    // List maintains order of confirmation
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
        System.out.println("✔ Added to history: " + r.getReservationId());
    }

    // Get all bookings (read-only usage)
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// -------------------- Report Service --------------------
class BookingReportService {

    // Display full history
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\n--- Booking History ---");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n--- Booking Summary Report ---");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : reservations) {
            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        for (String type : roomCount.keySet()) {
            System.out.println(type + " booked: " + roomCount.get(type));
        }

        System.out.println("Total Bookings: " + reservations.size());
    }
}

// -------------------- Main Class --------------------
public class uc8 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 8.0");
        System.out.println("=====================================");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from UC6)
        history.addReservation(new Reservation("SR101", "Alice", "Single Room"));
        history.addReservation(new Reservation("DR102", "Bob", "Double Room"));
        history.addReservation(new Reservation("SR103", "Charlie", "Single Room"));

        // Reporting service
        BookingReportService reportService = new BookingReportService();

        // Display history
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\n(Note: Reporting does NOT modify booking history)");
    }
}