package crm.com.CRM.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedHashMap;

@Service
public class PaymentService {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    
    @Value("${laravel.api.url}")
    private String laravelApiUrl;
    
    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public LinkedHashMap<String, Object>[] getAllPayments() {
        try {
            String url = laravelApiUrl + "/api/payments";
            logger.info("Fetching payments from: {}", url);
            LinkedHashMap<String, Object>[] payments = restTemplate.getForObject(url, LinkedHashMap[].class);
            logger.info("Received {} payments", payments != null ? payments.length : 0);
            return payments;
        } catch (Exception e) {
            logger.error("Error fetching payments: {}", e.getMessage(), e);
            return new LinkedHashMap[0];
        }
    }
    
    public LinkedHashMap<String, Object> getPaymentByExternalId(String externalId) {
        try {
            String url = laravelApiUrl + "/api/payments/" + externalId;
            logger.info("Fetching payment from: {}", url);
            return restTemplate.getForObject(url, LinkedHashMap.class);
        } catch (HttpClientErrorException e) {
            logger.error("Payment not found: {}", externalId);
            throw new RuntimeException("Paiement non trouvé avec l'ID: " + externalId);
        }
    }
}