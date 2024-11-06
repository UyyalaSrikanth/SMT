package com.SmartTicketSystem.SMT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {

    @GetMapping("/tickets")
    public String showTickets() {
        return "tickets"; // Show the ticket booking page
    }

    @PostMapping("/bookTicket")
    public String bookTicket(
            @RequestParam("destination") String destination,
            @RequestParam("numTickets") int numTickets,
            @RequestParam("ticketType") String ticketType,
            @RequestParam("email") String email, 
            Model model) {

        // Calculate the total price based on the ticket type and number of tickets
        int price = calculatePrice(ticketType, numTickets); 

        // Add details to the model to display on the confirmation page
        model.addAttribute("message", "Ticket booked successfully!");
        model.addAttribute("destination", destination);
        model.addAttribute("numTickets", numTickets);
        model.addAttribute("ticketType", ticketType);
        model.addAttribute("email", email);
        model.addAttribute("totalPrice", price);

        return "ticketConfirmation"; // Redirect to a new confirmation page
    }

    private int calculatePrice(String ticketType, int numTickets) {
        int pricePerTicket = 100; // Base price
        if (ticketType.equals("handicapped")) {
            pricePerTicket = 80; // Discount for handicapped
        } else if (ticketType.equals("baby")) {
            pricePerTicket = 50; // Further discount for babies
        }
        return pricePerTicket * numTickets;
    }
}
