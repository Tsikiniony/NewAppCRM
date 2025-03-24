package crm.com.CRM.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import crm.com.CRM.model.Client;

@Service
public class ClientService {
    
    @Value("${laravel.api.url}")
    private String laravelApiUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public Client[] getAllClients() {
        return restTemplate.getForObject(laravelApiUrl + "/clients", Client[].class);
    }
    
    public Client getClientByExternalId(String externalId) {
        return restTemplate.getForObject(laravelApiUrl + "/clients/" + externalId, Client.class);
    }
}