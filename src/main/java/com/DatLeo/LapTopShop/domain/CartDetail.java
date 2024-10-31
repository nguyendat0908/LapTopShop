package com.DatLeo.LapTopShop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "cart_details")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long quantity;
    double price;

    // CART_DETAILS many -> to one CART
    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    // CART_DETAILS many -> to one PRODUCT
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
