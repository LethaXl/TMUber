import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class TMUberRegistered {
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 9000;
    private static int firstDriverId = 7000;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current) {
        return "" + firstUserAccountID + current.size();

    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current) {
        return "" + firstDriverId + current.size();

    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code
    // (i.e. check your output with the
    // sample output provided).
    public static ArrayList<User> loadPreregisteredUsers(String filename) throws FileNotFoundException, IOException {
        File file = new File(filename.trim());
        Scanner filescanner = new Scanner(file);
        ArrayList<User> users = new ArrayList<>();
        String name = "";
        String address = "";
        Double wallet = null;
        String accountId = "";
        while (filescanner.hasNextLine()) {
            name = filescanner.nextLine();
            address = filescanner.nextLine();
            if (filescanner.hasNextDouble()) {
                wallet = filescanner.nextDouble();
                if (filescanner.hasNextLine()) {
                    filescanner.nextLine();
                }
            }
            accountId = generateUserAccountId(users);
            users.add(new User(accountId, name, address, wallet));
        }

        return users;
       
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename)
            throws FileNotFoundException, IOException {
        File file = new File(filename);
        Scanner filescanner = new Scanner(file);
        ArrayList<Driver> drivers = new ArrayList<>();
        String name = "";
        String model = "";
        String licenseplate = "";
        String address = "";
        String driverId = "";

        while (filescanner.hasNextLine()) {
            name = filescanner.nextLine();
            model = filescanner.nextLine();
            licenseplate = filescanner.nextLine();
            address = filescanner.nextLine();
            driverId = generateDriverId(drivers);
            drivers.add(new Driver(driverId, name, model, licenseplate, address));
        }
        filescanner.close();
        return drivers;

    }
}
