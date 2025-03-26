package crm.com.CRM.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class DiscountRateDTO {
    private Long id;

    @NotNull(message = "Le taux est obligatoire")
    @DecimalMin(value = "0.0", message = "Le taux doit être supérieur ou égal à 0")
    @DecimalMax(value = "100.0", message = "Le taux doit être inférieur ou égal à 100")
    private BigDecimal rate;

    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    private String description;

    @JsonProperty("is_active")  
    private boolean active;

    @JsonProperty("created_at") 
    private LocalDateTime createdAt;
    @JsonProperty("updated_at") 
    private LocalDateTime updatedAt;
    @JsonProperty("deleted_at") 
    private LocalDateTime deletedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    // Constructeur
    public DiscountRateDTO() {}

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}