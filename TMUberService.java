/*
 * 
 * General class that simulates a ride or a delivery in a simple Uber app
 * 
 * This class is made abstract since we never create an object. We only create subclass objects. 
 * 
 * Implement the Comparable interface and compare two service requests based on the distance
 */
abstract public class TMUberService {
    private String from;
    private String to;
    private User user;
    private String type; // Currently Ride or Delivery but other services could be added
    private int distance; // Units are City Blocks
    private double cost; // Cost of the service

    public TMUberService(String from, String to, User user, int distance, double cost, String type) {
        this.from = from;
        this.to = to;
        this.user = user;
        this.distance = distance;
        this.cost = cost;
        this.type = type;
    }

    abstract public String getServiceType();

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean biggerThan(TMUberService other) {
        return distance > other.distance;
    }

    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        TMUberService s = (TMUberService) other;
        return (this.type.equals(s.getServiceType())) && (this.user.equals(s.getUser()));
    }

    public void printInfo() {
        System.out.printf("\nType: %-9s From: %-15s To: %-15s", type, from, to);
        System.out.print("\nUser: ");
        user.printInfo();
    }
}
