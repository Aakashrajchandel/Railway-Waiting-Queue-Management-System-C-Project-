#include <iostream>
#include <vector>
#include <queue>
#include <fstream>
#include <string>
using namespace std;

class Passenger {
public:
    int ticketNo;
    string name;
    int age;
    string gender;
    string contact;
    string status; // Confirmed / Waiting

    Passenger(int t, string n, int a, string g, string c, string s) {
        ticketNo = t;
        name = n;
        age = a;
        gender = g;
        contact = c;
        status = s;
    }

    void display() const {
        cout << ticketNo << "\t" << name << "\t" << age << "\t"
             << gender << "\t" << contact << "\t" << status << endl;
    }
};

const int MAX_SEATS = 5;
vector<Passenger> confirmedList;
queue<Passenger> waitingQueue;
int ticketCounter = 1000;

// ------------------------------------------------------------
// BOOK SEAT
// ------------------------------------------------------------
void bookSeat() {
    string name, gender, contact;
    int age;

    cin.ignore();
    cout << "Enter Name: ";
    getline(cin, name);

    cout << "Enter Age: ";
    cin >> age;
    cin.ignore();

    cout << "Enter Gender: ";
    getline(cin, gender);

    cout << "Enter Contact Number: ";
    getline(cin, contact);

    if (confirmedList.size() < MAX_SEATS) {
        Passenger p(ticketCounter++, name, age, gender, contact, "Confirmed");
        confirmedList.push_back(p);
        cout << "Seat booked successfully! Ticket: " << p.ticketNo << endl;
    } else {
        Passenger p(ticketCounter++, name, age, gender, contact, "Waiting");
        waitingQueue.push(p);
        cout << "All seats full. Added to waiting list. Ticket: " << p.ticketNo << endl;
    }
}

// ------------------------------------------------------------
// CANCEL SEAT
// ------------------------------------------------------------
void cancelSeat() {
    if (confirmedList.empty()) {
        cout << "No confirmed bookings to cancel.\n";
        return;
    }

    int ticket;
    cout << "Enter Ticket Number to cancel: ";
    cin >> ticket;

    bool found = false;
    for (int i = 0; i < confirmedList.size(); i++) {
        if (confirmedList[i].ticketNo == ticket) {
            found = true;
            confirmedList.erase(confirmedList.begin() + i);
            cout << "Booking cancelled for Ticket: " << ticket << endl;

            if (!waitingQueue.empty()) {
                Passenger next = waitingQueue.front();
                waitingQueue.pop();
                next.status = "Confirmed";
                confirmedList.push_back(next);
                cout << "Seat allocated to waiting passenger: "
                     << next.name << " (Ticket " << next.ticketNo << ")\n";
            }

            break;
        }
    }

    if (!found)
        cout << "Ticket not found in confirmed list.\n";
}

// ------------------------------------------------------------
// DISPLAY CONFIRMED LIST
// ------------------------------------------------------------
void displayConfirmed() {
    cout << "\n--- Confirmed Passengers ---\n";
    if (confirmedList.empty()) {
        cout << "No confirmed passengers.\n";
        return;
    }

    cout << "Ticket\tName\tAge\tGender\tContact\tStatus\n";
    for (const auto& p : confirmedList)
        p.display();
}

// ------------------------------------------------------------
// DISPLAY WAITING LIST
// ------------------------------------------------------------
void displayWaiting() {
    cout << "\n--- Waiting List ---\n";
    if (waitingQueue.empty()) {
        cout << "No passengers in waiting list.\n";
        return;
    }

    queue<Passenger> temp = waitingQueue;

    cout << "Ticket\tName\tAge\tGender\tContact\tStatus\n";
    while (!temp.empty()) {
        temp.front().display();
        temp.pop();
    }
}

// ------------------------------------------------------------
// SEARCH PASSENGER
// ------------------------------------------------------------
void searchPassenger() {
    int ticket;
    cout << "Enter Ticket Number to search: ";
    cin >> ticket;
