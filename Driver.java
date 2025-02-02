/*
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */
public class Driver {
    private String id;
    private String name;
    private String carModel;
    private String licensePlate;
    private double wallet;
    private String type;
    private TMUberService service;
    private String address;
    private int zone;

    public static enum Status {
        AVAILABLE, DRIVING
    };

    private Status status;

    public Driver(String id, String name, String carModel, String licensePlate, String address) {
        this.id = id;
        this.name = name;
        this.carModel = carModel;
        this.licensePlate = licensePlate;
        this.status = Status.AVAILABLE;
        this.wallet = 0;
        this.type = "";
        this.address = address;
        this.zone = CityMap.getCityZone(address);
    }

    // Print Information about a driver
    public void printInfo() {
        System.out.printf(
                "Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f Status: %-10s Address: %-15s Zone: %d",
                id, name, carModel, licensePlate, wallet, status, address, zone);
        System.out.println();
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String a) {
        address = a;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setService(TMUberService s) {
        service = s;
    }

    public TMUberService getService() {
        return service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

   
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Driver)) {
            return false;
        }
        Driver d = (Driver) other;
        return (this.name.equals(d.getName())) && (this.licensePlate.equals(d.getLicensePlate()));
    }

    // A driver earns a fee for every ride or delivery
    public void pay(double fee) {
        wallet += fee;
    }
}
