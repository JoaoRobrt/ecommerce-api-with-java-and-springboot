package com.ecommerce.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(unique = true)
    @NotBlank(message = "Product name is required.")
    @Size(min = 4, message = "Product name must have at least 4 characters.")
    private String productName;
    private String image;
    private String description;
    private Integer quantity;

    @NotNull(message = "Product price value is required.")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Product discount value is required.")
    @PositiveOrZero(message = "Discount can't be a negative number.")
    private BigDecimal discount;
    private BigDecimal specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    public void updateSpecialPrice() {
        if (price == null || discount == null) {
            this.specialPrice = price;
            return;
        }

        BigDecimal discountPercentage = discount
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal discountValue = price.multiply(discountPercentage);

        this.specialPrice = price.subtract(discountValue);
    }

    @PrePersist
    @PreUpdate
    private void autoCalculate() {
        updateSpecialPrice();
    }
}
