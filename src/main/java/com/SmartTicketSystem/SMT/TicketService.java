package com.SmartTicketSystem.SMT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // Save a new ticket
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);  // Save the ticket to the existing database
    }

    // Find all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Find a ticket by ID
    public Ticket findTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    // Delete a ticket by ID
    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }
}
