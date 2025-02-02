import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap {
    
    private static String[] getParts(String address) {
        String parts[] = new String[3];

        if (address == null || address.length() == 0) {
            parts = new String[0];
            return parts;
        }
        int numParts = 0;
        Scanner sc = new Scanner(address);
        while (sc.hasNext()) {
            if (numParts >= 3)
                parts = Arrays.copyOf(parts, parts.length + 1);

            parts[numParts] = sc.next();
            numParts++;
        }
        if (numParts == 1)
            parts = Arrays.copyOf(parts, 1);
        else if (numParts == 2)
            parts = Arrays.copyOf(parts, 2);
        sc.close();
        return parts;
    }

    public static int getCityZone(String address) {
        if (!validAddress(address)) {
            return -1;
        }
        int[] block = getCityBlock(address);
        int st = block[0];
        int av = block[1];
        if (st >= 6 && av >= 6) {
            return 1;
        }
        if (st < 6 && av < 6) {
            return 3;
        }
        if (st >= 6 && av < 6) {
            return 0;
        }
        if (st < 6 && av >= 6) {
            return 2;
        }
        return -1;
    }

    public static boolean containsNumber(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAllNumber(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static int[] getNums(String address) {
        int[] x = { -1, 1 };
        x[0] = Integer.parseInt(getParts(address)[0]);
        x[1] = Integer.parseInt(getParts(address)[1].replaceAll("[^0-9]", ""));
        return x;
    }

    // Checks for a valid address
    public static boolean validAddress(String address) {
        

        if (getParts(address).length != 3) {
            return false;
        }

        if (!(containsNumber(getParts(address)[0]) && containsNumber(getParts(address)[1]))) {
            return false;
        }

        if (!((getParts(address)[1].contains("th")) || (getParts(address)[1].contains("st"))
                || (getParts(address)[1].contains("nd")) || (getParts(address)[1].contains("rd")))) {
            return false;
        }

        if ((getNums(address)[0] > 99) || (getNums(address)[0] < 1) || (getNums(address)[1] > 9)
                || (getNums(address)[1] < 1)) {
            return false;
        }

        if (!(getParts(address)[2].equalsIgnoreCase("street") || getParts(address)[2].equalsIgnoreCase("avenue"))) {
            return false;
        }

        return true;
    }

    // Computes the city block coordinates from an address string
    // returns an int array of size 2. e.g. [3, 4]
    // where 3 is the avenue and 4 the street
    // See comments at the top for a more detailed explanation
    public static int[] getCityBlock(String address) {
        int[] block = { -1, -1 };
        int[] nums = getNums(address);
        String[] test = getParts(address);
        if (test[2].equalsIgnoreCase("street")) {
            block[0] = nums[1];
            block[1] = nums[0] / 10;
        } else if (test[2].equalsIgnoreCase("avenue")) {
            block[0] = nums[0] / 10;
            block[1] = nums[1];
        }
        // Fill in the code
        return block;
    }

    // Calculates the distance in city blocks between the 'from' address and 'to'
    // address
    

  
    public static int getDistance(String from, String to) throws TMUberSystemManager.InvalidAddressException {
        if (!(validAddress(from) || validAddress(to))) {
            throw new TMUberSystemManager.InvalidAddressException("Invalid Address ");
        }
        return Math.abs(getCityBlock(to)[0] - getCityBlock(from)[0])
                + Math.abs(getCityBlock(to)[1] - getCityBlock(from)[1]);
    }
}
