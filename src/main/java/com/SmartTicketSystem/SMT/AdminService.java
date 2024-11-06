package com.SmartTicketSystem.SMT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    

    public void Save(Admin admin){
      adminRepository.save(admin);
    }
    public Admin existsByid(String username){
      return adminRepository.findByUsername(username);
    }
    public Admin passCheck(String password){
    return adminRepository.findByPassword(password);
    }
   
}
