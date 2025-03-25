package crm.com.CRM.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String external_id;
    private String created_at;
    private String updated_at;
}