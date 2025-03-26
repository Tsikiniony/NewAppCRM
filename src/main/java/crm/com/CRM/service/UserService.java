package crm.com.CRM.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import crm.com.CRM.model.User;

@Service
public class UserService {
    
    @Value("${laravel.api.url}")
    private String laravelApiUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public User[] getAllUsers() {
        return restTemplate.getForObject(laravelApiUrl + "/users", User[].class);
    }
    
    public User getUserByExternalId(String externalId) {
        return restTemplate.getForObject(laravelApiUrl + "/users/" + externalId, User.class);
    }
}