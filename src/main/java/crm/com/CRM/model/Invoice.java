package crm.com.CRM.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Invoice {
    private Long id;
    private String external_id;
    private String invoice_number;
    private String status;
    private LocalDateTime due_at;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Client client;
}