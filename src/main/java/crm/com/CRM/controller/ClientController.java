package crm.com.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import crm.com.CRM.service.ClientService;

@Controller
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @GetMapping("/clients")
    public String listClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "clients";
    }
    
    @GetMapping("/client/{externalId}")
    public String clientDetails(@PathVariable String externalId, Model model) {
        model.addAttribute("client", clientService.getClientByExternalId(externalId));
        return "client-details";
    }
}