# TMUber

TMUber is an enhanced version of the Uber-like ride-sharing system that introduces several key features, including service request queueing, driver location tracking, and improved user management. It leverages Java Collections (Maps & Queues), File I/O, and custom exception handling to simulate a more realistic Uber-like experience.

## Features Implemented
- **City Zones & Address Validation**: Added the `getCityZone()` method to determine the correct zone based on user addresses.
- **Service Request Queueing**: Utilized multiple queues to manage ride and delivery requests efficiently, grouped by city zones.
- **Driver Pickup & Dropoff**: Drivers can dynamically pick up the next request from their assigned zone and complete rides in real-time.
- **File I/O for User & Driver Registration**: Reads user and driver information from `users.txt` and `drivers.txt`, with robust exception handling.
- **Exception Handling**: Replaced error-prone boolean checks with custom exceptions for better reliability.
- **Maps for User Management**: Used `Map<String, User>` for fast lookups and efficient user management.

## Compilation & Execution
- **Compile**: To compile the project, run the following command:
    ```bash
    javac *.java
    ```
- **Run**: To execute the program, use the following command:
    ```bash
    java TMUberUI
    ```
