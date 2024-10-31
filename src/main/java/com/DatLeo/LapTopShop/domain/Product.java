package com.DatLeo.LapTopShop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name = "products")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    double price;
    String image;

    @Column(columnDefinition = "TEXT")
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
