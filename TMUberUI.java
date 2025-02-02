import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
// Simulation of a Simple Command-line based Uber App 
// This system supports "ride sharing" service and a delivery service

public class TMUberUI {
    public static void main(String[] args) {
        // Create the System Manager - the main system code is in here

        TMUberSystemManager tmuber = new TMUberSystemManager();

        Scanner scanner = new Scanner(System.in);
        System.out.print(">");

        // Process keyboard actions
        while (scanner.hasNextLine()) {
            String action = scanner.nextLine();

            if (action == null || action.equals("")) {
                System.out.print("\n>");
                continue;
            }
            // Quit the App
            else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
                return;
            // Print all the registered drivers
            else if (action.equalsIgnoreCase("DRIVERS")) // List all drivers
            {
                try {
                    tmuber.listAllDrivers();
                } catch (TMUberSystemManager.DriverNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Print all the registered users
            else if (action.equalsIgnoreCase("USERS")) // List all users
            {
                try {
                    tmuber.listAllUsers();
                } catch (TMUberSystemManager.UserNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Print all current ride requests or delivery requests
            else if (action.equalsIgnoreCase("REQUESTS")) // List all requests
            {
                try {
                    tmuber.listAllServiceRequests();
                } catch (TMUberSystemManager.InvalidRequestException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Register a new driver
            else if (action.equalsIgnoreCase("REGDRIVER")) {
                String name = "";
                System.out.print("Name: ");
                if (scanner.hasNextLine()) {
                    name = scanner.nextLine();
                }
                String carModel = "";
                System.out.print("Car Model: ");
                if (scanner.hasNextLine()) {
                    carModel = scanner.nextLine();
                }
                String license = "";
                System.out.print("Car License: ");
                if (scanner.hasNextLine()) {
                    license = scanner.nextLine();
                }

                String address = "";
                System.out.print("Address: ");
                if (scanner.hasNextLine()) {
                    address = scanner.nextLine();
                }
                try {
                    tmuber.registerNewDriver(name, carModel, license, address);
                    System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s", name,
                            carModel,
                            license,
                            address);
                } catch (TMUberSystemManager.InvalidNameException | TMUberSystemManager.InvalidLicensePlateException
                        | TMUberSystemManager.InvalidCarModelException | TMUberSystemManager.PreexistingDriverException
                        | TMUberSystemManager.InvalidAddressException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Register a new user
            else if (action.equalsIgnoreCase("REGUSER")) {
                String name = "";
                System.out.print("Name: ");
                if (scanner.hasNextLine()) {
                    name = scanner.nextLine();
                }
                String address = "";
                System.out.print("Address: ");
                if (scanner.hasNextLine()) {
                    address = scanner.nextLine();
                }
                double wallet = 0.0;
                System.out.print("Wallet: ");
                if (scanner.hasNextDouble()) {
                    wallet = scanner.nextDouble();
                    scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
                } else {
                    System.out.println("Invalid Wallet Entered, will default to 0");
                }
                try {
                    tmuber.registerNewUser(name, address, wallet);
                    System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
                }
                // check some cond
                catch (TMUberSystemManager.InvalidAddressException | TMUberSystemManager.InvalidWalletException
                        | TMUberSystemManager.InvalidNameException | TMUberSystemManager.PreexistingUserException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Request a ride
            else if (action.equalsIgnoreCase("REQRIDE")) {
                // Get the following information from the user (on separate lines)
                // Then use the TMUberSystemManager requestRide() method properly to make a ride
                // request
                // "User Account Id: " (string)
                // "From Address: " (string)
                // "To Address: " (string)
                System.out.print("User Account Id: ");
                String id = "";
                if (scanner.hasNextLine()) {
                    id = scanner.nextLine();
                }
                System.out.print("From Address: ");
                String from = "";
                if (scanner.hasNextLine()) {
                    from = scanner.nextLine();
                }
                System.out.print("To Address: ");
                String to = "";
                if (scanner.hasNextLine()) {
                    to = scanner.nextLine();
                }
                try {
                    tmuber.requestRide(id, from, to);
                    System.out.println("Request successful");
                } catch (TMUberSystemManager.InvalidIdException | TMUberSystemManager.InvalidAddressException
                        | TMUberSystemManager.InvalidDistanceException | TMUberSystemManager.DriverNotFoundException
                        | TMUberSystemManager.InvalidRequestException e) {
                    System.out.println(e.getMessage());
                }
                // check some cond
            }
            // Request a food delivery
            else if (action.equalsIgnoreCase("REQDLVY")) {
                // Get the following information from the user (on separate lines)
                // Then use the TMUberSystemManager requestDelivery() method properly to make a
                // ride request
                // "User Account Id: " (string)
                // "From Address: " (string)
                // "To Address: " (string)
                // "Restaurant: " (string)
                // "Food Order #: " (string)
                System.out.print("User Account Id: ");
                String id = "";
                if (scanner.hasNextLine()) {
                    id = scanner.nextLine();
                }
                System.out.print("From Address: ");
                String from = "";
                if (scanner.hasNextLine()) {
                    from = scanner.nextLine();
                }
                System.out.print("To Address: ");
                String to = "";
                if (scanner.hasNextLine()) {
                    to = scanner.nextLine();
                }
                System.out.print("Restaurant: ");
                String restaurant = "";
                if (scanner.hasNextLine()) {
                    restaurant = scanner.nextLine();
                }
                System.out.print("Food Order #: ");
                String order = "";
                if (scanner.hasNextLine()) {
                    order = scanner.nextLine();
                }
                try {
                    tmuber.requestDelivery(id, from, to, restaurant, order);
                    // check some cond
                    System.out.println("Request successful ");
                } catch (TMUberSystemManager.InvalidIdException | TMUberSystemManager.InvalidAddressException
                        | TMUberSystemManager.InvalidDistanceException
                        | TMUberSystemManager.DriverNotFoundException | TMUberSystemManager.UserNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }

            // Sort users by name
            else if (action.equalsIgnoreCase("SORTBYNAME")) {
                try {
                    tmuber.sortByUserName();
                    tmuber.listAllUsers();
                } catch (TMUberSystemManager.UserNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Sort users by number of ride they have had
            else if (action.equalsIgnoreCase("SORTBYWALLET")) {
                try {
                    tmuber.sortByWallet();
                    tmuber.listAllUsers();
                } catch (TMUberSystemManager.UserNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Sort current service requests (ride or delivery) by distance
            else if (action.equalsIgnoreCase("SORTBYDIST")) {
                try {
                    tmuber.sortByDistance();
                    tmuber.listAllServiceRequests();
                } catch (TMUberSystemManager.InvalidRequestException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Cancel a current service (ride or delivery) request
            else if (action.equalsIgnoreCase("CANCELREQ")) {
                int request = -1;
                int zone = -1;
                System.out.print("Request #: ");
                if (scanner.hasNextInt()) {
                    request = scanner.nextInt();
                    scanner.nextLine(); // consume nl character
                }
                System.out.println();
                System.out.print("Zone #: ");
                try {
                    if (scanner.hasNextInt()) {
                        zone = scanner.nextInt();
                        scanner.nextLine(); // consume nl character
                    } else {
                        throw new TMUberSystemManager.InvalidZoneException("Invalid Zone ");
                    }
                } catch (TMUberSystemManager.InvalidZoneException e) {
                    e.getMessage();
                }
                try {
                    tmuber.cancelServiceRequest(request, zone);
                    System.out.println("Service request #" + request + " cancelled");
                } catch (TMUberSystemManager.InvalidRequestException | TMUberSystemManager.InvalidZoneException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Drop-off the user or the food delivery to the destination address
            else if (action.equalsIgnoreCase("DROPOFF")) {
                System.out.print("driverId: ");
                String driverId = "";
                if (scanner.hasNextLine()) {
                    driverId = scanner.nextLine();
                }
                try {
                    tmuber.dropOff(driverId);
                    System.out.println("Successful Drop Off complete");
                } catch (TMUberSystemManager.DriverNotFoundException | TMUberSystemManager.InvalidStatusException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Get the Current Total Revenues
            else if (action.equalsIgnoreCase("REVENUES")) {
                System.out.println("Total Revenue: " + tmuber.totalRevenue);
            }
            // Unit Test of Valid City Address
            else if (action.equalsIgnoreCase("ADDR")) {
                String address = "";
                System.out.print("Address: ");
                if (scanner.hasNextLine()) {
                    address = scanner.nextLine();
                }
                System.out.print(address);
                if (CityMap.validAddress(address))
                    System.out.println("\nValid Address");
                else
                    System.out.println("\nBad Address");
            }
            // Unit Test of CityMap Distance Method
            else if (action.equalsIgnoreCase("DIST")) {
                String from = "";
                System.out.print("From: ");
                if (scanner.hasNextLine()) {
                    from = scanner.nextLine();
                }
                String to = "";
                System.out.print("To: ");
                if (scanner.hasNextLine()) {
                    to = scanner.nextLine();
                }
                System.out.print("\nFrom: " + from + " To: " + to);
                try {
                    System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
                } catch (TMUberSystemManager.InvalidAddressException e) {
                    System.out.println(e.getMessage());
                }
            }

            else if (action.equalsIgnoreCase("PICKUP")) {
                System.out.print("driverId: ");
                if (scanner.hasNextLine()) {
                    try {
                        tmuber.pickup(scanner.nextLine());
                    } catch (TMUberSystemManager.DriverNotFoundException
                            | TMUberSystemManager.InvalidRequestException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            else if (action.equalsIgnoreCase("LOADUSERS")) {
                System.out.print("filename: ");
                if (scanner.hasNextLine()) {
                    try {
                        String filename = scanner.nextLine();
                        System.out.println(filename);
                        ArrayList<User> users = TMUberRegistered.loadPreregisteredUsers(filename);
                        tmuber.setUsers(users);
                        System.out.print(users.size());
                        System.out.println(" Users Successfuly Added");
                    } catch (FileNotFoundException e) {
                        System.out.println("File Not Found ");
                    } catch (IOException e1) {
                        System.exit(1);
                    }
                }
            }

            else if (action.equalsIgnoreCase("LOADDRIVERS")) {
                System.out.print("filename: ");
                if (scanner.hasNextLine()) {
                    try {
                        ArrayList<Driver> drivers = TMUberRegistered.loadPreregisteredDrivers(scanner.nextLine());
                        tmuber.setDrivers(drivers);
                        System.out.print(drivers.size());
                        System.out.println(" Drivers Successfully Added ");
                    } catch (FileNotFoundException e) {
                        System.out.println("File Not Found ");
                    } catch (IOException e1) {
                        System.exit(1);
                    }
                }
            }

            else if (action.equalsIgnoreCase("DRIVETO")) {
                String driverid = "";
                String address = "";
                System.out.print("driverId: ");
                if (scanner.hasNextLine()) {
                    driverid = scanner.nextLine();
                }
                System.out.print("address: ");
                if (scanner.hasNextLine()) {
                    address = scanner.nextLine();
                }
                try {
                    tmuber.driveTo(driverid, address);
                } catch (TMUberSystemManager.DriverNotFoundException | TMUberSystemManager.InvalidAddressException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.print("\n>");
        }
    }
}
