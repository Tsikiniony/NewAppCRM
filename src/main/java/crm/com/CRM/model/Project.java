package crm.com.CRM.model;

import lombok.Data;

@Data
public class Project {
    private Long id;
    private String external_id;
    private String title;
    private String description;
    private String status;
    private String deadline;
    private String created_at;
    private String updated_at;
    private Client client;
    private User user;
}