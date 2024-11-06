package com.SmartTicketSystem.SMT;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BookingDetails")  // Mapping to your table name
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int noTickets;
    private int toPrice;
    private String gmail;
    private String destination;
    private String date;
    private String time;
    private String ticketType;

    // Constructors, getters, setters
    public Ticket() {}

    public Ticket(int noTickets, int toPrice, String gmail, String destination, String date, String time, String ticketType) {
        this.noTickets = noTickets;
        this.toPrice = toPrice;
        this.gmail = gmail;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.ticketType = ticketType; // Assign ticket type
    }

    public Long getId() { return id; }

    public int getNoTickets() { return noTickets; }

    public int getToPrice() { return toPrice; }

    public String getGmail() { return gmail; }

    public String getDestination() { return destination; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public void setId(Long id) { this.id = id; }

    public void setNoTickets(int noTickets) { this.noTickets = noTickets; }

    public void setToPrice(int toPrice) { this.toPrice = toPrice; }

    public void setGmail(String gmail) { this.gmail = gmail; }

    public void setDestination(String destination) { this.destination = destination; }

    public void setDate(String date) { this.date = date; }

    public void setTime(String time) { this.time = time; }
}
