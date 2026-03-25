/**
 * uc2
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance, and static availability.
 *
 * Version: 2.1
 *
 * @author Ram
 * @version 2.1
 */

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public abstract void displayDetails();
}

// Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 1500.0);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 2500.0);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }

    @Override
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Main Class
public class uc2 {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 2.1");
        System.out.println("=====================================");

        // Polymorphism
        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("\n--- Room Details & Availability ---\n");

        r1.displayDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("----------------------------------");

        r2.displayDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("----------------------------------");

        r3.displayDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("----------------------------------");
    }
}
