package crm.com.CRM.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedHashMap;

@Service
public class InvoiceService {
    
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    
    @Value("${laravel.api.url}")
    private String laravelApiUrl;
    
    private final RestTemplate restTemplate;

    public InvoiceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public LinkedHashMap<String, Object>[] getAllInvoices() {
        try {
            String url = laravelApiUrl + "/api/invoices";
            logger.info("Fetching invoices from: {}", url);
            LinkedHashMap<String, Object>[] invoices = restTemplate.getForObject(url, LinkedHashMap[].class);
            logger.info("Received {} invoices", invoices != null ? invoices.length : 0);
            if (invoices != null) {
                for (LinkedHashMap<String, Object> invoice : invoices) {
                    logger.info("Invoice: {}", invoice);
                }
            }
            return invoices;
        } catch (Exception e) {
            logger.error("Error fetching invoices: {}", e.getMessage(), e);
            return new LinkedHashMap[0];
        }
    }
    
    public LinkedHashMap<String, Object> getInvoiceByExternalId(String externalId) {
        try {
            String url = laravelApiUrl + "/api/invoices/" + externalId;
            logger.info("Fetching invoice from: {}", url);
            return restTemplate.getForObject(url, LinkedHashMap.class);
        } catch (HttpClientErrorException e) {
            logger.error("Invoice not found: {}", externalId);
            throw new RuntimeException("Facture non trouvée avec l'ID: " + externalId);
        } catch (Exception e) {
            logger.error("Error fetching invoice: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la récupération de la facture: " + e.getMessage());
        }
    }
}