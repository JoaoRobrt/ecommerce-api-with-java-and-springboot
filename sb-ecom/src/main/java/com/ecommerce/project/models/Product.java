package com.ecommerce.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Product name is required.")
    @Size(min = 4, message = "Product name must have at least 4 characters.")
    private String productName;

    @NotBlank(message = "Product image is required.")
    private String image;
    private String description;
    private Integer quantity;

    @NotNull(message = "Product price value is required.")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Product discount value is required.")
    @PositiveOrZero(message = "Discount can't be a negative number.")
    private Double discount;
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void updateSpecialPrice() {
        if (this.price != null && this.discount != null) {
            this.specialPrice = this.price - (this.price * (this.discount * 0.01));
        }
    }

    @PrePersist
    @PreUpdate
    private void autoCalculate() {
        updateSpecialPrice();
    }
}
