import java.util.*;

class Bus {
    int busNumber;
    String route;
    int totalSeats;
    int availableSeats;

    public Bus(int busNumber, String route, int totalSeats) {
        this.busNumber = busNumber;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }
}

class Reservation {
    int reservationId;
    String passengerName;
    int age;
    int busNumber;

    public Reservation(int reservationId, String passengerName, int age, int busNumber) {
        this.reservationId = reservationId;
        this.passengerName = passengerName;
        this.age = age;
        this.busNumber = busNumber;
    }
}

class BusManager {
    List<Bus> buses = new ArrayList<>();

    public void addBus(int busNumber, String route, int totalSeats) {
        buses.add(new Bus(busNumber, route, totalSeats));
        System.out.println("Bus " + busNumber + " added successfully!");
    }

    public void removeBus(int busNumber) {
        Bus bus = getBus(busNumber);
        if (bus != null) {
            buses.remove(bus);
            System.out.println("Bus " + busNumber + " removed!");
        } else {
            System.out.println("Bus not found!");
        }
    }

    public void viewBuses() {
        if (buses.isEmpty()) {
            System.out.println("No buses available.");
            return;
        }
        System.out.println("Available Buses:");
        for (Bus bus : buses) {
            System.out.println("Bus: " + bus.busNumber + " | Route: " + bus.route + 
                               " | Seats: " + bus.availableSeats + "/" + bus.totalSeats);
        }
    }

    public Bus getBus(int busNumber) {
        for (Bus bus : buses) {
            if (bus.busNumber == busNumber) return bus;
        }
        return null;
    }
}

class ReservationManager {
    List<Reservation> reservations = new ArrayList<>();
    BusManager busManager;
    int reservationCounter = 1;

    public ReservationManager(BusManager busManager) {
        this.busManager = busManager;
    }

    public void bookTicket(String name, int age, int busNumber) {
        Bus bus = busManager.getBus(busNumber);
        if (bus == null) {
            System.out.println("Bus not found!");
            return;
        }
        if (bus.availableSeats == 0) {
            System.out.println("Bus is full! No seats available.");
            return;
        }
        Reservation res = new Reservation(reservationCounter++, name, age, busNumber);
        reservations.add(res);
        bus.availableSeats--;
        System.out.println("Ticket Booked! Reservation ID: " + res.reservationId);
    }

    public void cancelTicket(int reservationId) {
        for (Reservation res : reservations) {
            if (res.reservationId == reservationId) {
                reservations.remove(res);
                Bus bus = busManager.getBus(res.busNumber);
                if (bus != null) bus.availableSeats++;
                System.out.println("Ticket Canceled! ID: " + reservationId);
                return;
            }
        }
        System.out.println("Reservation not found!");
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        System.out.println("Reservations:");
        for (Reservation res : reservations) {
            System.out.println("ID: " + res.reservationId + " | Passenger: " + res.passengerName + 
                               " | Age: " + res.age + " | Bus: " + res.busNumber);
        }
    }
}

public class BusReservationSystem {
    static Scanner sc = new Scanner(System.in);
    static BusManager busManager = new BusManager();
    static ReservationManager reservationManager = new ReservationManager(busManager);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Bus Reservation System ===");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n=== Admin Panel ===");
            System.out.println("1. Add Bus");
            System.out.println("2. Remove Bus");
            System.out.println("3. View Buses");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Bus Number: ");
                    int busNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Route: ");
                    String route = sc.nextLine();
                    System.out.print("Enter Total Seats: ");
                    int seats = sc.nextInt();
                    busManager.addBus(busNo, route, seats);
                    break;
                case 2:
                    System.out.print("Enter Bus Number to Remove: ");
                    int removeBusNo = sc.nextInt();
                    busManager.removeBus(removeBusNo);
                    break;
                case 3:
                    busManager.viewBuses();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void userMenu() {
        while (true) {
            System.out.println("\n=== User Panel ===");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. View Reservations");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    System.out.print("Enter Bus Number: ");
                    int busNo = sc.nextInt();
                    reservationManager.bookTicket(name, age, busNo);
                    break;
                case 2:
                    System.out.print("Enter Reservation ID to Cancel: ");
                    int resId = sc.nextInt();
                    reservationManager.cancelTicket(resId);
                    break;
                case 3:
                    reservationManager.viewReservations();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}