package com.vmzone.demo.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;

    public ShoppingCartItem(Product product, User user, int quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }
}
