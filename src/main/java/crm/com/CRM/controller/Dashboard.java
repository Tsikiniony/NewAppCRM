package crm.com.CRM.controller;

import crm.com.CRM.service.TokenStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Dashboard {
    @Value("${laravel.api.url}")
    private String laravelApiUrl;

    private final RestTemplate restTemplate;

    public Dashboard(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Map<String, Object>> clients = restTemplate.getForObject(laravelApiUrl + "/clients", List.class);
        List<Map<String, Object>> projects = restTemplate.getForObject(laravelApiUrl + "/projects", List.class);
        List<Map<String, Object>> tasks = restTemplate.getForObject(laravelApiUrl + "/tasks", List.class);
        List<Map<String, Object>> offers = restTemplate.getForObject(laravelApiUrl + "/offers", List.class);
        List<Map<String, Object>> invoices = restTemplate.getForObject(laravelApiUrl + "/invoices", List.class);
        List<Map<String, Object>> payments = restTemplate.getForObject(laravelApiUrl + "/payments", List.class);

        model.addAttribute("clients", clients);
        model.addAttribute("projects", projects);
        model.addAttribute("tasks", tasks);
        model.addAttribute("offers", offers);
        model.addAttribute("invoices", invoices);
        model.addAttribute("payments", payments);

        model.addAttribute("totalClients", clients != null ? clients.size() : 0);
        model.addAttribute("totalProjects", projects != null ? projects.size() : 0);
        model.addAttribute("totalTasks", tasks != null ? tasks.size() : 0);
        model.addAttribute("totalOffers", offers != null ? offers.size() : 0);
        model.addAttribute("totalInvoices", invoices != null ? invoices.size() : 0);
        model.addAttribute("totalPayments", payments != null ? payments.size() : 0);
        return "dashboard";
    }
}
