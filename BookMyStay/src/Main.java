/**
 * UseCase1HotelBookingApp
 *
 * This class represents the entry point of the Hotel Booking Management System.
 * It demonstrates how a Java application starts execution and displays
 * a welcome message to the user.
 *
 * The program prints:
 * - Application name
 * - Version information
 * - Welcome message
 *
 * This use case focuses on understanding the main() method,
 * console output, and basic program flow.
 *
 * @author Ram
 * @version 1.0
 */

public class Main {

    /**
     * The main method is the entry point of the Java application.
     * The JVM calls this method to start program execution.
     *
     * @param args Command-line arguments (not used here)
     */
    public static void main(String[] args) {

        // Application Name and Version
        String appName = "Book My Stay - Hotel Booking System";
        String version = "Version 1.0";

        // Welcome Message
        System.out.println("=====================================");
        System.out.println(" Welcome to " + appName);
        System.out.println(" " + version);
        System.out.println("=====================================");
        System.out.println("Your comfort is our priority!");
        System.out.println("Enjoy seamless hotel booking experience.");
        System.out.println("=====================================");

        // Program ends
    }
}