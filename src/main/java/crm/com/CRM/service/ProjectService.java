package crm.com.CRM.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import crm.com.CRM.model.Project;

@Service
public class ProjectService {
    
    @Value("${laravel.api.url}")
    private String laravelApiUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public Project[] getAllProjects() {
        return restTemplate.getForObject(laravelApiUrl + "/projects", Project[].class);
    }
    
    public Project getProjectByExternalId(String externalId) {
        return restTemplate.getForObject(laravelApiUrl + "/projects/" + externalId, Project.class);
    }
}