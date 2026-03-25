import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates:
 * - One-to-many relationship (Reservation → Multiple Services)
 * - Map + List usage
 * - Cost aggregation
 * - Separation from core booking logic
 *
 * @author Ram
 * @version 7.0
 */

// -------------------- Add-On Service --------------------
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + price);
    }
}

// -------------------- Add-On Service Manager --------------------
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService s : services) {
            total += s.getPrice();
        }

        return total;
    }
}

// -------------------- Main Class --------------------
public class uc7 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 7.0");
        System.out.println("=====================================");

        // Assume reservation already created in UC6
        String reservationId1 = "SR101";
        String reservationId2 = "DR102";

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService wifi = new AddOnService("WiFi", 100);
        AddOnService spa = new AddOnService("Spa", 800);

        // Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services to reservations
        manager.addService(reservationId1, breakfast);
        manager.addService(reservationId1, wifi);
        manager.addService(reservationId2, spa);

        // Display services
        manager.displayServices(reservationId1);
        manager.displayServices(reservationId2);

        // Show total cost
        System.out.println("\nTotal Add-On Cost for " + reservationId1 +
                ": ₹" + manager.calculateTotalCost(reservationId1));

        System.out.println("Total Add-On Cost for " + reservationId2 +
                ": ₹" + manager.calculateTotalCost(reservationId2));

        System.out.println("\n(Note: Booking & Inventory remain unchanged)");
    }
}