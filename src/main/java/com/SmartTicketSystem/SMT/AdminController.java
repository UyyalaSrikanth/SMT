package com.SmartTicketSystem.SMT;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/api/Admin")
public class AdminController {
  @Autowired
  AdminService adminService;
  @Autowired
  AdminRepository adminRepository;
 @Autowired 
 SmtService smtService;

  @PostMapping("/save")
  public String save(@RequestParam String username,
      @RequestParam String number,
      @RequestParam String password, Model model) {
        
    Admin exists = adminService.existsByid(username);
    if (exists == null) {
      adminService.Save(username,password,number);
      model.addAttribute("message", "Successfully created Account ");
      return "userslogin";
    } else {
      model.addAttribute("error", "email already exists !");
      return "userslogin";
    }
  }

  @GetMapping("/login")
  public String loginPage(Model model) {
    return "userslogin";
  }

  @PostMapping("/userslogin")
  public String LoginPage(@RequestParam String username,
                          @RequestParam String password,
                          Model model) {
    boolean results =adminService.login(username, password);
    Admin userCheck = adminService.existsByid(username);

    if (userCheck!=null) {
      if (results==true) {
        model.addAttribute("LoginMessage", "Login Successfully");
        List<TicketDetails> ticketDetails = smtService.getAllTicketsDetailsbyid(username);
        model.addAttribute("ticketDetails", ticketDetails);
       return "tickets";

      } else {
        model.addAttribute("wrongpassword", "Entered wrong password");
        return "userslogin";
      }
    } else {
      model.addAttribute("errorusername", " account does not exists");
      return "userslogin";
    }
  }

  @PostMapping("/adminlogin")
  public String adminLogin(@RequestParam String username,
                          @RequestParam String password,Model model) {
     
       String adminusername="usrikanthuyyala@gmail.com";
       boolean results =adminService.login(username,password);
       if (adminusername.equals(username)) {
        if (results==true) {
          model.addAttribute("LoginMessage", "login Successfully");
          List<TicketDetails> ticketDetails=smtService.getAllDetails();
         
          if (ticketDetails.isEmpty()) {
            model.addAttribute("noticketsfound", "no bookings with your id ");
            return"tickets";
          } else{
          model.addAttribute("ticketDetails", ticketDetails);
          return "tickets";
          }
        }else{
         model.addAttribute("wrongpassword", "Entered wrong password");
         return"login";
        }
       } else {
        model.addAttribute("errorusername", "username does not exist");
        return "login";
       }
      
  }
  
 
  
  @PostMapping("/delete")
public String deleteById(@RequestParam long id, RedirectAttributes redirectAttributes) {
     smtService.deleteTicket(id);
   redirectAttributes.addFlashAttribute("DeleteMessage","id deleted successfully");
   List<TicketDetails> ticketDetails = smtService.getAllDetails();
   redirectAttributes.addAttribute("ticketDetails", ticketDetails);
      return "redirect:/api/Admin/tickets";
}

@GetMapping("/tickets")
public String showTickets(Model model) {
    List<TicketDetails> ticketDetails = smtService.getAllDetails();
    model.addAttribute("ticketDetails", ticketDetails);
    return "tickets";
}


  @GetMapping("/Home")
  public String index(Model model) {
    return "index";
  }



}