package crm.com.CRM.controller;

import crm.com.CRM.service.TokenStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Value("${laravel.api.url}")
    private String laravelApiUrl;

    private final RestTemplate restTemplate;
    private final TokenStorage tokenStorage;

    public LoginController(RestTemplate restTemplate, TokenStorage tokenStorage) {
        this.restTemplate = restTemplate;
        this.tokenStorage = tokenStorage;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password, 
                       Model model) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("password", password);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                laravelApiUrl + "/login",
                request,
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                if ("success".equals(responseBody.get("status"))) {
                    // Stocker le token
                    String token = (String) responseBody.get("token");
                    tokenStorage.setToken(token);
                    
                    return "redirect:/dashboard";
                }
            }
            
            model.addAttribute("error", "Invalid credentials");
            return "login";
            
        } catch (Exception e) {
            model.addAttribute("error", "Erreur de connexion: " + e.getMessage());
            return "login";
        }
    }
}