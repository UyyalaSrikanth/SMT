package com.SmartTicketSystem.SMT;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SmtRepository extends JpaRepository<TicketDetails, Long> {
   
    List<TicketDetails> findByEmail(String email);  // This retrieves a list of TicketDetails by email
    // Remove the findEmail method
}
