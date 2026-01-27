package com.ecommerce.project.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
@ToString
// TODO: Refatorar CartItem para snapshot de produto (price, name, discount)
// Atualmente acoplado a Product
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private BigDecimal discount;
    private BigDecimal productPrice;

    public CartItem(Product product, Cart cart, Integer quantity, BigDecimal discount, BigDecimal price) {
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
        this.discount = discount;
        this.productPrice = price;

    }

    public BigDecimal getSubtotal() {
        return productPrice
                .subtract(discount != null ? discount : BigDecimal.ZERO)
                .multiply(BigDecimal.valueOf(quantity));
    }
}
