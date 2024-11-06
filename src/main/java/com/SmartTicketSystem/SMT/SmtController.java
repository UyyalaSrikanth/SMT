package com.SmartTicketSystem.SMT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;


@Controller
@RequestMapping("/api/TicketDetails")
public class SmtController {
   
    @Autowired
    SmtService smtService;

    @Autowired
    SmtRepository smtRepository;


    @PostMapping("/Price")
    public String saveTicket(
            @RequestParam String Destination,
            @RequestParam String TicketType,
            @RequestParam String Email,
            Model model) {
        
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setDestination(Destination);
        ticketDetails.setTicketType(TicketType);
        ticketDetails.setEmail(Email);

        int distance = 0;
        switch (Destination) {
            case "MNTY-PDPL":
                distance = 20;
                break;
            case "KLV-PDPL":
                distance = 35;
                break;
            case "GNDRM-PDPL":
                distance = 50;
                break;
            case "RGPR-PDPL":
                distance = 70;
                break;
            default:
                distance = 0;
                break;
        }
        // Calculate the ticket price
        ticketDetails.setDistance(distance);
        Integer calculatedPrice = smtService.Pricing(ticketDetails);
        ticketDetails.setPrice(calculatedPrice);
        smtService.saveAndSendMail(ticketDetails);
        model.addAttribute("message", "Ticket booked successfully with ID: " + ticketDetails.getId());
        model.addAttribute("EmailMessege","Receipt has sent to your email : "+ticketDetails.getEmail());
        
        return "success"; 
    }

    // @GetMapping
    // public List<TicketDetails> getallList() {
    //     return smtService.getALlDetails();
    // }
    @GetMapping("/find")
    public String getTicketDetails(@RequestParam("id") Long id, Model model) {
        Optional<TicketDetails> ticketDetails = smtRepository.findById(id);
        
        if (ticketDetails.isPresent()) {
            model.addAttribute("ticketDetails", ticketDetails.get());
            model.addAttribute("message", "Ticket found: ID " + ticketDetails.get().getId());
            System.out.println("Ticket found: " + ticketDetails.get());
        } else {
            model.addAttribute("errorMessage", "No ticket found with ID: " + id);
            System.out.println("No ticket found for ID: " + id);
        }
        return "index";
    }
    
    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }
    @GetMapping("/userlogin")
    public String userLoginPage() {
        return "userslogin";
    }
    @GetMapping("/Home")
    public String HomePage(){
        return "index";
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletebyudEntity(@PathVariable long id){
        try {
            smtService.deleteTicket(id);
            return ResponseEntity.ok(id+" id Deleted successfully");
        } catch (EmptyResultDataAccessException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
}
