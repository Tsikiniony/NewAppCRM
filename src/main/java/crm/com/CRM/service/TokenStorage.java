package crm.com.CRM.service;

import org.springframework.stereotype.Component;

@Component
public class TokenStorage {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}