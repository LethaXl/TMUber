Description
This project extends the TMUber ride-sharing system by adding new functionality such as service request queueing, driver location tracking, and improved user management. It utilizes Java Collections (Maps & Queues), File I/O, and custom exception handling to simulate a real-world Uber-like system.

Features Implemented
✔️ City Zones & Address Validation: Implemented a getCityZone() method to determine zones based on addresses.
✔️ Service Request Queueing: Used four queues to store ride/delivery requests based on city zones.
✔️ Driver Pickup & Dropoff: Drivers can pick up the next request from their zone and complete rides dynamically.
✔️ File I/O for User & Driver Registration: Reads users.txt and drivers.txt, handling exceptions properly.
✔️ Exception Handling: Replaced boolean error checks with custom exceptions for robustness.
✔️ Maps for User Management: Map<String, User> for efficient lookups.

Compilation & Execution
Compile: Run javac *.java to compile all Java files.
Run: Execute the program using java TMUberUI.
