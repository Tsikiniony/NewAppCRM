package crm.com.CRM.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import crm.com.CRM.service.PaymentService;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.ResponseEntity;

@Controller
public class PaymentController {
    
    @Value("${laravel.api.url}")
    private String laravelApiUrl;
    
    @Autowired
    private PaymentService paymentService;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/payments")
    public String listPayments(Model model) {
        LinkedHashMap<String, Object>[] payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payments";
    }
    @GetMapping("/payment/{externalId}")
    public String paymentDetails(@PathVariable String externalId, Model model) {
        try {
            // Récupérer les détails du paiement depuis l'API Laravel
            ResponseEntity<Object> response = restTemplate.getForEntity(
                laravelApiUrl + "/payments/" + externalId, 
                Object.class
            );
            
            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("payment", response.getBody());
                return "payment-details";
            } else {
                return "redirect:/dashboard";
            }
        } catch (HttpClientErrorException.NotFound e) {
            // Paiement non trouvé
            return "redirect:/dashboard";
        } catch (Exception e) {
            // Autre erreur
            System.out.println("Erreur: " + e.getMessage()); // Log pour le débogage
            model.addAttribute("error", "Une erreur est survenue lors de la récupération du paiement");
            return "redirect:/dashboard";
        }
    }
}