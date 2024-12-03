package com.SmartTicketSystem.SMT;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired 
    BCryptPasswordEncoder securityConfig;

    public void Save(String username,
                    String plainpassword,
                    String number){

       String hashPassword=securityConfig.encode(plainpassword);
       Admin admin=new Admin();
       admin.setUsername(username);
       admin.setPassword(hashPassword);
       admin.setNumber(number);
      adminRepository.save(admin);
    }
    
    public boolean login(String username,
                         String enteredpassword){
                          
      Optional<Admin> adminoptional=adminRepository.findById(username);
      
      if (adminoptional.isPresent()) {
         Admin admin=adminoptional.get();
         return securityConfig.matches(enteredpassword,admin.getPassword());
      } else{
        return false;
      }
   
    }
    public Admin existsByid(String username){
      return adminRepository.findByUsername(username);
    }
    public Admin passCheck(String password){
    return adminRepository.findByPassword(password);
    }
   

}
