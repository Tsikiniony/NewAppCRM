package crm.com.CRM.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import crm.com.CRM.dto.DiscountRateDTO;

@Service
public class DiscountRateService {
    private final RestTemplate restTemplate;
    private final TokenStorage tokenStorage;
    private final String phpApiBaseUrl;

    @Autowired
    public DiscountRateService(RestTemplate restTemplate, TokenStorage tokenStorage, @Value("${laravel.api.url}") String phpApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.tokenStorage = tokenStorage;
        this.phpApiBaseUrl = phpApiBaseUrl;
    }

    public List<DiscountRateDTO> getAllDiscountRates() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenStorage.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<DiscountRateDTO>> response = restTemplate.exchange(
            phpApiBaseUrl + "/discount-rates",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<DiscountRateDTO>>() {}
        );
        return response.getBody();
    }

    public DiscountRateDTO createDiscountRate(DiscountRateDTO discountRate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenStorage.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);  

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("rate", discountRate.getRate());
        requestBody.put("description", discountRate.getDescription());
        requestBody.put("is_active", discountRate.isActive());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<DiscountRateDTO> response = restTemplate.exchange(
            phpApiBaseUrl + "/discount-rates",
            HttpMethod.POST,
            entity,
            DiscountRateDTO.class
        );
        return response.getBody();
    }

    public BigDecimal getActiveDiscountRate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenStorage.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
            phpApiBaseUrl + "/discount-rates/active",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Map<String, String>>() {}
        );
        
        Map<String, String> body = response.getBody();
        if (body != null && body.containsKey("rate")) {
            String rateStr = body.get("rate");
            if (rateStr != null) {
                try {
                    return new BigDecimal(rateStr);
                } catch (NumberFormatException e) {
                    return BigDecimal.ZERO;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    public DiscountRateDTO toogle(Long id, DiscountRateDTO discountRate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenStorage.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);  

        HttpEntity<DiscountRateDTO> entity = new HttpEntity<>(discountRate, headers);

        ResponseEntity<DiscountRateDTO> response = restTemplate.exchange(
            phpApiBaseUrl + "/discount-rates/toggle/" + id,
            HttpMethod.PUT,
            entity,
            DiscountRateDTO.class
        );
        return response.getBody();
    }
}