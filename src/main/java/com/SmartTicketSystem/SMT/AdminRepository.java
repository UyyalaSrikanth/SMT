package com.SmartTicketSystem.SMT;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AdminRepository extends JpaRepository<Admin,String> {
   Admin findByUsername(String username);
   Admin findByPassword(String password);
    
} 