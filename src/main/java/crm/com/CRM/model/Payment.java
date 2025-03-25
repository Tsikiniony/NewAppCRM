package crm.com.CRM.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class Payment {
    private Long id;
    private String external_id;
    private BigDecimal amount;
    private String description;
    private String payment_source;
    private LocalDateTime payment_date;
    private String integration_payment_id;
    private String integration_type;
    private Long invoice_id;
    private LocalDateTime deleted_at;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}