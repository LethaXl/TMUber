import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager {
    private Map<String, User> users = new LinkedHashMap<>();
    private ArrayList<Driver> drivers = new ArrayList<>();
    @SuppressWarnings("unchecked")
    private LinkedList<TMUberService>[] serviceRequests = new LinkedList[4];
    public double totalRevenue;
    private static final double DELIVERYRATE = 1.2;
    private static final double RIDERATE = 1.5;
    private static final double PAYRATE = 0.1;
    int userAccountId = 900;
    int driverId = 700;
    String errMsg = null;

    public TMUberSystemManager() {
        for (int i = 0; i < 4; i++) {
            serviceRequests[i] = new LinkedList<>();
        }
        totalRevenue = 0;
    }

    public String getErrorMessage() {
        return errMsg;
    }

    public User getUser(String accountId) {
        for (User u : users.values()) {
            if (u.getAccountId().equals(accountId)) {
                return u;
            }
        }
        return null;
    }

    private boolean userExists(User user) {
        return users.values().contains(user);
    }

    private boolean driverExists(Driver driver) {
        return drivers.contains(driver);
    }

  

    private Driver getAvailableDriver() {
        for (Driver d : drivers) {
            if ((d.getStatus().equals(Driver.Status.AVAILABLE)) && (d != null)) {
                return d;
            }
        }
        return null;
    }

    public void listAllUsers() throws UserNotFoundException {
        System.out.println();
        int count = 1;
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException("No Users in System ");
        }
        for (User u : users.values()) {
            System.out.printf("%-2s. ", count);
            count++;
            u.printInfo();
            System.out.println();
        }
    }

    public Driver getDriver(String driverid) {
        for (Driver d : drivers) {
            if (d.getId().equals(driverid)) {
                return d;
            }
        }
        return null;
    }

    public void listAllDrivers() throws DriverNotFoundException {
        int c = 0;
        if (drivers == null || drivers.size() == 0) {
            throw new DriverNotFoundException("No Drivers in System ");
        }
        for (Driver d : drivers) {
            c++;
            System.out.print(c);
            System.out.print(" . ");
            d.printInfo();
            System.out.println();
        }
    }

    public void listAllServiceRequests() throws InvalidRequestException {
        int c = 0;
        int zone = -1;
        if (serviceRequests == null) {
            throw new InvalidRequestException("No Service Requests Found ");
        }
        for (LinkedList<TMUberService> l : serviceRequests) {
            c = 0;
            zone++;
            System.out.print("ZONE ");
            System.out.println(zone);
            System.out.println("======\n");
            for (Object service : l) {
                c++;
                System.out.print(c);
                System.out.print(
                        ". ------------------------------------------------------------------------------------------------------------------------\n");
                TMUberService s = (TMUberService) service;
                s.printInfo();
                System.out.print("\n\n");
            }
        }
    }

    public void registerNewUser(String name, String address, double wallet)
            throws InvalidAddressException, InvalidWalletException, InvalidNameException, PreexistingUserException {

        if (!(CityMap.validAddress(address))) {
            errMsg = "Invalid Address ";
            throw new InvalidAddressException(errMsg);
        }
        if (wallet < 0) {
            errMsg = "Invalid Wallet ";
            throw new InvalidWalletException(errMsg);
        }
        if (name == null) {
            errMsg = "Invalid User Name ";
            throw new InvalidNameException(errMsg);
        }
        ArrayList<User> temp = new ArrayList<>();
        for (User u : users.values()) {
            temp.add(u);
        }

        User nuser = new User(TMUberRegistered.generateUserAccountId(temp), name, address, wallet);
        if (userExists(nuser)) {
            errMsg = "User Already Exists ";
            throw new PreexistingUserException(errMsg);
        }
        users.put(nuser.getAccountId(), nuser);
    }

    public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
            throws InvalidNameException, InvalidLicensePlateException, InvalidCarModelException,
            PreexistingDriverException,
            InvalidAddressException {

        if ((name == null) || (name == "")) {
            errMsg = "Invalid Name ";
            throw new InvalidNameException(errMsg);
        }
        if (!(CityMap.validAddress(address))) {
            errMsg = "Invalid Address ";
            throw new InvalidAddressException(errMsg);
        }
        if ((carModel == null) || (carModel == "")) {
            errMsg = "Invalid Car Model ";
            throw new InvalidCarModelException(errMsg);
        }
        if ((carLicencePlate == null) || (carLicencePlate == "")) {
            errMsg = "Invalid License Plate ";
            throw new InvalidLicensePlateException(errMsg);
        }
        Driver ndriver = new Driver(TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate,
                address);
        if (driverExists(ndriver)) {
            errMsg = "Driver Already Exists ";
            throw new PreexistingDriverException(errMsg);
        }
        drivers.add(ndriver);
    }

    public void requestRide(String accountId, String from, String to)
            throws InvalidIdException, InvalidAddressException, InvalidDistanceException, DriverNotFoundException,
            InvalidRequestException {

        if (accountId == null) {
            errMsg = "Invalid Id ";
            throw new InvalidIdException(errMsg);
        }

        if (!CityMap.containsAllNumber(accountId)) {
            errMsg = "Invalid Id ";
            throw new InvalidIdException(errMsg);
        }

        if ((from == null) || (to == null)) {
            errMsg = "Invalid Address ";
            throw new InvalidAddressException(errMsg);
        }
        if (!(CityMap.validAddress(from)) || !(CityMap.validAddress(to))) {
            errMsg = "Invalid Address ";
            throw new InvalidAddressException(errMsg);
        }

        User user = getUser(accountId);
        if (user == null) {
            throw new InvalidIdException("Invalid Id ");
        }
        int distance = CityMap.getDistance(from, to);

        if (!(distance > 1)) {
            errMsg = "Insuffecient Travel Distance ";
            throw new InvalidDistanceException(errMsg);
        }

        Driver driver = getAvailableDriver();
        if (driver == null) {
            errMsg = "No available driver ";
            throw new DriverNotFoundException(errMsg);
        }
        TMUberRide ride = new TMUberRide(from, to, user, distance, RIDERATE * distance, "RIDE");
        int zone = CityMap.getCityZone(from);

        if (user.getRides() > 0) {
            errMsg = "Too Many Rides ";
            throw new InvalidRequestException(errMsg);
        }
        System.out.println();
        System.out.print("RIDE for: ");
        System.out.print(user.getName());
        System.out.print(" ");
        System.out.print("From: ");
        System.out.print(from);
        System.out.print(" ");
        System.out.print("To: ");
        System.out.println(to);
        System.out.print(" ");
        driver.setStatus(Driver.Status.DRIVING);
        driver.setService(ride);
        serviceRequests[zone].offer(ride);
        user.addRide();
    }

    public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
            throws InvalidIdException, InvalidAddressException, InvalidDistanceException, UserNotFoundException {

        if (accountId == null) {
            errMsg = "Invalid Id ";
            throw new InvalidIdException(errMsg);
        }
        if ((from == null) || (to == null)) {
            errMsg = "Invalid Address ";
            throw new InvalidAddressException(errMsg);
        }
        if (!(CityMap.validAddress(from) || !(CityMap.validAddress(to)))) {
            errMsg = "Invalid Address ";
            throw new InvalidAddressException(errMsg);
        }
        if (!(CityMap.containsAllNumber(accountId))) {
            errMsg = "Invalid Account Id ";
            throw new InvalidIdException(errMsg);
        }

        User user = getUser(accountId);
        if (user == null) {
            throw new UserNotFoundException("Incorrect User Id");
        }
        int distance = CityMap.getDistance(from, to);
        if (!(distance > 1)) {
            errMsg = "Invalid Distance ";
            throw new InvalidDistanceException(errMsg);
        }

        Driver driver = getAvailableDriver();
        if (driver == null) {
            throw new DriverNotFoundException("No Driver Available ");
        }
        TMUberDelivery delivery = new TMUberDelivery(from, to, user, distance, DELIVERYRATE * distance, "DELIVERY",
                restaurant, foodOrderId);
        driver.setStatus(Driver.Status.DRIVING);
        driver.setService(delivery);
        int zone = CityMap.getCityZone(from);
        serviceRequests[zone].offer(delivery);
        user.addDelivery();
        System.out.print("DELIVERY for: ");
        System.out.print(user.getName());
        System.out.print(" ");
        System.out.print("From: ");
        System.out.print(from);
        System.out.print(" ");
        System.out.print("To: ");
        System.out.print(to);
        System.out.print("\n\n");
    }

    public void pickup(String driverId) throws DriverNotFoundException, InvalidRequestException {
        Driver d = null;
        for (Driver driver : drivers) {
            if (driver.getId().equals(driverId)) {
                d = driver;
            }
        }
        if (d == null) {
            throw new DriverNotFoundException("No Driver Matching Id ");
        }
        int zone = CityMap.getCityZone(d.getAddress());
        TMUberService service = serviceRequests[zone].poll();
        if (service == null) {
            throw new InvalidRequestException("No request Found ");
        }
        System.out.print("Driver ");
        System.out.print(driverId);
        System.out.print(" ");
        System.out.print("Picking up in Zone ");
        System.out.print(zone);
        System.out.print("\n\n");
        d.setService(service);
        d.setStatus(Driver.Status.DRIVING);
        d.setAddress(service.getFrom());
    }

    public void cancelServiceRequest(int request, int zone) throws InvalidRequestException, InvalidZoneException {

        if (zone == -1 || zone > 3) {
            throw new InvalidZoneException("Invalid Zone Number ");
        }

        if ((request > serviceRequests[zone].size()) || (request <= 0)) {
            errMsg = "Invalid Request Number ";
            throw new InvalidRequestException(errMsg);
        }

        LinkedList<TMUberService> linkedlist = serviceRequests[zone];

        TMUberService temp = linkedlist.get(request - 1);
        linkedlist.remove(request - 1);

        if (temp.getServiceType().equalsIgnoreCase("RIDE")) {
            temp.getUser().removeRide();
        } else if (temp.getServiceType().equalsIgnoreCase("DELIVERY")) {
            temp.getUser().removeDelivery();
        }
    }

    public void dropOff(String driverId) throws DriverNotFoundException, InvalidStatusException {
        Driver d = null;
        for (Driver driver : drivers) {
            if (driver.getId().equals(driverId)) {
                d = driver;
            }
        }
        if (d == null) {
            throw new DriverNotFoundException("No driver matching Id ");
        }

        if (!(d.getStatus() == (Driver.Status.DRIVING))) {
            throw new InvalidStatusException("Driver is currently not driving ");
        }

        int zone = CityMap.getCityZone(d.getAddress());
        TMUberService service = d.getService();

        totalRevenue += service.getCost();
        getDriver(driverId).pay(service.getCost() * PAYRATE);
        totalRevenue -= service.getCost() * PAYRATE;
        getDriver(driverId).setStatus(Driver.Status.AVAILABLE);
        service.getUser().payForService(service.getCost());

        if (service.getServiceType().equalsIgnoreCase("RIDE")) {
            service.getUser().removeRide();
        }

        else if (service.getServiceType().equalsIgnoreCase("DELIVERY")) {
            service.getUser().removeDelivery();
        }

        d.setStatus(Driver.Status.AVAILABLE);
        d.setService(null);
        d.setAddress(service.getTo());
        d.setZone(CityMap.getCityZone(service.getTo()));
        System.out.print("Driver ");
        System.out.print(driverId);
        System.out.print(" ");
        System.out.print("Dropping off \n");

        serviceRequests[zone].remove(service);

    }

    public void driveTo(String driverId, String address) throws DriverNotFoundException, InvalidAddressException {
        Driver d = null;
        for (Driver driver : drivers) {
            if (driver.getId().equals(driverId)) {
                d = driver;
            }
        }
        if (d == null) {
            throw new DriverNotFoundException("No driver matching Id ");
        }

        if (d.getStatus() != Driver.Status.AVAILABLE) {
            throw new DriverNotFoundException("Driver is not available ");
        }

        if (!CityMap.validAddress(address)) {
            throw new InvalidAddressException("Invalid address ");
        }
        d.setAddress(address);
        d.setZone(CityMap.getCityZone(address));

    }

    public void setUsers(ArrayList<User> userList) {
        for (User u : userList) {
            users.put(u.getAccountId(), u);
        }
    }

    public void setDrivers(ArrayList<Driver> drivers) {
        this.drivers = drivers;
    }

    public void sortByUserName() {
        ArrayList<User> newusers = new ArrayList<>(users.values());
        Collections.sort(newusers, new NameComparator());
        Map<String, User> temp = new LinkedHashMap<>();
        for (User u : newusers) {
            temp.put(u.getAccountId(), u);
        }
        users = temp;
    }

    private class NameComparator implements Comparator<User> {
        public int compare(User u1, User u2) {
            return u1.getName().compareTo(u2.getName());
        }
    }

    public void sortByWallet() {
        ArrayList<User> newusers = new ArrayList<>(users.values());
        Collections.sort(newusers, new UserWalletComparator());
        Map<String, User> temp = new LinkedHashMap<>();
        for (User u : newusers) {
            temp.put(u.getAccountId(), u);
        }
        users = temp;
    }

    private class UserWalletComparator implements Comparator<User> {
        public int compare(User u1, User u2) {
            if (u1.getWallet() > u2.getWallet()) {
                return 1;
            } else if (u1.getWallet() < u2.getWallet()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private class DistanceComparator implements Comparator<TMUberService> {
        public int compare(TMUberService s1, TMUberService s2) {
            if (s1.getDistance() > s2.getDistance()) {
                return 1;
            } else if (s1.getDistance() < s2.getDistance()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public void sortByDistance() {
        int c = 0;
        for (LinkedList<TMUberService> l : serviceRequests) {
            Collections.sort(l, new DistanceComparator());
            
        }
    }

    public static class DriverNotFoundException extends RuntimeException {
        public DriverNotFoundException(String m) {
            super(m);
        }
    }

    public static class InvalidStatusException extends RuntimeException {
        public InvalidStatusException(String m) {
            super(m);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String m) {
            super(m);
        }
    }

    public static class InvalidAddressException extends RuntimeException {
        public InvalidAddressException(String m) {
            super(m);
        }
    }

    public static class InvalidWalletException extends RuntimeException {
        public InvalidWalletException(String m) {
            super(m);
        }
    }

    public static class InvalidNameException extends RuntimeException {
        public InvalidNameException(String m) {
            super(m);
        }
    }

    public static class PreexistingUserException extends RuntimeException {
        public PreexistingUserException(String m) {
            super(m);
        }
    }

    public static class InvalidLicensePlateException extends RuntimeException {
        public InvalidLicensePlateException(String m) {
            super(m);
        }
    }

    public static class InvalidCarModelException extends RuntimeException {
        public InvalidCarModelException(String m) {
            super(m);
        }
    }

    public static class PreexistingDriverException extends RuntimeException {
        public PreexistingDriverException(String m) {
            super(m);
        }
    }

    public static class InvalidIdException extends RuntimeException {
        public InvalidIdException(String m) {
            super(m);
        }
    }

    public static class InvalidDistanceException extends RuntimeException {
        public InvalidDistanceException(String m) {
            super(m);
        }
    }

    public static class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String m) {
            super(m);
        }
    }

    public static class InvalidZoneException extends RuntimeException {
        public InvalidZoneException(String m) {
            super(m);
        }
    }
}
