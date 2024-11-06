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
      Admin admin = new Admin();
      admin.setUsername(username);
      admin.setNumber(number);
      admin.setPassword(password);
      adminService.Save(admin);
      model.addAttribute("message", "Successfully created Account with " + admin.getUsername());
      return "login";
    } else {
      model.addAttribute("error", "email already exists !");
      return "login";
    }
  }

  @GetMapping("/login")
  public String loginPage(Model model) {
    return "login";
  }

  @PostMapping("/login")
  public String LoginPage(@RequestParam String username,
      @RequestParam String password, Model model) {

    Admin userCheck = adminService.existsByid(username);
    if (userCheck!=null) {

      if (userCheck.getPassword().equals(password)) {
        model.addAttribute("LoginMessage", "Login Successfully");
        List<TicketDetails> ticketDetails = smtService.getAllDetails();
        model.addAttribute("ticketDetails", ticketDetails);
        return "tickets";
      } else {
        model.addAttribute("wrongpassword", "Entered wrong password");
        return "login";
      }
    } else {
      model.addAttribute("errorusername", " account does not exists");
      return "login";
    }
  }
 
  @PostMapping("/userlogin")
  public String UserLogin(@RequestParam String username,
                          @RequestParam String password, Model model ){
                            Admin userCheck = adminService.existsByid(username);
                            if (userCheck!=null) {
                        
                              if (userCheck.getPassword().equals(password)) {
                                model.addAttribute("LoginMessage", "Login Successfully");
                                List<TicketDetails> ticketDetails = smtService.getAllTicketsDetails(username);
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