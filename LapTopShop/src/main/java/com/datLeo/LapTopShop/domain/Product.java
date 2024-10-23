package com.datLeo.LapTopShop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    long id;
    String name;
    double price;
    String image;
    String detailDesc;
    String shortDesc;
    long quantity;
    long sold;
    String factory;
    String target;

    // PRODUCT one -> to many CART_DETAIL
    @OneToMany(mappedBy = "product")
    List<CartDetail> cartDetails;

    // PRODUCT one -> to many ORDER_DETAIL
    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;
    
}
