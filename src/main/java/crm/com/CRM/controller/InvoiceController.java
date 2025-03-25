package crm.com.CRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import crm.com.CRM.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedHashMap;

@Controller
public class InvoiceController {
    
    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    
    @Autowired
    private InvoiceService invoiceService;
    
    @GetMapping("/invoices")
    public String listInvoices(Model model) {
        LinkedHashMap<String, Object>[] invoices = invoiceService.getAllInvoices();
        logger.info("Retrieved {} invoices", invoices.length);
        model.addAttribute("invoices", invoices);
        return "invoices";
    }
    
    @GetMapping("/invoice/{externalId}")
    public String invoiceDetails(@PathVariable String externalId, Model model) {
        LinkedHashMap<String, Object> invoice = invoiceService.getInvoiceByExternalId(externalId);
        model.addAttribute("invoice", invoice);
        return "invoice-details";
    }
}