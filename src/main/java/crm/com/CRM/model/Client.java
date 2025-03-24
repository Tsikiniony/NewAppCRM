package crm.com.CRM.model;

import lombok.Data;

@Data
public class Client {
    private Long id;
    private String external_id;
    private String name;
    private String company_name;
    private String vat;
    private String email;
    private String address;
    private String zipcode;
    private String city;
    private String primary_number;
    private String secondary_number;
    private String industry_id;
    private String company_type;
    private String created_at;
    private String updated_at;

    
}