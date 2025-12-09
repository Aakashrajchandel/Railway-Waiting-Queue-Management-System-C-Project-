import java.util.*;
import java.io.*;

public class RailwayWaitingQueueSystem {

    // ---------------------------- Passenger Class ----------------------------
    static class Passenger {
        int ticketNo;
        String name;
        int age;
        String gender;
        String contact;
        String status; // Confirmed / Waiting

        Passenger(int ticketNo, String name, int age, String gender, String contact, String status) {
            this.ticketNo = ticketNo;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.contact = contact;
            this.status = status;
        }

        @Override
        public String toString() {
            return ticketNo + "\t" + name + "\t" + age + "\t" + gender + "\t" + contact + "\t" + status;
        }
    }

    // Global Storage
    private static final int MAX_SEATS = 5;
    private static List<Passenger> confirmedList = new ArrayList<>();
    private static Queue<Passenger> waitingQueue = new LinkedList<>();
    private static int ticketCounter = 1000;


    // ---------------------------- BOOK SEAT ----------------------------
    public static void bookSeat(Scanner sc) {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine();

        String status;

        if (confirmedList.size() < MAX_SEATS) {
            status = "Confirmed";
            Passenger p = new Passenger(ticketCounter++, name, age, gender, contact, status);
            confirmedList.add(p);
            System.out.println("Seat booked successfully! Status: " + status + ", Ticket: " + p.ticketNo);
        } else {
            status = "Waiting";
            Passenger p = new Passenger(ticketCounter++, name, age, gender, contact, status);
            waitingQueue.add(p);
            System.out.println("All seats full. Added to Waiting List. Ticket: " + p.ticketNo);
        }
    }


    // ---------------------------- CANCEL SEAT ----------------------------
    public static void cancelSeat(Scanner sc) {
        if (confirmedList.isEmpty()) {
            System.out.println("No confirmed bookings to cancel.");
            return;
        }

        System.out.print("Enter Ticket Number to cancel: ");
        int ticket = Integer.parseInt(sc.nextLine());

        Passenger toRemove = null;
        for (Passenger p : confirmedList) {
            if (p.ticketNo == ticket) {
                toRemove = p;
                break;
            }
        }

        if (toRemove != null) {
            confirmedList.remove(toRemove);
            System.out.println("Booking cancelled for Ticket: " + ticket);

            // Allocate seat to waiting member
            if (!waitingQueue.isEmpty()) {
                Passenger next = waitingQueue.poll();
                next.status = "Confirmed";
                confirmedList.add(next);
                System.out.println("Seat allocated to waiting passenger: " + next.name + " (Ticket " + next.ticketNo + ")");
            }

        } else {
            System.out.println("Ticket not found in confirmed list.");
        }
    }


    // ---------------------------- DISPLAY CONFIRMED ----------------------------
    public static void displayConfirmed() {
        System.out.println("\n--- Confirmed Passengers ---");

        if (confirmedList.isEmpty()) {
            System.out.println("No confirmed passengers.");
        } else {
            System.out.println("Ticket\tName\tAge\tGender\tContact\tStatus");
            for (Passenger p : confirmedList) {
                System.out.println(p);
            }
        }
    }


    // ---------------------------- DISPLAY WAITING ----------------------------
    public static void displayWaiting() {
        System.out.println("\n--- Waiting List ---");

        if (waitingQueue.isEmpty()) {
            System.out.println("No passengers in waiting list.");
        } else {
            System.out.println("Ticket\tName\tAge\tGender\tContact\tStatus");
            for (Passenger p : waitingQueue) {
                System.out.println(p);
            }
        }
    }


    // ---------------------------- SEARCH PASSENGER ----------------------------
    public static void searchPassenger(Scanner sc) {
        System.out.print("Enter Ticket Number to search: ");
        int ticket = Integer.parseInt(sc.nextLine());

        for (Passenger p : confirmedList) {
            if (p.ticketNo == ticket) {
                System.out.println("Passenger found in Confirmed List:\n" + p);
                return;
            }
        }

        for (Passenger p : waitingQueue) {
            if (p.ticketNo == ticket) {
                System.out.println("Passenger found in Waiting List:\n" + p);
                return;
            }
        }

        System.out.println("Passenger with Ticket " + ticket + " not found.");
    }


    // ---------------------------- EDIT PASSENGER ----------------------------
    public static void editPassenger(Scanner sc) {
        System.out.print("Enter Ticket Number to edit: ");
        int ticket = Integer.parseInt(sc.nextLine());

        Passenger found = null;

        for (Passenger p : confirmedList) {
            if (p.ticketNo == ticket) {
                found = p;
                break;
            }
        }

        if (found == null) {
            for (Passenger p : waitingQueue) {
                if (p.ticketNo == ticket) {
                    found = p;
                    break;
                }
            }
        }

        if (found != null) {
            System.out.print("Enter new Name: ");
            found.name = sc.nextLine();

            System.out.print("Enter new Age: ");
            found.age = Integer.parseInt(sc.nextLine());

            System.out.print("Enter new Gender: ");
            found.gender = sc.nextLine();

            System.out.print("Enter new Contact: ");
            found.contact = sc.nextLine();

            System.out.println("Passenger details updated.");
        } else {
            System.out.println("Ticket not found.");
        }
    }


    // ---------------------------- EXPORT TO CSV ----------------------------
    public static void exportToCSV() {
        try (PrintWriter pw = new PrintWriter(new File("passengers.csv"))) {
            pw.println("Ticket,Name,Age,Gender,Contact,Status");

            for (Passenger p : confirmedList) {
                pw.println(p.ticketNo + "," + p.name + "," + p.age + "," + p.gender + "," + p.contact + "," + p.status);
            }

            for (Passenger p : waitingQueue) {
                pw.println(p.ticketNo + "," + p.name + "," + p.age + "," + p.gender + "," + p.contact + "," + p.status);
            }

            System.out.println("Passenger list exported to passengers.csv");
        } catch (Exception e) {
            System.out.println("Error exporting file.");
        }
    }


    // ---------------------------- MAIN MENU ----------------------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n--- Railway Waiting Queue Management System ---");
            System.out.println("Available Seats: " + (MAX_SEATS - confirmedList.size()) + " / " + MAX_SEATS);
            System.out.println("Waiting List: " + waitingQueue.size());

            System.out.println("\n1. Book Seat");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Show Confirmed Passengers");
            System.out.println("4. Show Waiting List");
            System.out.println("5. Search Passenger");
            System.out.println("6. Edit Passenger Details");
            System.out.println("7. Export Passenger List to CSV");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            switch (choice) {
                case 1 -> bookSeat(sc);
                case 2 -> cancelSeat(sc);
                case 3 -> displayConfirmed();
                case 4 -> displayWaiting();
                case 5 -> searchPassenger(sc);
                case 6 -> editPassenger(sc);
                case 7 -> exportToCSV();
                case 8 -> {
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
