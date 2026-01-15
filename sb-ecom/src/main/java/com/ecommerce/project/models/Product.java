package com.ecommerce.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
    private Double price;

    @NotNull(message = "Product discount value is required.")
    @PositiveOrZero(message = "Discount can't be a negative number.")
    private Double discount;
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CartItem> products = new ArrayList<>();

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
