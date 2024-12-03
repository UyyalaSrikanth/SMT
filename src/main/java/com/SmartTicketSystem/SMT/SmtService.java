package com.SmartTicketSystem.SMT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SmtService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SmtRepository smtRepository; // Ensure this is imported correctly

    private List<String> Stops;
    private Integer KMPrice = 2;
    private String ticketType = "Handicapped";

    // Constructor to initialize the Stops list
    public SmtService() {
        Stops = new ArrayList<>();
        Stops.add("MNTY-PDPL");
        Stops.add("KLV-PDPL");
        Stops.add("GNDRM-PDPL");
        Stops.add("RGVPR-PDPL");
    }

    public Integer getKMPrice(Integer KMPrice) {
        return KMPrice;
    }

    public Integer Pricing(TicketDetails ticketDetails) {
        Integer BasePrice = KMPrice * ticketDetails.getDistance();
        if (ticketType.equalsIgnoreCase(ticketDetails.getTicketType())) {
            return BasePrice / 2;
        } else {
            return BasePrice;
        }
    }

    public void save(TicketDetails ticketDetails) {
        smtRepository.save(ticketDetails);
    }

    public List<String> getAllStops() {
        return Stops;
    }

    public List<TicketDetails> getAllDetails() {
        return smtRepository.findAll();
    }
    public List<TicketDetails> getAllTicketsDetailsbyid(String email) {
        return smtRepository.findByEmail(email);
    }

    public TicketDetails findById(Long id) {
        return smtRepository.findById(id).orElse(null);
    }
   
    public void deleteTicket(long id) {
        smtRepository.deleteById(id);
    }

    public void sendMail(String from, String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true for multipart

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true for HTML

            mailSender.send(mimeMessage);
            System.out.println("Mail sent successfully to " + to);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions as needed
        }
    }

    public void saveAndSendMail(TicketDetails ticketDetails) {
        save(ticketDetails);

        String from = "uyyalasrikanth01@gmail.com";
        String to = ticketDetails.getEmail();
        String subject = "Ticket Booking Confirmation";

        String body = "<html>"
                + "<body style='font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4;'>"
                + "<div style='max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);'>"
                + "<h2 style='color: #333;'>Smart Ticket System Receipt</h2>"
                + "<p>Thank you for using our services. Here are your booking details:</p>"
                + "<table style='width: 100%; border-collapse: collapse;'>"
                + "<tr><td><strong>Booking Id:</strong></td><td>" + ticketDetails.getId() + "</td></tr>"
                + "<tr><td><strong>Destination:</strong></td><td>" + ticketDetails.getDestination() + "</td></tr>"
                + "<tr><td><strong>Ticket Type:</strong></td><td>" + ticketDetails.getTicketType() + "</td></tr>"
                + "<tr><td><strong>Total Price:</strong></td><td>$" + ticketDetails.getPrice() + "</td></tr>"
                + "<tr><td><strong>Date:</strong></td><td>" + LocalDate.now() + "</td></tr>"
                + "<tr><td><strong>Time:</strong></td><td>" + LocalTime.now() + "</td></tr>"
                + "</table>"
                + "<p style='margin-top: 20px;'>We hope you have a pleasant journey!</p>"
                + "<p>Best regards,<br/>Smart Ticket System Team</p>"
                + "</div>"
                + "</body>"
                + "</html>";

        sendMail(from, to, subject, body);
    }
}
