# Railway-Waiting-Queue-Management-System-C-Project-
Railway Waiting Queue Management System (C++ Project)
This project is a menu-driven C++ application that simulates a simplified Railway Reservation System with a waiting queue mechanism.
It allows users to book seats, cancel bookings, manage waiting lists, search passengers, edit details, and export all data to a CSV file.

📌 Features
✅ 1. Book Seat

Assigns a confirmed seat if available

Adds passenger to waiting queue when seats are full

Automatically generates unique ticket numbers

❌ 2. Cancel Booking

Cancels a confirmed passenger's ticket

Automatically assigns the freed seat to the first person in the waiting queue

👥 3. View Lists

Display all confirmed passengers

Display all waiting passengers

🔍 4. Search Passenger

Search passenger using ticket number

Shows whether the passenger is in Confirmed List or Waiting List

✏️ 5. Edit Passenger Details

Modify existing passenger details

Works for both confirmed & waiting passengers

📤 6. Export to CSV

Exports all passenger data (confirmed + waiting) into
passengers.csv

Useful for data backup or spreadsheet visualization

🛠️ Technologies Used

C++

STL Containers

vector (for confirmed list)

queue (for waiting list)

File Handling (fstream)

OOP Concepts

📂 Project Structure
RailwayWaitingQueueSystem.cpp
passengers.csv (created after export)
README.md

▶️ How to Run the Project
1. Compile the Program
g++ RailwayWaitingQueueSystem.cpp -o railway

2. Run the Program
./railway

📊 CSV Output Example

After exporting, the CSV will contain:

Ticket,Name,Age,Gender,Contact,Status
1000,Ankit,22,Male,9876543210,Confirmed
1001,Riya,25,Female,9876543211,Waiting
...


You can open passengers.csv in:

MS Excel

Google Sheets

Any text editor

🚀 Future Enhancements (Optional Suggestions)

If you want to upgrade this project later:

Add file-based persistence (saving data after exiting)

Add seat numbers instead of simple ticket allocation

Implement priority queue (e.g., for senior citizens)

Create a GUI version using Qt or JavaFX

Add PNR-style ticket lookup

🤝 Contributing

Pull requests are welcome.
For major changes, please open an issue first to discuss what you would like to improve.
