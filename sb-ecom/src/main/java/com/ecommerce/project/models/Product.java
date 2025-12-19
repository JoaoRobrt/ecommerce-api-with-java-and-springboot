package com.ecommerce.project.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String image;
    private String description;
    private Integer quantity;
    private Double price;
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
