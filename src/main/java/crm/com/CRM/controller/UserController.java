package crm.com.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import crm.com.CRM.service.UserService;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
    
    @GetMapping("/user/{externalId}")
    public String userDetails(@PathVariable String externalId, Model model) {
        model.addAttribute("user", userService.getUserByExternalId(externalId));
        return "user-details";
    }
}